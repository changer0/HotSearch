package com.qq.reader.provider.loader;

import com.qq.reader.provider.DataProvider;
import com.qq.reader.provider.cache.CacheController;
import com.qq.reader.provider.cache.CacheMode;
import com.qq.reader.provider.log.Logger;
import com.qq.reader.provider.task.TaskHandler;

import java.io.IOException;
import java.io.InputStream;

import io.reactivex.ObservableEmitter;

/**
 * @author zhanglulu on 2019/3/2.
 * for 用于分发网络/缓存
 */
@SuppressWarnings("rawtypes")
public class SimpleLoadDispatcherTask implements Runnable, SimpleLoadDiskDataTask.LoadDataListener
        , SimpleLoadDiskDataTask.LoadExpiredDataListener, SimpleLoadNetDataTask.LoadDataListener {
    private static final String TAG = "ReaderDataTask";

    /**
     * 数据加载类
     */
    private final DataProvider provider;

    /**
     * 结果发射器
     */
    private ObservableEmitter<DataProvider> mEmitter;

    /**
     * 缓存流
     */
    private InputStream cacheInputStream;

    /**
     * 缓存模式
     */
    private int cacheMode;

    /**
     * 一次请求参数
     */
    private OnceRequestParams onceRequestParams;

    /**
     * 判断当前快照是否有缓存数据
     * @return
     */
    private boolean hasCache() {
        if (cacheInputStream == null) {
            return false;
        }

        try {
            if (cacheInputStream.available() <= 0) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void setEmitter(ObservableEmitter<DataProvider> mEmitter) {
        this.mEmitter = mEmitter;
    }

    public int getCacheMode() {
        return cacheMode;
    }

    public SimpleLoadDispatcherTask(DataProvider provider, int cacheMode, OnceRequestParams onceRequestParams) {
        this.provider = provider;
        this.cacheMode = cacheMode;
        this.onceRequestParams = onceRequestParams;
    }

    @Override
    public void run() {
        cacheInputStream = CacheController.getInstance().get(onceRequestParams.getCacheKey());
        if (hasCache()){
            //优先使用缓存 和 使用缓存，但不使用过期数据
            if (getCacheMode() == CacheMode.CACHE_MODE_USE_CACHE_PRIORITY || getCacheMode() == CacheMode.CACHE_MODE_USE_CACHE_NOT_EXPIRED) {
                tryLoadDiskData(false);
            } else {
                tryLoadNetData();
            }
        } else {
            tryLoadNetData();
        }
    }

    /**
     * 网络中拉取
     */
    private void tryLoadNetData() {
        if (provider == null) {
            return;
        }
        SimpleLoadNetDataTask netTask = new SimpleLoadNetDataTask(provider, onceRequestParams);
        netTask.setLoadDataListener(this);
        TaskHandler.getInstance().enqueue(netTask);
    }

    /**
     * 从本地获取
     * [注] 当且仅当 网络加载失败或者网络数据解析失败时调用，否则会持续加载过期数据
     */
    private void tryLoadDiskData(boolean isLoadExpired) {
        if (cacheInputStream == null || provider == null) {
            return;
        }
        SimpleLoadDiskDataTask diskTask = new SimpleLoadDiskDataTask(provider,
                cacheInputStream, isLoadExpired, onceRequestParams);
        diskTask.setLoadDataListener(this);
        diskTask.setLoadExpiredDataListener(this);
        TaskHandler.getInstance().enqueue(diskTask);
    }


    /**
     * 成功通知
     * @param isCache
     */
    private void notifyLoadPageDataSuccess(boolean isCache) {
        if (mEmitter != null) {
            //并发请求直接发送出去
            provider.setCache(isCache);
            mEmitter.onNext(provider);
            mEmitter.onComplete();
        }
    }


    /**
     * 失败通知
     * @param e
     */
    private void notifyLoadPageDataFailed(Throwable e) {
        try {
            if (mEmitter != null) {
                mEmitter.onError(e);
                mEmitter.onComplete();
            }
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.e(TAG, "LoadNativePageDataTask");
        }
    }

    //----------------------------------------------------------------------------------------------
    // LoadDiskDataTask.LoadDataListener 缓存获取回调
    @Override
    public void onLoadDiskDataSuccess(DataProvider provider) {
        synchronized (this) {
            notifyLoadPageDataSuccess(true);
        }
    }

    @Override
    public void onLoadDiskDataFailed(DataProvider provider) {
        synchronized (this) {
            tryLoadNetData();
        }
    }
    //----------------------------------------------------------------------------------------------
    // LoadDiskDataTask.LoadExpiredDataListener 过期缓存获取回调
    @Override
    public void onLoadDiskExpiredDataSuccess(DataProvider provider) {
        synchronized (this) {
            notifyLoadPageDataSuccess(true);
        }
    }

    @Override
    public void onLoadDiskExpiredDataFailed(DataProvider provider) {
        String error = "本地过期数据加载失败 !!!! ";
        synchronized (this) {
            notifyLoadPageDataFailed(new RuntimeException(error));
        }
        Logger.e(TAG, "onLoadDiskExpiredDataFailed: " + error );
    }

    //----------------------------------------------------------------------------------------------
    // ReaderJSONNetTaskListener 网络获取回调
    @Override
    public void onLoadNetDataSuccess(DataProvider provider) {
        synchronized (this) {
            notifyLoadPageDataSuccess(false);
        }
    }

    @Override
    public void onLoadNetDataFailed(Throwable throwable) {
        synchronized (this) {
            handleNetDataError(throwable);
        }
    }

    /**
     *  处理网络数据异常
     */
    private void handleNetDataError(Throwable e) {
        if (hasCache()
                //针对两种模式：只有当网络数据失败时使用缓存 和 优先使用缓存
                && (getCacheMode() == CacheMode.CACHE_MODE_USE_CACHE_WHEN_NET_ERROR || getCacheMode() == CacheMode.CACHE_MODE_USE_CACHE_PRIORITY)) {
            //如果本地存在 加载本地过期数据
            tryLoadDiskData(true);
        } else {
            //否则 直接通知失败
            notifyLoadPageDataFailed(e);
        }
    }
}

package com.qq.reader.zebra.loader;

import com.qq.reader.zebra.Zebra;
import com.qq.reader.zebra.cache.CacheController;
import com.qq.reader.zebra.cache.CacheMode;
import com.qq.reader.zebra.log.Logger;
import com.qq.reader.zebra.task.TaskHandler;
import com.qq.reader.zebra.utils.ZebraUtil;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhanglulu on 2019/3/2.
 * for 用于分发网络/缓存
 */
@SuppressWarnings("rawtypes")
public class LoadDispatcherTask<R> implements Runnable, LoadDiskDataTask.LoadDataListener
        , LoadDiskDataTask.LoadExpiredDataListener, LoadNetDataTask.LoadDataListener {
    private static final String TAG = "ReaderDataTask";

    /**
     * 数据加载类
     */
    private final Zebra<R> provider;


    /**
     * Task 完成监听
     */
    private ITaskFinishListener<R> mTaskFinishListener;

    /**
     * 缓存流
     */
    private InputStream cacheInputStream;

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

    public void setTaskFinishListener(ITaskFinishListener mTaskFinishListener) {
        this.mTaskFinishListener = mTaskFinishListener;
    }

    public int getCacheMode() {
        return provider.getCacheMode();
    }

    public LoadDispatcherTask(Zebra<R> provider) {
        this.provider = provider;
    }

    @Override
    public void run() {
        cacheInputStream = CacheController.getInstance().get(provider.getRequestKey());
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
        LoadNetDataTask netTask = new LoadNetDataTask(provider);
        netTask.setLoadDataListener(this);
        if (ZebraUtil.isUIThread()) {
            TaskHandler.getInstance().enqueue(netTask);
        } else {
            netTask.run();
        }
    }

    /**
     * 从本地获取
     * [注] 当且仅当 网络加载失败或者网络数据解析失败时调用，否则会持续加载过期数据
     */
    private void tryLoadDiskData(boolean isLoadExpired) {
        if (cacheInputStream == null || provider == null) {
            return;
        }
        LoadDiskDataTask diskTask = new LoadDiskDataTask(provider,
                cacheInputStream, isLoadExpired);
        diskTask.setLoadDataListener(this);
        diskTask.setLoadExpiredDataListener(this);
        if (ZebraUtil.isUIThread()) {
            TaskHandler.getInstance().enqueue(diskTask);
        } else {
            diskTask.run();
        }
    }

    /**
     * 成功通知
     * @param isCache
     */
    private void notifyLoadPageDataSuccess(boolean isCache) {
        if (mTaskFinishListener != null) {
            provider.setCache(isCache);
            mTaskFinishListener.onSuccess(provider);
        }
    }


    /**
     * 失败通知
     * @param e
     */
    private void notifyLoadPageDataFailed(Throwable e) {
        try {
            if (mTaskFinishListener != null) {
                mTaskFinishListener.onFailure(e);
            }
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.e(TAG, "LoadNativePageDataTask");
        }
    }

    //----------------------------------------------------------------------------------------------
    // LoadDiskDataTask.LoadDataListener 缓存获取回调
    @Override
    public void onLoadDiskDataSuccess(Zebra provider) {
        synchronized (this) {
            notifyLoadPageDataSuccess(true);
        }
    }

    @Override
    public void onLoadDiskDataFailed(Zebra provider) {
        synchronized (this) {
            tryLoadNetData();
        }
    }
    //----------------------------------------------------------------------------------------------
    // LoadDiskDataTask.LoadExpiredDataListener 过期缓存获取回调
    @Override
    public void onLoadDiskExpiredDataSuccess(Zebra provider) {
        synchronized (this) {
            notifyLoadPageDataSuccess(true);
        }
    }

    @Override
    public void onLoadDiskExpiredDataFailed(Zebra provider) {
        String error = "本地过期数据加载失败 !!!! ";
        synchronized (this) {
            notifyLoadPageDataFailed(new RuntimeException(error));
        }
        Logger.e(TAG, "onLoadDiskExpiredDataFailed: " + error );
    }

    //----------------------------------------------------------------------------------------------
    // ReaderJSONNetTaskListener 网络获取回调
    @Override
    public void onLoadNetDataSuccess(Zebra provider) {
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

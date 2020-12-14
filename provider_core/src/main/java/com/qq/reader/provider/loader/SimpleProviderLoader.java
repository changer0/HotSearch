package com.qq.reader.provider.loader;

import com.qq.reader.provider.DataProvider;
import com.qq.reader.provider.ProviderLiveData;
import com.qq.reader.provider.cache.CacheController;
import com.qq.reader.provider.define.ProviderConstants;
import com.qq.reader.provider.log.Logger;
import com.qq.reader.provider.task.TaskHandler;
import com.qq.reader.provider.utils.ProviderUtility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * 对 Loader 的简单实现，支持本地缓存 使用方可根据自己的需求进行定制
 */
public class SimpleProviderLoader<R> implements ILoader<R> {
    private static final String TAG = "SimpleProviderLoader";

    private DataProvider<R> provider;
    private ProviderLiveData liveData = new ProviderLiveData();
    private long lastTime = 0L;
    private ObserverEntity syncObserverEntity;

    private final ITaskFinishListener<R> mTaskFinishListener = new ITaskFinishListener<R>() {
        @Override
        public void onSuccess(DataProvider<R> provider) {
            notifyLoadPageDataSuccess(provider);
        }

        @Override
        public void onFailure(Throwable throwable) {
            notifyLoadPageDataFailed(provider, throwable);
        }
    };

    private void sendASyncDispatcherTask() {
        LoadDispatcherTask<R> dispatcher = getDispatcherTask();
        dispatcher.setTaskFinishListener(mTaskFinishListener);
        TaskHandler.getInstance().enqueue(dispatcher);
    }

    /**
     * 为 DataProvider<R> 提供分发任务的 Runnable
     */
    private synchronized LoadDispatcherTask<R> getDispatcherTask() {
        lastTime = System.currentTimeMillis();
        return new LoadDispatcherTask<R>(provider);
    }

    //----------------------------------------------------------------------------------------------
    // notify

    /**
     * 通知失败回调
     */
    public void notifyLoadPageDataFailed(DataProvider<R> p, Throwable e) {
        ObserverEntity observerEntity = new ObserverEntity();
        observerEntity.provider = p;
        observerEntity.throwable = e;
        observerEntity.state = ProviderConstants.PROVIDER_DATA_ERROR;
        liveData.postValue(observerEntity);
        syncObserverEntity = observerEntity;
        Logger.e(TAG, "notifyLoadPageDataFailed: 耗时：" + getFormatTimeConsuming());
    }

    /**
     * 通知成功回调
     */
    public void notifyLoadPageDataSuccess(DataProvider<R> p) {
        ObserverEntity observerEntity = new ObserverEntity();
        observerEntity.provider = p;
        observerEntity.state = ProviderConstants.PROVIDER_DATA_SUCCESS;
        liveData.postValue(observerEntity);
        syncObserverEntity = observerEntity;
        Logger.i(TAG, "notifyLoadPageDataSuccess: 耗时：" + getFormatTimeConsuming());
    }

    //----------------------------------------------------------------------------------------------
    // 暴露缓存相关操作

    public void saveCache(String key,InputStream inputStream) {
        try {
            CacheController.getInstance().save(key, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean removeCache() {
        return CacheController.getInstance().remove(provider.getRequestKey());
    }

    //----------------------------------------------------------------------------------------------
    // ILoader 的接口实现
    @Override
    public ProviderLiveData loadData(DataProvider<R> provider) {
        this.provider = provider;
        if (provider == null) {
            throw new NullPointerException("provider 不可为空");
        }
        sendASyncDispatcherTask();
        return liveData;
    }

    private String getFormatTimeConsuming() {
        long time = System.currentTimeMillis() - lastTime;
        return String.format(Locale.CHINA, "%fs", time/1000f);
    }

}

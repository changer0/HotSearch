package com.yuewen.reader.zebra.loader;

import com.yuewen.reader.zebra.Zebra;
import com.yuewen.reader.zebra.ZebraLiveData;
import com.yuewen.reader.zebra.define.ZebraConstants;
import com.yuewen.reader.zebra.log.Logger;
import com.yuewen.reader.zebra.task.TaskHandler;

import java.util.Locale;

/**
 * 对 Loader 的简单实现，支持本地缓存 使用方可根据自己的需求进行定制
 */
public class SimpleLoader<R> implements ILoader<R> {
    private static final String TAG = "SimpleProviderLoader";

    private Zebra<R> zebra;
    private ZebraLiveData liveData = new ZebraLiveData();
    private long lastTime = 0L;
    private ObserverEntity syncObserverEntity;

    private final ITaskFinishListener<R> mTaskFinishListener = new ITaskFinishListener<R>() {
        @Override
        public void onSuccess(Zebra<R> provider) {
            notifyLoadPageDataSuccess(provider);
        }

        @Override
        public void onFailure(Throwable throwable) {
            notifyLoadPageDataFailed(zebra, throwable);
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
        return new LoadDispatcherTask<R>(zebra);
    }

    //----------------------------------------------------------------------------------------------
    // notify

    /**
     * 通知失败回调
     */
    public void notifyLoadPageDataFailed(Zebra<R> p, Throwable e) {
        ObserverEntity observerEntity = new ObserverEntity();
        observerEntity.zebra = p;
        observerEntity.throwable = e;
        observerEntity.state = ZebraConstants.ZEBRA_DATA_ERROR;
        liveData.postValue(observerEntity);
        syncObserverEntity = observerEntity;
        Logger.e(TAG, "notifyLoadPageDataFailed: 耗时：" + getFormatTimeConsuming());
    }

    /**
     * 通知成功回调
     */
    public void notifyLoadPageDataSuccess(Zebra<R> p) {
        ObserverEntity observerEntity = new ObserverEntity();
        observerEntity.zebra = p;
        observerEntity.state = ZebraConstants.ZEBRA_DATA_SUCCESS;
        liveData.postValue(observerEntity);
        syncObserverEntity = observerEntity;
        Logger.i(TAG, "notifyLoadPageDataSuccess: 耗时：" + getFormatTimeConsuming());
    }

    //----------------------------------------------------------------------------------------------
    // ILoader 的接口实现
    @Override
    public ZebraLiveData loadData(Zebra<R> provider) {
        this.zebra = provider;
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

package com.qq.reader.provider.loader;

import androidx.lifecycle.MutableLiveData;

import com.qq.reader.provider.DataProvider;
import com.qq.reader.provider.ProviderLiveData;
import com.qq.reader.provider.cache.CacheController;
import com.qq.reader.provider.define.ProviderConstants;
import com.qq.reader.provider.log.Logger;
import com.qq.reader.provider.task.TaskHandler;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 对 Loader 的简单实现，支持本地缓存 使用方可根据自己的需求进行定制
 */
public class SimpleProviderLoader<R, P> implements ILoader<R, P> {
    private static final String TAG = "SimpleProviderLoader";

    private DataProvider<R, P> provider;
    private ProviderLiveData liveData = new ProviderLiveData();
    private long lastTime = 0L;


    /**
     * 获取 RxJava 请求
     */
    private Observable<DataProvider<R, P>> getObservable() {
        return Observable.create(emitter -> {
            LoadDispatcherTask<R, P> dispatcher = getDispatcherTask();
            dispatcher.setEmitter(emitter);
            TaskHandler.getInstance().enqueue(dispatcher);
        });
    }

    /**
     * 为 DataProvider<R, P> 提供分发任务的 Runnable
     */
    private synchronized LoadDispatcherTask<R, P> getDispatcherTask() {
        return new LoadDispatcherTask<R, P>(provider);
    }

    //----------------------------------------------------------------------------------------------
    // notify

    /**
     * 通知失败回调
     */
    public void notifyLoadPageDataFailed(DataProvider<R, P> p, Throwable e) {
        ObserverEntity observerEntity = new ObserverEntity();
        observerEntity.provider = p;
        observerEntity.throwable = e;
        observerEntity.state = ProviderConstants.PROVIDER_DATA_ERROR;
        liveData.postValue(observerEntity);
    }

    /**
     * 通知成功回调
     */
    public void notifyLoadPageDataSuccess(DataProvider<R, P> p) {
        ObserverEntity observerEntity = new ObserverEntity();
        observerEntity.provider = p;
        observerEntity.state = ProviderConstants.PROVIDER_DATA_SUCCESS;
        liveData.postValue(observerEntity);
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
    public ProviderLiveData loadData(DataProvider<R, P> provider) {
        this.provider = provider;
        if (provider == null) {
            throw new NullPointerException("provider 不可为空");
        }
        lastTime = System.currentTimeMillis();
        Observable<DataProvider<R, P>> observable = getObservable();
        observable.subscribe(new Observer<DataProvider<R, P>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                        Logger.d(TAG, "onSubscribe: called: " + Thread.currentThread());
                    }

                    @Override
                    public void onNext(@NotNull DataProvider<R, P> dataProvider) {
                        Logger.d(TAG, "onNext: called: 耗时：" + getFormatTimeConsuming());
                        notifyLoadPageDataSuccess(dataProvider);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Logger.e(TAG, "loadData onError: 耗时：" + getFormatTimeConsuming());
                        notifyLoadPageDataFailed(provider, e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Logger.d(TAG, "onComplete: called");
                    }
                });
        return liveData;
    }

    private String getFormatTimeConsuming() {
        long time = System.currentTimeMillis() - lastTime;
        return String.format(Locale.CHINA, "%fs", time/1000f);
    }

}

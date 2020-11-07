package com.qq.reader.provider.loader;

import androidx.lifecycle.MutableLiveData;

import com.qq.reader.provider.DataProvider;
import com.qq.reader.provider.cache.CacheController;
import com.qq.reader.provider.cache.CacheMode;
import com.qq.reader.provider.define.ProviderConstants;
import com.qq.reader.provider.log.Logger;
import com.qq.reader.provider.task.TaskHandler;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 对 Loader 的简单实现，支持本地缓存 使用方可根据自己的需求进行定制
 */
public class SimpleProviderLoader<P> implements ILoader<P> {
    private static final String TAG = "SimpleProviderLoader";

    private DataProvider<P> provider;
    private int cacheMode = CacheMode.CACHE_MODE_USE_CACHE_PRIORITY;
    private MutableLiveData<ObserverEntity> liveData = new MutableLiveData<>();

    public int getCacheMode() {
        return cacheMode;
    }

    public void setCacheMode(int cacheMode) {
        this.cacheMode = cacheMode;
    }

    /**
     * 获取 RxJava 请求
     */
    private Observable<DataProvider<P>> getObservable() {
        return Observable.create(emitter -> {
            LoadDispatcherTask<P> dispatcher = getDispatcherTask();
            dispatcher.setEmitter(emitter);
            TaskHandler.getInstance().enqueue(dispatcher);
        });
    }

    /**
     * 为 DataProvider<P> 提供分发任务的 Runnable
     */
    private synchronized LoadDispatcherTask<P> getDispatcherTask() {
        return new LoadDispatcherTask<P>(provider, cacheMode);
    }

    //----------------------------------------------------------------------------------------------
    // notify

    /**
     * 通知失败回调
     */
    public void notifyLoadPageDataFailed(DataProvider<P> p, Throwable e) {
        ObserverEntity observerEntity = new ObserverEntity();
        observerEntity.provider = p;
        observerEntity.throwable = e;
        observerEntity.state = ProviderConstants.PROVIDER_DATA_ERROR;
        liveData.postValue(observerEntity);
    }

    /**
     * 通知成功回调
     */
    public void notifyLoadPageDataSuccess(DataProvider<P> p) {
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
    public MutableLiveData<ObserverEntity> loadData(DataProvider<P> provider) {
        this.provider = provider;
        if (provider == null) {
            throw new NullPointerException("provider 不可为空");
        }
        Observable<DataProvider<P>> observable = getObservable();
        observable.subscribe(new Observer<DataProvider<P>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                        Logger.d(TAG, "onSubscribe: called: " + Thread.currentThread());
                    }

                    @Override
                    public void onNext(@NotNull DataProvider<P> dataProvider) {
                        Logger.d(TAG, "onNext: called: " + Thread.currentThread());
                        notifyLoadPageDataSuccess(dataProvider);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Logger.e(TAG, "loadData onError: ");
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

}

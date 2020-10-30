package com.qq.reader.provider.loader;

import androidx.lifecycle.MutableLiveData;
import com.qq.reader.provider.cache.CacheController;
import com.qq.reader.provider.cache.core.DiskLruCache;
import com.qq.reader.provider.BaseViewBindItem;
import com.qq.reader.provider.BaseDataProvider;
import com.qq.reader.provider.define.ProviderConstants;
import com.qq.reader.provider.task.LoadDispatcherTask;
import com.qq.reader.provider.log.Logger;
import com.qq.reader.provider.task.TaskHandler;
import org.jetbrains.annotations.NotNull;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author zhanglulu on 2019/3/2.
 * for 数据加载器 <br/>
 * 用于加载 {@link BaseDataProvider}
 */
@SuppressWarnings("rawtypes")
public class DataProviderLoader {

    private static final String TAG = "ReaderDataLoader";
    private static volatile DataProviderLoader instance = null;
    private CacheController mCache;

    private DataProviderLoader() {
        initDiskCache();
    }

    public static DataProviderLoader getInstance() {
        if (instance == null) {
            synchronized (DataProviderLoader.class) {
                if (instance == null) {
                    instance = new DataProviderLoader();
                }
            }
        }
        return instance;
    }

    /**
     * 强烈建议使用 LiveData 形式回调
     */
    @Deprecated
    public void unReceiveData(BaseDataProvider provider) {
        if (provider != null) {
            LoadDispatcherTask dispatcher = provider.getDispatcherTask();
            if (dispatcher != null) {
                dispatcher.setEmitter(null);
            }
        }
    }

    /**
     * 初始化文件缓存
     */
    private void initDiskCache() {
        mCache = new CacheController();
    }

    /**
     * 保存 数据
     */
    public void save(String key, InputStream is) {
        try {
            mCache.save(key, is);
        } catch (Exception e) {
            Logger.e(TAG, "save");
            e.printStackTrace();
        }
    }

    public DiskLruCache.Snapshot get(String key) {
        return mCache.get(key);
    }

    public boolean remove(String key) {
        return mCache.remove(key);
    }

    /**
     * 加载DataProvider
     */
    public void loadData(BaseDataProvider provider) {
        if (provider == null) {
            throw new NullPointerException("provider 不可为空");
        }
        Observable<BaseDataProvider> observable = getObservable(provider);
        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseDataProvider>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                        Logger.d(TAG, "onSubscribe: called");
                    }

                    @Override
                    public void onNext(@NotNull BaseDataProvider baseDataProvider) {
                        notifyLoadPageDataSuccess(baseDataProvider);
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
    }

    /**
     * 并发请求 DataProvider
     *
     * @param p1              并发请求Provider 1
     * @param p2              并发请求Provider 2
     * @param mergeLiveData   数据成功结果
     */
    public void loadDataConcurrent(BaseDataProvider p1,
                                   BaseDataProvider p2,
                                   MutableLiveData<MergeObserverEntity> mergeLiveData) {

        if (p1 == null || p2 == null) {
            throw new NullPointerException("provider 不可为空");
        }
        List<BaseViewBindItem> dataItemsResult = new ArrayList<>();
        Observable<BaseDataProvider> net1 = getObservable(p1);
        Observable<BaseDataProvider> net2 = getObservable(p2);

        Observable.merge(net1, net2).subscribe(new Observer<BaseDataProvider>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
                Logger.d(TAG, "onSubscribe: 执行");
            }

            @Override
            public void onNext(@NotNull BaseDataProvider p) {
                Logger.d(TAG, "onNext: 执行");
                List<BaseViewBindItem> dataItems = p.getDataItems();
                if (dataItems == null) {
                    Logger.e(TAG, "onNext: dataItems == null");
                    return;
                }
                if (p == p1) {
                    dataItemsResult.addAll(0, dataItems);
                    Logger.d(TAG, "onNext: P1");
                    notifyLoadPageDataSuccess(p1);
                } else {
                    dataItemsResult.addAll(dataItems);
                    notifyLoadPageDataSuccess(p2);
                    Logger.d(TAG, "onNext: P2");
                }

            }

            @Override
            public void onError(@NotNull Throwable e) {
                Logger.e(TAG, "loadDataConcurrent onError: 执行");
                notifyLoadPageMergeDataFailed(e, mergeLiveData);
            }

            @Override
            public void onComplete() {
                notifyLoadPageMergeDataSuccess(dataItemsResult, mergeLiveData);
                Logger.d(TAG, "onComplete: 执行");
            }
        });
    }

    /**
     * 自定义并发请求
     * @param p Provider
     * @param customObservable 自定义请求任务
     * @param mergeObserver 自定义处理合并
     */
    public void loadDataCustomConcurrent (BaseDataProvider p,
                                          Observable<?> customObservable,
                                          Observer<? super Object> mergeObserver) {
        if (p == null) {
            return;
        }
        //源数据
        Observable<BaseDataProvider> sourceObservable = getObservable(p);
        //合并请求
        Observable.merge(sourceObservable, customObservable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mergeObserver);
    }

    /**
     * 通知失败回调
     */
    public void notifyLoadPageDataFailed(BaseDataProvider p, Throwable e) {
        ObserverEntity observerEntity = new ObserverEntity();
        observerEntity.provider = p;
        observerEntity.throwable = e;
        observerEntity.state = ProviderConstants.PROVIDER_DATA_ERROR;
        p.getLiveData().postValue(observerEntity);
    }

    /**
     * 通知成功回调
     */
    public void notifyLoadPageDataSuccess(BaseDataProvider p) {
        ObserverEntity observerEntity = new ObserverEntity();
        observerEntity.provider = p;
        observerEntity.state = ProviderConstants.PROVIDER_DATA_SUCCESS;
        p.getLiveData().postValue(observerEntity);
    }

    /**
     * 通知合并成功回调
     */
    public void notifyLoadPageMergeDataSuccess(List<BaseViewBindItem> dataItemsResult, MutableLiveData<MergeObserverEntity> liveData) {
        MergeObserverEntity observerEntity = new MergeObserverEntity();
        observerEntity.state = ProviderConstants.PROVIDER_DATA_SUCCESS;
        liveData.postValue(observerEntity);
    }

    /**
     * 通知合并失败回调
     */
    public void notifyLoadPageMergeDataFailed(Throwable throwable,
                                              MutableLiveData<MergeObserverEntity> liveData) {
        MergeObserverEntity observerEntity = new MergeObserverEntity();
        observerEntity.state = ProviderConstants.PROVIDER_DATA_ERROR;
        observerEntity.throwable = throwable;
        liveData.postValue(observerEntity);
    }

    private Observable<BaseDataProvider> getObservable(BaseDataProvider p) {
        return Observable.create(emitter -> {
            LoadDispatcherTask dispatcher = p.getDispatcherTask();
            dispatcher.setEmitter(emitter);
            TaskHandler.getInstance().enqueue(dispatcher);
        });
    }
}

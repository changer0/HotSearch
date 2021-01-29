package com.qq.reader.bookstore;

import android.os.Bundle;

import androidx.collection.LruCache;
import androidx.lifecycle.ViewModel;

import com.qq.reader.zebra.ZebraLiveData;

/**
 * @author zhanglulu
 */
public abstract class BaseZebraViewModel extends ViewModel {

    /**
     * ZebraLiveData Cache
     */
    protected final LruCache<String, ZebraLiveData> zebraLiveDataCache = new LruCache<>(100);

    /**
     * 获取 ZebraLiveData
     * @param params 参数
     * @return ZebraLiveData
     */
    abstract public ZebraLiveData getZebraLiveData(Bundle params);

    @Override
    protected void onCleared() {
        super.onCleared();
        zebraLiveDataCache.evictAll();
    }
}

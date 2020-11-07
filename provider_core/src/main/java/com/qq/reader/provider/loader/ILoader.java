package com.qq.reader.provider.loader;

import androidx.lifecycle.MutableLiveData;

import com.qq.reader.provider.DataProvider;

/**
 * 加载器 用户可自定义 数据加载、缓存逻辑
 */
public interface ILoader<P> {
    /**加载数据*/
    MutableLiveData<ObserverEntity> loadData(DataProvider<P> provider);
}

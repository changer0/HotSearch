package com.qq.reader.provider.loader;
import com.qq.reader.provider.DataProvider;
import com.qq.reader.provider.ProviderLiveData;

/**
 * 加载器 用户可自定义 数据加载、缓存逻辑
 */
public interface ILoader<R, P> {
    /**加载数据*/
    ProviderLiveData loadData(DataProvider<R, P> provider);
}

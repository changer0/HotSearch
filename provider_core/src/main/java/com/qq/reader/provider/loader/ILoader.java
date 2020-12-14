package com.qq.reader.provider.loader;
import com.qq.reader.provider.DataProvider;
import com.qq.reader.provider.ProviderLiveData;
import com.qq.reader.provider.R;

/**
 * 加载器 用户可自定义 数据加载、缓存逻辑
 */
public interface ILoader<T> {
    /**加载数据*/
    ProviderLiveData loadData(DataProvider<T> provider);
}

package com.qq.reader.provider.inter;

import com.qq.reader.provider.DataProvider;

import java.io.InputStream;

/**
 * 加载器 用户可自定义 数据加载、缓存逻辑
 */
public interface ILoader {
    /**加载数据*/
    void loadData(DataProvider provider);

    void saveCache(String key, InputStream inputStream);

    InputStream getCache(String key);

    boolean removeCache(String key);

    boolean isCache();

    String getCacheKey();
}

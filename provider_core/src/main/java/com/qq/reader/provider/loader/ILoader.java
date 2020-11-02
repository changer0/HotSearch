package com.qq.reader.provider.loader;

import com.qq.reader.provider.DataProvider;

/**
 * 加载器 用户可自定义 数据加载、缓存逻辑
 */
public interface ILoader {
    /**加载数据*/
    void loadData(DataProvider provider);
}

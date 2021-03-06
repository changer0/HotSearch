package com.yuewen.reader.zebra.loader;
import com.yuewen.reader.zebra.Zebra;
import com.yuewen.reader.zebra.ZebraLiveData;

/**
 * 加载器 用户可自定义 数据加载、缓存逻辑
 */
public interface ILoader<T> {
    /**加载数据*/
    ZebraLiveData loadData(Zebra<T> provider);
}

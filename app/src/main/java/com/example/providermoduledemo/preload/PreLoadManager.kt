package com.example.providermoduledemo.preload

import com.qq.reader.provider.DataProvider
import com.qq.reader.provider.bean.BaseBean
import java.util.concurrent.ConcurrentHashMap

/**
 * 用于管理预加载的 Provider
 */
object PreLoadManager {
    /**
     * 存储用于预加载的 Provider
     */
    private val map = ConcurrentHashMap<String, DataProvider<out BaseBean>>()

    /**
     * 获取预加载 Provider
     */
    public fun getProvider(key: String): DataProvider<out BaseBean>? {
        return map[key]
    }

    /**
     * 执行预加载 Provider
     */
    public fun preLoadProvider(key: String, provider: DataProvider<out BaseBean>) {
        var resultProvider = map[key]
        if (resultProvider == null) {
            resultProvider = provider
            map[key] = resultProvider
        }
        //resultProvider.loadData()
    }

    /**
     * 释放
     */
    public fun release(key: String) {
        if (map.containsKey(key)) {
            map.remove(key)
        }
    }
}
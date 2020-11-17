package com.example.providermoduledemo.preload

import com.qq.reader.provider.ProviderLiveData
import java.util.concurrent.ConcurrentHashMap

/**
 * 用于管理预加载的 LiveData
 */
object PreLoadLiveDataManager {
    /**
     * 存储用于预加载的 LiveData
     */
    private val map = ConcurrentHashMap<String, ProviderLiveData>()

    /**
     * 获取预加载 LiveData
     */
    public fun getLiveData(key: String): ProviderLiveData? {
        return map[key]
    }

    /**
     * 保存 LiveData
     */
    public fun savePreLoadLiveData(key: String, liveData: ProviderLiveData) {
        map[key] = liveData
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
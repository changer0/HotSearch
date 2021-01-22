package com.example.providermoduledemo.preload

import com.qq.reader.zebra.ZebraLiveData
import java.util.concurrent.ConcurrentHashMap

/**
 * 用于管理预加载的 LiveData
 */
object PreLoadLiveDataManager {
    /**
     * 存储用于预加载的 LiveData
     */
    private val map = ConcurrentHashMap<String, ZebraLiveData>()

    /**
     * 获取预加载 LiveData
     */
    public fun getLiveData(key: String): ZebraLiveData? {
        return map[key]
    }

    /**
     * 保存 LiveData
     */
    public fun savePreLoadLiveData(key: String, liveData: ZebraLiveData) {
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
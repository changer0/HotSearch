package com.example.providermoduledemo.preload

import androidx.lifecycle.MutableLiveData
import com.qq.reader.provider.loader.ObserverEntity
import java.util.concurrent.ConcurrentHashMap

/**
 * 用于管理预加载的 Provider
 */
object PreLoadManager {
    /**
     * 存储用于预加载的 Provider
     */
    private val map = ConcurrentHashMap<String, MutableLiveData<ObserverEntity>>()

    /**
     * 获取预加载 Provider
     */
    public fun getLiveData(key: String): MutableLiveData<ObserverEntity>? {
        return map[key]
    }

    /**
     * 执行预加载 Provider
     */
    public fun savePreLoadLiveData(key: String, liveData: MutableLiveData<ObserverEntity>) {
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
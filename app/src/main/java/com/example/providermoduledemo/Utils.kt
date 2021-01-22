package com.example.providermoduledemo

import com.qq.reader.zebra.cache.CacheMode.*

object Utils {

    fun getCacheStr(cacheMode: Int) : String{
        when(cacheMode) {
            CACHE_MODE_NOT_USE_CACHE -> return "不使用缓存"
            CACHE_MODE_USE_CACHE_WHEN_NET_ERROR -> return "网络数据失败时使用缓存"
            CACHE_MODE_USE_CACHE_PRIORITY -> return "优先使用缓存"
            CACHE_MODE_USE_CACHE_NOT_EXPIRED -> return "使用缓存，但不使用过期数据"
        }
        return ""
    }
}
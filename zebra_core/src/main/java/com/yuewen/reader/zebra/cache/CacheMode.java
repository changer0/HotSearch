package com.yuewen.reader.zebra.cache;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.yuewen.reader.zebra.cache.CacheMode.*;

/**
 * @author zhanglulu on 2019/9/18.
 * for 缓存模式
 */
@IntDef({CACHE_MODE_NOT_USE_CACHE, CACHE_MODE_USE_CACHE_WHEN_NET_ERROR, CACHE_MODE_USE_CACHE_PRIORITY, CACHE_MODE_USE_CACHE_NOT_EXPIRED})
@Retention(RetentionPolicy.SOURCE)
public @interface CacheMode {
    /**
     * 不使用缓存
     */
    public static final int CACHE_MODE_NOT_USE_CACHE = 0;
    /**
     * 只有当网络数据失败时使用缓存
     */
    public static final int CACHE_MODE_USE_CACHE_WHEN_NET_ERROR = 1;
    /**
     * @apiNote
     * 优先使用缓存: 本地有缓存且未过期则用缓存 -> 过期则使用网络数据 -> 网络失败使用过期数据
     */
    public static final int CACHE_MODE_USE_CACHE_PRIORITY = 2;

    /**
     * 使用缓存，但不使用过期数据
     */
    public static final int CACHE_MODE_USE_CACHE_NOT_EXPIRED = 3;

}
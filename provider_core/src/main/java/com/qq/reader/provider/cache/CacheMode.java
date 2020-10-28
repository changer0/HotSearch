package com.qq.reader.provider.cache;

/**
 * @author zhanglulu on 2019/9/18.
 * for 缓存模式
 */
public interface CacheMode {
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
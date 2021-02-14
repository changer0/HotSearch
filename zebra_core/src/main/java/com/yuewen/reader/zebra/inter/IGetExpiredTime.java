package com.yuewen.reader.zebra.inter;

import androidx.annotation.NonNull;

/**
 * 获取数据过期时间，需要后端数据配置支持
 */
public interface IGetExpiredTime<T> {
    /**过期时间，用户*/
    long getExpiredTime(@NonNull T mData);
}

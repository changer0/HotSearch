package com.qq.reader.provider.cache.core;


import com.qq.reader.provider.log.Logger;

/**
 * Created by Ryan
 * On 2016/5/9.
 */
class System {
    private final static String TAG = "DiskLruCache";

    public static void logW(String log) {
        Logger.w(TAG, log);
    }
}

package com.qq.reader.zebra.cache.core;


import com.qq.reader.zebra.log.Logger;

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

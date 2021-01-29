package com.qq.reader.zebra.log;

import static com.qq.reader.zebra.ZebraConfig.isDebug;

public class Logger {
    public static final String TAG = "TAG_DataProvider";

    public static void d(String tag, String info) {
        if (isDebug) {
            android.util.Log.d(TAG, tag + ": " + info);
        }
    }

    public static void i(String tag, String info) {
        if (isDebug) {
            android.util.Log.i(TAG, tag + ": " + info);
        }
    }

    public static void e(String tag, String info) {
        android.util.Log.e(TAG, tag + ": " + info);
    }

    public static void w(String tag, String info) {
        android.util.Log.w(TAG, tag + ": " + info);
    }

}

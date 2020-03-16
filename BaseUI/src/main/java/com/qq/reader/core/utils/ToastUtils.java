package com.qq.reader.core.utils;

import android.widget.Toast;

import com.qq.reader.core.BaseApplication;
import com.qq.reader.core.view.ReaderToast;

/**
 * Created by liuyang.a on 2017/3/16.
 */

public class ToastUtils {
    public static void showToast_Short(String text) {
        ReaderToast.makeText(BaseApplication.Companion.getINSTANCE(),
                text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast_Short(int resId) {
        ReaderToast.makeText(BaseApplication.Companion.getINSTANCE(),
                resId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast_Long(String text) {
        ReaderToast.makeText(BaseApplication.Companion.getINSTANCE(),
                text, Toast.LENGTH_LONG).show();
    }

    public static void showToast_Long(int resId) {
        ReaderToast.makeText(BaseApplication.Companion.getINSTANCE(),
                resId, Toast.LENGTH_LONG).show();
    }
}

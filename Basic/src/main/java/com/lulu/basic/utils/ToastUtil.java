package com.lulu.basic.utils;

import android.widget.Toast;

import com.lulu.baseutil.Init;

/**
 * Author: zhanglulu
 * Time: 2021/2/15
 */
public class ToastUtil {

    public static void showShortToast(String msg) {
        Toast.makeText(Init.context, msg, Toast.LENGTH_SHORT).show();
    }
}

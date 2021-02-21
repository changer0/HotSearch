package com.lulu.basic.activity;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

/**
 * Author: zhanglulu
 * Time: 2021/2/14
 */
public class InputMethodManagerLastSrvView {
    private static boolean sHasField = true;

    public static void fixLeak(@NonNull Context context) {

        if (!sHasField) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mLastSrvView"};
        for (String param : arr) {
            try {
                Field field = imm.getClass().getDeclaredField(param);
                field.setAccessible(true);
                field.set(imm, null);
            } catch (Throwable t) {
                t.printStackTrace();
                sHasField = false;
            }
        }
    }
}
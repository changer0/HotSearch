package com.lulu.baseutil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.IBinder;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 通用型工具
 */
public class CommonUtil {
    /**
     * 根据读书位置获取当前进度
     */
    @SuppressLint("DefaultLocale")
    public static String getPercentStr(double percnet) {
        if (percnet > 0 && percnet <= 0.001) {  //避免进度过小时显示0.0%
            return "0.1%";
        } else {
            return String.format("%.1f", percnet * 100) + "%";
        }
    }

    /**
     * dp 转 px 工具
     * @param dipValue dp 值
     * @return px 值
     */
    public static int dp2px(float dipValue) {
        Resources resources = Resources.getSystem();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue
                , resources.getDisplayMetrics());
    }

    /**
     * 版本名称 eg:7.5.5
     * @param context 上下文
     * @return 版本名称
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e = null;
        }
        return versionName;
    }

    /**
     * 版本号 eg:169
     * @param context 上下文
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 展示软键盘
     * @param view 目标 View
     * @param mContext 上下文
     * @return 是否展示成功
     */
    public static boolean showKeyBoard(View view, Context mContext) {
        boolean b = false;
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) mContext
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                b = inputMethodManager.showSoftInput(view, 0);
            }
        } catch (Exception e) {
            //try for bad token
        }
        return b;
    }

    /**
     * 隐藏软键盘
     * @param token 目标 token
     * @param mContext 上下文
     * @return 是否展示成功
     */
    public static boolean hideKeyBoard(IBinder token, Context mContext) {
        boolean b = false;
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) mContext
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null && inputMethodManager.isActive()) {
                b = inputMethodManager.hideSoftInputFromWindow(
                        token, 0);
            }
        } catch (Exception e) {
            //ignore for bad token
        }
        return b;
    }
}

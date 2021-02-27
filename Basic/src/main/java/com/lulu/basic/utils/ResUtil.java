package com.lulu.basic.utils;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import com.lulu.baseutil.Init;

/**
 * 资源工具
 */
public class ResUtil {

    public static String getString(@StringRes int stringRes) {
        return Init.context.getResources().getString(stringRes);
    }
    public static Drawable getDrawable(@DrawableRes int drawableRes) {
        return Init.context.getResources().getDrawable(drawableRes);
    }
}

package com.lulu.baseutil;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import androidx.core.graphics.drawable.DrawableCompat;

/**
 * @author zhanglulu
 * @date : 2020/12/16 2:59 PM
 */
public class DrawableUtil {
    /**
     * 染色 Drawable
     * @param drawable 目标 Drawable
     * @param colors 颜色集
     * @return
     */
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable).mutate();
        if (wrappedDrawable != null) {
            DrawableCompat.setTintList(wrappedDrawable, colors);
            return wrappedDrawable;
        }
        return drawable;
    }
}

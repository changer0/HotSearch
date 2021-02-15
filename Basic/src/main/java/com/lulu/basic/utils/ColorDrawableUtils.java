package com.lulu.basic.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;
import androidx.palette.graphics.Target;


public class ColorDrawableUtils {
    public static final int[] STATE_NORMAL = {android.R.attr.state_enabled};//-代表此属性为false
    public static final int[] STATE_PRESSED = {android.R.attr.state_pressed, android.R.attr.state_enabled};
    public static final int[] STATE_SELECTED = {android.R.attr.state_selected, android.R.attr.state_enabled};
    public static final int[] STATE_DISABLED = {-android.R.attr.state_enabled};

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLOR_DRAWABLE_DIMENSION = 2;

    /**
     * 指定色值设置透明度百分比
     *
     * @param originColor  原始颜色
     * @param alphaPercent 透明度百分比（在原有基础上）
     * @return 处理后色值
     */
    public static int colorCovertAlpha(@ColorInt int originColor, float alphaPercent) {
        int alpha = Color.alpha(originColor);
        int red = Color.red(originColor);
        int green = Color.green(originColor);
        int blue = Color.blue(originColor);

        int newAlpha = (int) (alpha * alphaPercent);
        return Color.argb(newAlpha, red, green, blue);
    }

    /**
     * 解析文本为色值
     *
     * @param colorStr     颜色String
     * @param defaultColor 解析失败返回默认色值
     * @return 解析后的色值
     */
    public static int parseColor(@Nullable final String colorStr, @ColorInt int defaultColor) {
        if (TextUtils.isEmpty(colorStr)) {
            return defaultColor;
        }

        try {
            return Color.parseColor(colorStr);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultColor;
        }
    }

    /**
     * 获取Drawable中的指定样本颜色
     *
     * @param drawable          源图
     * @param targetArray       目标色
     * @param defaultColorArray 默认颜色
     * @return 样本颜色
     */
    @NonNull
    public static int[] getDrawableColor(@NonNull final Drawable drawable, @NonNull Target[] targetArray, @NonNull int[] defaultColorArray) {
        final int[] colorArray = new int[targetArray.length];
        final Bitmap bitmap = getBitmapFromDrawable(drawable);
        if (bitmap == null) {
            return colorArray;
        }

        final Palette.Builder builder = Palette.from(bitmap).clearTargets();
        for (Target target : targetArray) {
            builder.addTarget(target);
        }
        final Palette palette = builder.generate();
        for (int i = 0; i < targetArray.length; i++) {
            colorArray[i] = palette.getColorForTarget(targetArray[i], defaultColorArray[i]);
        }
        return colorArray;
    }

    /**
     * 更新图片颜色
     *
     * @param surfaceColor  表层颜色
     * @param drawableArray 图片数组
     * @return 换完颜色后的图片
     */
    public static Drawable[] changeDrawableColor(final int surfaceColor, final Drawable... drawableArray) {
        if (drawableArray == null) {
            return null;
        }

        for (Drawable drawable : drawableArray) {
            if (drawable == null) {
                continue;
            }
            drawable.mutate().setColorFilter(surfaceColor, PorterDuff.Mode.SRC_IN);
        }
        return drawableArray;
    }

    /**
     * 从Drawable中获取Bitmap
     *
     * @param drawable 源
     * @return Bitmap
     */
    public static Bitmap getBitmapFromDrawable(@Nullable Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof StateListDrawable) {
            drawable = drawable.getCurrent();
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }


        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLOR_DRAWABLE_DIMENSION, COLOR_DRAWABLE_DIMENSION, BITMAP_CONFIG);
                bitmap.eraseColor(((ColorDrawable) drawable).getColor());
            } else if (drawable.getBounds().isEmpty()) {
                bitmap = Bitmap.createBitmap(Math.max(drawable.getIntrinsicWidth(), 2), Math.max(drawable.getIntrinsicHeight(), 2), BITMAP_CONFIG);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
            } else {
                bitmap = Bitmap.createBitmap(Math.max(drawable.getIntrinsicWidth(), 2), Math.max(drawable.getIntrinsicHeight(), 2), BITMAP_CONFIG);
                Canvas canvas = new Canvas(bitmap);
                canvas.translate(-drawable.getBounds().left, -drawable.getBounds().top);
                drawable.draw(canvas);
            }
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 动态生成Shape样式的Drawable
     *
     * @return
     */
    public static class ShapeDrawableBuilder {

        int corner;
        int strokeWidth, strokeColor;
        int solidColor;
        float[] corners;

        public ShapeDrawableBuilder setCorner(int corner) {
            this.corner = corner;
            return this;
        }

        public ShapeDrawableBuilder setCornerRadii(float tlcorner, float trcorner, float brcorner, float blcorner) {
            this.corners = new float[]{tlcorner, tlcorner, trcorner, trcorner, brcorner, brcorner, blcorner, blcorner};
            return this;
        }

        public ShapeDrawableBuilder setStrokeWidth(int strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }

        public ShapeDrawableBuilder setStrokeColor(int strokeColor) {
            this.strokeColor = strokeColor;
            return this;
        }

        public ShapeDrawableBuilder setSolidColor(int solidColor) {
            this.solidColor = solidColor;
            return this;
        }

        public GradientDrawable build() {
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setCornerRadius(corner);
            if (corners != null) {
                gradientDrawable.setCornerRadii(corners);
            }
            gradientDrawable.setStroke(strokeWidth, strokeColor);
            gradientDrawable.setColor(solidColor);
            return gradientDrawable;
        }

    }

}

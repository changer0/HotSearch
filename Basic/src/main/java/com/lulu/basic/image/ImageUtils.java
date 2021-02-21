package com.lulu.basic.image;//package com.qq.reader.common.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lulu.baseutil.bezelless.DensityUtil;
import com.qq.reader.module.bookstore.qweb.fragment.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN;

public class ImageUtils {

    //高斯模糊效果
    private static RequestOptions commonGaussBlurOptions;
    private static RequestOptions commonRoundCornerOptions2;
    private static RequestOptions commonRoundCornerOptions4;
    private static RequestOptions commonRoundCornerOptions7;
    private static RequestOptions commonRoundCornerOptions1000;


    public static void trimMemory(Context context, int level) {
        try {
            Glide.get(context).trimMemory(level);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void clearMemory(Context context) {
        try {
            Glide.get(context).clearMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 判断Context是否有效，如果Context 是Activity，而Activity又被销毁了，会导致Glide的崩溃。
     *
     * @param context
     * @return
     */
    private static boolean isContextEnable(Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Application) {
            return true;
        }
        if (context instanceof FragmentActivity) {
            return !((FragmentActivity) context).isDestroyed();
        } else if (context instanceof Activity) {
            return !((Activity) context).isDestroyed();
        } else if (context instanceof ContextWrapper) {
            return isContextEnable(((ContextWrapper) context).getBaseContext());
        }
        return true;
    }

    public static void displayImage(Context context, String url, ImageView imageView) {
        if (!isContextEnable(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    public static void displayImage(Context context, String url, ImageView imageView, RequestOptions options) {
        if (!isContextEnable(context)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void displayImage(Context context, String url, Target<?> target) {
        if (!isContextEnable(context)) {
            return;
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into((ImageView) target);
    }

    public static void displayImage(Context context, String url, RequestOptions options, Target<?> target) {
        if (!isContextEnable(context)) {
            return;
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(options)
                .into((ImageView) target);
    }

    public static void displayImage(Context context, int resource, Target<?> target, RequestOptions options) {
        if (!isContextEnable(context)) {
            return;
        }
        Glide.with(context)
                .asBitmap()
                .load(resource)
                .apply(options)
                .into((ImageView) target);
    }

    public static Bitmap loadBitmap(Context context, String url, RequestOptions options) {
        try {
            FutureTarget<Bitmap> target = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .apply(options)
                    .submit();
            return target.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }






    //书封圆角
    public static synchronized RequestOptions getRoundCornerOptions4() {
        if (commonRoundCornerOptions4 == null) {
            commonRoundCornerOptions4 = RequestOptions
                    .bitmapTransform(new RoundedCornersTransformation(DensityUtil.dip2px(4), 0))
                    .placeholder(R.color.img_loading_placeholder)
                    .error(R.color.img_loading_placeholder);
        }
        return commonRoundCornerOptions4;
    }

    public static synchronized RequestOptions getRoundCornerOptions2() {
        if (commonRoundCornerOptions2 == null) {
            commonRoundCornerOptions2 = RequestOptions
                    .bitmapTransform(new RoundedCornersTransformation(DensityUtil.dip2px(2), 0))
                    .placeholder(R.color.img_loading_placeholder)
                    .error(R.color.img_loading_placeholder);
        }
        return commonRoundCornerOptions2;
    }

    public static synchronized RequestOptions getRoundCornerOptions7() {
        if (commonRoundCornerOptions7 == null) {
            commonRoundCornerOptions7 = RequestOptions
                    .bitmapTransform(new RoundedCornersTransformation(DensityUtil.dip2px(7), 0))
                    .placeholder(R.color.img_loading_placeholder)
                    .error(R.color.img_loading_placeholder);
        }
        return commonRoundCornerOptions7;
    }

    public static synchronized RequestOptions getRoundCornerOptions1000() {
        if (commonRoundCornerOptions1000 == null) {
            commonRoundCornerOptions1000 = RequestOptions
                    .bitmapTransform(new RoundedCornersTransformation(1000, 0))
                    .placeholder(R.color.img_loading_placeholder)
                    .error(R.color.img_loading_placeholder);
        }
        return commonRoundCornerOptions1000;
    }

    // 高斯模糊
    public static synchronized RequestOptions getGaussBlurCommonOptions() {
        if (commonGaussBlurOptions == null) {
            commonGaussBlurOptions = RequestOptions
                    .bitmapTransform(new BlurTransformation(25))
                    .placeholder(R.color.img_loading_placeholder)
                    .error(R.color.img_loading_placeholder);
        }
        return commonGaussBlurOptions;
    }
}
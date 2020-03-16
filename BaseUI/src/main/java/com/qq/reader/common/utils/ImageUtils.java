package com.qq.reader.common.utils;//package com.qq.reader.common.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.qq.reader.baseui.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ImageUtils {
    private static RequestOptions localstoreCommonOptions;
    private static RequestOptions bookShelfCommonOptions;
    private static RequestOptions oppoCommonOptions;
    private static RequestOptions stackCommonOptions;
    private static RequestOptions avtarCircleCommonOptions;
    private static RequestOptions avtarProfileCircleCommonOptions;
    private static RequestOptions activateShowerOptions;
    private static RequestOptions avtarCommonOptions;
    private static RequestOptions headNoIconNoDefaultOptions;
    private static RequestOptions myMessageAvtarOptions;
    private static RequestOptions avtarSearchCircleCommonOptions;
    private static RequestOptions readerEndPagerPicOptions;
    private static RequestOptions advCommonOptions;
    //高斯模糊效果
    private static RequestOptions commonGaussBlurOptions;
    //书封圆角效果
    private static RequestOptions commonRoundCornorOptions4;
    //包月圆角广告
    private static RequestOptions commonRoundCornorOptions7;
    private static RequestOptions commonRoundCornorOptions1000;

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

    public static void displayImage(Context context, String url, Target target) {
        if (!isContextEnable(context)) {
            return;
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(target);
    }

    public static void displayImage(Context context, String url, RequestOptions options, Target target) {
        if (!isContextEnable(context)) {
            return;
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(target);
    }

    public static void displayImage(Context context, int resource, Target target, RequestOptions options) {
        if (!isContextEnable(context)) {
            return;
        }
        Glide.with(context)
                .asBitmap()
                .load(resource)
                .apply(options)
                .into(target);
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

    //普通头像
    public static synchronized RequestOptions getHeadIconOptions() {
        if (avtarCircleCommonOptions == null) {
            avtarCircleCommonOptions = new RequestOptions()
                    .placeholder(R.drawable.default_user_icon)
                    .error(R.drawable.default_user_icon)
                    .circleCrop();
        }
        return avtarCircleCommonOptions;
    }

    //我的头像
    public static synchronized RequestOptions getProfileHeadIconOptions() {
        if (avtarProfileCircleCommonOptions == null) {
            avtarProfileCircleCommonOptions = new RequestOptions()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.default_user_icon)
                    .error(R.drawable.default_user_icon)
                    .circleCrop();
        }
        return avtarProfileCircleCommonOptions;
    }

    //搜索直达头像
    public static synchronized RequestOptions getHeadIconSearchOptions() {
        if (avtarSearchCircleCommonOptions == null) {
            avtarSearchCircleCommonOptions = new RequestOptions()
                    .placeholder(R.drawable.default_user_icon)
                    .error(R.drawable.default_user_icon)
                    .circleCrop();
        }
        return avtarSearchCircleCommonOptions;
    }

    //消息头像
    public static synchronized RequestOptions getHeadIconMessageOptions() {
        if (myMessageAvtarOptions == null) {
            myMessageAvtarOptions = new RequestOptions()
                    .placeholder(R.drawable.default_user_icon)
                    .error(R.drawable.default_user_icon)
                    .circleCrop();
        }
        return myMessageAvtarOptions;
    }

    //书架激活图片
    public static synchronized RequestOptions getActivateShowerOptions() {
        if (activateShowerOptions == null) {
            activateShowerOptions = new RequestOptions()
                    .placeholder(R.color.activate_img_loading)
                    .error(R.color.activate_img_loading);
        }
        return activateShowerOptions;
    }

    //书架书封、信息流、书库分类里的书封等大部分地方，统一用这个
    public static synchronized RequestOptions getLocalstoreCommonOptions() {
        if (localstoreCommonOptions == null) {
            localstoreCommonOptions = new RequestOptions()
                    .placeholder(R.color.localstore_img_loading)
                    .error(R.color.localstore_img_loading);
        }
        return localstoreCommonOptions;
    }

    //书架书封、信息流、书库分类里的书封等大部分地方，统一用这个
    public static synchronized RequestOptions getBookShelfHeaderOptions() {
        if (localstoreCommonOptions == null) {
            localstoreCommonOptions = new RequestOptions()
                    .placeholder(R.color.color_C101)
                    .error(R.color.color_C101);
        }
        return localstoreCommonOptions;
    }

    //书库的tab的图片
    public static synchronized RequestOptions getStackTabCommonOption() {
        if (stackCommonOptions == null) {
            stackCommonOptions = new RequestOptions()
                    .placeholder(R.color.book_store_default_cover_color)
                    .error(R.color.book_store_default_cover_color);
        }
        return stackCommonOptions;
    }

    //书封圆角
    public static synchronized RequestOptions getRoundCornorOptions4() {
        if (commonRoundCornorOptions4 == null) {
            commonRoundCornorOptions4 = new RequestOptions()
                    .bitmapTransform(new RoundedCornersTransformation(CommonUtility.dip2px(4), 0))
                    .placeholder(R.color.localstore_img_loading)
                    .error(R.color.localstore_img_loading);
        }
        return commonRoundCornorOptions4;
    }

    public static synchronized RequestOptions getRoundCornorOptions7() {
        if (commonRoundCornorOptions7 == null) {
            commonRoundCornorOptions7 = new RequestOptions()
                    .bitmapTransform(new RoundedCornersTransformation(CommonUtility.dip2px(7), 0))
                    .placeholder(R.color.localstore_img_loading)
                    .error(R.color.localstore_img_loading);
        }
        return commonRoundCornorOptions7;
    }

    public static synchronized RequestOptions getRoundCornorOptions1000() {
        if (commonRoundCornorOptions1000 == null) {
            commonRoundCornorOptions1000 = new RequestOptions()
                    .bitmapTransform(new RoundedCornersTransformation(1000, 0))
                    .placeholder(R.color.localstore_img_loading)
                    .error(R.color.localstore_img_loading);
        }
        return commonRoundCornorOptions1000;
    }

    // 高斯模糊
    public static synchronized RequestOptions getGaussBlurCommonOptions() {
        if (commonGaussBlurOptions == null) {
            commonGaussBlurOptions = new RequestOptions()
                    .bitmapTransform(new BlurTransformation(25))
                    .placeholder(R.color.localstore_img_loading)
                    .error(R.color.localstore_img_loading);
        }
        return commonGaussBlurOptions;
    }

    // 书架文字链广告
    public static synchronized RequestOptions getBookShelfTextAdOptions() {
        if (readerEndPagerPicOptions == null) {
            readerEndPagerPicOptions = new RequestOptions()
                    .placeholder(R.drawable.icon_bookshelf_adtext_default)
                    .error(R.drawable.icon_bookshelf_adtext_default);
        }
        return readerEndPagerPicOptions;
    }

    public static synchronized RequestOptions getHeadIconNoDefaultOptions() {

        if (headNoIconNoDefaultOptions == null) {
            headNoIconNoDefaultOptions = new RequestOptions()
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        }
        return headNoIconNoDefaultOptions;
    }

    // 书架文字链广告
    public static void loadBookShelfTextAdIcon(ImageView view, String imageUrl) {
        if (view == null) {
            return;
        }
        if (!isContextEnable(view.getContext())) {
            return;
        }
        ImageUtils.displayImage(view.getContext(), imageUrl, view, getBookShelfTextAdOptions());
    }


    //书城 大banner
    public static synchronized RequestOptions getBookStoreBigBannerOptions() {
        if (localstoreCommonOptions == null) {
            localstoreCommonOptions = new RequestOptions()
                    .placeholder(R.drawable.bg_default_big_banner)
                    .error(R.color.localstore_img_loading);
        }
        return localstoreCommonOptions;
    }

    //书城 小banner
    public static synchronized RequestOptions getBookStoreSmallBannerOptions() {
        if (localstoreCommonOptions == null) {
            localstoreCommonOptions = new RequestOptions()
                    .placeholder(R.drawable.bg_default_small_banner)
                    .error(R.color.localstore_img_loading);
        }
        return localstoreCommonOptions;
    }

    //普通头像
    @BindingAdapter({"android:headIconUrl"})
    public static void loadHeadIcon(ImageView view, String headIconUrl) {
        if (view == null) {
            return;
        }
        if (!isContextEnable(view.getContext())) {
            return;
        }
        ImageUtils.displayImage(view.getContext(), headIconUrl, view, getHeadIconOptions());
    }

    //搜索直达头像
    @BindingAdapter({"android:headIconSearchUrl"})
    public static void loadHeadIconSearch(ImageView view, String headIconUrl) {
        if (view == null) {
            return;
        }
        if (!isContextEnable(view.getContext())) {
            return;
        }
        ImageUtils.displayImage(view.getContext(), headIconUrl, view, getHeadIconSearchOptions());
    }

    //消息头像
    @BindingAdapter({"android:headIconMessageUrl"})
    public static void loadHeadIconMessage(ImageView view, String headIconUrl) {
        if (view == null) {
            return;
        }
        if (!isContextEnable(view.getContext())) {
            return;
        }
        ImageUtils.displayImage(view.getContext(), headIconUrl, view, getHeadIconMessageOptions());
    }

    //没有默认图片的头像
    @BindingAdapter({"android:headIconNoDefaultUrl"})
    public static void loadHeadIconNoDefault(ImageView view, String headIconUrl) {
        if (view == null) {
            return;
        }
        if (!isContextEnable(view.getContext())) {
            return;
        }
        ImageUtils.displayImage(view.getContext(), headIconUrl, view);
    }

    //书架激活图片
    @BindingAdapter({"android:activateShowerIconUrl"})
    public static void loadActivateShowerIcon(ImageView view, String imageUrl) {
        if (view == null) {
            return;
        }
        if (!isContextEnable(view.getContext())) {
            return;
        }
        ImageUtils.displayImage(view.getContext(), imageUrl, view, getActivateShowerOptions());
    }


    //书架书封、信息流、书库分类里的书封等大部分地方，统一用这个
    @BindingAdapter({"android:localstoreIconUrl"})
    public static void loadLocalstoreIcon(ImageView view, String imageUrl) {
        if (view == null) {
            return;
        }
        if (!isContextEnable(view.getContext())) {
            return;
        }
        ImageUtils.displayImage(view.getContext(), imageUrl, view, getLocalstoreCommonOptions());
    }

    //书库的tab的图片
    @BindingAdapter({"android:stackTabCommonIconUrl"})
    public static void loadStackTabCommonIcon(ImageView view, String imageUrl) {
        if (view == null) {
            return;
        }
        if (!isContextEnable(view.getContext())) {
            return;
        }
        ImageUtils.displayImage(view.getContext(), imageUrl, view, getStackTabCommonOption());
    }
}
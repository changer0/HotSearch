package com.lulu.baseutil;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.lulu.baseutil.bezelless.samsung.DisplayCutoutWrapper;
import com.lulu.baseutil.bezelless.samsung.WindowInsetsWrapper;
import com.yuewen.component.baseutil.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhanglulu
 * @date : 2020/12/16 3:39 PM
 */
public class SpecialScreenUtils {
    private static String HUAWEI_EMUI_ROM_VERSION_3_1 = "EmotionUI_3.1";
    private static boolean SPECIFIC_ROM_VERSION_HAS_CHECKED = false;
    private static String SPECIFIC_ROM_VERSION_NAME = "";
    private static boolean IS_MEIZU_DEVICE;

    /**
     * 一些SystemUiVisibility的参数
     */
    public static final int SYSTEM_UI_FLAG_IMMERSIVE = 0x00000800;
    public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = 0x00001000;
    public static final int SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN = 0x00000400;
    public static final int SYSTEM_UI_FLAG_HIDE_NAVIGATION = 0x00000002;
    public static final int SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION = 0x00000200;
    public static final int SYSTEM_UI_FLAG_FULLSCREEN = 0x00000004;
    public static final int SYSTEM_UI_FLAG_LAYOUT_STABLE = 0x00000100;
    public static final int SYSTEM_UI_FLAG_VISIBLE = 0x00000000;
    public static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 0x00002000;

    private static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT = 0;
    private static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES = 1;
    private static final int LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER = 2;

    private static final String DISPLAY_NOTCH_STATUS = "display_notch_status";

    /**
     * AndroidP凹形区的高度
     */
    private static int cutoutHeight = 0;

    /**
     * AndroidP挖孔屏幕矩形区域right值
     */
    private static int cutOutLeft;
    /**
     * 初始化AndroidP手机的刘海屏配置
     *
     * @param window window对象
     */
    public static void initDisplayCutout(final Window window) {
        if (window == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // 处理Android P及以上的刘海屏
            // 设置允许填充到凹形区
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            final View decorView = window.getDecorView();
            decorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                    if (decorView.getRootWindowInsets() == null) {
                        return null;
                    }

                    // 获取刘海参数
                    DisplayCutout displayCutout = decorView.getRootWindowInsets().getDisplayCutout();
                    if (displayCutout != null) {
                        // 如果有刘海
                        cutoutHeight = cutOutLeft = Math.max(displayCutout.getSafeInsetTop(), displayCutout.getSafeInsetLeft());
                    }
                    decorView.setOnApplyWindowInsetsListener(null);

                    //listener.onApplyWindowInsets方法，会拦截View.onApplyWindowInsets方法，从而导致contentView拓展到navigator的下面，这是调用一下默认实现。
                    insets = decorView.onApplyWindowInsets(insets);

                    return insets;
                }
            });
        } else if (isSamsungBezelLessDevice()) {
            // 处理三星9.0以下的挖孔屏设备
            samsungReflectBezelLess(window);
        }
    }

    /**
     * 处理三星8.1版本挖孔屏的逻辑
     *
     * @param window
     */
    private static void samsungReflectBezelLess(Window window) {
        if (window == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            WindowManager.LayoutParams lp = window.getAttributes();
            try {
                Field field = lp.getClass().getField("layoutInDisplayCutoutMode");
                field.setAccessible(true);
                // LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT 0
                // LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES 1
                // LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER 2
                field.setInt(lp, LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
                window.setAttributes(lp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            final View decorView = window.getDecorView();
            decorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                    if (insets == null) {
                        return null;
                    }
                    // 获取刘海参数
                    try {
                        WindowInsetsWrapper windowInsetsWrapper = new WindowInsetsWrapper(insets);
                        DisplayCutoutWrapper displayCutoutWrapper = windowInsetsWrapper.getDisplayCutoutWrapper();
                        if (displayCutoutWrapper != null) {
                            // 如果有刘海
                            cutoutHeight = displayCutoutWrapper.getSafeInsetTop();
                            if (displayCutoutWrapper.getBoundingRects() != null && displayCutoutWrapper.getBoundingRects().size() >= 1) {
                                Rect rect = displayCutoutWrapper.getBoundingRects().get(0);
                                if (rect != null) {
                                    cutOutLeft = rect.right;
                                }
                            }
                        }
                        decorView.setOnApplyWindowInsetsListener(null);

                        //listener.onApplyWindowInsets方法，会拦截View.onApplyWindowInsets方法，从而导致contentView拓展到navigator的下面，这是调用一下默认实现。
                        insets = decorView.onApplyWindowInsets(insets);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return insets;
                }
            });
        }
    }

    /**
     * 获取异形屏幕顶部高度
     *
     * @return
     */
    public static int getCutoutHeight() {
        return cutoutHeight;
    }

    public static int getCutOutLeft() {
        return cutOutLeft;
    }

    /**
     * 通过反射的方式来查看是否是VIVO刘海屏
     *
     * @return 是否是刘海屏
     */
    private static boolean isVivoBezelLessDevice() {
        Boolean result = false;
        try {
            Class<?> clazz = Class.forName("android.util.FtFeature");
            Method method = clazz.getMethod("isFeatureSupport", int.class);
            if (method != null) {
                // 通过是否有刘海来判断是否是全面屏
                int vivoHasGroove = 0x00000020;
                result = (Boolean) method.invoke(clazz.newInstance(), vivoHasGroove);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result.booleanValue();
    }


    /**
     * 判断是否是OPPO刘海屏
     *
     * @param context 上下文
     * @return 是否刘海屏
     */
    public static boolean isOppoBezelLessDevice(Context context) {
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    /**
     * 判断是否是HUAWEI刘海屏
     *
     * @param context 上下文
     * @return 是否是刘海屏
     */
    private static boolean isHuaweiBezelLessDevice(Context context) {
        Boolean result = false;
        try {
            Class<?> clazz = Class.forName("com.huawei.android.util.HwNotchSizeUtil");
            Method method = clazz.getMethod("hasNotchInScreen");
            if (method != null) {
                result = (Boolean) method.invoke(clazz.newInstance());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断是否是小米刘海屏
     *
     * @param context 上下文
     * @return 是否是刘海屏
     */
    private static boolean isXiaomiBezelLessDevice(Context context) {
        try {
            ClassLoader cl = context.getClassLoader();
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");

            //Parameters Types
            Class[] paramTypes = new Class[2];
            paramTypes[0] = String.class;
            paramTypes[1] = int.class;

            Method getInt = SystemProperties.getMethod("getInt", paramTypes);

            //Parameters
            Object[] params = new Object[2];
            params[0] = "ro.miui.notch";
            params[1] = 0;

            return (Integer) getInt.invoke(SystemProperties, params) == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 判断是否是联想刘海屏
     *
     * @param context 上下文
     * @return 是否是刘海屏
     */
    private static boolean isLenovoBezelLessDevice(Context context) {
        try {
            return context.getResources().getBoolean(context.getResources().getIdentifier("config_screen_has_notch", "bool", "android"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断是否是三星挖孔屏
     * @return 是否是刘海屏
     */
    private static boolean isSamsungBezelLessDevice() {
        try {
            final Resources res = Resources.getSystem();
            final int resId = res.getIdentifier("config_mainBuiltInDisplayCutout", "string", "android");
            final String spec = resId > 0 ? res.getString(resId) : null;
            return !TextUtils.isEmpty(spec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断AndroidP手机是否是刘海屏
     *
     * @return 是否是刘海屏
     */
    private static boolean isAndroidPBezelLessDevice() {
        return cutoutHeight > 0 || cutOutLeft > 0;
    }

    public static final int DEVICE_BRAND_OPPO_BEZEL_LESS = 1;
    public static final int DEVICE_BRAND_VIVO_BEZEL_LESS = 2;
    public static final int DEVICE_BRAND_HUAWEI_BEZEL_LESS = 3;
    public static final int DEVICE_BRAND_XIAOMI_BEZEL_LESS = 4;
    public static final int DEVICE_BRAND_LENOVO_BEZEL_LESS = 5;
    public static final int DEVICE_BRAND_ANDROID_P_BEZEL_LESS = 6;
    public static final int DEVICE_BRAND_SAMSUNG_BEZEL_LESS = 7;

    /**
     * 是否是全面屏，如果是则返回手机厂商
     *
     * @param context 上下文
     * @return 0 = 不是全面屏，!0 = 手机厂商
     */
    public static int isBezelLessWithDeviceBrand(Context context) {
        String deviceBrandStr = Build.BRAND.toLowerCase();

        // 优先判断是否是AndroidP手机
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // 如果是AndroidP手机
            return isAndroidPBezelLessDevice() ? DEVICE_BRAND_ANDROID_P_BEZEL_LESS : 0;
        }

        if (deviceBrandStr.contains("oppo")) {
            return isOppoBezelLessDevice(context) ? DEVICE_BRAND_OPPO_BEZEL_LESS : 0;
        } else if (deviceBrandStr.contains("vivo")) {
            return isVivoBezelLessDevice() ? DEVICE_BRAND_VIVO_BEZEL_LESS : 0;
        } else if (deviceBrandStr.contains("huawei") || deviceBrandStr.contains("honor")) {
            return isHuaweiBezelLessDevice(context) ? DEVICE_BRAND_HUAWEI_BEZEL_LESS : 0;
        } else if (deviceBrandStr.contains("xiaomi")) {
            return isXiaomiBezelLessDevice(context) ? DEVICE_BRAND_XIAOMI_BEZEL_LESS : 0;
        } else if (deviceBrandStr.contains("lenovo") || deviceBrandStr.contains("motorola")) {
            return isLenovoBezelLessDevice(context) ? DEVICE_BRAND_LENOVO_BEZEL_LESS : 0;
        } else if (deviceBrandStr.contains("samsung")) {
            return isSamsungBezelLessDevice() ? DEVICE_BRAND_SAMSUNG_BEZEL_LESS : 0;
        } else {
            return 0;
        }
    }

    /**
     * OPPO全面屏的margin
     */
    private static final int OPPO_MARGIN = 80;


    /**
     * 得到全面屏左上右下四个间距
     *
     * @param context 上下文
     * @return 左上右下四个间距
     */
    public static int[] getMargins(Context context) {
        // 左上右下四个间距
        int[] margins = new int[]{0, 0, 0, 0};
        int deviceBrand = isBezelLessWithDeviceBrand(context);
        if (deviceBrand == 0) {
            // 非刘海屏
            return margins;
        }
        if (!isFillCutout(context, deviceBrand)) {
            // 非填充凹形区
            return margins;
        }

        switch (deviceBrand) {
            case DEVICE_BRAND_OPPO_BEZEL_LESS: {
                margins[0] = margins[1] = margins[2] = OPPO_MARGIN;
                margins[3] = 0;
                break;
            }
            case DEVICE_BRAND_VIVO_BEZEL_LESS: {
                margins[0] = margins[2] = (int) (DeviceUtil.getStatusBarHeight() * 1.5f);
                margins[1] = DeviceUtil.getStatusBarHeight();
                margins[3] = (int) (DeviceUtil.getStatusBarHeight() * 0.75f);
                break;
            }
            case DEVICE_BRAND_HUAWEI_BEZEL_LESS: {
                margins[0] = margins[1] = margins[2] = DeviceUtil.getStatusBarHeight();
                margins[3] = 0;
                break;
            }
            case DEVICE_BRAND_XIAOMI_BEZEL_LESS: {
                margins[0] = margins[1] = margins[2] = DeviceUtil.getStatusBarHeight();
                margins[3] = 0;
                break;
            }
            case DEVICE_BRAND_LENOVO_BEZEL_LESS: {
                margins[0] = margins[1] = margins[2] = DeviceUtil.getStatusBarHeight();
                margins[3] = 0;
                break;
            }
            case DEVICE_BRAND_SAMSUNG_BEZEL_LESS: {
                margins[0] = margins[2] = cutOutLeft;
                margins[1] = cutoutHeight == 0 ? DeviceUtil.getStatusBarHeight() : cutoutHeight;
                margins[3] = 0;
                break;
            }
            case DEVICE_BRAND_ANDROID_P_BEZEL_LESS: {
                margins[0] = cutOutLeft;
                margins[1] = cutoutHeight;
                margins[3] = margins[2] = 0;
                break;
            }
            default:
                break;
        }

        return margins;
    }

    /**
     * 是否填充凹形区
     *
     * @param context     上下文
     * @param deviceBrand 设备厂商
     * @return 是否填充
     */
    public static boolean isFillCutout(Context context, int deviceBrand) {
        if (deviceBrand == 0) {
            // 如果不是刘海屏则返回不填充
            return false;
        }

        boolean isEnableCutout = true;
        switch (deviceBrand) {
            case DEVICE_BRAND_OPPO_BEZEL_LESS: {
                // OPPO刘海屏
                Object object = null;
                String pkg = context.getPackageName();
                try {
                    Class ColorDisplayCompatUtils = Class.forName("com.color.util.ColorDisplayCompatUtils");
                    Method getInstance = ColorDisplayCompatUtils.getMethod("getInstance");
                    Object sColorDisplayCompatUtils = getInstance.invoke(null);
                    Method method = ColorDisplayCompatUtils.getMethod("shouldNonImmersiveAdjustForPkg", pkg.getClass());
                    object = method.invoke(sColorDisplayCompatUtils, pkg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (object != null) {
                    String str = object.toString();
                    if (String.valueOf(true).equalsIgnoreCase(str)) {
                        isEnableCutout = false;
                    }
                }
                break;
            }
            case DEVICE_BRAND_HUAWEI_BEZEL_LESS: {
                // 华为刘海屏
                isEnableCutout = Settings.Secure.getInt(context.getContentResolver(), DISPLAY_NOTCH_STATUS, 0) == 0;
                break;
            }
            case DEVICE_BRAND_XIAOMI_BEZEL_LESS: {
                // 小米刘海屏
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    isEnableCutout = Settings.Global.getInt(context.getContentResolver(), "force_black", 0) == 0;
                }
                break;
            }
            default: {
                // 默认为填充
                break;
            }
        }
        return isEnableCutout;
    }


    public static boolean checkNavigationBarHasShow(View anchor) {
        boolean show=false;
        if (Build.VERSION.SDK_INT >16){
            if (anchor.getContext() instanceof Activity){
                Activity activity= (Activity) anchor.getContext();
                Display display = activity.getWindow().getWindowManager().getDefaultDisplay();
                Point point = new Point();
                display.getRealSize(point);

                View decorView = activity.getWindow().getDecorView();
                Configuration conf = activity.getResources().getConfiguration();
                if (Configuration.ORIENTATION_LANDSCAPE == conf.orientation) {
                    View contentView = decorView.findViewById(android.R.id.content);
                    show = (point.x != contentView.getWidth());
                } else {
                    Rect rect = new Rect();
                    decorView.getWindowVisibleDisplayFrame(rect);
                    show = (rect.bottom != point.y);
                }
            }
        }
        return show;
    }

    public static void addImmersiveStatusBarFlag(Activity activity) {
        //某些版本rom不适用5.0以上的设置方式
        if (Build.VERSION.SDK_INT >= 21 && (!isSpecificRomVersion21())) {// 5.0以上使用FLAG_DRAWS_SYSTEM_BAR_BACKGROUND
            Window window = activity.getWindow();
            // FLAG_TRANSLUCENT_STATUS=0x04000000
            window.clearFlags(0x04000000);
            // SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN=0x00000400
            window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            // FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS=0x80000000
            window.addFlags(0x80000000);
        } else if (Build.VERSION.SDK_INT >= 19) {// 4.4以上使用FLAG_TRANSLUCENT_STATUS
            Window window = activity.getWindow();
            window.clearFlags(0x80000000);
            window.addFlags(0x04000000);
        }
        setStatusBarWithColor(activity, Color.TRANSPARENT);
        setStatusBarLightMode(activity);
    }
    public static boolean isSpecificRomVersion21() {
        if (!SPECIFIC_ROM_VERSION_HAS_CHECKED) {
            SPECIFIC_ROM_VERSION_HAS_CHECKED = true;
            SPECIFIC_ROM_VERSION_NAME = DeviceUtil.getSystemProperty("ro.build.version.emui");
        }
        //华为EMUI 3.1 基于andorid 5.1系统，但沉浸式状态栏设置不适用5.1的设置方式
        if (HUAWEI_EMUI_ROM_VERSION_3_1.equals(SPECIFIC_ROM_VERSION_NAME)) {
            return true;
        }
        return false;
    }

    public static void setStatusBarWithColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= 21) {// 5.0以上使用FLAG_DRAWS_SYSTEM_BAR_BACKGROUND
            Window window = activity.getWindow();
            try {
                Class<?> windowClass = Class.forName("android.view.Window");
                Method setSBCMethod = windowClass.getDeclaredMethod(
                        "setStatusBarColor", int.class);
                setSBCMethod.invoke(window, color);// 透明状态栏
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                window.clearFlags(0x80000000);
                window.addFlags(0x04000000);
            }
        } else if (Build.VERSION.SDK_INT >= 19) {// 4.4以上使用FLAG_TRANSLUCENT_STATUS
            if (color != Color.TRANSPARENT) {
                createStatusBar(activity, color);// 4.4需要自己绘制一个view控制状态栏颜色
            }
        }
    }
    private static void createStatusBar(Activity activity, int color) {
        // createISBView
        View ISBView = new View(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                DeviceUtil.getStatusBarHeight());
        params.gravity = Gravity.TOP;
        ISBView.setLayoutParams(params);
        ISBView.setVisibility(View.VISIBLE);
        // set color
        ISBView.setBackgroundColor(color);
        // addISBView
        ((ViewGroup) activity.getWindow().getDecorView()).addView(ISBView);
    }

    private static int statusbar_type = -1;

    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public static void setStatusBarLightMode(Activity activity) {
        if (statusbar_type > 0) {
            if (statusbar_type == 1) {
                StatusBarBlackTextFixer.MIUISetStatusBarLightMode(activity, true);
            } else if (statusbar_type == 2) {
                StatusBarBlackTextFixer.FlymeSetStatusBarLightMode(activity.getWindow(), true);
            } else if (statusbar_type == 3) {
                activity.getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else if (statusbar_type == 1000) {
                setStatusBarWithColor(activity, activity.getResources().getColor(R.color.status_bar_bg)); //状态栏设为灰色
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                if (StatusBarBlackTextFixer.MIUISetStatusBarLightMode(activity, true)) {
                    statusbar_type = 1;
                } else if (StatusBarBlackTextFixer.FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
                    statusbar_type = 2;
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activity.getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    statusbar_type = 3;
                } else {
                    statusbar_type = 1000;
                }
            }
        }
    }
    /**
     * 状态栏暗色模式，清除MIUI、flyme或6.0以上版本状态栏黑色文字、图标
     */
    public static void setStatusBarDarkMode(Activity activity) {
        if (activity == null) {
            return;
        }
        if (statusbar_type == 1) {
            StatusBarBlackTextFixer.MIUISetStatusBarLightMode(activity, false);
        } else if (statusbar_type == 2) {
            StatusBarBlackTextFixer.FlymeSetStatusBarLightMode(activity.getWindow(), false);
        } else if (statusbar_type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (statusbar_type == 1000) {
            setStatusBarWithColor(activity, Color.TRANSPARENT);
        }

    }
    public static void checkNightBar(Activity activity, boolean isNight){
        if (!isNight) {
            setStatusBarLightMode(activity);
        } else {
            setStatusBarDarkMode(activity);
        }

    }


}

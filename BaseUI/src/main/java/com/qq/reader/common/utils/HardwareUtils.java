package com.qq.reader.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.qq.reader.core.BaseApplication;
import com.tencent.mars.xlog.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

import static com.qq.reader.common.utils.ScreenModeUtils.cutOutHeight;

/**
 * Created by liuxiaoyang on 2018/4/3.
 * 硬件相关工具类
 * 判断是否是异形屏
 * 判断是否是全面屏
 * 等硬件相关的工具类
 * 不要放业务代码
 */

public class HardwareUtils {

    /**
     * 获取默认和隐藏刘海区开关值接口
     */
    public static final String DISPLAY_NOTCH_STATUS = "display_notch_status";

    /**
     * 是否是异形全面屏手机
     * 根据Build.BRAND(品牌)分别判断vivo oppo是否是异形屏
     */
    public static boolean isBezelLessDevice(Context context) {
        boolean ret = false;

        String brand = Build.BRAND.toLowerCase();
        if (brand.contains("oppo")) {
            ret = context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
        } else if (brand.contains("vivo")) {
            ret = isVivoBezelLessDevice();
        } else if (brand.contains("honor") || brand.contains("huawei")) {
            ret = isHuaweiBezelLessDevice(context);
        } else if (brand.contains("xiaomi") ) {
            ret = isXiaomiBezelLessDevice(context);
        } else if (cutOutHeight > 0) {
            return true;
        }
        // 测试使用
//		ret = true;
        Log.d("BezelLess", brand + " isBezelLessDevice " + ret);
        return ret;
    }

    /**
     * 如果应用不在凹槽区显示返回true oppo
     * 异形屏手机，部分手机支持用户手动设置不在异形屏显示区域显示，支持异形屏后不做处理会有显示bug
     * @param pkg
     * @return
     */
    public static boolean shouldNonImmersiveAdjustForPkg(String pkg) {
        boolean result = false;
        Object object = null;
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
            if ("true".equalsIgnoreCase(str)) {
                return true;
            }
        }
        return result;
    }

    /**
     * 如果应用不在凹槽区显示返回true oppo & huawei
     * 异形屏手机，部分手机支持用户手动设置不在异形屏显示区域显示，支持异形屏后不做处理会有显示bug
     * @return
     * TODO 其他手机支持
     */
    public static boolean shouldNonImmersiveAdjust() {
        boolean ret = false;
        String brand = Build.BRAND.toLowerCase();
        if (brand.contains("oppo")) {
            ret = shouldNonImmersiveAdjustForPkg(BaseApplication.Companion.getINSTANCE().getPackageName());
        } else if (brand.contains("vivo")) {
        } else if (brand.contains("honor") || brand.contains("huawei")) {
            ret = isNothSwitchOpen(BaseApplication.Companion.getINSTANCE());
        }
        Log.d("BezelLess","isNothSwitchOpen ret = " + ret);
        return  ret;
    }

    /**
     * 是否是vivo全面屏手机
     * <p>
     * 通过反射看屏幕是否有凹槽来判断是否是全名屏手机。
     *
     * @return
     */
    @SuppressLint("PrivateApi")
    public static boolean isVivoBezelLessDevice() {

        int isHaveGroove = 0x00000020;
        Boolean result = false;
        try {
            Class<?> clazz = Class.forName("android.util.FtFeature");
            Method m1 = clazz.getMethod("isFeatureSupport", int.class);
            if (m1 != null) {
                result = (Boolean) m1.invoke(clazz.newInstance(), isHaveGroove);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
//		return true;
    }

    /**
     * 是否是huwawei全面屏手机
     * <p>
     * 通过反射看屏幕是否有凹槽来判断是否是全名屏手机。
     *
     * @return
     */
    private static boolean isHuaweiBezelLessDevice(Context context){
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
            Log.d("BezelLess", "isHuaweiBezelLessDevice " + ret);
        } catch (ClassNotFoundException e) {
            Log.e("test", "hasNotchInScreen ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "hasNotchInScreen NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "hasNotchInScreen Exception");
        } finally {
            return ret;
        }
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
     * 获取是否打开隐藏刘海区开关，华为特有，其他机型不生效
     * @param context
     * @return
     */
    public static boolean isNothSwitchOpen(Context context) {
        if (FlavorUtils.isCommonUI()) {
            int mIsNotchSwitchOpen = Settings.Secure.getInt(context.getContentResolver(), DISPLAY_NOTCH_STATUS, 0);//0表示默认，1表示打开
            return mIsNotchSwitchOpen == 1;
        }
        return false;
    }

    /**
     * 获取是否是小米机型
     *
     * @return
     */
    public static boolean isXiaomiDevice() {
        String brand = Build.BRAND.toLowerCase();
        return brand == null ? false : brand.contains("xiaomi");
    }

}

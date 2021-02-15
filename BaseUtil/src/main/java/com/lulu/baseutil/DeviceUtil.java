package com.lulu.baseutil;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewConfiguration;

import com.lulu.baseutil.emulatorcheck.EmulatorCheckUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * 设备工具类
 */
public class DeviceUtil {
    /**
     * 获取状态栏高度
     *
     */
    public static int getStatusBarHeight() {
        int result = 0;
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        if (result <= 0) {
            result = CommonUtil.dp2px(25);
        }
        return result;
    }

    /**
     * 获取导航啦高度
     */
    public static int getNavigationBarHeight() {
        try {
            Resources resources = Resources.getSystem();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            int height = resources.getDimensionPixelSize(resourceId);
            Log.v("dbw", "Navi height:" + height);
            return height;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 获取屏幕宽度xt
     * @return
     */
    public static int getScreenWidth() {
        Resources resources = Resources.getSystem();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight() {
        Resources resources = Resources.getSystem();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取当前路径大小
     */
    public static long getAvailableSize(String path) {
        try {
            final StatFs statFs = new StatFs(path);
            long blockSize = 0;
            long availableBlocks = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = statFs.getBlockSizeLong();
                availableBlocks = statFs.getAvailableBlocksLong();
            } else {
                blockSize = statFs.getBlockSize();
                availableBlocks = statFs.getAvailableBlocks();
            }
            return availableBlocks * blockSize;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static final long SPACE_LEFT = 1024 * 1024 * 2;

    /**
     * 判断是否有足够的空间供下载
     */
    public static boolean hasEnoughSDCardSpace(Context context) {
        try {
            StatFs statFs = new StatFs(FileUtil.getStorageFileDir(context).getPath());
            // sd卡可用分区数
            int avCounts = statFs.getAvailableBlocks();
            // 一个分区数的大小
            long blockSize = statFs.getBlockSize();
            // sd卡可用空间
            long spaceLeft = avCounts * blockSize;
            if (spaceLeft < SPACE_LEFT) {
                return false;
            }
        } catch (Throwable t) {
            return false;
        }

        return true;
    }

    /**
     * 是否为模拟器
     */
    public static boolean isEmulator(Context context) {
        return EmulatorCheckUtil.isEmulator(context);
    }

    /**
     * 判断底部是否有虚拟导航栏 （true：虚拟导航栏，false：物理导航栏）
     * //tip: 全面屏手机将导航键隐藏使用全屏手势时仍返回true
     *
     * @param activity
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context activity) {
        // 魅族M460A不能控制底部虚拟导航栏
        if (Build.VERSION.SDK_INT < 19 || Build.MODEL.contains("M460A") || activity == null) {
            return false;
        }

        //update by p_jwcao  at 2017.9.4 ,fix xiaomi 5(miui 8.5) bug
        //http://blog.etongwl.com/archives/1030.html
        Resources res = activity.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            // 通过判断设备是否有菜单键(不是虚拟键,是手机屏幕外的物理按键)来确定是否有navigation bar
            return !ViewConfiguration.get(activity.getApplicationContext()).hasPermanentMenuKey();
        }
    }
    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    /**
     * 获取系统属性
     * @param propName
     * @return
     */
    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (Exception e) {
            Log.e("getSystemPropertyError", e.getMessage());
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.e("getSystemPropertyError", e.getMessage());
                }
            }
        }
        return line;
    }

}

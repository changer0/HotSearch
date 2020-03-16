package com.qq.reader.common.utils;

import android.app.Activity;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.qq.reader.core.BaseApplication;
import com.tencent.mars.xlog.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 系统同事工具类
 * create by liujain
 * 2019/12/18
 */
public class SystemNotificationUtil {

    public static final String TAG = "SystemNotificationUtil";

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    /**
     * 判断是否打开接收推送通知提醒
     *
     * @return
     */
    public static boolean isOpen() {
//        if (HardwareUtils.isXiaomiDevice()) {
//            NotificationManagerCompat manager = NotificationManagerCompat.from(AbsReaderApplication.getInstance());
//            // areNotificationsEnabled方法的有效性官方只最低支持到API 19，低于19的仍可调用此方法不过只会返回true，即默认为用户已经开启了通知。
//            return manager.areNotificationsEnabled();
//        }
        return isNotificationEnabled();
    }

    /**
     * 跳转APP系统通知提醒界面
     */
    public static void goSetting(Activity activity) {
        if (activity == null) {
            return;
        }

        if (HardwareUtils.isXiaomiDevice()) {
            //小米应用详情界面数据无效,直接跳转通知设置详
            gotoNotificationSetting(activity);
        } else {
            //其余跳转APP应用详情页
            try {
                Intent intent = new Intent();
                //下面这种方案是直接跳转到当前应用的设置界面。
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
            } catch (Exception e) {
                Log.i(TAG, "goAppNoticeReminderSetting--e=" + e.toString());
            }

        }
    }


    /**
     * 判断是否开启通知权限
     */
    private static boolean isNotificationEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return isEnableV26();
        } else {
            return isEnableV19();
        }
    }

    /**
     * Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
     * 19及以上
     */
    private static boolean isEnableV19() {
        try {
            BaseApplication baseApplication = BaseApplication.Companion.getINSTANCE();
            AppOpsManager mAppOps = (AppOpsManager) baseApplication.getSystemService(Context.APP_OPS_SERVICE);
            ApplicationInfo appInfo = baseApplication.getApplicationInfo();
            String pkg = baseApplication.getApplicationContext().getPackageName();

            int uid = appInfo.uid;
            Class appOpsClass;
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e) {
        } catch (NoSuchFieldException e) {
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {

        } catch (Exception e) {

        }
        return false;
    }

    /**
     * Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
     * 针对8.0及以上设备
     */
    private static boolean isEnableV26() {
        try {
            BaseApplication baseApplication = BaseApplication.Companion.getINSTANCE();
            NotificationManager notificationManager = (NotificationManager)
                    baseApplication.getSystemService(Context.NOTIFICATION_SERVICE);
            Method sServiceField = notificationManager.getClass().getDeclaredMethod("getService");
            sServiceField.setAccessible(true);
            Object sService = sServiceField.invoke(notificationManager);

            ApplicationInfo appInfo = baseApplication.getApplicationInfo();
            String pkg = baseApplication.getPackageName();
            int uid = appInfo.uid;

            Method method = sService.getClass().getDeclaredMethod("areNotificationsEnabledForPackage", String.class, Integer.TYPE);
            method.setAccessible(true);
            return (boolean) method.invoke(sService, pkg, uid);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 跳转到系统通知权限设置界面
     *
     * @param activity
     */
    private static void gotoNotificationSetting(Activity activity) {
        if (activity == null) {
            return;
        }
        ApplicationInfo appInfo = BaseApplication.Companion.getINSTANCE().getApplicationInfo();
        String pkg = BaseApplication.Companion.getINSTANCE().getPackageName();
        int uid = appInfo.uid;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, pkg);
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, uid);
                //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
                intent.putExtra("app_package", pkg);
                intent.putExtra("app_uid", uid);
                activity.startActivityForResult(intent, 9999);
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + BaseApplication.Companion.getINSTANCE().getPackageName()));
                activity.startActivityForResult(intent, 9999);
            } else {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                activity.startActivityForResult(intent, 9999);
            }
        } catch (Exception e) {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            activity.startActivityForResult(intent, 9999);
        }
    }

}

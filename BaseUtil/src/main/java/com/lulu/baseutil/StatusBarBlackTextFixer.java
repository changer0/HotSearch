package com.lulu.baseutil;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StatusBarBlackTextFixer {
        /**
         * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
         * 可以用来判断是否为Flyme用户
         *
         * @param window 需要设置的窗口
         * @param dark   是否把状态栏字体及图标颜色设置为深色
         * @return boolean 成功执行返回true
         */
        public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
            boolean result = false;
            if (window != null) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //已验证魅族android7.0 、8.0手机用老的方法设置无效且不会报错，所以此处还是走原系统api设置
                        if (dark) {
                            window.getDecorView().setSystemUiVisibility(SpecialScreenUtils.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SpecialScreenUtils.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        } else {
                            window.getDecorView().setSystemUiVisibility(SpecialScreenUtils.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                        }
                    } else {
                        WindowManager.LayoutParams lp = window.getAttributes();
                        Field darkFlag = WindowManager.LayoutParams.class
                                .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                        Field meizuFlags = WindowManager.LayoutParams.class
                                .getDeclaredField("meizuFlags");
                        darkFlag.setAccessible(true);
                        meizuFlags.setAccessible(true);
                        int bit = darkFlag.getInt(null);
                        int value = meizuFlags.getInt(lp);
                        if (dark) {
                            value |= bit;
                        } else {
                            value &= ~bit;
                        }
                        meizuFlags.setInt(lp, value);
                        window.setAttributes(lp);
                    }
                    result = true;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        /**
         * 需要MIUIV6以上
         *
         * @param activity
         * @param dark     是否把状态栏字体及图标颜色设置为深色
         * @return boolean 成功执行返回true
         */
        public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
            boolean result = false;
            Window window = activity.getWindow();
            if (window != null) {
                Class clazz = window.getClass();
                try {
                    int darkModeFlag = 0;
                    Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                    Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                    darkModeFlag = field.getInt(layoutParams);
                    Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                    if (dark) {
                        extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                    } else {
                        extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                    }
                    result = true;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                        if (dark) {
                            //View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                            activity.getWindow().getDecorView().setSystemUiVisibility(0x00000400 | 0x00002000);
                        } else {
                            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | SpecialScreenUtils.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                        }
                    }
                } catch (Exception e) {

                }
            }
            return result;
        }
    }
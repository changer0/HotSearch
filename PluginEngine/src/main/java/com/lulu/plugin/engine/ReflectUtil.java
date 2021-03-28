package com.lulu.plugin.engine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射操作集中放在这，保证逻辑清晰
 * 用于替换系统的 Instrumentation
 * @author zhanglulu
 */
@SuppressLint("PrivateApi")
public class ReflectUtil {
    public static final String METHOD_currentActivityThread = "currentActivityThread";
    public static final String CLASS_ActivityThread = "android.app.ActivityThread";
    public static final String FIELD_mInstrumentation = "mInstrumentation";
    public static final String TAG = "ReflectUtil";


    private static Instrumentation sInstrumentation;
    private static Instrumentation sActivityInstrumentation;
    private static Field sActivityThreadInstrumentationField;
    private static Field sActivityInstrumentationField;
    private static Object sActivityThread;

    public static boolean init() {
        //获取当前的ActivityThread对象
        Class<?> activityThreadClass = null;
        try {
            //找到 ActivityThread 类
            activityThreadClass = Class.forName(CLASS_ActivityThread);
            //找到 currentActivityThread 方法
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod(METHOD_currentActivityThread);
            currentActivityThreadMethod.setAccessible(true);
            //这是当前的 ActivityThread 对象
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            //拿到在ActivityThread类里面的原始 mInstrumentation 对象
            Field instrumentationField = activityThreadClass.getDeclaredField(FIELD_mInstrumentation);
            instrumentationField.setAccessible(true);
            sActivityThreadInstrumentationField = instrumentationField;

            //这个就是原始的 mInstrumentation 对象
            sInstrumentation = (Instrumentation) instrumentationField.get(currentActivityThread);
            sActivityThread = currentActivityThread;

            //Activity mInstrumentation Field
            sActivityInstrumentationField = Activity.class.getDeclaredField(FIELD_mInstrumentation);
            sActivityInstrumentationField.setAccessible(true);
            return true;
        } catch (ClassNotFoundException
                | NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 拿到当前 Instrumentation 对象
     * @return Instrumentation
     */
    public static Instrumentation getInstrumentation() {
        return sInstrumentation;
    }

    /**
     * 拿到当前的 ActivityThread
     * @return ActivityThread
     */
    public static Object getActivityThread() {
        return sActivityThread;
    }

    /**
     * 给 ActivityThread 设置 Instrumentation
     * @param activityThread ActivityThread
     * @param hookedInstrumentation HookedInstrumentation
     */
    public static void setInstrumentation(Object activityThread, HookedInstrumentation hookedInstrumentation) {
        try {
            sActivityThreadInstrumentationField.set(activityThread, hookedInstrumentation);
            if (PluginConstant.DEBUG) Log.e(TAG, "set hooked instrumentation");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给当前 Activity 设置 Instrumentation
     * @param activity 当前 Activity
     * @param manager PluginManager
     */
    public static void setActivityInstrumentation(Activity activity, PluginManager manager) {
        try {
            sActivityInstrumentation = (Instrumentation) sActivityInstrumentationField.get(activity);
            HookedInstrumentation instrumentation = new HookedInstrumentation(sActivityInstrumentation, manager);
            sActivityInstrumentationField.set(activity, instrumentation);
            if (PluginConstant.DEBUG) Log.e(TAG, "set activity hooked instrumentation");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射设置字段值
     *
     * @param clazz 反射类型
     * @param target 目标对象
     * @param field 字段名
     * @param value 值
     */
    public static void setField(Class clazz, Object target, String field, Object value) {
        try {
            Field f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            f.set(target, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

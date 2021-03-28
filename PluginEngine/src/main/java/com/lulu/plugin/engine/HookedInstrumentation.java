package com.lulu.plugin.engine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.ContextThemeWrapper;

import java.lang.reflect.Method;

/**
 * 自己实现一个Instrumentation，在里面做一些替换工作，然后去Hook掉系统持有的对象
 *
 * 在负责启动的execStartActivity设置为启动已注册的Activity，再在newActivity设置为实际要启动的插件的Activity。然后去Hook系统持有的该字段
 *
 * @author zhanglulu
 */
public class HookedInstrumentation extends Instrumentation implements Handler.Callback  {
    public static final String TAG = "HookedInstrumentation";
    protected Instrumentation mBase;
    private final PluginManager mPluginManager;

    public HookedInstrumentation(Instrumentation base, PluginManager pluginManager) {
        mBase = base;
        mPluginManager = pluginManager;
    }

    /**
     * 覆盖掉原始Instrumentation类的对应方法,用于插件内部跳转Activity时适配
     *
     * 在负责启动的execStartActivity设置为启动已注册的Activity 欺骗 AndroidManifest.xml 已注册
     *
     * @Override 应该使用 Override 但是由于 @hide 所以无法使用
     */
    @SuppressLint("DiscouragedPrivateApi")
    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {

        if (PluginConstant.DEBUG) Log.e(TAG, "execStartActivity");
        mPluginManager.hookToStubActivity(intent);

        try {
            //执行系统 Instrumentation 类的 execStartActivity
            Method execStartActivity = Instrumentation.class.getDeclaredMethod(
                    "execStartActivity", Context.class, IBinder.class, IBinder.class,
                    Activity.class, Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            return (ActivityResult) execStartActivity.invoke(mBase, who,
                    contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("do not support!!!" + e.getMessage());
        }
    }

    /**
     * 在newActivity设置为实际要启动的插件的Activity
     */
    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (PluginConstant.DEBUG) Log.e(TAG, "newActivity");

        if (mPluginManager.hookToPluginActivity(intent)) {
            String targetClassName = intent.getComponent().getClassName();
            PluginApp pluginApp = mPluginManager.getLoadedPluginApk();
            //用插件 ClassLoader 去加载这个 Activity !!!
            Activity activity = mBase.newActivity(pluginApp.mClassLoader, targetClassName, intent);
            activity.setIntent(intent);
            //给 Activity 的 ContextThemeWrapper 设置 mResources 对象 !
            ReflectUtil.setField(ContextThemeWrapper.class, activity, PluginConstant.FIELD_RESOURCES, pluginApp.mResources);
            return activity;
        }

        if (PluginConstant.DEBUG) Log.e(TAG, "super.newActivity(...)");
        return super.newActivity(cl, className, intent);
    }

    @Override
    public boolean handleMessage(Message message) {
        if (PluginConstant.DEBUG) Log.e(TAG, "handleMessage");
        return false;
    }


    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        if (PluginConstant.DEBUG) Log.e(TAG, "callActivityOnCreate");
        super.callActivityOnCreate(activity, icicle);
    }
}

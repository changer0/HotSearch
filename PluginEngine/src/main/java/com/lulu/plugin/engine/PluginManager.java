package com.lulu.plugin.engine;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * 插件框架基础功能
 * @author zhanglulu
 */
public class PluginManager {
    private final static String TAG = "PluginManager";
    @SuppressLint("StaticFieldLeak")
    private static PluginManager sInstance;
    private Context mContext;
    private PluginApp mPluginApp;

    public static PluginManager get() {
        if (sInstance == null) {
            sInstance = new PluginManager();
        }
        return sInstance;
    }

    private PluginManager() {
    }

    /**
     * 需要率先调用
     * @param context 上下文
     */
    public void init(Context context) {
        mContext = context.getApplicationContext();
        ReflectUtil.init();
        hookInstrumentation();
    }

    private void hookInstrumentation() {
        try {
            Instrumentation baseInstrumentation = ReflectUtil.getInstrumentation();
            final HookedInstrumentation instrumentation = new HookedInstrumentation(baseInstrumentation, this);
            Object activityThread = ReflectUtil.getActivityThread();
            ReflectUtil.setInstrumentation(activityThread, instrumentation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hookCurrentActivityInstrumentation(Activity activity) {
        ReflectUtil.setActivityInstrumentation(activity, sInstance);
    }

    /**
     * Hook SubActivity, 如果检查到时插件包则直接将 Intent 塞入 SubActivity 以及实际加载的插件中的包名和类名!
     * @param intent 用于替换的 Intent
     */
    public void hookToStubActivity(Intent intent) {
        if (PluginConstant.DEBUG) Log.e(TAG, "hookToStubActivity");

        if (intent == null || intent.getComponent() == null) {
            return;
        }
        String targetPackageName = intent.getComponent().getPackageName();
        String targetClassName = intent.getComponent().getClassName();

        if (mContext != null
                && !mContext.getPackageName().equals(targetPackageName)//非宿主包名
                && isPluginLoaded(targetPackageName)) {//插件包名 可做白名单配置
            if (PluginConstant.DEBUG) Log.e(TAG, "hook " +  targetClassName + " to " + PluginConstant.STUB_ACTIVITY);

            intent.setClassName(mContext.getPackageName(), PluginConstant.STUB_ACTIVITY);
            intent.putExtra(PluginConstant.KEY_IS_PLUGIN, true);
            intent.putExtra(PluginConstant.KEY_PACKAGE, targetPackageName);
            intent.putExtra(PluginConstant.KEY_ACTIVITY, targetClassName);
        }
    }

    /**
     * Hook 插件包中的 Activity
     * @param intent
     * @return
     */
    public boolean hookToPluginActivity(Intent intent) {
        if (PluginConstant.DEBUG) Log.e(TAG, "hookToPluginActivity");
        if (intent.getBooleanExtra(PluginConstant.KEY_IS_PLUGIN, false)) {
            String pkg = intent.getStringExtra(PluginConstant.KEY_PACKAGE);
            String activity = intent.getStringExtra(PluginConstant.KEY_ACTIVITY);
            if (PluginConstant.DEBUG) Log.e(TAG, "hook " + intent.getComponent().getClassName() + " to " + activity);
            intent.setClassName(pkg, activity);
            return true;
        }
        return false;
    }

    /**
     * 插件包名 可做白名单配置
     */
    private boolean isPluginLoaded(String packageName) {
        // TODO 检查packageNmae是否匹配
        return mPluginApp != null;
    }


    /**
     * 加载插件包
     *
     * @param apkPath 插件包路径
     * @return 插件 App
     */
    public PluginApp loadPluginApk(String apkPath) {
        checkInit();
        String addAssetPathMethod = "addAssetPath";
        PluginApp pluginApp = null;
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod(addAssetPathMethod, String.class);
            addAssetPath.invoke(assetManager, apkPath);
            Resources pluginRes = new Resources(assetManager,
                    mContext.getResources().getDisplayMetrics(),
                    mContext.getResources().getConfiguration());
            pluginApp = new PluginApp(pluginRes);
            pluginApp.mClassLoader = createDexClassLoader(apkPath);
        } catch (IllegalAccessException
                | InstantiationException
                | NoSuchMethodException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
        return pluginApp;
    }

    /**
     * 创建插件 DexClassLoader
     * @param apkPath 插件包路径
     * @return DexClassLoader
     */
    private DexClassLoader createDexClassLoader(String apkPath) {
        File dexOutputDir = mContext.getDir("dex", Context.MODE_PRIVATE);
        return new DexClassLoader(apkPath, dexOutputDir.getAbsolutePath(),
                null, mContext.getClassLoader());

    }

    /**
     * 加载插件
     *
     * @param apkPath 插件路径
     * @return 是否加载成功
     */
    public boolean loadPlugin(String apkPath) {
        if (mPluginApp != null) {
            return true;
        }
        File apk = new File(apkPath);
        if (!apk.exists()) {
            return false;
        }
        mPluginApp = loadPluginApk(apkPath);
        return mPluginApp != null;
    }

    public PluginApp getLoadedPluginApk() {
        return mPluginApp;
    }

    /**
     * 检查 初始化
     */
    private void checkInit() {
        if (mContext == null) {
            throw new RuntimeException("PluginManager::Please call the Init method before use ");
        }
    }

    /**
     * 启动插件 Activity !
     */
    public void startPluginActivity(Activity activity, Intent intent) {
        //Hook 当前 Activity 的 Instrumentation 对象
        hookCurrentActivityInstrumentation(activity);
        activity.startActivity(intent);
    }
}

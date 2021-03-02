package com.lulu.plugin.business.activity;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

import com.lulu.plugin.Constants;
import com.lulu.plugin.PluginApk;
import com.lulu.plugin.PluginManager;

import java.lang.reflect.Constructor;

/**
 * 系统实际启动的类
 * @author zhanglulu
 */
public class LifeCircleController implements ActivityLifecycle {
    Activity mProxy;
    PluginActivity mPlugin;
    Resources mResources;
    Resources.Theme mTheme;
    PluginApk mPluginApk;
    String mPluginClazz;

    public LifeCircleController(Activity activity) {
        mProxy = activity;
    }

    @Override
    public void onCreate(Bundle bundle) {
        mPluginClazz = bundle.getString(Constants.PLUGIN_CLASS_NAME);
        String packageName = bundle.getString(Constants.PACKAGE_NAME);
        mPluginApk = PluginManager.get().getPluginApk(packageName);
        try {
            mPlugin = (PluginActivity) loadPluginAble(mPluginApk.classLoader, mPluginClazz);
            mPlugin.attach(mProxy, mPluginApk);
            mResources = mPluginApk.pluginRes;
            mPlugin.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private Object loadPluginAble(ClassLoader classLoader, String pluginActivityClass)
            throws Exception {
        Class<?> pluginClz = classLoader.loadClass(pluginActivityClass);
        Constructor<?> constructor = pluginClz.getConstructor(new Class[] {});
        constructor.setAccessible(true);
        return constructor.newInstance(new Object[] {});
    }

    @Override
    public void onStart() {
        if (mPlugin != null) {
            mPlugin.onStart();
        }
    }

    @Override
    public void onResume() {
        if (mPlugin != null) {
            mPlugin.onResume();
        }
    }

    @Override
    public void onStop() {
        mPlugin.onStop();
    }

    @Override
    public void onPause() {
        mPlugin.onPause();
    }

    @Override
    public void onDestroy() {
        mPlugin.onDestroy();
    }

    public Resources getResources() {
        return mResources;
    }

    public Resources.Theme getTheme() {
        return mTheme;
    }

    public AssetManager getAssets() {
        return mResources.getAssets();
    }

    public void onSkinUpdate() {
        mPlugin.onSkinUpdate();
    }

}



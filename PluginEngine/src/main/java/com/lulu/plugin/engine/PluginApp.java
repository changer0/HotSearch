package com.lulu.plugin.engine;

import android.content.res.Resources;

/**
 * Plugin 描述
 * @author zhanglulu
 */
public class PluginApp {
    public Resources mResources;
    public ClassLoader mClassLoader;

    public PluginApp(Resources mResources) {
        this.mResources = mResources;
    }
}

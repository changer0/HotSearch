package com.lulu.plugin;

import android.content.pm.PackageInfo;
import android.content.res.Resources;

/**
 * 描述一个 APK 文件
 */
public class PluginApk {
    public PackageInfo packageInfo;
    public ClassLoader classLoader;
    public Resources pluginRes;

    public PluginApk(Resources pluginRes) {
        this.pluginRes = pluginRes;
    }

}

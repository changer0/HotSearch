package com.lulu.plugin.business;

import android.content.Intent;
import android.os.Bundle;

import com.lulu.baseutil.Init;
import com.lulu.plugin.Constants;
import com.lulu.plugin.business.activity.ProxyActivity;

/**
 * 插件启动类
 * @author zhanglulu
 */
public class PluginLauncher {

    /**
     * 启动 Activity
     * @param intent
     */
    public void startActivity(Intent intent) {
        Intent pluginIntent = new Intent(Init.context, ProxyActivity.class);
        Bundle extra = intent.getExtras();
        // complicate if statement
        if (extra == null || !extra.containsKey(Constants.PLUGIN_CLASS_NAME) && !extra.containsKey(Constants.PACKAGE_NAME)) {
            try {
                throw new IllegalAccessException("lack class of plugin and package name");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        pluginIntent.putExtras(intent);
        pluginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Init.context.startActivity(pluginIntent);
    }
}

package com.lulu.hotsearch

import android.app.Activity
import android.content.Intent
import android.view.View
import com.lulu.baseutil.Init
import com.lulu.basic.utils.ToastUtil
import com.lulu.plugin.engine.PluginConstant
import com.lulu.plugin.engine.PluginManager

object PluginTest {
    val PLUGIN_PATH = Init.ROOT_PATH + "plugin/" + "TestPlugin.apk"
    val PLUGIN_PACKAGE ="com.lulu.plugin.test"
    val PLUGIN_ACTIVITY ="com.lulu.plugin.libs.TestPluginActivity"
    fun testPlugin(view: View, activity: Activity?) {
        activity?:return
        //TestPlugin.apk
        view.setOnLongClickListener {
            if (PluginManager.get().loadPlugin(PLUGIN_PATH)) {
                ToastUtil.showShortToast("插件加载成功!")
                PluginManager.get().startPluginActivity(activity, Intent().apply {
                    setClassName(PLUGIN_PACKAGE, PLUGIN_ACTIVITY)
                })
            } else {
                ToastUtil.showShortToast("插件加载失败!")
            }
            true
        }
    }
}
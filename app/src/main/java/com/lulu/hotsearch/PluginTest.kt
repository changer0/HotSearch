package com.lulu.hotsearch

import android.app.Activity
import android.content.Intent
import android.view.View
import com.lulu.baseutil.Init
import com.lulu.basic.utils.ToastUtil
import com.lulu.plugin.engine.PluginManager

object PluginTest {
    private val PLUGIN_PATH = Init.ROOT_PATH + "plugin/" + "TestPlugin.apk"
    private const val PLUGIN_PACKAGE ="com.lulu.plugin.test"
    private const val PLUGIN_ACTIVITY ="com.lulu.plugin.libs.TestPluginActivity"
    fun testPlugin(view: View, activity: Activity?) {
        //if (true) return

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
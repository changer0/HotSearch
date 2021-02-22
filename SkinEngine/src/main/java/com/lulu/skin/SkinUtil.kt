package com.lulu.skin

import android.content.Context

public object SkinUtil {

    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    @Synchronized
    public fun getPackageName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                    context.packageName, 0)
            return packageInfo.packageName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取系统 ResId
     */
    public fun getSysResId(context: Context, resName: String, typeName: String): Int {
        return context.resources.getIdentifier(resName, typeName, getPackageName(context))
    }
}
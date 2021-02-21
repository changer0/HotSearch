package com.lulu.skin

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * 换肤管理类
 */
class SkinManager private constructor(){
    companion object {
        @JvmStatic
        public fun get(): SkinManager {
            return Holder.instance
        }
    }
    object Holder {
        val instance = SkinManager()
    }

    private val scope = CoroutineScope(Dispatchers.Main)

    /**
     * 是否为外部皮肤
     */
    public var isExternalSkin = false

    /**
     * 皮肤包的包名
     */
    private var skinPackageName = ""

    /**
     * 皮肤包的资源
     */
    private var skinResources: Resources? = null

    private var skinUpdateListenerSet = mutableSetOf<ISkinUpdateListener>()

    /**
     * 加载皮肤包 <br/>
     * 建议调用时机：<br/>
     * 1. 更换皮肤包时 <br/>
     * 2. 进入应用时
     */
    public fun loadSkin(_context: Context, skinPath: String) {
        if (TextUtils.isEmpty(skinPath)) {
            return
        }
        val context = _context.applicationContext

        scope.launch {
            skinResources = getSkinResources(context, skinPath)
            skinResources.apply {
                isExternalSkin = true
                notifySkinUpdate()
            }
        }
    }

    private suspend fun getSkinResources(context: Context, path: String): Resources? = withContext(Dispatchers.IO) {
        var resources: Resources? = null

        try {
            val file = File(path)
            if (!file.exists()) return@withContext null
            val pm = context.packageManager
            val info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES)
            skinPackageName = info.packageName
            //皮肤包的 AssetManager
            val assetManager = AssetManager::class.java.newInstance()
            val addAssetPath = assetManager.javaClass.getMethod("addAssetPath", String::class.java)
            addAssetPath.invoke(assetManager, path)
            val superResources = context.resources;
            resources = Resources(assetManager, superResources.displayMetrics, superResources.configuration)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        resources
    }

    public fun addSkinUpdateListener(listener: ISkinUpdateListener) {
        skinUpdateListenerSet.add(listener)
    }

    /**
     * 通知皮肤更换
     */
    private fun notifySkinUpdate() {
        val listenerList = ArrayList(skinUpdateListenerSet)
        for (listener in listenerList) {
            listener.onSkinUpdate()
        }
    }

    /**
     * 恢复默认主题
     */
    public fun restoreDefaultTheme() {
        isExternalSkin = false
        skinResources = null
        notifySkinUpdate()
    }
    
    //----------------------------------------------------------------------------------------------
    // 工具方法

    public fun getColor(context: Context, resName: String?, resId: Int): Int {
        val originColor: Int = context.resources.getColor(resId)
        if (!isExternalSkin) {
            return originColor
        }
        val newResId: Int = skinResources?.getIdentifier(resName, "color", skinPackageName)?:resId
        return try {
            skinResources?.getColor(newResId)?:originColor
        } catch (e: Exception) {
            e.printStackTrace()
            return originColor
        }
    }

    public fun getDrawable(context: Context, resName: String?, resId: Int): Drawable? {
        val originDrawable: Drawable = context.resources.getDrawable(resId)
        if (!isExternalSkin) {
            return originDrawable
        }
        val newResId: Int = skinResources?.getIdentifier(resName, "drawable", skinPackageName)?:resId
        return try {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                skinResources?.getDrawable(newResId)?:originDrawable
            } else {
                skinResources?.getDrawable(newResId, null)?:originDrawable
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return originDrawable
        }
    }

}
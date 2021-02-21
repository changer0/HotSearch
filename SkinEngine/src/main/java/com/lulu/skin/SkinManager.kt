package com.lulu.skin

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.text.TextUtils
import kotlinx.coroutines.*
import java.io.File
import java.lang.NullPointerException
import kotlin.reflect.full.createInstance

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

    private lateinit var context: Context
    //外部皮肤
    private var isExternalSkin = false

    private var skinPackageName = ""

    private var skinResources: Resources? = null

    private var skinUpdateListenerSet = mutableSetOf<ISkinUpdateListener>()

    public fun init(context: Context) {
        this.context = context.applicationContext
    }

    /**
     * 加载皮肤包 <br/>
     * 1. 更换皮肤包时
     * 2.
     */
    public fun loadSkin(skinPath: String) {
        if (TextUtils.isEmpty(skinPath)) {
            return
        }

        scope.launch {
            skinResources = getSkinResources(skinPath)
            skinResources.apply {
                isExternalSkin = true
                notifySkinUpdate()
            }
        }
    }

    private suspend fun getSkinResources(path: String): Resources? = withContext(Dispatchers.IO) {
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

    public fun release() {
        scope.cancel()
    }
}
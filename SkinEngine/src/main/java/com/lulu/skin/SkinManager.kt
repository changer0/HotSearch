package com.lulu.skin

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.text.TextUtils
import kotlinx.coroutines.*
import java.io.File
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

    /**
     * 加载皮肤包
     */
    public fun loadSkin(context: Context, skinPath: String) {
        if (TextUtils.isEmpty(skinPath)) {
            return
        }

        scope.launch {
            val resources = getSkinResources(context, skinPath)

        }
    }

    private suspend fun getSkinResources(context: Context, path: String): Resources? = withContext(Dispatchers.IO) {
        var resources: Resources? = null

        try {
            val file = File(path)
            if (!file.exists()) null
            val pm = context.packageManager
            val info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES)
            val pn = info.packageName
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

    private suspend fun get() {
        withContext(Dispatchers.IO) {

        }
    }

    public fun release() {
        scope.cancel()
    }
}
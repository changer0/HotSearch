package com.lulu.hotsearch

import android.app.Application
import com.lulu.baseutil.CommonUtil
import com.lulu.baseutil.FileUtil
import com.lulu.baseutil.Init
import com.lulu.basic.image.ImageUtils
import com.lulu.basic.kvstorage.KVStorage
import com.lulu.basic.net.Http
import com.lulu.basic.skin.SkinManager
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy
import com.yuewen.component.router.YWRouter
import com.yuewen.reader.zebra.ZebraConfig
import java.io.File


/**
 * Created by zhanglulu on 2020/3/16.
 * for
 */
class MyApp: Application() {


    override fun onCreate() {
        super.onCreate()
        Init.app = this
        Init.context = this
        initPath()
        initBugly()
        initMMKV()
        initZebra()
        //初始化 YWRouter
        YWRouter.init(this, BuildConfig.DEBUG)
        //换肤初始化
        SkinManager.get().init()
    }

    private fun initMMKV() {
        val innerRootPath: String =
            filesDir.absolutePath + File.separator
        KVStorage.init(this, innerRootPath)
    }

    /**
     * Zebra 初始化
     */
    private fun initZebra() {
        val builder = ZebraConfig.Builder(this) { params ->
            Http.sendRequest(params.url, params.requestContent, params.requestMethod, null, params.contentType, this)
        }
        builder.setDebug(BuildConfig.DEBUG)
        ZebraConfig.init(builder)
    }

    /**
     * 路径初始化
     */
    private fun initPath() {
        Init.ROOT_PATH = FileUtil.getStorageFileDir(this)?.path + File.separator
    }

    /**
     * bugly 初始化
     */
    private fun initBugly() {
        val strategy = UserStrategy(this)
        if (BuildConfig.DEBUG) {
            strategy.appVersion = "${CommonUtil.getVersionName(this)}_debug"
        }
        CrashReport.initCrashReport(this, "1b2b486e59", BuildConfig.DEBUG, strategy)

    }


    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            ImageUtils.clearMemory(this)
        }
        ImageUtils.trimMemory(this, level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        ImageUtils.clearMemory(this)
    }
}

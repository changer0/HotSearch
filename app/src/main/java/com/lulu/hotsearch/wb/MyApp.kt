package com.lulu.hotsearch.wb

import android.app.Application
import com.lulu.basic.Init
import com.lulu.basic.net.Http
import com.yuewen.reader.zebra.ZebraConfig
import com.yuewen.component.router.YWRouter

/**
 * Created by zhanglulu on 2020/3/16.
 * for
 */
class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Init.app = this
        Init.context = this
        initZebra()

        //初始化 YWRouter
        YWRouter.init(this, BuildConfig.DEBUG)
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
}

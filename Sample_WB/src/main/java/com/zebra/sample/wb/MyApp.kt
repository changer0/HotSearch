package com.zebra.sample.wb

import android.app.Application
import com.yuewen.reader.zebra.ZebraConfig
import com.yuewen.component.router.YWRouter
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/**
 * Created by zhanglulu on 2020/3/16.
 * for
 */
class MyApp: Application() {
    private val client = OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build()//1
    override fun onCreate() {
        super.onCreate()
        initZebra()

        //初始化 YWRouter
        YWRouter.init(this, BuildConfig.DEBUG)
    }

    /**
     * Zebra 初始化
     */
    private fun initZebra() {
        val builder = ZebraConfig.Builder(this) { params ->
            val request = Request.Builder().url(params.url).get().build()
            val call = client.newCall(request)
            val response = call.execute()
            response.body()?.byteStream()
        }
        builder.setDebug(true)
        ZebraConfig.init(builder)
    }
}

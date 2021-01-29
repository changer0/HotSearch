package com.example.providermoduledemo

import android.app.Application
import com.qq.reader.zebra.ZebraConfig
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
        initDataProvider()
    }

    /**
     * DataProvider 初始化
     */
    private fun initDataProvider() {
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

package com.example.providermoduledemo

import android.app.Application
import com.qq.reader.provider.DataProviderConfig
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
        DataProviderConfig.init(this) { params ->
            val request = Request.Builder()
                .url(params.url)
                .get().build()//2
            val call = client.newCall(request)//3
            val response = call.execute()//4
            response.body()?.byteStream()
        }
    }
}
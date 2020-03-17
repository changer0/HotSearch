package com.example.providermoduledemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.qq.reader.core.utils.NetUtils
import com.qq.reader.module.bookstore.dataprovider.loader.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val provider = MyDataProvider(MyRequestBean())
        val loadParams = DataLoaderParams()
        //缓存模式
        loadParams.cacheMode = CacheMode.CACHE_MODE_USE_CACHE_PRIORITY
        loadParams.liveData.observeForever {
            if (it?.state == ProviderObserverEntity.PROVIDER_DATA_SUCCESS) {
                Log.d(TAG, "接受数据：${it.provider.jsonStr}")
            } else {
                Log.d(TAG, "数据异常")
            }
        }
        //val it  = CommonLiveData
        ReaderDataLoader.getInstance().loadData(provider, loadParams)
    }

}


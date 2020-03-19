package com.example.providermoduledemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.qq.reader.module.bookstore.dataprovider.loader.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

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
                urlTv.text = it.provider.url
                cacheModeTv.text = loadParams.cacheMode.toString()
                isCacheTv.text = it.provider.isCache.toString()
                val jsonObj = JSONObject(it.provider.jsonStr)
                content.text = jsonObj.toString(4)
                Log.d(TAG, "接受数据：${it.provider.jsonStr}")
            } else {
                content.text = "加载失败！"
                Log.d(TAG, "数据异常")
            }
        }
        //val it  = CommonLiveData
        ReaderDataLoader.getInstance().loadData(provider, loadParams)

        removeCacheBtn.setOnClickListener {
            provider.removeCache()
        }

        reloadData.setOnClickListener {
            ReaderDataLoader.getInstance().loadData(provider, loadParams)
            content.text = "加载中"
        }

        modifyCacheModel.setOnClickListener {
            content.text = "加载中"
            loadParams.cacheMode++
            if (loadParams.cacheMode > 3) {
                loadParams.cacheMode = 0
            }
            ReaderDataLoader.getInstance().loadData(provider, loadParams)
        }
    }

}


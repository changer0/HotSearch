package com.example.providermoduledemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qq.reader.module.bookstore.dataprovider.loader.CacheMode
import com.qq.reader.module.bookstore.dataprovider.loader.DataLoaderAdParams
import com.qq.reader.module.bookstore.dataprovider.loader.DataLoaderParams
import com.qq.reader.module.bookstore.dataprovider.loader.ReaderDataLoader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val provider = MyDataProvider(MyRequestBean())
        val loadParams = DataLoaderParams()
        //缓存模式
        loadParams.cacheMode = CacheMode.CACHE_MODE_USE_CACHE_PRIORITY
        loadParams.liveData.let {

        }

        //val it  = CommonLiveData
        ReaderDataLoader.getInstance().loadData(provider, loadParams)
    }

}


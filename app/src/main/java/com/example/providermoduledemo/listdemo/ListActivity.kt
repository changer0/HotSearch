package com.example.providermoduledemo.listdemo

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.qq.reader.pagelist.ReaderBaseListProviderActivity
import com.qq.reader.provider.loader.CacheMode
import com.qq.reader.provider.loader.DataLoaderParams
import com.qq.reader.provider.loader.ProviderObserverEntity
import com.qq.reader.provider.loader.ReaderDataLoader

class ListActivity : ReaderBaseListProviderActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val provider = ListDataProvider(ListRequestBean())
        val loadParams = DataLoaderParams()
        //缓存模式
        loadParams.cacheMode = CacheMode.CACHE_MODE_USE_CACHE_PRIORITY
        loadParams.liveData.observe(this, Observer {
            if (it.state == ProviderObserverEntity.PROVIDER_DATA_SUCCESS) {
                mAdapter.setNewData(it.provider.dataItems)
                hideLoadingView()
            } else {
                Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show()
            }
        })
        ReaderDataLoader.getInstance().loadData(provider, loadParams)
    }
}
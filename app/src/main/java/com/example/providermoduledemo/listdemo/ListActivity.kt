package com.example.providermoduledemo.listdemo

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.qq.reader.pagelist.ReaderBaseListProviderActivity
import com.qq.reader.provider.loader.CacheMode
import com.qq.reader.provider.loader.DataLoaderParams
import com.qq.reader.provider.loader.DataProviderLoader

class ListActivity : ReaderBaseListProviderActivity() {
    lateinit var provider: ListDataProvider
    lateinit var loadParams: DataLoaderParams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        provider = ListDataProvider(ListRequestDataBean())
        loadParams = DataLoaderParams()
        //缓存模式
        loadParams.cacheMode = CacheMode.CACHE_MODE_USE_CACHE_PRIORITY
        loadParams.liveData.observe(this, Observer {
            if (it.isSuccess) {
                if (mRecyclerViewState == STATE_ENTER_INIT) {
                    mAdapter.setNewData(it.provider.dataItems)
                    hideLoadingView()
                } else if (mRecyclerViewState == STATE_UP_LOAD_MORE) {
                    mAdapter.addData(it.provider.dataItems)
                }
                mAdapter.loadMoreComplete()
            } else {
                Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show()
            }
        })
        DataProviderLoader.getInstance().loadData(provider, loadParams)
    }

    override fun onLoadMoreRequested() {
        super.onLoadMoreRequested()
        DataProviderLoader.getInstance().loadData(provider, loadParams)
    }
}
package com.example.providermoduledemo.viewmodel

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.providermoduledemo.listdemo.ListRequestDataBean
import com.example.providermoduledemo.pagelist.ReaderBaseListProviderActivity
import com.qq.reader.provider.loader.DataProviderLoader
import com.qq.reader.provider.log.Logger

class ViewModelActivity : ReaderBaseListProviderActivity() {
    lateinit var provider: ViewModelDataProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        provider = ViewModelDataProvider(ListRequestDataBean())
        provider.liveData.observe(this, Observer {
            if (it.isSuccess) {
                Logger.d("ViewModelActivity isCache：", it.provider.isCache.toString())
                val dataItems = it.provider.dataItems
                if (mRecyclerViewState == STATE_ENTER_INIT) {
                    mAdapter.setNewData(dataItems)
                    hideLoadingView()
                } else if (mRecyclerViewState == STATE_UP_LOAD_MORE) {
                    mAdapter.addData(dataItems)
                }
                mAdapter.loadMoreComplete()
            } else {
                Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show()
            }
        })
        DataProviderLoader.getInstance().loadData(provider)
    }

    override fun onLoadMoreRequested() {
        super.onLoadMoreRequested()
        DataProviderLoader.getInstance().loadData(provider)
    }


}
package com.example.providermoduledemo.listdemo

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.providermoduledemo.pagelist.ReaderBaseListProviderActivity
import com.qq.reader.provider.loader.DataProviderLoader

class ListActivity : ReaderBaseListProviderActivity() {
    lateinit var provider: ListDataProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        provider = ListDataProvider(ListRequestDataBean())
        provider.liveData.observe(this, Observer {
            if (it.isSuccess) {
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
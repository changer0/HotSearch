package com.example.providermoduledemo.preload

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.providermoduledemo.pagelist.ReaderBaseListProviderActivity
import com.example.providermoduledemo.viewmodel.ViewModelRequestDataBean
import com.example.providermoduledemo.viewmodel.ViewModelResponseDataBean
import com.qq.reader.provider.DataProvider
import com.qq.reader.provider.cache.CacheMode

class PreLoadActivity : ReaderBaseListProviderActivity() {
    lateinit var provider: DataProvider<ViewModelRequestDataBean, ViewModelResponseDataBean>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        provider = PreLoadProviderCreator.get().provider
        provider.liveData.observe(this, Observer {
            if (it.isSuccess) {
                val dataItems = it.provider.viewBindItems
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
    }

    override fun onLoadMoreRequested() {
        super.onLoadMoreRequested()
        provider.loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        PreLoadProviderCreator.get().release()
    }

}
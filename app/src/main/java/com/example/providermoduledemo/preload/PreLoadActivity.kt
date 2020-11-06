package com.example.providermoduledemo.preload

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.providermoduledemo.SampleReponseBean
import com.example.providermoduledemo.SampleRequestBean
import com.example.providermoduledemo.pagelist.ReaderBaseListProviderActivity
import com.qq.reader.provider.DataProvider
@Suppress("UNCHECKED_CAST")
class PreLoadActivity : ReaderBaseListProviderActivity() {

    companion object {
        public const val TEST_BID = "bid"
    }

    private lateinit var provider: DataProvider<SampleRequestBean, SampleReponseBean>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var p = PreLoadManager.getProvider(TEST_BID)
        if (p == null) {
            p = SamplePreLoadProviderCreator(SampleRequestBean(TEST_BID)).provider
            p.loadData()
        }

        provider = p as DataProvider<SampleRequestBean, SampleReponseBean>
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
        loadNextData()
    }

    override fun onDestroy() {
        super.onDestroy()
        PreLoadManager.release(TEST_BID)
    }


    public fun loadNextData() {
        var index = provider.requestBean.index
        index++
        if (index > 5) {
            index = 1
        }
        provider.requestBean.index = index
        provider.loadData()
    }


}
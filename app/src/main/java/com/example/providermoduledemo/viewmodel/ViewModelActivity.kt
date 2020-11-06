package com.example.providermoduledemo.viewmodel

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.providermoduledemo.R
import com.example.providermoduledemo.SampleReponseBean
import com.example.providermoduledemo.SampleRequestBean
import com.example.providermoduledemo.pagelist.ReaderBaseListProviderActivity
import com.qq.reader.provider.DataProvider
import com.qq.reader.provider.cache.CacheMode
import com.qq.reader.provider.loader.OnceRequestParams
import com.qq.reader.provider.loader.SimpleProviderLoader

class ViewModelActivity : ReaderBaseListProviderActivity() {
    lateinit var provider: DataProvider<SampleRequestBean, SampleReponseBean>
    lateinit var loader: SimpleProviderLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val providerCreator = ViewModelProviderCreator(SampleRequestBean())
        provider = providerCreator.provider
        loader = providerCreator.loader
        loader.cacheMode = CacheMode.CACHE_MODE_NOT_USE_CACHE
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
                showCacheMode(provider.isCache)
            } else {
                Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show()
            }
        })
        provider.loadData()
    }

    override fun onLoadMoreRequested() {
        super.onLoadMoreRequested()
        loadNextData()
    }

    private fun showCacheMode(cache: Boolean) {
        if (supportActionBar != null) {
            supportActionBar!!.title = "Provider 缓存：(${cache})"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.data_control_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.getItem(2).title = "修改缓存模式：${loader.cacheMode}"
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.clearCache -> {
                loader.removeCache(OnceRequestParams.buildParams(provider.netQuestParams).cacheKey)
            }
            R.id.reloadData -> {
                provider.loadData()
                mRecyclerViewState = STATE_ENTER_INIT
                showLoadingView()
            }
            R.id.modifyCacheModel -> {
                var cacheMode = loader.cacheMode + 1
                if (cacheMode > 3) {
                    cacheMode = 0;
                }
                loader.cacheMode = cacheMode
                item.title = "修改缓存模式：${cacheMode}"
                provider.loadData()
                mRecyclerViewState = STATE_ENTER_INIT
                showLoadingView()
            }
        }
        return true
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
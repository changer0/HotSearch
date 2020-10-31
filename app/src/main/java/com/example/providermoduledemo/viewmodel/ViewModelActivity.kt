package com.example.providermoduledemo.viewmodel

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.providermoduledemo.R
import com.example.providermoduledemo.pagelist.ReaderBaseListProviderActivity
import com.qq.reader.provider.DataProvider
import com.qq.reader.provider.simple.OnceRequestParams
import com.qq.reader.provider.simple.SimpleProviderLoader

class ViewModelActivity : ReaderBaseListProviderActivity() {
    lateinit var provider: DataProvider<ViewModelRequestDataBean, ViewModelResponseDataBean>
    lateinit var loader: SimpleProviderLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val providerCreator = ViewModelProviderCreator(ViewModelRequestDataBean())
        provider = providerCreator.provider
        loader = providerCreator.loader
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
        provider.loadData()
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
}
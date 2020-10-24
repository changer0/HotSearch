package com.example.providermoduledemo.viewmodel

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.providermoduledemo.R
import com.example.providermoduledemo.pagelist.ReaderBaseListProviderActivity
import com.qq.reader.provider.loader.DataProviderLoader
import com.qq.reader.provider.log.Logger

class ViewModelActivity : ReaderBaseListProviderActivity() {
    lateinit var provider: ViewModelDataProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        provider = ViewModelDataProvider(ViewModelRequestDataBean())
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
                showCacheMode(provider.isCache)
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
        menu.getItem(2).title = "修改缓存模式：${provider.cacheMode}"
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.clearCache -> {
                provider.removeCache()
            }
            R.id.reloadData -> {
                DataProviderLoader.getInstance().loadData(provider)
                mRecyclerViewState = STATE_ENTER_INIT
                showLoadingView()
            }
            R.id.modifyCacheModel -> {
                var cacheMode = provider.cacheMode + 1
                if (cacheMode > 3) {
                    cacheMode = 0;
                }
                provider.cacheMode = cacheMode
                item.title = "修改缓存模式：${cacheMode}"
                DataProviderLoader.getInstance().loadData(provider)
                mRecyclerViewState = STATE_ENTER_INIT
                showLoadingView()
            }
        }
        return true
    }
}
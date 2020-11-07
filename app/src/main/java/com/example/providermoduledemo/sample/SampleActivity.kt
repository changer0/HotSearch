package com.example.providermoduledemo.sample

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.providermoduledemo.R
import com.example.providermoduledemo.Utils
import com.example.providermoduledemo.pagelist.ReaderBaseListProviderActivity
import com.qq.reader.provider.DataProvider
import com.qq.reader.provider.cache.CacheMode
import com.qq.reader.provider.loader.ObserverEntity
/**
 * 示例 Activity
 */
private const val TAG = "SampleActivity"
class SampleActivity : ReaderBaseListProviderActivity(), Observer<ObserverEntity> {

    private var cacheMode = CacheMode.CACHE_MODE_NOT_USE_CACHE

    companion object {
        public const val SERVER_URL =
            "https://gitee.com/luluzhang/publish-json/raw/master/bookdetail-bid- (%s).json"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadInitData()
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
        menu.getItem(1).title = "缓存模式：${Utils.getCacheStr(cacheMode)}"
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.reloadData -> {
                loadInitData()
                mRecyclerViewState = STATE_ENTER_INIT
                showLoadingView()
            }
            R.id.modifyCacheModel -> {
                var cacheMode = this.cacheMode + 1
                if (cacheMode > 3) {
                    cacheMode = 0
                }
                this.cacheMode = cacheMode
                item.title = "缓存模式：${Utils.getCacheStr(cacheMode)}"
                loadInitData()
                mRecyclerViewState = STATE_ENTER_INIT
                showLoadingView()
            }
        }
        return true
    }

    private var curIndex = 1

    public fun loadNextData() {
        curIndex++
        if (curIndex > 5) {
            curIndex = 1
        }
        loadData(curIndex)
    }


    private fun loadInitData() {
        loadData(1)
    }

    private fun loadData(index: Int) {
        curIndex = index

        val url = String.format(SERVER_URL, index)
        Log.d(TAG, "loadData: url: $url")

        DataProvider.with(SampleResponseBean::class.java)
            .url(url)
            .viewBindItemBuilder(SampleViewBindItemBuilder())
            .cacheConfig(cacheMode, SampleGetExpiredTime())
            .load()
            .observe(this, this)
    }

    //----------------------------------------------------------------------------------------------
    // 回调数据

    override fun onChanged(entity: ObserverEntity) {
        if (entity.isSuccess) {
            val dataItems = entity.provider.viewBindItems
            if (mRecyclerViewState == STATE_ENTER_INIT) {
                mAdapter.setNewData(dataItems)
                hideLoadingView()
            } else if (mRecyclerViewState == STATE_UP_LOAD_MORE) {
                mAdapter.addData(dataItems)
            }
            mAdapter.loadMoreComplete()
            showCacheMode(entity.provider.isCache)
        } else {
            Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show()
        }
    }

}
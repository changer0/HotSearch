package com.zebra.sample.wb

import android.os.Bundle
import com.qq.reader.bookstore.BaseBookStoreViewModel
import com.qq.reader.bookstore.define.LoadSignal
import com.yuewen.reader.zebra.Zebra
import com.yuewen.reader.zebra.ZebraLiveData
import com.yuewen.reader.zebra.cache.CacheMode
import com.zebra.sample.wb.bean.WBHotSearchBean
import com.zebra.sample.wb.itembuilder.WBViewBindItemBuilder

/**
 * 微博 ViewModel
 */
class WBViewModel : BaseBookStoreViewModel() {

    val URL = "http://changer2.uicp.io/hotSearch"

    override fun getZebraLiveData(params: Bundle?): ZebraLiveData {
        return Zebra.with(WBHotSearchBean::class.java)
            .get()
            .url(URL)
            .cacheConfig(CacheMode.CACHE_MODE_USE_CACHE_PRIORITY, WBGetExpiredTime())
            .viewBindItemBuilder(WBViewBindItemBuilder())
            .load(LoadSignal.parseSignal(params))
    }
}
package com.lulu.hotsearch.wb

import android.os.Bundle
import com.qq.reader.bookstore.BaseBookStoreViewModel
import com.qq.reader.bookstore.define.LoadSignal
import com.yuewen.reader.zebra.Zebra
import com.yuewen.reader.zebra.ZebraLiveData
import com.yuewen.reader.zebra.cache.CacheMode
import com.lulu.hotsearch.wb.bean.HotSearchBean
import com.lulu.hotsearch.wb.itembuilder.WBViewBindItemBuilder

/**
 * 微博 ViewModel
 */
class HotSearchViewModel : BaseBookStoreViewModel() {

    val URL = "http://changer2.uicp.io/hotSearch"

    override fun getZebraLiveData(params: Bundle?): ZebraLiveData {
        return Zebra.with(HotSearchBean::class.java)
            .get()
            .url(URL)
            .cacheConfig(CacheMode.CACHE_MODE_USE_CACHE_PRIORITY, HotSearchGetExpiredTime())
            .viewBindItemBuilder(WBViewBindItemBuilder())
            .load(LoadSignal.parseSignal(params))
    }
}
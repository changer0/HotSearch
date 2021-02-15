package com.lulu.hotsearch

import android.os.Bundle
import com.qq.reader.bookstore.BaseBookStoreViewModel
import com.qq.reader.bookstore.define.LoadSignal
import com.yuewen.reader.zebra.Zebra
import com.yuewen.reader.zebra.ZebraLiveData
import com.yuewen.reader.zebra.cache.CacheMode
import com.lulu.hotsearch.bean.HotSearchBean
import com.lulu.hotsearch.itembuilder.ViewBindItemBuilder

/**
 * 微博 ViewModel
 */
class HotSearchViewModel : BaseBookStoreViewModel() {

    val URL = ServerUrl.DOMAIN + "hotSearch?type="

    override fun getZebraLiveData(params: Bundle?): ZebraLiveData {
        val type = params?.getString(Constant.HOT_SEARCH_TYPE,
            Constant.HOT_SEARCH_WB)?:Constant.HOT_SEARCH_WB
        return Zebra.with(HotSearchBean::class.java)
            .get()
            .url(URL+type)
            .cacheConfig(CacheMode.CACHE_MODE_USE_CACHE_PRIORITY,
                HotSearchGetExpiredTime()
            )
            .viewBindItemBuilder(ViewBindItemBuilder())
            .load(LoadSignal.parseSignal(params))
    }
}
package com.lulu.hotsearch

import android.os.Bundle
import com.qq.reader.bookstore.BasePageViewModel
import com.qq.reader.bookstore.define.LoadSignal
import com.yuewen.reader.zebra.Zebra
import com.yuewen.reader.zebra.ZebraLiveData
import com.yuewen.reader.zebra.cache.CacheMode
import com.lulu.hotsearch.bean.HotSearchBean
import com.lulu.hotsearch.define.Constant
import com.lulu.basic.define.ServerUrl
import com.lulu.hotsearch.itembuilder.ViewBindItemBuilder

/**
 * 微博 ViewModel
 */
class HotSearchViewModel : BasePageViewModel() {

    val URL = ServerUrl.DOMAIN + "list?type="

    override fun getZebraLiveData(params: Bundle?): ZebraLiveData {
        val type = params?.getString(
            Constant.HOT_SEARCH_TYPE,
            Constant.HOT_SEARCH_WB)?: Constant.HOT_SEARCH_WB
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
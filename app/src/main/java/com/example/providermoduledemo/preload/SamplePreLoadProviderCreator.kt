package com.example.providermoduledemo.preload

import android.util.Log
import com.chad.library.adapter.base.BaseViewHolder
import com.example.providermoduledemo.SampleRequestBean
import com.example.providermoduledemo.viewmodel.ViewBindItemBuilder
import com.example.providermoduledemo.SampleReponseBean
import com.qq.reader.provider.BaseViewBindItem
import com.qq.reader.provider.bean.BaseBean
import com.qq.reader.provider.cache.CacheMode
import com.qq.reader.provider.simple.SimpleDataProviderCreator

/**
 * Created by zhanglulu on 2020/3/16.
 * for 预加载 ProviderCreator 示例
 */
public class SamplePreLoadProviderCreator constructor(requestBean: SampleRequestBean)
    : SimpleDataProviderCreator<SampleReponseBean>(SampleReponseBean::class.java) {


    init {
        loader.cacheMode = CacheMode.CACHE_MODE_NOT_USE_CACHE
    }

    override fun getUrl(): String {
        val url =
            "https://gitee.com/luluzhang/publish-json/raw/master/bookdetail-bid- (1).json"
        Log.d("SamplePreLoad", "url: $url")
        return url
    }

    override fun fillData(data: SampleReponseBean): List<BaseViewBindItem<out BaseBean, BaseViewHolder>> {
        //填充数据
        return ViewBindItemBuilder.buildViewBindItem(data)
    }

    override fun getExpiredTime(mData: SampleReponseBean?): Long {
        if (mData == null) {
            return 0
        }
        return mData.time
    }


}
package com.example.providermoduledemo.normallist

import com.chad.library.adapter.base.BaseViewHolder
import com.qq.reader.provider.BaseViewBindItem
import com.qq.reader.provider.DataProvider
import com.qq.reader.provider.bean.BaseDataBean
import com.qq.reader.provider.simple.SimpleDataProviderCreator

/**
 * Created by zhanglulu on 2020/3/16.
 * for DataProvider 实现类
 */

class NormalProviderCreator(requestBean: NormalRequestDataBean)
    : SimpleDataProviderCreator<NormalRequestDataBean, NormalResponseDataBean>(requestBean, NormalResponseDataBean::class.java) {


    override fun fillData(data: NormalResponseDataBean): List<BaseViewBindItem<out BaseDataBean, BaseViewHolder>> {
        return ViewBindItemBuilder.buildViewBindItem(data);
    }

    override fun getExpiredTime(mData: NormalResponseDataBean?): Long {
        if (mData == null) {
            return 0
        }
        return mData.time
    }

    override fun getUrl(): String {
        return "https://gitee.com/luluzhang/publish-json/raw/master/list_json.json"
    }
}
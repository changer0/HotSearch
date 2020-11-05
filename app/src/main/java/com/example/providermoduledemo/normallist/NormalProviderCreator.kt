package com.example.providermoduledemo.normallist

import com.chad.library.adapter.base.BaseViewHolder
import com.qq.reader.provider.BaseViewBindItem
import com.qq.reader.provider.bean.BaseBean
import com.qq.reader.provider.simple.SimpleDataProviderCreator

/**
 * Created by zhanglulu on 2020/3/16.
 * for DataProvider 实现类
 */

class NormalProviderCreator(requestBean: NormalRequestBean)
    : SimpleDataProviderCreator<NormalRequestBean, NormalResponseBean>(requestBean, NormalResponseBean::class.java) {


    override fun fillData(data: NormalResponseBean): List<BaseViewBindItem<out BaseBean, BaseViewHolder>> {
        return ViewBindItemBuilder.buildViewBindItem(data);
    }

    override fun getExpiredTime(mData: NormalResponseBean?): Long {
        if (mData == null) {
            return 0
        }
        return mData.time
    }

    override fun getUrl(): String {
        return "https://gitee.com/luluzhang/publish-json/raw/master/list_json.json"
    }
}
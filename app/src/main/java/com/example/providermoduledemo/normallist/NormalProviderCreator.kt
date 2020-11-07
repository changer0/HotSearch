package com.example.providermoduledemo.normallist

import com.chad.library.adapter.base.BaseViewHolder
import com.qq.reader.provider.BaseViewBindItem
import com.qq.reader.provider.bean.BaseBean
import com.qq.reader.provider.simple.SimpleDataProviderCreator

/**
 * Created by zhanglulu on 2020/3/16.
 * for DataProvider 构建类
 */
class NormalProviderCreator(requestBean: NormalRequestBean)
    : SimpleDataProviderCreator<NormalResponseBean>(NormalResponseBean::class.java) {

    override fun fillData(data: NormalResponseBean): List<BaseViewBindItem<out BaseBean, BaseViewHolder>> {
        //给 ViewBindItem 填充数据
        return ViewBindItemBuilder.buildViewBindItem(data);
    }

    override fun getExpiredTime(mData: NormalResponseBean?): Long {
        if (mData == null) {
            return 0
        }
        return mData.time
    }

    override fun getUrl(): String {
        //可以根据 provider.requestBean 获取请求参数，拼接到 url
        return "https://gitee.com/luluzhang/publish-json/raw/master/list_json.json"
    }
}
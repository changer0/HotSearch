package com.example.providermoduledemo.viewmodel

import com.chad.library.adapter.base.BaseViewHolder
import com.qq.reader.provider.BaseViewBindItem
import com.qq.reader.provider.bean.BaseDataBean
import com.qq.reader.provider.simple.SimpleDataProviderCreator

/**
 * Created by zhanglulu on 2020/3/16.
 * for DataProvider 实现类
 */

class ViewModelProviderCreator(requestBean: ViewModelRequestDataBean)
    : SimpleDataProviderCreator<ViewModelRequestDataBean, ViewModelResponseDataBean>(requestBean, ViewModelResponseDataBean::class.java) {
    override fun getUrl(): String {
        //拼接 URL
        return "https://gitee.com/luluzhang/publish-json/raw/master/view_model.json"
    }

    override fun fillData(data: ViewModelResponseDataBean): List<BaseViewBindItem<out BaseDataBean, BaseViewHolder>> {
        //填充数据
        return ViewBindItemBuilder.buildViewBindItem(data)
    }

    override fun getExpiredTime(mData: ViewModelResponseDataBean?): Long {
        if (mData == null) {
            return 0
        }
        return mData.time
    }
}
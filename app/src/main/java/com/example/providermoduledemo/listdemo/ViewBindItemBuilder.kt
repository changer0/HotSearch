package com.example.providermoduledemo.listdemo

import com.qq.reader.provider.BaseViewBindItem
import com.qq.reader.provider.bean.BaseDataBean

object ViewBindItemBuilder {

    @JvmStatic
    fun buildViewBindItem(data: ListResponseDataBean): List<BaseViewBindItem<out BaseDataBean>> {
        val viewBindItemList = mutableListOf<BaseViewBindItem<out BaseDataBean>>()
        if (data.dataItemList == null) {
            return viewBindItemList
        }
        for (item in data.dataItemList!!) {
            val viewBindItemStyle1 = ViewBindItemStyle1()
            viewBindItemStyle1.data = item
            viewBindItemList.add(viewBindItemStyle1)
        }
        return viewBindItemList
    }
}
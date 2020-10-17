package com.example.providermoduledemo.listdemo

import com.qq.reader.common.gsonbean.BaseBean
import com.qq.reader.provider.BaseViewBindItem

object ViewBindItemBuilder {

    @JvmStatic
    fun buildViewBindItem(data: ListResponseBean): List<BaseViewBindItem<out BaseBean>> {
        val viewBindItemList = mutableListOf<BaseViewBindItem<out BaseBean>>()
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
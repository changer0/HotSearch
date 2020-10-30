package com.example.providermoduledemo.normallist

import com.chad.library.adapter.base.BaseViewHolder
import com.qq.reader.provider.BaseViewBindItem
import com.qq.reader.provider.bean.BaseDataBean

object ViewBindItemBuilder {

    @JvmStatic
    fun buildViewBindItem(data: NormalResponseDataBean): List<BaseViewBindItem<out BaseDataBean, BaseViewHolder>> {
        val viewBindItemList = mutableListOf<BaseViewBindItem<NormalResponseDataBean.ItemData, BaseViewHolder>>()
        if (data.list == null) {
            return viewBindItemList
        }
        for (item in data.list!!) {
            var bindViewItem : BaseViewBindItem<NormalResponseDataBean.ItemData, BaseViewHolder>? = null
            when (item.style) {
                0 -> {
                    bindViewItem = ViewBindItemStyle0()
                }
                1 -> {
                    bindViewItem = ViewBindItemStyle1()
                }
            }
            bindViewItem?.let {
                it.data = item
                viewBindItemList.add(it)
            }
        }
        return viewBindItemList
    }
}
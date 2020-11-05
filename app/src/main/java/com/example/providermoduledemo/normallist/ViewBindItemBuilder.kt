package com.example.providermoduledemo.normallist

import com.chad.library.adapter.base.BaseViewHolder
import com.qq.reader.provider.BaseViewBindItem
import com.qq.reader.provider.bean.BaseBean

object ViewBindItemBuilder {

    @JvmStatic
    fun buildViewBindItem(data: NormalResponseBean): List<BaseViewBindItem<out BaseBean, BaseViewHolder>> {
        val viewBindItemList = mutableListOf<BaseViewBindItem<NormalResponseBean.Item, BaseViewHolder>>()
        if (data.list == null) {
            return viewBindItemList
        }
        for (item in data.list!!) {
            var bindViewItem : BaseViewBindItem<NormalResponseBean.Item, BaseViewHolder>? = null
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
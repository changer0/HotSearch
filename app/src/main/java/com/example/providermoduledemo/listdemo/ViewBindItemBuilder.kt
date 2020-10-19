package com.example.providermoduledemo.listdemo

import com.qq.reader.provider.BaseViewBindItem
import com.qq.reader.provider.bean.BaseDataBean
import com.qq.reader.widget.recyclerview.base.BaseViewHolder

object ViewBindItemBuilder {

    @JvmStatic
    fun buildViewBindItem(data: ListResponseDataBean): List<BaseViewBindItem<out BaseDataBean, BaseViewHolder>> {
        val viewBindItemList = mutableListOf<BaseViewBindItem<out BaseDataBean, BaseViewHolder>>()
        if (data.list == null) {
            return viewBindItemList
        }
        for (item in data.list!!) {
            var bindViewItem : BaseViewBindItem<out BaseDataBean, BaseViewHolder>? = null
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
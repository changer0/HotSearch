package com.example.providermoduledemo.viewmodel

import com.qq.reader.provider.BaseViewBindItem
import com.qq.reader.provider.bean.BaseDataBean
import com.qq.reader.widget.recyclerview.base.BaseViewHolder

object ViewBindItemBuilder {

    @JvmStatic
    fun buildViewBindItem(data: ViewModelResponseDataBean): List<BaseViewBindItem<out BaseDataBean, BaseViewHolder>> {
        val viewBindItemList = mutableListOf<BaseViewBindItem<ViewModelResponseDataBean.ItemData, BaseViewHolder>>()
        if (data.list == null) {
            return viewBindItemList
        }
        for (item in data.list!!) {
            var bindViewItem : BaseViewBindItem<ViewModelResponseDataBean.ItemData, BaseViewHolder>? = null
            when (item.style) {
                0 -> {
                    bindViewItem = ViewModelBindItemStyle0()
                }
                1 -> {
                    bindViewItem = ViewModelBindItemStyle1()
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
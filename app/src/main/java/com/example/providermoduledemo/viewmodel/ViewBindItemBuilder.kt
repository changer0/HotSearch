package com.example.providermoduledemo.viewmodel

import com.chad.library.adapter.base.BaseViewHolder
import com.qq.reader.provider.BaseViewBindItem
import com.qq.reader.provider.bean.BaseBean

object ViewBindItemBuilder {

    @JvmStatic
    fun buildViewBindItem(data: ViewModelResponseBean): List<BaseViewBindItem<out BaseBean, BaseViewHolder>> {
        val viewBindItemList = mutableListOf<BaseViewBindItem<ViewModelResponseBean.Item, BaseViewHolder>>()
        if (data.list == null) {
            return viewBindItemList
        }
        for (item in data.list!!) {
            var bindViewItem : BaseViewBindItem<ViewModelResponseBean.Item, BaseViewHolder>? = null
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
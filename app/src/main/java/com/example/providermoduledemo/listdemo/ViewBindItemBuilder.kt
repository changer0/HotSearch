package com.example.providermoduledemo.listdemo

import androidx.recyclerview.widget.RecyclerView
import com.qq.reader.provider.BaseViewBindItem
import com.qq.reader.provider.bean.BaseDataBean
import com.qq.reader.widget.recyclerview.base.BaseViewHolder

object ViewBindItemBuilder {

    @JvmStatic
    fun buildViewBindItem(data: ListResponseDataBean): List<BaseViewBindItem<out BaseDataBean, out RecyclerView.ViewHolder>> {
        val viewBindItemList = mutableListOf<BaseViewBindItem<out BaseDataBean, BaseViewHolder>>()
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
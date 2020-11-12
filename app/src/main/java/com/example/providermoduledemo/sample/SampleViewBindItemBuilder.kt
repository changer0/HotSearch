package com.example.providermoduledemo.sample

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.example.providermoduledemo.viewmodel.ViewBindItemLIRTGroupStyle0
import com.example.providermoduledemo.viewmodel.ViewBindItemLIRTGroupStyle1
import com.qq.reader.provider.BaseViewBindItem
import com.qq.reader.provider.inter.IViewBindItemBuilder


/**
 * ViewBindItemBuilder 构建示例
 */
class SampleViewBindItemBuilder : IViewBindItemBuilder<SampleResponseBean> {
    override fun buildViewBindItem(data: SampleResponseBean): List<BaseViewBindItem<*, out RecyclerView.ViewHolder>> {
        val viewBindItemList = mutableListOf<BaseViewBindItem<SampleResponseBean.Item, BaseViewHolder>>()
        if (data.list == null) {
            return viewBindItemList
        }
        for (item in data.list!!) {
            var bindViewItem : BaseViewBindItem<SampleResponseBean.Item, BaseViewHolder>? = null
            when (item.style) {
                0 -> bindViewItem = ViewBindItemLIRTGroupStyle0()
                1 -> bindViewItem = ViewBindItemLIRTGroupStyle1()
            }
            bindViewItem?.let {
                it.data = item
                viewBindItemList.add(it)
            }
        }
        return viewBindItemList
    }
}
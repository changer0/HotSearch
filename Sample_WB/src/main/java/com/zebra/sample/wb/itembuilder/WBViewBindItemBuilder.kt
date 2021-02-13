package com.zebra.sample.wb.itembuilder

import androidx.recyclerview.widget.RecyclerView
import com.yuewen.reader.zebra.BaseViewBindItem
import com.yuewen.reader.zebra.inter.IViewBindItemBuilder
import com.zebra.sample.wb.bean.WBHotSearchBean

class WBViewBindItemBuilder: IViewBindItemBuilder<WBHotSearchBean> {
    override fun buildViewBindItem(data: WBHotSearchBean): MutableList<BaseViewBindItem<*, out RecyclerView.ViewHolder>> {
        val res: MutableList<BaseViewBindItem<*, out RecyclerView.ViewHolder>> = mutableListOf()
        if (data.code < 0) {
            return res
        }
        for (result in data.result) {
            val element = WBHotSearchBindItem(result)
            res.add(element)
        }
        return res
    }
}
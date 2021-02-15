package com.lulu.hotsearch.itembuilder

import androidx.recyclerview.widget.RecyclerView
import com.yuewen.reader.zebra.BaseViewBindItem
import com.yuewen.reader.zebra.inter.IViewBindItemBuilder
import com.lulu.hotsearch.bean.HotSearchBean

class WBViewBindItemBuilder: IViewBindItemBuilder<HotSearchBean> {
    override fun buildViewBindItem(data: HotSearchBean): MutableList<BaseViewBindItem<*, out RecyclerView.ViewHolder>> {
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
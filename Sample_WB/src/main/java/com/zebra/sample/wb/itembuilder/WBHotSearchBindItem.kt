package com.zebra.sample.wb.itembuilder

import android.app.Activity
import android.widget.TextView
import android.widget.Toast
import com.qq.reader.bookstore.CommonViewHolder
import com.yuewen.reader.zebra.BaseViewBindItem
import com.zebra.sample.wb.R
import com.zebra.sample.wb.bean.Result

class WBHotSearchBindItem(itemData: Result?) :
    BaseViewBindItem<Result, CommonViewHolder>(itemData) {
    override fun getResLayoutId(): Int {
        return R.layout.wb_hot_search_item
    }

    override fun bindView(holder: CommonViewHolder, activity: Activity): Boolean {
        val hotIndex = holder.getView<TextView>(R.id.hotIndex)
        val hotTitle = holder.getView<TextView>(R.id.hotTitle)
        hotIndex.text = "${index+1}"
        hotTitle.text = itemData.title
        hotTitle.setOnClickListener {
            Toast.makeText(activity, "跳转：" + itemData.url, Toast.LENGTH_LONG).show()
        }
        return true
    }

}
package com.zebra.sample.wb.itembuilder

import android.app.Activity
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import com.qq.reader.bookstore.CommonViewHolder
import com.yuewen.reader.zebra.BaseViewBindItem
import com.zebra.sample.wb.Constant
import com.zebra.sample.wb.R
import com.zebra.sample.wb.WebActivity
import com.zebra.sample.wb.bean.Result

class WBHotSearchBindItem(itemData: Result?) :
    BaseViewBindItem<Result, CommonViewHolder>(itemData) {
    override fun getResLayoutId(): Int {
        return R.layout.wb_hot_search_item
    }

    override fun bindView(holder: CommonViewHolder, activity: Activity): Boolean {
        val hotIndex = holder.getView<TextView>(R.id.hotIndex)
        val hotTitle = holder.getView<TextView>(R.id.hotTitle)
        val hotNum = holder.getView<TextView>(R.id.hotNum)
        val hotTag = holder.getView<TextView>(R.id.hotTag)
        hotIndex.text = itemData.order
        hotTitle.text = itemData.title
        hotNum.text = itemData.hotNum
        hotTag.text = itemData.tag
        holder.itemView.setOnClickListener {
            val intent = Intent(activity, WebActivity::class.java)
            intent.putExtra(Constant.WEB_URL, itemData.url)
            intent.putExtra(Constant.WEB_TITLE, itemData.title)
            activity.startActivity(intent)
            activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
        return true
    }

}
package com.lulu.hotsearch.itembuilder

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.widget.TextView
import com.qq.reader.bookstore.CommonViewHolder
import com.yuewen.reader.zebra.BaseViewBindItem
import com.lulu.hotsearch.Constant
import com.lulu.hotsearch.wb.R
import com.lulu.hotsearch.WebActivity
import com.lulu.hotsearch.bean.Result

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
            activity.startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(activity, hotTitle, "anim_weibo_item")
                .toBundle())
            activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
        return true
    }

}
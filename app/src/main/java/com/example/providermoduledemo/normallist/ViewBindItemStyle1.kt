package com.example.providermoduledemo.normallist

import android.app.Activity
import android.widget.TextView
import android.widget.Toast
import com.chad.library.adapter.base.BaseViewHolder
import com.example.providermoduledemo.R
import com.qq.reader.provider.BaseViewBindItem

class ViewBindItemStyle1 : BaseViewBindItem<NormalResponseBean.Item, BaseViewHolder>() {
    override fun getResLayoutId(): Int {
        return R.layout.view_data_item_style1
    }

    override fun bindView(holder: BaseViewHolder, activity: Activity): Boolean {
        val text1 = holder.getView<TextView>(R.id.text1)
        text1.text = "${mItemData.name}  ${mItemData.content}"
        text1.setOnClickListener {
            Toast.makeText(activity, "当前 Activity : ${activity.javaClass.simpleName}", Toast.LENGTH_SHORT).show()
        }
        val text2 = holder.getView<TextView>(R.id.text2)
        text2.text = "ViewBindItemStyle1"
        return true
    }
}
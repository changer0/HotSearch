package com.example.providermoduledemo.listdemo

import android.app.Activity
import android.widget.TextView
import android.widget.Toast
import com.example.providermoduledemo.R
import com.qq.reader.provider.BaseViewBindItem
import com.qq.reader.widget.recyclerview.base.BaseViewHolder

class ViewBindItemStyle1 : BaseViewBindItem<ListResponseDataBean.DataItemListDataBean, BaseViewHolder>() {
    override fun getResLayoutId(): Int {
        return R.layout.view_data_item_style1
    }

    override fun bindView(holder: BaseViewHolder, activity: Activity): Boolean {
        val view = holder.getView<TextView>(R.id.text1)
        view.text = "${mItemData.title}  ${mItemData.content}"
        view.setOnClickListener {
            Toast.makeText(activity, "当前 Activity : ${activity.javaClass.simpleName}", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}
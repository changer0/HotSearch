package com.example.providermoduledemo.listdemo

import android.widget.TextView
import com.example.providermoduledemo.R
import com.qq.reader.provider.BaseViewBindItem
import com.qq.reader.widget.recyclerview.base.BaseViewHolder

class ViewBindItemStyle1 : BaseViewBindItem<ListResponseBean.DataItemListBean>() {
    override fun getResLayoutId(): Int {
        return R.layout.view_data_item_style1
    }

    override fun attachView(): Boolean {
        if (mViewHolder == null ||  mViewHolder.get() == null) {
            return false
        }
        val holder: BaseViewHolder = mViewHolder.get() ?: return false

        holder.getView<TextView>(R.id.text1).text = "${mItemData.title}  ${mItemData.content}"
        return true
    }
}
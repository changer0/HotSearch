package com.example.providermoduledemo.viewmodel

import android.app.Activity
import com.example.providermoduledemo.R
import com.example.providermoduledemo.sample.SampleResultBean
import com.qq.reader.bookstore.CommonViewHolder
import com.qq.reader.zebra.BaseViewBindItem

/**
 * 左图右文 组合样式 1
 */
class ViewBindItemLIRTGroupStyle1 : BaseViewBindItem<SampleResultBean.Item, CommonViewHolder>() {
    override fun getResLayoutId(): Int {
        return R.layout.view_model_data_item_style1
    }

    override fun bindView(holder: CommonViewHolder, activity: Activity): Boolean {
        val titleView = holder.getView<TitlePartView>(R.id.title) as TitlePartView
        titleView.setPartViewModel(TitlePartViewModel(itemData.title))
        val bookList = itemData?.bookList!!
        val size = bookList.size
        if (size < 1) {
            return false
        }
        holder.getView<LIRTPartView>(R.id.singleBook0).setPartViewModel(LIRTPartViewModel(bookList[0].leftImgUrl, bookList[0].rightText))
        if (size < 2) {
            return true
        }
        holder.getView<LIRTPartView>(R.id.singleBook1).setPartViewModel(LIRTPartViewModel(bookList[1].leftImgUrl, bookList[1].rightText))
        if (size < 3) {
            return true
        }
        holder.getView<LIRTPartView>(R.id.singleBook2).setPartViewModel(LIRTPartViewModel(bookList[2].leftImgUrl, bookList[2].rightText))
        return true
    }
}
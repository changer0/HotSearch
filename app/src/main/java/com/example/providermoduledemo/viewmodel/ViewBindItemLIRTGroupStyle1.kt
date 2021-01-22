package com.example.providermoduledemo.viewmodel

import android.app.Activity
import com.example.providermoduledemo.R
import com.example.providermoduledemo.sample.SampleResultBean
import com.qq.reader.zebra.BaseViewBindItem
import com.qq.reader.zebra.viewmodel.CommonViewHolder

/**
 * 左图右文 组合样式 1
 */
class ViewBindItemLIRTGroupStyle1 : BaseViewBindItem<SampleResultBean.Item, CommonViewHolder>() {
    override fun getResLayoutId(): Int {
        return R.layout.view_model_data_item_style1
    }

    override fun bindView(holder: CommonViewHolder, activity: Activity): Boolean {
        val titleView = holder.getView<TitleView>(R.id.title) as TitleView
        titleView.setViewModel(TitleViewModel(mItemData.title))
        val bookList = mItemData?.bookList!!
        val size = bookList.size
        if (size < 1) {
            return false
        }
        holder.getView<LIRTView>(R.id.singleBook0).setViewModel(LIRTViewModel(bookList[0].leftImgUrl, bookList[0].rightText))
        if (size < 2) {
            return true
        }
        holder.getView<LIRTView>(R.id.singleBook1).setViewModel(LIRTViewModel(bookList[1].leftImgUrl, bookList[1].rightText))
        if (size < 3) {
            return true
        }
        holder.getView<LIRTView>(R.id.singleBook2).setViewModel(LIRTViewModel(bookList[2].leftImgUrl, bookList[2].rightText))
        return true
    }
}
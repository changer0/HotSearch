package com.example.providermoduledemo.viewmodel

import android.app.Activity
import com.example.providermoduledemo.R
import com.example.providermoduledemo.Utils
import com.qq.reader.provider.BaseViewBindModelItem
import com.qq.reader.widget.recyclerview.base.BaseViewHolder

class ViewModelBindItemStyle0 : BaseViewBindModelItem<ViewModelResponseDataBean.ItemData, BaseViewHolder>() {
    override fun getResLayoutId(): Int {
        return R.layout.view_model_data_item_style0
    }

    override fun buildViewModelMap(
        holder: BaseViewHolder,
        activity: Activity,
        viewModelList: MutableList<VM>
    ) {
        for (book in mItemData.bookList!!.withIndex()) {
            val view = holder.getView<SingleBookView>(Utils.getResIdByString("singleBook${book.index}", R.id::class.java))
            val model = SingleBookModel(book.value.name, book.value.content)
            viewModelList.add(VM(view, model))
        }
    }
}
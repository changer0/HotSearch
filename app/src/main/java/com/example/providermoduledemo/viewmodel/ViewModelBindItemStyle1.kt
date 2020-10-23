package com.example.providermoduledemo.viewmodel

import android.app.Activity
import com.example.providermoduledemo.R
import com.example.providermoduledemo.Utils
import com.qq.reader.provider.BaseViewBindModelItem
import com.qq.reader.provider.viewmodel.IModel
import com.qq.reader.provider.viewmodel.IView
import com.qq.reader.widget.recyclerview.base.BaseViewHolder

class ViewModelBindItemStyle1 : BaseViewBindModelItem<ViewModelResponseDataBean.ItemData, BaseViewHolder>() {
    override fun getResLayoutId(): Int {
        return R.layout.view_model_data_item_style1
    }

    override fun buildViewModelMap(
        holder: BaseViewHolder,
        activity: Activity,
        viewModelMap: MutableMap<IView<out IModel>, IModel>
    ) {
        for (book in mItemData.bookList!!.withIndex()) {
            val view = holder.getView<SingleBookView>(Utils.getResIdByString("singleBook${book.index}", R.id::class.java))
            val model = SingleBookModel(book.value.name, book.value.content)
            viewModelMap[view] = model
        }
    }

}
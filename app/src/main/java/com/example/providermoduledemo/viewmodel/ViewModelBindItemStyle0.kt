package com.example.providermoduledemo.viewmodel

import com.example.providermoduledemo.R
import com.qq.reader.provider.viewmodel.BindUIView

class ViewModelBindItemStyle0 : BaseViewBindModelItem<ViewModelResponseDataBean.ItemData>() {

    override fun getResLayoutId(): Int {
        return R.layout.view_model_data_item_style0
    }

    @BindUIView(targetView = R.id.singleBook0)
    private lateinit var model0: SingleBookModel

    @BindUIView(targetView = R.id.singleBook1)
    private lateinit var model1: SingleBookModel


    @BindUIView(targetView = R.id.singleBook2)
    private lateinit var model2: SingleBookModel

    @BindUIView(targetView = R.id.title)
    private lateinit var titleModel: ItemTitleModel

    override fun onCreateModel(data: ViewModelResponseDataBean.ItemData?) {
        val bookList = data?.bookList!!
        titleModel = ItemTitleModel(data.title)
        val size = bookList.size
        if (size < 1) {
            return
        }
        model0 = SingleBookModel(bookList[0].name, bookList[0].content)
        if (size < 2) {
            return
        }
        model1 = SingleBookModel(bookList[1].name, bookList[1].content)
        if (size < 3) {
            return
        }
        model2 = SingleBookModel(bookList[2].name, bookList[2].content)
    }

}
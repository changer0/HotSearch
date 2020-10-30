package com.example.providermoduledemo.viewmodel

import com.example.providermoduledemo.R
import com.qq.reader.provider.viewmodel.BaseViewBindModelItem
import com.qq.reader.provider.viewmodel.annotations.BindView

class ViewModelBindItemStyle0 : BaseViewBindModelItem<ViewModelResponseDataBean.ItemData>() {

    override fun getResLayoutId(): Int {
        return R.layout.view_model_data_item_style0
    }

    @BindView(R.id.singleBook0)
    public lateinit var model0: SingleBookViewModel

    @BindView(R.id.singleBook1)
    public lateinit var model1: SingleBookViewModel


    @BindView(R.id.singleBook2)
    public lateinit var model2: SingleBookViewModel

    @BindView(R.id.title)
    public lateinit var titleModel: ItemTitleViewModel

    override fun onInitModel(data: ViewModelResponseDataBean.ItemData?) {
        val bookList = data?.bookList!!
        titleModel = ItemTitleViewModel(data.title)
        val size = bookList.size
        if (size < 1) {
            return
        }
        model0 = SingleBookViewModel(bookList[0].name, bookList[0].content)
        if (size < 2) {
            return
        }
        model1 = SingleBookViewModel(bookList[1].name, bookList[1].content)
        if (size < 3) {
            return
        }
        model2 = SingleBookViewModel(bookList[2].name, bookList[2].content)
    }


}
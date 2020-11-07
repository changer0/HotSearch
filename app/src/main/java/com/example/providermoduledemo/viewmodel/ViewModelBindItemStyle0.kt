package com.example.providermoduledemo.viewmodel

import com.example.providermoduledemo.R
import com.example.providermoduledemo.sample.SampleReponseBean
import com.qq.reader.provider.viewmodel.BaseViewBindModelItem
import com.qq.reader.provider.viewmodel.IViewModel

class ViewModelBindItemStyle0 : BaseViewBindModelItem<SampleReponseBean.Item>() {

    override fun getResLayoutId(): Int {
        return R.layout.view_model_data_item_style0
    }

    override fun onBindViewModel(
        data: SampleReponseBean.Item?,
        viewModelMap: MutableMap<Int, IViewModel>
    ) {
        val bookList = data?.bookList!!
        viewModelMap[R.id.title] = ItemTitleViewModel(data.title)
        val size = bookList.size
        if (size < 1) {
            return
        }
        viewModelMap[R.id.singleBook0] = SingleBookViewModel(bookList[0].name, bookList[0].content)
        if (size < 2) {
            return
        }
        viewModelMap[R.id.singleBook1] = SingleBookViewModel(bookList[1].name, bookList[1].content)
        if (size < 3) {
            return
        }
        viewModelMap[R.id.singleBook2] = SingleBookViewModel(bookList[2].name, bookList[2].content)
    }


}
package com.example.providermoduledemo.viewmodel

import com.example.providermoduledemo.R
import com.example.providermoduledemo.sample.SampleResultBean
import com.qq.reader.provider.viewmodel.BaseViewBindModelItem
import com.qq.reader.provider.viewmodel.IViewModel

/**
 * 左图右文 组合样式 1
 */
class ViewBindItemLIRTGroupStyle1 : BaseViewBindModelItem<SampleResultBean.Item>() {
    override fun getResLayoutId(): Int {
        return R.layout.view_model_data_item_style1
    }

    override fun onBindViewModel(
        viewModelMap: MutableMap<Int, IViewModel>
    ) {
        val bookList = data?.bookList!!
        viewModelMap[R.id.title] = TitleViewModel(data.title)
        val size = bookList.size
        if (size < 1) {
            return
        }
        viewModelMap[R.id.singleBook0] = LIRTViewModel(bookList[0].leftImgUrl, bookList[0].rightText)
        if (size < 2) {
            return
        }
        viewModelMap[R.id.singleBook1] = LIRTViewModel(bookList[1].leftImgUrl, bookList[1].rightText)
        if (size < 3) {
            return
        }
        viewModelMap[R.id.singleBook2] = LIRTViewModel(bookList[2].leftImgUrl, bookList[2].rightText)
    }
}
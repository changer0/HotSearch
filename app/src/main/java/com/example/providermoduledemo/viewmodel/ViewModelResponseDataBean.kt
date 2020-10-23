package com.example.providermoduledemo.viewmodel

import com.qq.reader.provider.bean.BaseDataBean

class ViewModelResponseDataBean : BaseDataBean() {
    var time: Long = 0
    var list: List<ItemData>? = null
    class ItemData : BaseDataBean(){
        var style: Int = -1
        var bookList: List<Book>? = null
        class Book: BaseDataBean() {
            var name: String? = null
            var content: String? = null
        }
    }
}
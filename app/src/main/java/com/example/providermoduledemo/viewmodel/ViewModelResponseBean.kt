package com.example.providermoduledemo.viewmodel

import com.qq.reader.provider.bean.BaseBean

class ViewModelResponseBean : BaseBean() {
    var time: Long = 0
    var list: List<Item>? = null
    class Item : BaseBean(){
        var style: Int = -1
        var title: String? = null
        var bookList: List<Book>? = null
        class Book: BaseBean() {
            var name: String? = null
            var content: String? = null
        }
    }
}
package com.example.providermoduledemo.sample

import com.qq.reader.provider.bean.BaseBean

class SampleConvertResponseBean : BaseBean() {
    var time: Long = 0
    var list: List<Item>? = null
    class Item : BaseBean(){
        var style: Int = -1
        var title: String? = null
        var bookList: List<Element>? = null
        class Element: BaseBean() {
            var imageUrl: String? = null
            var text: String? = null
        }
    }
}
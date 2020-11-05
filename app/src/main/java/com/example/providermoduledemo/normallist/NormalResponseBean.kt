package com.example.providermoduledemo.normallist

import com.qq.reader.provider.bean.BaseBean

class NormalResponseBean : BaseBean() {
    var time: Long = 0
    var list: List<Item>? = null
    class Item : BaseBean(){
        var style: Int = -1
        var name: String = ""
        var content: String = ""
    }
}
package com.example.providermoduledemo.normallist

import com.qq.reader.provider.bean.BaseDataBean

class NormalResponseDataBean : BaseDataBean() {
    var time: Long = 0
    var list: List<ItemData>? = null
    class ItemData : BaseDataBean(){
        var style: Int = -1
        var name: String = ""
        var content: String = ""
    }
}
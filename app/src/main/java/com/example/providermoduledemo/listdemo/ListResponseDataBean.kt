package com.example.providermoduledemo.listdemo

import com.qq.reader.provider.bean.BaseDataBean

class ListResponseDataBean : BaseDataBean() {
    var time: Long = 0
    var list: List<ItemData>? = null
    class ItemData : BaseDataBean(){
        var style: Int = -1
        var name: String = ""
        var content: String = ""
    }
}
package com.example.providermoduledemo.listdemo

import com.qq.reader.provider.bean.BaseDataBean
import java.io.Serializable

class ListResponseDataBean : BaseDataBean() {
    var time: Long = 0
    var list: List<ListData>? = null
    class ListData : BaseDataBean(){
        var style: Int = -1
        var name: String = ""
        var content: String = ""
    }
}
package com.example.providermoduledemo

import com.qq.reader.provider.bean.BaseDataBean

/**
 * Created by zhanglulu on 2020/3/16.
 * for
 */
class MyResponseDataBean : BaseDataBean() {
    var time: Long = 0

    class ListData {
        var style: Int = -1
        var name: String = ""
        var content: String = ""
    }
}
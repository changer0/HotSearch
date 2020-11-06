package com.example.providermoduledemo

import com.qq.reader.provider.bean.BaseBean

class SampleRequestBean(): BaseBean() {
    var index = 1
    var bid = ""
    constructor(bid: String) : this() {
        this.bid = bid
    }
}
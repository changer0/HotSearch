package com.example.providermoduledemo.sample

import com.qq.reader.zebra.bean.BaseBean

class SampleRequestBean(): BaseBean() {
    var index = 1
    var bid = ""
    constructor(bid: String) : this() {
        this.bid = bid
    }
}
package com.example.providermoduledemo.viewmodel

import com.qq.reader.bookstore.part.IPartViewModel

/**
 * @author zhanglulu on 2020/10/23.
 * for 左图右文 ViewMode
 */
class LIRTPartViewModel(public var leftImgUrl: String? = null, public var rightText: String? = null) :
    IPartViewModel
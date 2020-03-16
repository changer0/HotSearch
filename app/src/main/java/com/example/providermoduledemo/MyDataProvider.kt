package com.example.providermoduledemo

import com.qq.reader.module.bookstore.dataprovider.ReaderBaseDataProvider

/**
 * Created by zhanglulu on 2020/3/16.
 * for DataProvider 实现类
 */

class MyDataProvider(requestBean: MyRequestBean)
    : ReaderBaseDataProvider<MyRequestBean, MyResponseBean>(requestBean, MyResponseBean::class.java) {
    override fun fillData() {
        //填充数据
    }

    override fun composeUrl(p0: MyRequestBean?): String {
        //拼接 URL

    }

}
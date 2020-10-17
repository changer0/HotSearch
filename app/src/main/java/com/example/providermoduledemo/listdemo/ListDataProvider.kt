package com.example.providermoduledemo.listdemo

import com.qq.reader.provider.ReaderBaseDataProvider

/**
 * Created by zhanglulu on 2020/3/16.
 * for DataProvider 实现类
 */

class ListDataProvider(requestBean: ListRequestBean)
    : ReaderBaseDataProvider<ListRequestBean, ListResponseBean>(requestBean, ListResponseBean::class.java) {
    override fun fillData() {
        //填充数据
        mDataItems = ViewBindItemBuilder.buildViewBindItem(mData)
    }

    override fun composeUrl(p0: ListRequestBean?): String {
        //拼接 URL
        return "https://free.reader.qq.com/bookstore/get?sex=0&actionId=604&type=3&index=0&recommendFlag=0&num=10"
        //return "https://www.baidu.com"
    }

}
package com.example.providermoduledemo.listdemo

import com.qq.reader.provider.BaseDataProvider

/**
 * Created by zhanglulu on 2020/3/16.
 * for DataProvider 实现类
 */

class ListDataProvider(requestBean: ListRequestDataBean)
    : BaseDataProvider<ListRequestDataBean, ListResponseDataBean>(requestBean, ListResponseDataBean::class.java) {
    override fun fillData() {
        //填充数据
        mViewBindItems = ViewBindItemBuilder.buildViewBindItem(mData)
    }

    override fun composeUrl(p0: ListRequestDataBean?): String {
        //拼接 URL
        return "https://gitee.com/luluzhang/publish-json/raw/master/list_json.json"
        //return "https://www.baidu.com"
    }

    override fun getExpiredTime(): Long {
        if (mData == null) {
            return 0
        }
        return mData.time
    }

}
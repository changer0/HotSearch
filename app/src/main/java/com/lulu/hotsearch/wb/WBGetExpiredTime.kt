package com.lulu.hotsearch.wb

import com.yuewen.reader.zebra.inter.IGetExpiredTime
import com.lulu.hotsearch.wb.bean.WBHotSearchBean

class WBGetExpiredTime : IGetExpiredTime<WBHotSearchBean> {
    override fun getExpiredTime(mData: WBHotSearchBean): Long {
        return mData.expired_time.toLong()
    }
}
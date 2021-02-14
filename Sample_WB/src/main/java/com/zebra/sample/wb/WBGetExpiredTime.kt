package com.zebra.sample.wb

import com.yuewen.reader.zebra.inter.IGetExpiredTime
import com.zebra.sample.wb.bean.WBHotSearchBean

class WBGetExpiredTime : IGetExpiredTime<WBHotSearchBean> {
    override fun getExpiredTime(mData: WBHotSearchBean): Long {
        return mData.expired_time.toLong()
    }
}
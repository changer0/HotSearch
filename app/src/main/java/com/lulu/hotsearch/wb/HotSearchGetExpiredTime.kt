package com.lulu.hotsearch.wb

import com.yuewen.reader.zebra.inter.IGetExpiredTime
import com.lulu.hotsearch.wb.bean.HotSearchBean

class HotSearchGetExpiredTime : IGetExpiredTime<HotSearchBean> {
    override fun getExpiredTime(mData: HotSearchBean): Long {
        return mData.expired_time.toLong()
    }
}
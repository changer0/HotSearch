package com.lulu.hotsearch

import com.yuewen.reader.zebra.inter.IGetExpiredTime
import com.lulu.hotsearch.bean.HotSearchBean

class HotSearchGetExpiredTime : IGetExpiredTime<HotSearchBean> {
    override fun getExpiredTime(mData: HotSearchBean): Long {
        return mData.expired_time.toLong()
    }
}
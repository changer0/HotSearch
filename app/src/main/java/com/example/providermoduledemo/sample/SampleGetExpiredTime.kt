package com.example.providermoduledemo.sample

import com.qq.reader.zebra.inter.IGetExpiredTime

class SampleGetExpiredTime : IGetExpiredTime<SampleResultBean>{
    override fun getExpiredTime(mData: SampleResultBean?): Long {
        if (mData == null) {
            return 0
        }
        return mData.time
    }

}
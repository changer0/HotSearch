package com.example.providermoduledemo.sample

import com.qq.reader.provider.inter.IGetExpiredTime

class SampleGetExpiredTime : IGetExpiredTime<SampleResponseBean>{
    override fun getExpiredTime(mData: SampleResponseBean?): Long {
        if (mData == null) {
            return 0
        }
        return mData.time
    }

}
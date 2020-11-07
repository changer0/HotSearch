package com.example.providermoduledemo.sample

import com.qq.reader.provider.inter.IGetExpiredTime

class SampleGetExpiredTime : IGetExpiredTime<SampleReponseBean>{
    override fun getExpiredTime(mData: SampleReponseBean?): Long {
        if (mData == null) {
            return 0
        }
        return mData.time
    }

}
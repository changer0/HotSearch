package com.example.providermoduledemo.sample;

import android.os.Bundle;

import com.yuewen.reader.bookstore.BaseBookStoreViewModel;
import com.yuewen.reader.bookstore.define.LoadSignal;
import com.yuewen.reader.zebra.Zebra;
import com.yuewen.reader.zebra.ZebraLiveData;
import com.yuewen.reader.zebra.cache.CacheMode;

/**
 * @author zhanglulu
 */
public class SampleBookStoreViewModel extends BaseBookStoreViewModel {
    private static final String SERVER_URL = "https://gitee.com/luluzhang/publish-json/raw/master/convertTest (%s).json";
    private int index = 1;
    @Override
    public ZebraLiveData getZebraLiveData(Bundle params) {

        int signal = LoadSignal.parseSignal(params);
        switch (signal) {
            case LoadSignal.LOAD_SIGNAL_INIT:
            case LoadSignal.LOAD_SIGNAL_REFRESH:
                index = 1;
                break;
            case LoadSignal.LOAD_SIGNAL_MORE:
                index++;
                break;
        }
        String url = String.format(SERVER_URL, index);
        return Zebra.with(SampleResultBean.class)
                .url(url)
                .parser(new SampleConvertParser())
                .viewBindItemBuilder(new SampleViewBindItemBuilder())
                .cacheConfig(CacheMode.CACHE_MODE_USE_CACHE_PRIORITY, new SampleGetExpiredTime())
                .load(signal);
    }
}

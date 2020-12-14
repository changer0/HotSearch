package com.example.providermoduledemo.build;

import android.os.Bundle;

import com.example.providermoduledemo.sample.SampleConvertParser;
import com.example.providermoduledemo.sample.SampleGetExpiredTime;
import com.example.providermoduledemo.sample.SampleResultBean;
import com.example.providermoduledemo.sample.SampleViewBindItemBuilder;
import com.qq.reader.provider.DataProvider;
import com.qq.reader.provider.ProviderLiveData;
import com.qq.reader.provider.cache.CacheMode;
import com.qq.reader.provider.page.annotations.PageBuilderType;
import com.yuewen.dataprovider.page.IPage;
/**
 * 男生 Provider 构建类 （举例说明）
 */
@PageBuilderType(PageTypes.BOY_PAGE)
public class BoyPage implements IPage {
    private static final String SERVER_URL = "https://gitee.com/luluzhang/publish-json/raw/master/convertTest (%s).json";
    @Override
    public ProviderLiveData loadPageData(Bundle params) {
        int index = params.getInt(PageBuilderParams.PAGE_INDEX);
        String url = String.format(SERVER_URL, index);
        return DataProvider.with(SampleResultBean.class)
                .url(url)
                .parser(new SampleConvertParser())
                .viewBindItemBuilder(new SampleViewBindItemBuilder())
                .cacheConfig(CacheMode.CACHE_MODE_NOT_USE_CACHE, new SampleGetExpiredTime())
               .load();
    }

    @Override
    public PageInfo buildPageInfo() {
        return new PageInfo.Builder()
                .setTitleName("男生页面")
                .setEnableLoadMore(true)
                .setEnablePullDownRefresh(true)
                .setStartIndex(1)
                .build();
    }

}

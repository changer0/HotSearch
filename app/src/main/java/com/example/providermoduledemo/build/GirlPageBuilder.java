package com.example.providermoduledemo.build;

import android.os.Bundle;

import com.example.providermoduledemo.sample.SampleGetExpiredTime;
import com.example.providermoduledemo.sample.SampleResultBean;
import com.example.providermoduledemo.sample.SampleViewBindItemBuilder;
import com.qq.reader.provider.DataProvider;
import com.qq.reader.provider.ProviderLiveData;
import com.qq.reader.provider.build.IPageBuilder;
import com.qq.reader.provider.build.PageConfigInfo;
import com.qq.reader.provider.build.annotations.PageBuilderType;
import com.qq.reader.provider.cache.CacheMode;

/**
 * 女生 Provider 构建类 （举例说明）
 */
@PageBuilderType(PageBuilderTypes.GIRL_PAGE)
public class GirlPageBuilder implements IPageBuilder {
    private static final String SERVER_URL = "https://gitee.com/luluzhang/publish-json/raw/master/leftImgRightText (%s).json";

    @Override
    public ProviderLiveData buildProvider(Bundle params) {
        int index = params.getInt(PageBuilderParams.PAGE_INDEX);
        String url = String.format(SERVER_URL, index);
        return DataProvider.with(SampleResultBean.class)
                .url(url)
                .viewBindItemBuilder(new SampleViewBindItemBuilder())
                .cacheConfig(CacheMode.CACHE_MODE_NOT_USE_CACHE, new SampleGetExpiredTime())
                .load();
    }

    @Override
    public PageConfigInfo buildPageConfigInfo() {
        return new PageConfigInfo.Builder()
                .setEnableLoadMore(false)
                .setEnablePullDownRefresh(false)
                .setTitleName("女生页面")
                .setStartIndex(1)
                .build();
    }
}

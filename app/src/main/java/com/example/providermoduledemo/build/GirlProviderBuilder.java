package com.example.providermoduledemo.build;

import com.example.providermoduledemo.sample.SampleConvertResponseBean;
import com.example.providermoduledemo.sample.SampleConverter;
import com.example.providermoduledemo.sample.SampleGetExpiredTime;
import com.example.providermoduledemo.sample.SampleResultBean;
import com.example.providermoduledemo.sample.SampleViewBindItemBuilder;
import com.qq.reader.provider.DataProvider;
import com.qq.reader.provider.ProviderLiveData;
import com.qq.reader.provider.build.IProviderBuilder;
import com.qq.reader.provider.build.PageConfigInfo;
import com.qq.reader.provider.build.annotations.ProviderBuilderType;
import com.qq.reader.provider.cache.CacheMode;

/**
 * 女生 Provider 构建类 （举例说明）
 */
@ProviderBuilderType(ProviderBuilderTypes.GIRL_PROVIDER_BUILDER)
public class GirlProviderBuilder implements IProviderBuilder {
    private static final String SERVER_URL = "https://gitee.com/luluzhang/publish-json/raw/master/leftImgRightText (%s).json";

    @Override
    public ProviderLiveData buildProvider(int index) {
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

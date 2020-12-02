package com.example.providermoduledemo.generator;

import com.example.providermoduledemo.sample.SampleConvertResponseBean;
import com.example.providermoduledemo.sample.SampleConverter;
import com.example.providermoduledemo.sample.SampleGetExpiredTime;
import com.example.providermoduledemo.sample.SampleResultBean;
import com.example.providermoduledemo.sample.SampleViewBindItemBuilder;
import com.qq.reader.provider.DataProvider;
import com.qq.reader.provider.ProviderLiveData;
import com.qq.reader.provider.cache.CacheMode;
import com.qq.reader.provider.build.annotations.ProviderBuilderType;
import com.qq.reader.provider.build.IProviderBuilder;

/**
 * 男生 Provider 构建类 （举例说明）
 */
@ProviderBuilderType(ProviderGeneratorTypes.TEST_PAGE)
public class BoyProviderBuilder implements IProviderBuilder {
    private static final String SERVER_URL = "https://gitee.com/luluzhang/publish-json/raw/master/convertTest (%s).json";
    @Override
    public ProviderLiveData loadData(int index) {
        String url = String.format(SERVER_URL, index);
        return DataProvider.with(SampleResultBean.class, SampleConvertResponseBean.class)
                .url(url)
                .converter(new SampleConverter())
                .viewBindItemBuilder(new SampleViewBindItemBuilder())
                .cacheConfig(CacheMode.CACHE_MODE_NOT_USE_CACHE, new SampleGetExpiredTime())
                .load();
    }
}

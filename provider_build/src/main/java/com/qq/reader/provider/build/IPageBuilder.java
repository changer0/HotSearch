package com.qq.reader.provider.build;

import android.os.Bundle;

import com.qq.reader.provider.ProviderLiveData;

/**
 * 页面构建器
 */
public interface IPageBuilder {

    /**
     * 构建 Provider
     * @param params
     * @return
     */
    public ProviderLiveData buildProvider(Bundle params);

    public PageConfigInfo buildPageConfigInfo();
}

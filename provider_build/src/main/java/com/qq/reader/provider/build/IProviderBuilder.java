package com.qq.reader.provider.build;

import android.os.Bundle;

import com.qq.reader.provider.ProviderLiveData;

/**
 * Provider 构建器
 */
public interface IProviderBuilder {

    /**
     * 构建 Provider
     * @param params
     * @return
     */
    public ProviderLiveData buildProvider(Bundle params);

    public PageConfigInfo buildPageConfigInfo();
}

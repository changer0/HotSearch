package com.qq.reader.provider.build;

import com.qq.reader.provider.ProviderLiveData;

/**
 * Provider 构建器
 */
public interface IProviderBuilder {

    /**
     * 构建 Provider
     * @param index
     * @return
     */
    public ProviderLiveData buildProvider(int index);

    public PageConfigInfo buildPageConfigInfo();
}

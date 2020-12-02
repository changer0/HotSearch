package com.qq.reader.provider.build;

import com.qq.reader.provider.ProviderLiveData;

/**
 * Provider 构建器
 */
public interface IProviderBuilder {

    public ProviderLiveData buildProvider(int index);
}

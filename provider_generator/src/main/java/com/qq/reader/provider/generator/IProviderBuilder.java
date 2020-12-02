package com.qq.reader.provider.generator;

import com.qq.reader.provider.ProviderLiveData;

/**
 * Provider 构建器
 */
public interface IProviderBuilder {

    public ProviderLiveData loadData(int index);
}

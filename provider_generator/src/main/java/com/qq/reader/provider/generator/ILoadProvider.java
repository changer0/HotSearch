package com.qq.reader.provider.generator;

import com.qq.reader.provider.ProviderLiveData;

/**
 * Provider 生成器接口
 */
public interface ILoadProvider {
    public ProviderLiveData loadData(int index);
}

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

    /**
     * 返回起始 index
     * @return
     */
    default int getStartIndex() {
        return 0;
    }
}

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

    /**
     * 标题名
     * @return
     */
    String getTitleName();

    // TODO: 2020/12/3 后续对应需求可以继续添加，但是一定要保持克制！
}

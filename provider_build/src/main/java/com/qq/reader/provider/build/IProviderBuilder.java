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
     * 标题名
     * @return
     */
    String getTitleName();

    /**
     * 返回起始 index
     */
    default int getStartIndex() {
        return 1;
    }

    /**
     * 是否支持下拉刷新
     */
    default boolean isEnablePullDownRefresh() { return true;}

    /**
     * 是否支持上拉加载更多
     */
    default boolean isEnableLoadMore() { return true;}

    // TODO: 2020/12/3 后续对应需求可以继续添加，但是一定要保持克制！
}

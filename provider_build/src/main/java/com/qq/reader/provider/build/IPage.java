package com.qq.reader.provider.build;

import android.os.Bundle;

import com.qq.reader.provider.ProviderLiveData;

/**
 * 页面构建器
 */
public interface IPage {

    /**
     * 构建 Provider
     * @param params
     * @return
     */
    public ProviderLiveData loadPageData(Bundle params);

    public PageInfo buildPageInfo();
}

package com.qq.reader.provider.build;

import android.os.Bundle;

import com.qq.reader.provider.ProviderLiveData;

/**
 * 页面构建器
 */
public interface IPage {

    /**
     * 加载 Page 数据
     * @param params
     * @return
     */
    public ProviderLiveData loadPageData(Bundle params);

    /**
     * 构建 Page 信息
     * @return
     */
    public PageInfo buildPageInfo();
}

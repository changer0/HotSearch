package com.qq.reader.provider.page;

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
    ProviderLiveData loadPageData(Bundle params);

    /**
     * 构建 Page 信息 {并不是所有的场景都需要 PageInfo}
     * @return
     */
    default PageInfo buildPageInfo() {return new PageInfo.Builder().build();}
}

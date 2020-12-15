package com.yuewen.dataprovider.page;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
    default IPageInfo buildPageInfo() {return null;}

    default Class<? extends Fragment> getFragment() {return null;}
}

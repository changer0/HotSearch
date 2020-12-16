package com.yuewen.dataprovider.page;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.qq.reader.provider.ProviderLiveData;

import java.util.Map;

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
     * 构建 Fragment 参数信息 {并不是所有的场景都需要 PageInfo}
     * @return 参数 Map
     */
    default Map<String, Object> getFragmentArguments() {return null;}

    /**
     * 每个 Page 绑定一个 Fragment！
     * @return Fragment 类
     */
    default Class<? extends Fragment> getFragment() { return null; }
}

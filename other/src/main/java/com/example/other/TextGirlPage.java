package com.example.other;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.qq.reader.provider.ProviderLiveData;
import com.qq.reader.provider.page.annotations.PageType;
import com.yuewen.zebra.building.IFragmentParam;
import com.yuewen.zebra.building.IPage;

/**
 * @author zhanglulu
 * @date : 2021/1/18 5:21 PM
 */
@PageType("TextGirlPag")
public class TextGirlPage implements IPage {
    /**
     * 加载 Page 数据
     *
     * @param params
     * @return
     */
    @Override
    public ProviderLiveData loadPageData(Bundle params) {
        return null;
    }

    /**
     * 构建 Fragment 参数信息 {并不是所有的场景都需要 PageInfo}
     *
     * @return 参数 Map
     */
    @Override
    public IFragmentParam getFragmentParam() {
        return null;
    }

    /**
     * 每个 Page 绑定一个 Fragment！
     *
     * @return Fragment 类
     */
    @Override
    public Class<? extends Fragment> getFragment() {
        return null;
    }
}

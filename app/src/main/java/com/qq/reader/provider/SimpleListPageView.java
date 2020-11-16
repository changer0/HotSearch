package com.qq.reader.provider;

import android.content.Context;

import com.example.providermoduledemo.R;

/**
 * 简易列表加载 View，可以实现该 View 实现各自业务逻辑
 */
public class SimpleListPageView extends BaseListPageView {
    public SimpleListPageView(Context context) {
        super(context);
    }

    @Override
    public int getContentViewLayoutRes() {
        return R.layout.common_recycle_layout;
    }

    @Override
    public int getRecyclerViewIdRes() {
        return R.id.refresh_target_view;
    }

    @Override
    public int getLoadingViewIdRes() {
        return R.id.loading_view;
    }

    @Override
    public int getDataErrorViewIdRes() {
        return R.id.data_error_view;
    }

}

package com.qq.reader.provider.listpage;

import android.content.Context;
import android.view.View;

import com.example.providermoduledemo.R;

/**
 * 简易列表加载 View，可以实现该 View 实现各自业务逻辑
 */
public class SimpleListPageView extends BaseListPageView {
    public SimpleListPageView(Context context) {
        super(context);
    }

    @Override
    protected void onCreateView(View contentView) {
        // TODO: Perform findViewById and other initialization operations
    }

    @Override
    public ListPageResParams getListPageResParams() {
        return new ListPageResParams.Builder(R.layout.common_recycle_layout, R.id.refresh_target_view)
                .setLoadingViewIdRes(R.id.loading_view)
                .setPullDownViewIdRes(R.id.pull_down_list)
                .setDataErrorViewIdRes(R.id.data_error_view)
                .build();
    }

}

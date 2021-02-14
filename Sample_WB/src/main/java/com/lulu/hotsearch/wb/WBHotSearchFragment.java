package com.lulu.hotsearch.wb;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qq.reader.bookstore.BaseBookStoreFragment;
import com.qq.reader.bookstore.define.LoadSignal;

/**
 * Author: zhanglulu
 * Time: 2021/2/14
 */
@Route(path = Constant.WB_HOT_SEARCH)
public class WBHotSearchFragment extends BaseBookStoreFragment<WBHotSearchView, WBViewModel> {
    @Override
    protected WBHotSearchView onCreateBookStoreView() {
        return new WBHotSearchView(mContext);
    }

    @Override
    protected Class<WBViewModel> onCreateBookStoreViewModel(@NonNull Bundle enterBundle) {
        return WBViewModel.class;
    }

    @Override
    protected void onLaunchSuccess(@NonNull View container, @NonNull Bundle enterBundle, @Nullable Bundle savedInstanceState) {
        loadData(LoadSignal.LOAD_SIGNAL_INIT);
        mBookStoreView.searchBtn.setOnClickListener(v -> {

        });
    }
}

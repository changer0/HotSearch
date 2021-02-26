package com.qq.reader.bookstore;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qq.reader.bookstore.define.PageConstant;
import com.qq.reader.bookstore.define.LoadSignal;
import com.qq.reader.bookstore.view.CommonPageView;
import com.yuewen.reader.zebra.utils.CastUtils;


/**
 * @author zhanglulu
 */
@Route(path = PageConstant.BOOK_STORE_COMMON_FRAGMENT)
public class CommonPageFragment<VM extends BasePageViewModel> extends BasePageFragment<CommonPageView, VM> {

    @Override
    protected CommonPageView onCreatePageView() {
        return new CommonPageView(mContext);
    }

    @Override
    protected Class<VM> onCreatePageViewModel(@NonNull Bundle enterBundle) {
        Class<? extends BasePageViewModel> viewModelClass = mLaunchParams.getViewModelClass();
        if (viewModelClass == null) {
            throw new RuntimeException("启动通用 Fragment 时,需在 LaunchParams 中传递 viewModelClass!!!");
        }
        return CastUtils.cast(viewModelClass);
    }

    @Override
    protected void onLaunchSuccess(@NonNull View container, @NonNull Bundle enterBundle, @Nullable Bundle savedInstanceState) {
        loadData(LoadSignal.LOAD_SIGNAL_INIT);
    }

}

package com.qq.reader.bookstore;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qq.reader.bookstore.define.BookStoreConstant;
import com.qq.reader.bookstore.define.LoadSignal;
import com.qq.reader.bookstore.view.CommonBookStoreView;
import com.yuewen.reader.zebra.utils.CastUtils;


/**
 * @author zhanglulu
 */
@Route(path = BookStoreConstant.BOOK_STORE_COMMON_FRAGMENT)
public class CommonBookStoreFragment<VM extends BaseBookStoreViewModel> extends BaseBookStoreFragment<CommonBookStoreView, VM> {
    private Handler loadingHandler = new Handler(Looper.getMainLooper());

    private Runnable loadingRunnable = () -> loadData(LoadSignal.LOAD_SIGNAL_INIT);

    @Override
    protected CommonBookStoreView onCreateBookStoreView() {
        return new CommonBookStoreView(mContext);
    }

    @Override
    protected Class<VM> onCreateBookStoreViewModel(@NonNull Bundle enterBundle) {
        Class<? extends BaseBookStoreViewModel> viewModelClass = mLaunchParams.getViewModelClass();
        if (viewModelClass == null) {
            throw new RuntimeException("启动通用 Fragment 时,需在 LaunchParams 中传递 viewModelClass!!!");
        }
        return CastUtils.cast(viewModelClass);
    }

    @Override
    protected void onLaunchSuccess(@NonNull View container, @NonNull Bundle enterBundle, @Nullable Bundle savedInstanceState) {
        loadData(LoadSignal.LOAD_SIGNAL_INIT);
    }

    @Override
    public void onLoading() {
        loadingHandler.post(loadingRunnable);
    }

    @Override
    public void cancleLoadData() {
        super.cancleLoadData();
        loadingHandler.removeCallbacks(loadingRunnable);
    }

}
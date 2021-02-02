package com.qq.reader.bookstore;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qq.reader.bookstore.define.BookStoreConstant;
import com.qq.reader.bookstore.view.CommonBookStoreView;
import com.qq.reader.zebra.utils.CastUtils;


/**
 * @author zhanglulu
 */
@Route(path = BookStoreConstant.BOOK_STORE_COMMON_FRAGMENT)
public class CommonBookStoreFragment<VM extends BaseBookStoreViewModel> extends BaseBookStoreFragment<CommonBookStoreView, VM> {

    @Override
    protected CommonBookStoreView onCreateBookStoreView() {
        return new CommonBookStoreView(mContext);
    }

    @Override
    protected Class<VM> onCreateBookStoreViewModel(@NonNull Bundle enterBundle) {
        return CastUtils.cast(enterBundle.getSerializable(BookStoreActivityLauncher.BOOK_STORE_VIEW_MODEL));
    }

    @Override
    protected void onLaunchSuccess(View container, @NonNull Bundle enterBundle, @Nullable Bundle savedInstanceState) {

    }
}

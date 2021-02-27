package com.qq.reader.bookstore.view

import android.content.Context
import android.view.View
import com.qq.reader.bookstore.define.BookStoreViewParams
import com.qq.reader.bookstore.R

/**
 * @author zhanglulu
 */
class CommonPageView(context: Context): BasePageView(context) {
    override fun onCreateParams(): BookStoreViewParams {
        return BookStoreViewParams.Builder(
            R.layout.common_page_layout,
            R.id.list_layout
        )
            .setDataErrorViewIdRes(R.id.loading_failed_layout)
            .setPullDownViewIdRes(R.id.pull_down_list)
            .setLoadingViewIdRes(R.id.loading_layout)
            .setLoadMoreView(CommonLoadMoreView())
            .build()
    }

    override fun onCreateView(contentView: View) {

    }
}
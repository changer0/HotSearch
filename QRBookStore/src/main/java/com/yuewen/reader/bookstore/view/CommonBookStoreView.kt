package com.yuewen.reader.bookstore.view

import android.content.Context
import android.view.View
import com.yuewen.reader.bookstore.define.BookStoreViewParams
import com.yuewen.reader.bookstore.R

/**
 * @author zhanglulu
 */
class CommonBookStoreView(context: Context): BaseBookStoreView(context) {
    override fun onCreateParams(): BookStoreViewParams {
        return BookStoreViewParams.Builder(
            R.layout.bookstore_common_page_layout,
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
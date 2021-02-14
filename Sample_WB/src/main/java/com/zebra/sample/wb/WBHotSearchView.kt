package com.zebra.sample.wb

import android.content.Context
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.qq.reader.bookstore.define.BookStoreViewParams
import com.qq.reader.bookstore.view.BaseBookStoreView
import com.qq.reader.bookstore.view.CommonLoadMoreView

class WBHotSearchView(context: Context) : BaseBookStoreView(context) {

    public lateinit var searchBtn: FloatingActionButton

    override fun onCreateParams(): BookStoreViewParams {
        return BookStoreViewParams.Builder(
            R.layout.wb_hot_search_fragment,
            R.id.list_layout
        )
            .setDataErrorViewIdRes(R.id.loading_failed_layout)
            .setPullDownViewIdRes(R.id.pull_down_list)
            .setLoadingViewIdRes(R.id.loading_layout)
            .setLoadMoreView(CommonLoadMoreView())
            .setActionBarContainerIdRes(R.id.common_titler)
            .setActionBarTitleViewIdRes(R.id.profile_header_title)
            .build()
    }

    override fun onCreateView(contentView: View) {
        searchBtn = contentView.findViewById(R.id.searchBtn)
    }
}
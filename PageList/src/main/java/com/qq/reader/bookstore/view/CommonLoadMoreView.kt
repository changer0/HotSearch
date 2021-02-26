package com.qq.reader.bookstore.view

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.qq.reader.bookstore.R


/**
 * @author zhanglulu
 * @date : 2020/12/11 4:52 PM
 */
class CommonLoadMoreView: LoadMoreView() {
    override fun getLayoutId(): Int {
        return R.layout.common_second_page_loadmore
    }

    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading
    }

    override fun getLoadEndViewId(): Int {
        return R.id.load_more_end
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_fail
    }
}
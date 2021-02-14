package com.qq.reader.bookstore.define;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.qq.reader.bookstore.R;

/**
 * ListPage 资源参数
 */
public class BookStoreViewParams {
    @LayoutRes private int contentViewLayoutRes;
    @IdRes private int recyclerViewIdRes;
    @IdRes private int pullDownViewIdRes;
    @IdRes private int loadingViewIdRes;
    @IdRes private int dataErrorViewIdRes;
    @IdRes private int netErrorViewIdRes;
    @IdRes private int actionBarBackViewIdRes;
    @IdRes private int actionBarTitleViewIdRes;
    @IdRes private int actionBarContainerIdRes;
    private LoadMoreView loadMoreView;


    public int getContentViewLayoutRes() {
        return contentViewLayoutRes;
    }

    public int getRecyclerViewIdRes() {
        return recyclerViewIdRes;
    }

    public int getPullDownViewIdRes() {
        return pullDownViewIdRes;
    }

    public int getLoadingViewIdRes() {
        return loadingViewIdRes;
    }

    public int getNetErrorViewIdRes() {
        return netErrorViewIdRes;
    }

    public int getDataErrorViewIdRes() {
        return dataErrorViewIdRes;
    }

    public int getActionBarBackViewIdRes() {
        if (actionBarBackViewIdRes == 0) {
            return R.id.profile_header_left_back;
        }
        return actionBarBackViewIdRes;
    }

    public int getActionBarTitleViewIdRes() {
        if (actionBarTitleViewIdRes == 0) {
            return R.id.profile_header_title;
        }
        return actionBarTitleViewIdRes;
    }

    public int getActionBarContainerIdRes() {
        if (actionBarContainerIdRes == 0) {
            return R.id.common_titler;
        }
        return actionBarContainerIdRes;
    }

    public LoadMoreView getLoadMoreView() {
        if (loadMoreView == null) {
            return new SimpleLoadMoreView();
        }
        return loadMoreView;
    }

    public static class Builder {

        private BookStoreViewParams P = new BookStoreViewParams();

        public Builder(@LayoutRes int contentViewLayoutRes,  @IdRes int recyclerViewIdRes) {
            P.contentViewLayoutRes = contentViewLayoutRes;
            P.recyclerViewIdRes = recyclerViewIdRes;
        }

        public Builder setPullDownViewIdRes(@IdRes int pullDownViewIdRes) {
            P.pullDownViewIdRes = pullDownViewIdRes;
            return this;
        }

        public Builder setLoadingViewIdRes(@IdRes int loadingViewIdRes) {
            P.loadingViewIdRes = loadingViewIdRes;
            return this;
        }

        public Builder setDataErrorViewIdRes(@IdRes int dataErrorViewIdRes) {
            P.dataErrorViewIdRes = dataErrorViewIdRes;
            return this;
        }

        public Builder setNetErrorViewIdRes(@IdRes int netErrorViewIdRes) {
            P.netErrorViewIdRes = netErrorViewIdRes;
            return this;
        }

        public Builder setActionBarBackViewIdRes(@IdRes int actionBarBackViewIdRes) {
            P.actionBarBackViewIdRes = actionBarBackViewIdRes;
            return this;
        }

        public Builder setActionBarTitleViewIdRes(@IdRes int actionBarTitleViewIdRes) {
            P.actionBarTitleViewIdRes = actionBarTitleViewIdRes;
            return this;
        }

        public Builder setActionBarContainerIdRes(@IdRes int actionBarContainerIdRes) {
            P.actionBarContainerIdRes = actionBarContainerIdRes;
            return this;
        }

        public Builder setLoadMoreView(LoadMoreView loadMoreView) {
            P.loadMoreView = loadMoreView;
            return this;
        }

        public BookStoreViewParams build() {
            return P;
        }
    }


}

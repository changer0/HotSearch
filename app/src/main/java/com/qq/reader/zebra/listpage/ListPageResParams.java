package com.qq.reader.zebra.listpage;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

/**
 * ListPage 资源参数
 */
public class ListPageResParams {
    @LayoutRes private int contentViewLayoutRes;
    @IdRes private int recyclerViewIdRes;
    @IdRes private int pullDownViewIdRes;
    @IdRes private int loadingViewIdRes;
    @IdRes private int dataErrorViewIdRes;

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

    public int getDataErrorViewIdRes() {
        return dataErrorViewIdRes;
    }

    public static class Builder {

        private ListPageResParams P = new ListPageResParams();

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

        public ListPageResParams build() {
            return P;
        }
    }


}

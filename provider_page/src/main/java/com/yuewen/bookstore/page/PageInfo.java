package com.yuewen.bookstore.page;

import com.yuewen.bookstore.filter.IViewAttachFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面信息
 */
public class PageInfo {
    private String titleName;
    private int startIndex;
    private boolean isEnablePullDownRefresh;
    private boolean isEnableLoadMore;
    private List<IViewAttachFilter> viewAttachFilters;

    private PageInfo() {
    }

    public String getTitleName() {
        return titleName;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public boolean isEnablePullDownRefresh() {
        return isEnablePullDownRefresh;
    }

    public boolean isEnableLoadMore() {
        return isEnableLoadMore;
    }

    public List<IViewAttachFilter> getViewAttachFilters() {
        return viewAttachFilters;
    }

    /**
     * 构建类
     */
    public static class Builder {
        private PageInfo P = new PageInfo();

        public Builder setTitleName(String titleName) {
            P.titleName = titleName;
            return this;
        }
        public Builder setStartIndex(int startIndex) {
            P.startIndex = startIndex;
            return this;
        }
        public Builder setEnablePullDownRefresh(boolean enablePullDownRefresh) {
            P.isEnablePullDownRefresh = enablePullDownRefresh;
            return this;
        }
        public Builder setEnableLoadMore(boolean enableLoadMore) {
            P.isEnableLoadMore = enableLoadMore;
            return this;
        }

        public Builder addViewAttachFilter(IViewAttachFilter viewAttachFilter) {
            if (P.viewAttachFilters == null) {
                P.viewAttachFilters = new ArrayList<>();
            }
            P.viewAttachFilters.add(viewAttachFilter);
            return this;
        }

        public PageInfo build() {
            return P;
        }
    }
}

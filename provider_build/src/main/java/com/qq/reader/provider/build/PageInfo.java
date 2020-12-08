package com.qq.reader.provider.build;

/**
 * 页面信息
 */
public class PageInfo {
    private String titleName;
    private int startIndex;
    private boolean isEnablePullDownRefresh;
    private boolean isEnableLoadMore;

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

        public PageInfo build() {
            return P;
        }
    }
}

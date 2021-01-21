package com.example.providermoduledemo.build;

import com.yuewen.zebra.building.IFragmentParam;
/**
 * 页面信息
 */
public class FragmentParam implements IFragmentParam {
    private String titleName;
    private int startIndex;
    private boolean isEnablePullDownRefresh;
    private boolean isEnableLoadMore;

    private FragmentParam() {
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
        private FragmentParam P = new FragmentParam();

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


        public FragmentParam build() {
            return P;
        }
    }
}

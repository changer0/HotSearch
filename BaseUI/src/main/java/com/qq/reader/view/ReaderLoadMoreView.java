package com.qq.reader.view;

import com.qq.reader.baseui.R;
import com.qq.reader.widget.recyclerview.loadmore.LoadMoreView;

/**
 * LoadMoreView具體實現類
 *
 * @author liubin
 * @date 2018/10/10
 */
public class ReaderLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.view_load_more_layout;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.layout_loading;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.view_load_fail;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.view_load_end;
    }

    /**
     * 设置底部 完成加载更多 文案
     * @param text
     */
    public void setLoadEndText(String text) {
        mEndMessage = text;
    }
}

package com.qq.reader.view;

import com.qq.reader.baseui.R;
import com.qq.reader.widget.recyclerview.base.BaseViewHolder;
import com.qq.reader.widget.recyclerview.loadmore.LoadMoreView;

/**
 * LoadMoreView 频道页底部
 *
 * @author zhanglulu
 * @date 2019/03/19
 */
public class ChannelLoadMoreView extends ReaderLoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.channel_load_more_layout;
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

    @Override
    protected void visibleLoadEnd(BaseViewHolder holder, boolean visible) {
        super.visibleLoadEnd(holder, visible);
        holder.setGone(R.id.view_load_end_bottom, visible);
    }
}

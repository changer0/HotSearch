package com.qq.reader.bookstore.filter;



import com.qq.reader.bookstore.CommonViewHolder;
import com.qq.reader.bookstore.QuickRecyclerViewAdapter;
import com.yuewen.reader.zebra.BaseViewBindItem;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewAttach 过滤器链
 * @author zhanglulu
 */
public class ViewAttachedChain implements IViewAttachFilter {
    List<IViewAttachFilter> filters = new ArrayList<>();
    int index = 0;

    public ViewAttachedChain addFilter(IViewAttachFilter filter) {
        filters.add(filter);
        return this;
    }


    @Override
    public void doFilter(BaseViewBindItem<?, CommonViewHolder> viewBindItem, CommonViewHolder viewHolder, QuickRecyclerViewAdapter adapter, ViewAttachedChain chain) throws Exception{
        if (index >= filters.size()) return;

        IViewAttachFilter filter = filters.get(index);
        index++;
        filter.doFilter(viewBindItem, viewHolder, adapter, chain);
    }

    public void resetIndex() {
        index = 0;
    }
}

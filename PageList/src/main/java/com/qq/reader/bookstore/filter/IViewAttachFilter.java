package com.qq.reader.bookstore.filter;


import com.qq.reader.bookstore.CommonViewHolder;
import com.qq.reader.bookstore.QuickRecyclerViewAdapter;
import com.yuewen.reader.zebra.BaseViewBindItem;

/**
 * ViewAttach 过滤器
 * @author zhanglulu
 */
public interface IViewAttachFilter {
    void doFilter(BaseViewBindItem<?, CommonViewHolder> viewBindItem, CommonViewHolder viewHolder, QuickRecyclerViewAdapter adapter, ViewAttachedChain chain) throws Exception;
}

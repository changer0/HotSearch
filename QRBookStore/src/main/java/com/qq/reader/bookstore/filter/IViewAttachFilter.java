package com.qq.reader.bookstore.filter;


import com.qq.reader.bookstore.CommonViewHolder;
import com.qq.reader.bookstore.QuickRecyclerViewAdapter;
import com.qq.reader.zebra.BaseViewBindItem;

/**
 * ViewAttach 过滤器
 * @author zhanglulu
 * @date : 2020/12/14 11:22 AM
 */
public interface IViewAttachFilter {
    void doFilter(BaseViewBindItem<?, CommonViewHolder> viewBindItem, CommonViewHolder viewHolder, QuickRecyclerViewAdapter adapter, ViewAttachedChain chain) throws Exception;
}

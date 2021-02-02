package com.qq.reader.bookstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qq.reader.bookstore.filter.IViewAttachFilter;
import com.qq.reader.bookstore.filter.ViewAttachedChain;
import com.qq.reader.zebra.BaseViewBindItem;

import java.util.List;

/**
 * @author zhanglulu on 2019/2/28.
 * for RecyclerView Adapter
 */
public class QuickRecyclerViewAdapter extends BaseQuickAdapter<BaseViewBindItem, CommonViewHolder> {

    private final Context mContext;

//    private ViewAttachedChain viewAttachedChain;

    public QuickRecyclerViewAdapter(Context context, @Nullable List<BaseViewBindItem> data) {
        super(data);
        mContext = context;
//        viewAttachedChain = new ViewAttachedChain();
//        viewAttachedChain.addFilter((viewBindItem, viewHolder, adapter, chain) -> {
//            viewBindItem.attachView(viewHolder);
//            chain.doFilter(viewBindItem, viewHolder, adapter, chain);
//        });
    }


    @Override
    protected CommonViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if (mContext != null) {
            itemView = LayoutInflater.from(mContext).inflate(viewType, parent, false);
        }
        return createBaseViewHolder(itemView);
    }

    @SuppressWarnings("all")
    @Override
    protected void convert(CommonViewHolder holder,
                           BaseViewBindItem item) {
        int position = holder.getLayoutPosition() - getHeaderLayoutCount();
        if (item != null) {
            item.setIndex(position);
            try {
//                viewAttachedChain.resetIndex();
//                viewAttachedChain.doFilter(item, holder, this , viewAttachedChain);

                item.attachView(holder);
            } catch (Exception e) {
                //如果发生异常, 该item不显示
                holder.itemView.setVisibility(View.GONE);
                item.hideItemView();
                e.printStackTrace();
            }
        }
    }


    @Override
    protected int getDefItemViewType(int position) {
        int type = 0;
        BaseViewBindItem item = getItem(position);
        if (item != null) {
            type = item.getResLayoutId();
        }
        return type;
    }

//    /**
//     * 添加 ViewAttach 过滤器
//     * @param filter
//     */
//    public void addViewAttachFilter(IViewAttachFilter filter) {
//        viewAttachedChain.addFilter(filter);
//    }
}

package com.qq.reader.provider.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qq.reader.provider.BaseViewBindItem;

import java.util.List;

/**
 * @author zhanglulu on 2019/2/28.
 * for RecyclerView Adapter
 */
public class SimpleRecyclerViewAdapter extends BaseQuickAdapter<BaseViewBindItem, BaseViewHolder> {

    private final Context mContext;

    public SimpleRecyclerViewAdapter(Context context, @Nullable List<BaseViewBindItem> data) {
        super(data);
        mContext = context;
    }


    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if (mContext != null) {
            itemView = LayoutInflater.from(mContext).inflate(viewType, parent, false);
        }
        return createBaseViewHolder(itemView);
    }

    @SuppressWarnings("all")
    @Override
    protected void convert(BaseViewHolder holder,
                           BaseViewBindItem item) {

        int position = holder.getLayoutPosition() - getHeaderLayoutCount();
        if (item != null) {
            item.setIndex(position);
            try {
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
}

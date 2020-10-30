package com.qq.reader.provider.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.qq.reader.provider.BaseViewBindItem;
import com.qq.reader.provider.bean.BaseDataBean;
import com.qq.reader.widget.recyclerview.base.BaseQuickAdapter;
import com.qq.reader.widget.recyclerview.base.BaseViewHolder;

import java.util.List;

/**
 * @author zhanglulu on 2019/2/28.
 * for RecyclerView Adapter
 */
public class SimpleRecyclerViewAdapter extends BaseQuickAdapter<BaseViewBindItem, BaseViewHolder> {

    private static final String TAG = "NativeBookStoreAdapterF";
    private Context mContext;

    public SimpleRecyclerViewAdapter(Context context, @Nullable List<BaseViewBindItem> data) {
        super(data);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder holder, int position) {
        BaseViewBindItem<? extends BaseDataBean, BaseViewHolder> item = getItem(position);
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
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if (mContext != null) {
            itemView = LayoutInflater.from(mContext).inflate(viewType, parent, false);
        }
        return createBaseViewHolder(itemView);
    }


    @Override
    protected int getDefItemViewType(int position) {
        int type = 0;
        BaseViewBindItem<? extends BaseDataBean, BaseViewHolder> item = getItem(position);
        if (item != null) {
            type = item.getResLayoutId();
        }
        return type > 0 ? type : 0;
    }
}

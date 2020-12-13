package com.example.providermoduledemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qq.reader.provider.BaseViewBindItem;
import com.qq.reader.provider.viewmodel.CommonViewHolder;

import java.util.List;

/**
 * @author zhanglulu on 2019/2/28.
 * for RecyclerView Adapter
 */
public class QuickRecyclerViewAdapter extends BaseQuickAdapter<BaseViewBindItem, CommonViewHolder> {

    private final Context mContext;

    public QuickRecyclerViewAdapter(Context context, @Nullable List<BaseViewBindItem> data) {
        super(data);
        mContext = context;
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
                //此处需要格外注意，由于 ViewModel 模块本身采用了 CommonViewHolder，过此处需要做转换！！
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

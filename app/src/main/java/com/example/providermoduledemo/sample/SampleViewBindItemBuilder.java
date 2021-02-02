package com.example.providermoduledemo.sample;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.providermoduledemo.viewmodel.ViewBindItemLIRTGroupStyle0;
import com.example.providermoduledemo.viewmodel.ViewBindItemLIRTGroupStyle1;
import com.qq.reader.bookstore.CommonViewHolder;
import com.qq.reader.zebra.BaseViewBindItem;
import com.qq.reader.zebra.inter.IViewBindItemBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewBindItemBuilder 示例
 */
public class SampleViewBindItemBuilder implements IViewBindItemBuilder<SampleResultBean> {
    @Override
    public List<BaseViewBindItem<?, ? extends RecyclerView.ViewHolder>> buildViewBindItem(@NonNull SampleResultBean data) {
        List<BaseViewBindItem<?, ? extends RecyclerView.ViewHolder>> viewBindItemList = new ArrayList<>();
        List<SampleResultBean.Item> dataList = data.getList();
        if (dataList == null) return viewBindItemList;
        for (SampleResultBean.Item item : dataList) {
            BaseViewBindItem<SampleResultBean.Item, CommonViewHolder> bindViewItem = null;
            switch (item.getStyle()) {
                case 0:
                    bindViewItem = new ViewBindItemLIRTGroupStyle0();
                    break;
                case 1:
                    bindViewItem = new ViewBindItemLIRTGroupStyle1();
                    break;
            }
            if (bindViewItem != null) {
                bindViewItem.setData(item);
                viewBindItemList.add(bindViewItem);
            }
        }
        return viewBindItemList;
    }
}
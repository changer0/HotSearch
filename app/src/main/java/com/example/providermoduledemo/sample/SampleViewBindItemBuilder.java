package com.example.providermoduledemo.sample;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.providermoduledemo.viewmodel.ViewBindItemLIRTGroupStyle0;
import com.example.providermoduledemo.viewmodel.ViewBindItemLIRTGroupStyle1;
import com.qq.reader.provider.BaseViewBindItem;
import com.qq.reader.provider.inter.IViewBindItemBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewBindItemBuilder 示例
 */
public class SampleViewBindItemBuilder implements IViewBindItemBuilder<SampleResponseBean> {
    @Override
    public List<BaseViewBindItem> buildViewBindItem(@NonNull SampleResponseBean data) {
        List<BaseViewBindItem> viewBindItemList = new ArrayList<>();
        List<SampleResponseBean.Item> dataList = data.getList();
        if (dataList == null) return viewBindItemList;
        for (SampleResponseBean.Item item : dataList) {
            BaseViewBindItem<SampleResponseBean.Item, BaseViewHolder> bindViewItem = null;
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
package com.example.providermoduledemo.sample;

import androidx.annotation.NonNull;

import com.example.providermoduledemo.viewmodel.ViewBindItemLIRTGroupStyle0;
import com.example.providermoduledemo.viewmodel.ViewBindItemLIRTGroupStyle1;
import com.qq.reader.provider.BaseViewBindItem;
import com.qq.reader.provider.inter.IViewBindItemBuilder;
import com.qq.reader.provider.simple.SimpleViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewBindItemBuilder 示例
 */
public class SampleViewBindItemBuilder implements IViewBindItemBuilder<SampleResultBean> {
    @Override
    public List<BaseViewBindItem> buildViewBindItem(@NonNull SampleResultBean data) {
        List<BaseViewBindItem> viewBindItemList = new ArrayList<>();
        List<SampleResultBean.Item> dataList = data.getList();
        if (dataList == null) return viewBindItemList;
        for (SampleResultBean.Item item : dataList) {
            BaseViewBindItem<SampleResultBean.Item, SimpleViewHolder> bindViewItem = null;
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
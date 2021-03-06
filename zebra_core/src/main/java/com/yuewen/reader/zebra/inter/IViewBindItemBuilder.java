package com.yuewen.reader.zebra.inter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuewen.reader.zebra.BaseViewBindItem;
import java.util.List;

/**
 * ViewBindItem 构建器
 */
public interface IViewBindItemBuilder<T> {
    /**构建ViewBindItem*/
    List<BaseViewBindItem<?, ? extends RecyclerView.ViewHolder>> buildViewBindItem(@NonNull T data);
}

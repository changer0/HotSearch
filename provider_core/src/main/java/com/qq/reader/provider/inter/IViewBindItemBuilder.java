package com.qq.reader.provider.inter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qq.reader.provider.BaseViewBindItem;
import java.util.List;

/**
 * ViewBindItem 构建器
 */
public interface IViewBindItemBuilder<T> {
    /**构建ViewBindItem*/
    List<BaseViewBindItem<?, ? extends RecyclerView.ViewHolder>> buildViewBindItem(@NonNull T data);

}

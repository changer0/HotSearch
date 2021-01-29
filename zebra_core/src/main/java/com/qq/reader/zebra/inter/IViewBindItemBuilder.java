package com.qq.reader.zebra.inter;

import androidx.annotation.NonNull;

import com.qq.reader.zebra.BaseViewBindItem;
import java.util.List;

/**
 * ViewBindItem 构建器
 */
public interface IViewBindItemBuilder<T> {
    /**构建ViewBindItem*/
    List<BaseViewBindItem> buildViewBindItem(@NonNull T data);
}

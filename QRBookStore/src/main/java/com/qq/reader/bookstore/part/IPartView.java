package com.qq.reader.bookstore.part;

/**
 * Item 中局部 View
 * @author zhanglulu on 2020/10/23.
 */
public interface IPartView<T extends IPartViewModel> {
    void setViewModel(T model);
}

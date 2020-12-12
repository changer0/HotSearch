package com.qq.reader.provider.viewmodel;

/**
 * @author zhanglulu on 2020/10/23.
 * for
 */
public interface IView<T extends IViewModel> {
    void setViewModel(T model);
}

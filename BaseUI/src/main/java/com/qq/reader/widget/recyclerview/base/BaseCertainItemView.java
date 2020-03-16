package com.qq.reader.widget.recyclerview.base;

/**
 * File description.
 *
 * @author p_bbinliu
 * @date 2019-12-15
 */
public abstract class BaseCertainItemView {

    public abstract int getLayoutId();

    public abstract void onBindViewHolder(BaseViewHolder holder);

    public abstract int getCertainItemIndex();
    
    public abstract void notifyData();
}

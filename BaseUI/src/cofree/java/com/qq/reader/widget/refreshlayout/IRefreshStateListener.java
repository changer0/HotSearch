package com.qq.reader.widget.refreshlayout;

/**
 * @author zhanglulu on 2019/12/16.
 * for
 */
public interface IRefreshStateListener {
    void setState(int state);
    void onUpdateScroll(float diff);
}

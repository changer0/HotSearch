package com.qq.reader.view.refresh;

/**
 * 刷新相关的接口.
 *
 * @author liubin
 * @date 2018/3/2
 */

public abstract class OnRefreshListener {

    /**
     * 刷新
     */
    public abstract void onRefresh();


    /**
     * 二级刷新（类似于二楼）
     */
    public void onTwoLevel() {}
}

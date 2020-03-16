package com.qq.reader.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lvxinghe on 2018/7/5.
 */

public interface IBottomNavigationView {

    /**
     * 初始化bottom布局
     */
    void inflate(Context context,ViewGroup rootView);

    /**
     * 设置当前选中tab
     * @param index
     * @return
     */
    boolean setCurrentTab(int index);

    /**
     * 切换tab 监听
     * @param listener
     */
    void setOnTabChangedListener(OnTabChangedListener listener);

    /**
     * 历史记录里上个选中tab的index
     * @return
     */
    int getPreTabIndex();

    /**
     * 当前选中tab的index
     * @return
     */
    int getCurrentTabIndex();

    /**
     * 加入历史记录
     * @param index
     */
    void addToHistory(int index);

    /**
     * 清空历史记录
     */
    void clearHistory();

    /**
     * 处理tab红点逻辑
     * @param positoin
     * @param isShowReddot
     */
    void handleRedDot(int positoin, boolean isShowReddot);

    /**
     * 获取引导要显示的位置，看代码似乎废弃了e
     * @return
     */
    int[] getArea();

    /**
     * 获取当前view
     * @return
     */
    View getView();

    /**
     * 现在不再判断lastPosition和currentPosition是否是一样的，交给回调去判断
     * @author p_jwcao
     */
    interface OnTabChangedListener {
        void onTabSelectionChanged(int lastPosition,int currentPosition);
    }

}

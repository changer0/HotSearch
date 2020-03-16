package com.qq.reader.widget;

import android.util.SparseArray;
import android.view.MenuItem;

public interface IBottomMenu {
    
    void hideBottom();
    
    void showBottom();

    //common分支，使用自定义actionbar,添加和更新使用同一方法
    void inflateBottomMenu(SparseArray<MenuItem> mMenuItems, int[] mMenuItemIds);

    //点击事件操作的接口
    interface IOnBottomMenuClickListener {
        boolean onClick(IActionBar.IMenuItem item);
    }

    boolean isNull();
    
    //设置按钮的点击事件
    //【Java8重构：因为IOnOptionsMenuClickListener是一个函数式接口，所以可以使用lambda进行重构。
    //除了使用自定义IOnOptionsMenuClickListener函数式接口外。可以使用系统提供的函数式接口Predicate，因为它的签名是接受一个泛型T的对象，返回一个boolean值】
    void setOnOptionsMenuClickListener(IOnBottomMenuClickListener onClickListener);

    //ActionBar按钮点击的处理
    boolean onOptionsItemSelected(MenuItem item);
}

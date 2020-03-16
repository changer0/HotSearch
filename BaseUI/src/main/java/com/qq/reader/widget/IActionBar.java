package com.qq.reader.widget;

import android.graphics.drawable.Drawable;
import androidx.annotation.ColorRes;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

/**
 * Created by qiaoshouqing on 2017/2/23.
 */

public interface IActionBar {
    int EMPTY_LAYOUT = 0;

    //设置标题文字
    void setTitle(String str);
    void setTitle(int id);
    //设置返回图标右边的文字
    void setBackTitle(String str);
    void setBackIcon(int drawableResource);
    //设置返回按钮的图片和文字。以下三个接口不允许除JSInteract外的地方使用。以后想想能不能用注解规避一下。
    void setLeftIcon(Drawable drawable);
    void setLeftIcon(int drawableResource);
    void setLeftTitle(String str);

    void setRightItem(String str);
    void setRightItemColor(@ColorRes int color);

    //隐藏标题栏，仅仅在Oppo侧有用。
    void hide();
    void show();

    void hideBottom();
    void showBottom();
    

    boolean isNull();

    View getDeepLinkBackView();

    //设置标题栏下拉菜单
    void setNavigationMode(int var1);
    void setListNavigationCallbacks(SpinnerAdapter var1, IOnNavigationListener var2);
    void setSelectedNavigationItem(int var1);
    boolean haveNavigation();

    //设置标题栏背景图片
    void setBackgroundResource(int titler_bg);
    //设置标题栏主题色
    void setBackgroundColor(int color);

    //设置标题透明度
    void setTitleAlpha(int i);

    interface IOnNavigationListener {
        boolean onNavigationItemSelected(int var1, long var2);
    }

    //获取标题栏按钮布局
    void inflate(int layoutId, Menu menu);
    //这个方法仅对于oppo这一边写的是布局，华为写的是menu的情况处理。
    View inflateBottom(ViewGroup rootView, int layout, int[] layoutId, int[] textId, int[] iconId);

    //common分支，使用自定义actionbar,添加和更新使用同一方法
    void inflateBottomMenu(SparseArray<MenuItem> mMenuItems,int[] mMenuItemIds);

    //点击事件操作的接口
    interface IOnOptionsMenuClickListener {
        boolean onClick(IMenuItem item);
    }
    //设置按钮的点击事件
    //【Java8重构：因为IOnOptionsMenuClickListener是一个函数式接口，所以可以使用lambda进行重构。
    //除了使用自定义IOnOptionsMenuClickListener函数式接口外。可以使用系统提供的函数式接口Predicate，因为它的签名是接受一个泛型T的对象，返回一个boolean值】
    void setOnOptionsMenuClickListener(IOnOptionsMenuClickListener onClickListener);

    //ActionBar按钮点击的处理
    boolean onOptionsItemSelected(MenuItem item);

    //封装右侧按钮为IActionBarItem
    public interface IMenuItem {
        void setVisible(boolean bool);
        void setIcon(int drawableResource);
        void setIcon(Drawable drawable);
        int getItemId();
        void setTitle(String title);
        void setTitle(int titleRes);
        void setTitleColor(@ColorRes int colorResource);
        String getTitle();
        void setEnabled(boolean menuEnable);
        void setClickable(boolean b);
        int getLayoutId();
        //下面两个函数仅仅是给ReaderActionBar设置点击事件使用的，oppo侧不需要。这里使用默认函数优化。
        //itemId是在menu文件里设置的id,暴露给外层获取item以及外部点击事件使用。
        default void setItemId(int id) {
            throw new UnsupportedOperationException();
        }
        default void setOnClickListener(View.OnClickListener onClickListener) {
            throw new UnsupportedOperationException();
        }
    }


    /**
     * 获取右侧按钮
     * @param id oppo分支menuItem的id
     * @return
     */
    IMenuItem findItem(int id);

    //是否加返回图标
    void setDisplayHomeAsUpEnabled(boolean var1);

    void setCustomView(View var1);

    /**
     * actionbar中的spinner
     * @param listener
     * @param adapter
     * @param section
     */
    void setCustomViewSpinner(IOnItemSelectedListener listener, SpinnerAdapter adapter, int section);

    /**
     * spinner的OnItemSelectedListener
     */
    interface IOnItemSelectedListener {
        void onItemSelected(int var3, long var4);
        void onNothingSelected();
    }

    /**
     * 获取ActionBar title
     * @return
     */
    CharSequence getTitle();

    /**
     * 获取actionbar是否显示title
     */
    void setDisplayShowTitleEnabled(boolean showTitle);

    int getNavigationMode();

    void setDisplayOptions(int options, int mask);

    void setAddBackItem(boolean addBackItem);

    void setShowBackItem(boolean showBackItem);
}
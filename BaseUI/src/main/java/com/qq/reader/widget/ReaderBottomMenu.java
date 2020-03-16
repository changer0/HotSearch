package com.qq.reader.widget;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.qq.reader.baseui.R;
import com.tencent.mars.xlog.Log;

public class ReaderBottomMenu implements IBottomMenu, View.OnClickListener {

    private static final String TAG = "ReaderBottomMenu";
    private Activity mActivity;
    private LayoutInflater inflater;
    private LinearLayout menuView;
    private IOnBottomMenuClickListener mOnClickListener;
    private View mBottomRoot;
    private int[] mMenuItemIds;

    public ReaderBottomMenu(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onClick(View view) {

    }

    private void initBottomMenu() {
        inflater = (LayoutInflater) mActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBottomRoot = mActivity.findViewById(R.id.reader_popbottom_menu);
        if (mBottomRoot != null) {
            menuView = (LinearLayout) mBottomRoot.findViewById(R.id.bottom_menu);
            Log.e("BranchActionBar", "初始化成功");
        } else {
            Log.e("BranchActionBar", "BranchActionBar初始化失败。" +
                    "有可能是布局里没有reader_popbottom_menu布局id，也有可能是在setContentView之前调用了该代码");
        }
    }

    private void initBottomView(SparseArray<MenuItem> mMenuItems) {
        menuView.removeAllViews();
        int menuId = 0;
        int itemInVisibles = 0;//记录不可见item的个数，如果均不可见，则隐藏menuView
        for (int i = 0; i < mMenuItems.size(); i++) {
            if (mMenuItemIds != null) {
                menuId = mMenuItemIds[i];
            } else {
                menuId = mMenuItems.keyAt(i);
            }
            MenuItem menuItem = mMenuItems.get(menuId);
            itemInVisibles = menuItem.isVisible() ? itemInVisibles : itemInVisibles + 1;
            final View view = inflater.inflate(R.layout.reader_popbottom_menu_item, null);
            BranchBottomMenuItem bottomItem = new BranchBottomMenuItem(view, menuId, R.id.menu_name, R.id.menu_icon);
            bottomItem.setTitle(String.valueOf(menuItem.getTitle()));
            bottomItem.setIcon(menuItem.getIcon());
            bottomItem.setVisible(menuItem.isVisible());

            bottomItem.setEnabled(menuItem.isEnabled());
            bottomItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickListener != null) mOnClickListener.onClick(bottomItem);
                }
            });

            menuView.addView(view);
        }
        if (itemInVisibles == mMenuItems.size()) {
            hideBottom();
        } else {
            showBottom();
        }
    }

    @Override
    public boolean isNull() {
        return mBottomRoot == null;
    }


    @Override
    public void hideBottom() {
        if (mBottomRoot != null) {
            mBottomRoot.setVisibility(View.GONE);
        }
    }

    @Override
    public void showBottom() {
        if (mBottomRoot != null) {
            mBottomRoot.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void inflateBottomMenu(SparseArray<MenuItem> mMenuItems, int[] mMenuItemIds) {
        if (mMenuItemIds.length == 0) return;
        this.mMenuItemIds = mMenuItemIds;

        if (inflater != null && menuView != null) {
            initBottomView(mMenuItems);
        } else {
            initBottomMenu();
            initBottomView(mMenuItems);
        }
    }

    @Override
    public void setOnOptionsMenuClickListener(IOnBottomMenuClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

}

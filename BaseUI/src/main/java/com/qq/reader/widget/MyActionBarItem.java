package com.qq.reader.widget;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.qq.reader.common.utils.CommonUtility;
import com.qq.reader.core.BaseApplication;

/**
 * Created by qiaoshouqing on 2017/8/31.
 */

// TODO: 2018/1/19 返回键的接口有严重问题，用起来太费劲。有时间重构一下。

public class MyActionBarItem implements IActionBar.IMenuItem {

    private TextView mItem;
    private int mItemId = -1;

    public MyActionBarItem(Activity activity, int viewId) {
        mItem = (TextView) activity.findViewById(viewId);
    }

    @Override
    public void setVisible(boolean bool) {
        if(mItem != null) {
            mItem.setVisibility(bool ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void setIcon(int drawableResource) {
        Drawable drawable = BaseApplication.Companion.getINSTANCE().getResources().getDrawable(drawableResource);
        setIcon(drawable);
    }

    @Override
    public void setIcon(Drawable drawable) {
        if(mItem != null) {
            //清除文字内容
            mItem.setText("");
            //设置图片
            setDrawable(drawable);
        }
    }

    @Override
    public void setItemId(int id) {
        mItemId = id;
    }

    @Override
    public int getItemId() {
        return mItemId;
    }

    public int getLayoutId() {
        if(mItem != null) {
            return mItem.getId();
        }
        return 0;
    }

    @Override
    public void setTitle(String title) {
        if(mItem != null) {
            removeDrawable();

            mItem.setText(title);
            mItem.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setTitle(int titleRes) {
        setTitle(BaseApplication.Companion.getINSTANCE().getResources().getString(titleRes));
    }

    @Override
    public void setTitleColor(int colorResource) {
        if(mItem != null) {
            mItem.setTextColor(BaseApplication.Companion.getINSTANCE().getResources().getColor(colorResource));
        }
    }

    @Override
    public String getTitle() {
        if(mItem != null){
            return mItem.getText().toString();
        }
        return null;
    }

    @Override
    public void setEnabled(boolean menuEnable) {
        if(mItem != null) {
            mItem.setEnabled(menuEnable);
        }
    }

    @Override
    public void setClickable(boolean b) {
        if(mItem != null) {
            mItem.setClickable(b);
        }
    }

    @Override
    public void setOnClickListener(View.OnClickListener onClickListener) {
        if(mItem != null) {
            mItem.setOnClickListener(onClickListener);
        }
    }

    //为设置图片使用
    private void setDrawable(Drawable drawable) {
        if(mItem != null) {
            int width = CommonUtility.dip2px(56);
            int leftRightBound = (width - drawable.getMinimumWidth()) / 2;
            int topBottomBound = (width - drawable.getMinimumHeight()) / 2;

            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mItem.setPadding(leftRightBound, topBottomBound, leftRightBound, topBottomBound);
            mItem.setCompoundDrawables(drawable, null, null, null);
            mItem.setVisibility(View.VISIBLE);
        }
    }

    //清除之前设置图片的设置
    private void removeDrawable() {
        if(mItem != null) {
            mItem.setCompoundDrawables(null, null, null, null);
            if (getItemId() == android.R.id.home) {
                mItem.setPadding(CommonUtility.dip2px(16), 0, CommonUtility.dip2px(16), 0);
            }
            else {
                mItem.setPadding(0, 0, CommonUtility.dip2px(14), 0);
            }
        }
    }
}

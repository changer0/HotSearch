package com.qq.reader.widget;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import com.qq.reader.core.BaseApplication;

/**
 * Created by qiaoshouqing on 2018/7/2.
 *
 * 针对底部用布局写出来的menu写的Item。
 */

public class BottomMenuItem implements IActionBar.IMenuItem {

    private View mView;
    private ReaderTextView mTextView;
    private ImageView mIconView;

    private int mItemId = -1;

    public BottomMenuItem(View parentView, int layoutId, int textId, int iconId) {
        mItemId = layoutId;

        mView = parentView.findViewById(layoutId);
        mTextView = (ReaderTextView) parentView.findViewById(textId);
        mIconView = (ImageView) parentView.findViewById(iconId);
    }

    @Override
    public void setVisible(boolean bool) {
        if(mView != null) {
            mView.setVisibility(bool ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void setIcon(int drawableResource) {
        if(mIconView != null) {
            mIconView.setImageDrawable(BaseApplication.Companion.getINSTANCE().getResources().getDrawable(drawableResource));
        }
    }

    @Override
    public void setIcon(Drawable drawable) {
        if(mIconView != null) {
            mIconView.setImageDrawable(drawable);
        }
    }

    @Override
    public int getItemId() {
        return mItemId;
    }

    @Override
    public int getLayoutId() {
        return mItemId;
    }

    private String mTitle;
    @Override
    public void setTitle(String title) {
        mTitle = title;
        if(mTextView != null) {
            mTextView.setText(title);
        }
    }

    @Override
    public void setTitle(int titleRes) {
        mTitle = BaseApplication.Companion.getINSTANCE().getResources().getString(titleRes);
        if(mTextView != null) {
            mTextView.setText(titleRes);
        }
    }

    @Override
    public void setTitleColor(int colorResource) {
        if(mTextView != null) {
            mTextView.setTextColor(colorResource);
        }
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public void setEnabled(boolean menuEnable) {
        if(mView != null) {
            mView.setEnabled(menuEnable);
        }
        if(mTextView != null) {
            mTextView.setEnabled(menuEnable);
        }
        if(mIconView != null) {
            mIconView.setEnabled(menuEnable);
        }
    }

    @Override
    public void setClickable(boolean b) {
        if(mView != null) {
            mView.setClickable(b);
        }
        if(mTextView != null) {
            mTextView.setClickable(b);
        }
        if(mIconView != null) {
            mIconView.setClickable(b);
        }
    }


    @Override
    public void setOnClickListener(View.OnClickListener onClickListener) {
        if(mView != null) {
            mView.setOnClickListener(onClickListener);
        }
    }
}

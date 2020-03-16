package com.qq.reader.widget.refreshlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * 刷新基类
 * 便于统一管理
 *
 * @author p_bbinliu
 * @date 2019-12-12
 */
public abstract class BaseRefreshHeader extends LinearLayout {

    public BaseRefreshHeader(Context context) {
        super(context);
        initView();
    }

    public BaseRefreshHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BaseRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public abstract void initView();

    public abstract void setState(int state);

    public abstract View getView();

    public void onUpdateScroll(float diff) {}
}

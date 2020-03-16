/**
 * Name        : PinnedDividerListView.java
 * Copyright   : Copyright (c) Tencent Inc. All rights reserved.
 * Description : 包含两种View的ListView：除了普通的ItemView外，还有DeviderView。 ItemView分为多组，每组有一个DeviderView打头，DeviderView一般为组名。
 * List最上方始终有一个悬浮的DeviderView来指示第一组的名称。
 */

package com.tencent.widget;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

public class PinnedDividerListView extends XListView implements AbsListView.OnScrollListener {

    public interface OnLayoutListener {
        public void onLayout(View v, int left, int top, int right, int bottom);
    }

    private static final int PINNED_DIVIDER_STATE_INVISIBLE = -1;
    private static final int PINNED_DIVDER_STATE_PINNED = 0;
    private static final int PINNED_DIVDER_STATE_PUSHING_UP = 1;

    private Context mContext = null;
    private DividerAdapter mAdapter = null;
    private View mFloatingView = null;
    private int mCurDividerViewState = PINNED_DIVDER_STATE_PINNED;
    private int mCurDividerViewPushUpDistance = 0;

    private XListView.OnScrollListener mOnScrollListener = null;

    private LinkedList<View> mHeaderViews = new LinkedList<View>();

    private OnLayoutListener mOnLayoutListener = null;

    // CAUTIONS: getView should make sure the result view is visible~
    public static abstract class DividerAdapter extends BaseAdapter {
        public abstract int getDividerLayout();

        public abstract boolean isDividerView(int position);

        public abstract void configDividerView(View view, int position);
    }

    public PinnedDividerListView(Context context) {
        super(context);
        init(context);
    }

    public PinnedDividerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PinnedDividerListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setOnLayoutListener(OnLayoutListener l) {
        mOnLayoutListener = l;
    }

    @Override
    public void addHeaderView(View header) {
        addHeaderView(header, null, false);
    }

    @Override
    public void addHeaderView(View header, Object data, boolean isSelectable) {
        super.addHeaderView(header, data, isSelectable);
        mHeaderViews.add(header);
    }

    @Override
    public boolean removeHeaderView(View header) {
        boolean result = super.removeHeaderView(header);
        if (result) {
            mHeaderViews.remove(header);
        }
        return result;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (adapter instanceof DividerAdapter) {
            mAdapter = (DividerAdapter) adapter;
            int dividerViewLayoutId = mAdapter.getDividerLayout();
            if (dividerViewLayoutId != 0) {
                mFloatingView = LayoutInflater.from(mContext).inflate(dividerViewLayoutId, this,
                        false);
                requestLayout();
            }
            super.setAdapter(mAdapter);
        } else {
            mAdapter = null;
            super.setAdapter(adapter);
        }
    }

    @Override
    public void setOnScrollListener(AbsListView.OnScrollListener l) {
        mOnScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount) {
        if (mFloatingView != null) {
            if (firstVisibleItem >= mHeaderViews.size()) {
                boolean makeFirstChildInvisible = false;
                firstVisibleItem -= mHeaderViews.size();
                mAdapter.configDividerView(mFloatingView, firstVisibleItem);
                if (mAdapter.isDividerView(firstVisibleItem)) {
                    mCurDividerViewState = PINNED_DIVDER_STATE_PINNED;
                    makeFirstChildInvisible = true;
                } else {
                    View v = getChildAt(0);
                    if (v.getBottom() > mFloatingView.getMeasuredHeight()) {
                        mCurDividerViewState = PINNED_DIVDER_STATE_PINNED;
                    } else {
                        if (mAdapter.isDividerView(firstVisibleItem + 1)) {
                            mCurDividerViewState = PINNED_DIVDER_STATE_PUSHING_UP;
                        } else {
                            mCurDividerViewState = PINNED_DIVDER_STATE_PINNED;
                        }
                    }
                }
                int childCount = getChildCount();
                View v;
                if (makeFirstChildInvisible) {
                    v = getChildAt(0);
                    if (v.getVisibility() != View.INVISIBLE) {
                        v.setVisibility(View.INVISIBLE);
                    }
                }
                for (int i = (makeFirstChildInvisible ? 1 : 0); i < childCount; ++i) {
                    v = getChildAt(i);
                    if (v.getVisibility() != View.VISIBLE) {
                        v.setVisibility(View.VISIBLE);
                    }
                }
                if (mCurDividerViewState == PINNED_DIVDER_STATE_PUSHING_UP) {
                    View childAtOne = getChildAt(1);
                    mCurDividerViewPushUpDistance = mFloatingView.getMeasuredHeight()
                            - (childAtOne != null ? childAtOne.getTop() : 0);
                } else {
                    mCurDividerViewPushUpDistance = 0;
                }
                mFloatingView.setVisibility(View.VISIBLE);
                mFloatingView.layout(0, -mCurDividerViewPushUpDistance,
                        mFloatingView.getMeasuredWidth(), mFloatingView.getMeasuredHeight()
                                - mCurDividerViewPushUpDistance);
                //mFloatingView.invalidate();
            } else {
                mCurDividerViewState = PINNED_DIVIDER_STATE_INVISIBLE;
                mFloatingView.setVisibility(INVISIBLE);
                int firstNonHeaderViewPos = mHeaderViews.size();
                if (mAdapter.getCount() > 0 && mAdapter.isDividerView(0)
                        && firstNonHeaderViewPos >= firstVisibleItem
                        && firstNonHeaderViewPos < firstVisibleItem + visibleItemCount) {
                    getChildAt(firstNonHeaderViewPos - firstVisibleItem)
                            .setVisibility(View.VISIBLE);
                }
            }
        }
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mFloatingView != null) {
            measureChild(mFloatingView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @SuppressLint("WrongCall")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mFloatingView != null) {
            int firstItem = getFirstVisiblePosition() - mHeaderViews.size();
            if (firstItem >= 0) {
                mFloatingView.setVisibility(View.VISIBLE);
                mFloatingView.layout(0, -mCurDividerViewPushUpDistance,
                        mFloatingView.getMeasuredWidth(), mFloatingView.getMeasuredHeight()
                                - mCurDividerViewPushUpDistance);
                mAdapter.configDividerView(mFloatingView, firstItem);
            } else {
                mFloatingView.setVisibility(View.INVISIBLE);
            }
        }
        final int childCount = getChildCount();
        View v;
        for (int i = mHeaderViews.size() + 1; i < childCount; ++i) {
            v = getChildAt(i);
            if (v.getVisibility() != View.VISIBLE) {
                v.setVisibility(View.VISIBLE);
            }
        }
        if (mOnLayoutListener != null) {
            mOnLayoutListener.onLayout(this, left, top, right, bottom);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mFloatingView != null && mFloatingView.getVisibility() == View.VISIBLE) {
            drawChild(canvas, mFloatingView, getDrawingTime());
        }
    }

    private void init(Context context) {
        mContext = context;
        super.setOnScrollListener(this);
    }
}

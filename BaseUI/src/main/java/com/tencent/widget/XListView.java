package com.tencent.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * 增加上拉/下拉刷新功能
 * 
 * @FIXME 子View中如果有EditText，在某些2.3手机上，会发生leak的window（TextView.HandleView）的问题：<br>
 *        重现方法：点击EditText，显示出HandleView；5s之内back退出Activity。
 */
public class XListView extends ListView {
	public interface MotionEventInterceptor {
		boolean intercept(View eventRelativeToTheView, MotionEvent event);
	}

	public static final int WINDOW_ORIENTATION_LANDSCAPE = 1;
	public static final int WINDOW_ORIENTATION_POERRAIT = 2;
	// 当前屏幕方向
	private int mOrientation = 0;

	private OnSizeChangeListener mOnSizeChangeListener;
	private MotionEventInterceptor mInterceptor;

	public XListView(Context context) {
		this(context, null);
	}

	public XListView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.listViewStyle);
	}

	public XListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// 关掉边界动画
		setEdgeEffectEnabled(false);
		mOverscrollDistance = Integer.MAX_VALUE;
		// setOverScrollMode(ListView.OVER_SCROLL_ALWAYS);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int currentOrientation = getWindowOrientation();
		// 通知监听器
		notifySizeChanged(w, h, oldw, oldh, mOrientation != currentOrientation,
				currentOrientation);
		mOrientation = currentOrientation;
	}

	public void setOnSizeChangeListener(OnSizeChangeListener listener) {
		this.mOnSizeChangeListener = listener;
	}

	private void notifySizeChanged(int w, int h, int oldw, int oldh,
			boolean windowOrientationChanged, int currentOrientation) {
		if (mOnSizeChangeListener != null) {
			mOnSizeChangeListener.onSizeChanged(w, h, oldw, oldh,
					windowOrientationChanged, currentOrientation);
		}

	}

	private int getWindowOrientation() {
		int width = getContext().getResources().getDisplayMetrics().widthPixels;
		int height = getContext().getResources().getDisplayMetrics().heightPixels;
		return width > height ? WINDOW_ORIENTATION_LANDSCAPE
				: WINDOW_ORIENTATION_POERRAIT;
	}

	// kanedong[03/04/2014] 以下方法均为个性名片需求所加：
	public void setOverScrollDistance(int distance) {
		mOverscrollDistance = distance;
	}

	public void setMotionEventInterceptor(MotionEventInterceptor interceptor) {
		mInterceptor = interceptor;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		boolean handled = false;
		/**
		 * kanedong[03/10/2014] 因为该容器在实现过量滚动(Over Scroll) 时截获了所有 MotionEvent
		 * 事件，导致子 View 在 Over Scroll View 显示时无法收到 任何
		 * MotionEvent，继而不响应点击等操作。为解决这个问题在此增加一个事件截取 机制，让一些特殊的需求得以实现（例如个性资料卡要以
		 * Over Scroll Over 作为可 处理点击事件的引导条）
		 */
		if (null != mInterceptor) {
			// ev 里的坐标等信息是相对 XListView 对象的，如果直接给别的 View 使用可能需要
			// 转换坐标，因此创建一个副本给 interceptor，避免发生改变后影响本对象使用。
			MotionEvent eventCopy = MotionEvent.obtain(ev);
			if (null != eventCopy) {
				handled = mInterceptor.intercept(this, eventCopy);
				eventCopy.recycle();
			}
		}
		return (handled || super.dispatchTouchEvent(ev));
	}

	public void setEnsureOverScrollStatusToIdleWhenRelease(boolean b) {
		mEnsureOverScrollStatusToIdleWhenRelease = b;
	}
	///////////////////////////
	//解决 listview 和Viewpager冲突
    private float xDistance, yDistance, xLast, yLast;
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
        case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if (xDistance > yDistance) {
                        return false;
                }
        }
		return super.onInterceptTouchEvent(ev);
	}
	////////////////////////////
}

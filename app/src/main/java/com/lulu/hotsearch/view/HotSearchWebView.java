package com.lulu.hotsearch.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;

import com.lulu.hotsearch.OnStartLoadingListener;

/**
 * Author: zhanglulu
 * Time: 2021/2/15
 */
public class HotSearchWebView extends WebView {
    public static final int STEP = 200;
    private static final String TAG = "HotSearchWebView";
    public HotSearchWebView(Context context) {
        super(context);
    }

    public HotSearchWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HotSearchWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HotSearchWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * public boolean dispatchTouchEvent(MotionEvent event) {
     *     if (mOnTouchListener != null && (mViewFlags & ENABLED_MASK) == ENABLED &&
     *             mOnTouchListener.onTouch(this, event)) {
     *         return true;
     *     }
     *     return onTouchEvent(event);
     * }
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        //Log.d(TAG, "dispatchTouchEvent: action :" + action + " event.getX():" + event.getX() + " event.getRawX():" + event.getRawX());
        float curX = event.getX();
        float curY = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = curX;
                downY = curY;
                break;
            case MotionEvent.ACTION_UP:
                if (downX - curX > STEP && Math.abs(curY - downY) < STEP) {
                    Log.d(TAG, "dispatchTouchEvent: 下一个");
                    if (listener != null) {
                        listener.onNext();
                    }
                } else if (curX - downX > STEP && Math.abs(curY - downY) < STEP) {
                    Log.d(TAG, "dispatchTouchEvent: 上一个");
                    if (listener != null) {
                        listener.onPre();
                    }
                }

                break;

        }
        return super.dispatchTouchEvent(event);
    }

    private float downX;
    private float downY;
    private OnSwitchListener listener;

    public void setOnSwitchListener(OnSwitchListener listener) {
        this.listener = listener;
    }

    public interface OnSwitchListener {
        public void onPre();
        public void onNext();
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
        if (onStartLoadingListener != null) {
            onStartLoadingListener.onStartLoading(url);
        }
    }

    private OnStartLoadingListener onStartLoadingListener;

    public void setOnStartLoadingListener(OnStartLoadingListener listener) {
        onStartLoadingListener = listener;
    }

    public interface OnScrollChangedListener {
        default public void onScrollChanged(int l, int t, int oldl, int oldt){}
        default public void onScrollStateChanged(boolean isIdle){}
    }
    private OnScrollChangedListener onScrollChangedListener;

    public void seOnScrollChangedListener(OnScrollChangedListener listener) {
        onScrollChangedListener = listener;
    }

    private Handler idleHandler = new Handler(Looper.myLooper());
    private Runnable runnable = () -> {
        if (onScrollChangedListener != null) {
            onScrollChangedListener.onScrollStateChanged(true);
        }
    };

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        onScrollChangedListener.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangedListener != null) {
            onScrollChangedListener.onScrollStateChanged(false);
        }
        idleHandler.removeCallbacks(runnable);
        idleHandler.postDelayed(runnable, 500);
    }


}

package com.lulu.hotsearch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.webkit.WebView;

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
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = curX;
                break;
            case MotionEvent.ACTION_UP:
                if (downX - curX > STEP) {
                    Log.d(TAG, "dispatchTouchEvent: 下一个");
                    if (listener != null) {
                        listener.onNext();
                    }
                } else if (curX - downX > STEP) {
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
    private OnSwitchListener listener;

    public void setOnSwitchListener(OnSwitchListener listener) {
        this.listener = listener;
    }

    public interface OnSwitchListener {
        public void onPre();
        public void onNext();
    }
}

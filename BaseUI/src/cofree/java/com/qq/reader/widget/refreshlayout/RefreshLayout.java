package com.qq.reader.widget.refreshlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import androidx.core.view.MotionEventCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.qq.reader.baseui.R;
import com.qq.reader.core.config.AppConstant;
import com.qq.reader.core.utils.WeakReferenceHandler;
import com.qq.reader.view.refresh.OnRefreshListener;
import com.tencent.mars.xlog.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * 刷新控件，核心类
 *
 * @author p_bbinliu
 * @date 2019-07-04
 * base on Aspsine
 * github: https://github.com/Aspsine/SwipeToLoadLayout
 */
public class RefreshLayout extends ViewGroup implements Handler.Callback {

    private static final String TAG = RefreshLayout.class.getSimpleName();

    private static final int DEFAULT_SWIPING_TO_REFRESH_TO_DEFAULT_SCROLLING_DURATION = 200;

    private static final int DEFAULT_RELEASE_TO_REFRESHING_SCROLLING_DURATION = 200;

    private static final int DEFAULT_RELEASE_TO_TWO_LEVEL_SCROLLING_DURATION = 500;

    private static final int DEFAULT_REFRESH_COMPLETE_DELAY_DURATION = 300;

    private static final int DEFAULT_REFRESH_COMPLETE_TO_DEFAULT_SCROLLING_DURATION = 500;

    private static final int DEFAULT_DEFAULT_TO_REFRESHING_SCROLLING_DURATION = 500;

    /**
     * how hard to drag
     */
    private static final float DEFAULT_DRAG_RATIO = 0.5f;

    private static final int INVALID_POINTER = -1;

    private static final int INVALID_COORDINATE = -1;

    private AutoScroller mAutoScroller;

    private OnRefreshListener mRefreshListener;

    private View mHeaderView;

    private View mHeaderContainerView;

    private View mTargetView;

    private BaseRefreshHeader mRefreshHeader;

    private int mHeaderHeight;

    private boolean mHasHeaderView;

    /**
     * indicate whether in debug mode
     */
    private boolean mDebug = false;

    private float mDragRatio = DEFAULT_DRAG_RATIO;

    /**
     * the threshold of the touch event
     */
    private final int mTouchSlop;

    /**
     * status of SwipeToLoadLayout
     */
    private int mStatus = STATUS.STATUS_DEFAULT;

    private int mOldState = STATUS.STATUS_DEFAULT;

    /**
     * target view top offset
     */
    private int mHeaderOffset;

    /**
     * target offset
     */
    private int mTargetOffset;

    /**
     * init touch action down point.y
     */
    private float mInitDownY;

    /**
     * init touch action down point.x
     */
    private float mInitDownX;

    /**
     * last touch point.y
     */
    private float mLastY;

    /**
     * last touch point.x
     */
    private float mLastX;

    /**
     * action touch pointer's id
     */
    private int mActivePointerId;

    /**
     * <b>ATTRIBUTE:</b>
     * a switcher indicate whither refresh function is enabled
     */
    private boolean mRefreshEnabled = true;

    /**
     * 刷新时是否可以纵向滑动滑动
     * 默认禁止滑动
     */
    private boolean mScrollVerticalEnabledWhenRefreshing = false;


    private boolean mTwoLevelEnabled = false;

    /**
     * <b>ATTRIBUTE:</b>
     * the style default classic
     */
    private int mStyle = STYLE.CLASSIC;

    /**
     * <b>ATTRIBUTE:</b>
     * offset to trigger refresh
     */
    private float mRefreshTriggerOffset;


    /**
     * 二级刷新触发距离
     * 默认为刷新触发距离两倍
     */
    private float mTwoLevelTriggerOffset;

    /**
     * 二级刷新预触发距离
     * 默认为二级刷新触发距离 3/4
     */
    private float mPreTwoLevelTriggerOffset;

    /**
     * <b>ATTRIBUTE:</b>
     * the max value of top offset
     */
    private float mRefreshFinalDragOffset;

    /**
     * <b>ATTRIBUTE:</b>
     * Scrolling duration swiping to refresh -> default
     */
    private int mSwipingToRefreshToDefaultScrollingDuration = DEFAULT_SWIPING_TO_REFRESH_TO_DEFAULT_SCROLLING_DURATION;

    /**
     * <b>ATTRIBUTE:</b>
     * Scrolling duration status release to refresh -> refreshing
     */
    private int mReleaseToRefreshToRefreshingScrollingDuration = DEFAULT_RELEASE_TO_REFRESHING_SCROLLING_DURATION;

    private int mReleaseToTwoLevelScrollingDuration = DEFAULT_RELEASE_TO_TWO_LEVEL_SCROLLING_DURATION;

    /**
     * <b>ATTRIBUTE:</b>
     * Refresh complete delay duration
     */
    private int mRefreshCompleteDelayDuration = DEFAULT_REFRESH_COMPLETE_DELAY_DURATION;

    /**
     * <b>ATTRIBUTE:</b>
     * Scrolling duration status refresh complete -> default
     * {@link #setRefreshing(boolean)} false
     */
    private int mRefreshCompleteToDefaultScrollingDuration = DEFAULT_REFRESH_COMPLETE_TO_DEFAULT_SCROLLING_DURATION;

    /**
     * <b>ATTRIBUTE:</b>
     * Scrolling duration status default -> refreshing, mainly for auto refresh
     * {@link #setRefreshing(boolean)} true
     */
    private int mDefaultToRefreshingScrollingDuration = DEFAULT_DEFAULT_TO_REFRESHING_SCROLLING_DURATION;


    private String pullRefreshTimeSaveKey = "pullRefreshTimeSaveKey";

    boolean isSetLastPullRefreshTime;

    public static final int MSG_TYPE_REFRESH_TIME_OUT = 23333;
    public static final int MSG_TYPE_TWO_LEVEL_TIME_OUT = 32333;
    public static final int REFRESH_TIME_OUT = 10000;
    public static final int TWO_LEVEL_TIME_OUT = 3000;
    private WeakReferenceHandler mWeakReferenceHandler;

    private int mStatusBarHeight = 0;

    //维护一个全局的页面刷新SparseArray，用于存储每个页面的刷新时间
    //不再用sp 来存储
    public static HashMap<String,Long> mRefreshTimeMap = new HashMap<>();

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_TYPE_REFRESH_TIME_OUT:
                Log.d(TAG, "handleMessage -> MSG_TYPE_REFRESH_TIME_OUT");
                if (isRefreshing()) {
                    setRefreshing(false);
                }
                break;
            case MSG_TYPE_TWO_LEVEL_TIME_OUT:
                Log.d(TAG, "handleMessage -> MSG_TYPE_TWO_LEVEL_TIME_OUT");
                setRefreshing(false);
                break;
            default:
                break;
        }
        return false;
    }


    /**
     * the style enum
     */
    public static final class STYLE {
        public static final int CLASSIC = 0;
        public static final int ABOVE = 1;
        public static final int BLEW = 2;
        public static final int SCALE = 3;
    }

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RefreshLayout, defStyleAttr, 0);
        try {
            final int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.RefreshLayout_refresh_enabled) {
                    setRefreshEnabled(a.getBoolean(attr, true));

                } else if (attr == R.styleable.RefreshLayout_swipe_style) {
                    setSwipeStyle(a.getInt(attr, STYLE.CLASSIC));

                } else if (attr == R.styleable.RefreshLayout_drag_ratio) {
                    setDragRatio(a.getFloat(attr, DEFAULT_DRAG_RATIO));

                } else if (attr == R.styleable.RefreshLayout_refresh_final_drag_offset) {
                    setRefreshFinalDragOffset(a.getDimensionPixelOffset(attr, 0));

                } else if (attr == R.styleable.RefreshLayout_refresh_trigger_offset) {
                    setRefreshTriggerOffset(a.getDimensionPixelOffset(attr, 0));

                } else if (attr == R.styleable.RefreshLayout_swiping_to_refresh_to_default_scrolling_duration) {
                    setSwipingToRefreshToDefaultScrollingDuration(a.getInt(attr, DEFAULT_SWIPING_TO_REFRESH_TO_DEFAULT_SCROLLING_DURATION));

                } else if (attr == R.styleable.RefreshLayout_release_to_refreshing_scrolling_duration) {
                    setReleaseToRefreshingScrollingDuration(a.getInt(attr, DEFAULT_RELEASE_TO_REFRESHING_SCROLLING_DURATION));

                } else if (attr == R.styleable.RefreshLayout_refresh_complete_delay_duration) {
                    setRefreshCompleteDelayDuration(a.getInt(attr, DEFAULT_REFRESH_COMPLETE_DELAY_DURATION));

                } else if (attr == R.styleable.RefreshLayout_refresh_complete_to_default_scrolling_duration) {
                    setRefreshCompleteToDefaultScrollingDuration(a.getInt(attr, DEFAULT_REFRESH_COMPLETE_TO_DEFAULT_SCROLLING_DURATION));

                } else if (attr == R.styleable.RefreshLayout_default_to_refreshing_scrolling_duration) {
                    setDefaultToRefreshingScrollingDuration(a.getInt(attr, DEFAULT_DEFAULT_TO_REFRESHING_SCROLLING_DURATION));
                }
            }

            //未开启是使用默认header
            //header位置默认在最顶部
            if (!a.getBoolean(R.styleable.RefreshLayout_custom_header_enable, false)) {
                mRefreshHeader = new RefreshHeader(getContext());
                mHeaderView = mRefreshHeader.getView();
                mHeaderContainerView = mRefreshHeader;
                addView(mHeaderContainerView);
            }
        } finally {
            a.recycle();
        }



        mWeakReferenceHandler = new WeakReferenceHandler(this);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mAutoScroller = new AutoScroller();
    }


    /**
     * 外部调用，可以任意控制header位置
     * 只需要把header传进来即可
     * @param refreshHeader
     */
    public void setRefreshHeader(RefreshHeader refreshHeader) {
        if (refreshHeader == null) {
            return;
        }

        mRefreshHeader = refreshHeader;
        if (mRefreshHeader.getParent() == null) {
            removeView(mHeaderContainerView);
            mHeaderContainerView = mRefreshHeader;
            addView(mHeaderContainerView);
        }

        mHeaderView = mRefreshHeader.getView();
    }

    public int getHeaderHeight() {
        return mHeaderHeight;
    }


    /**
     * 设置沉浸式刷新
     * 如果是沉浸式，会留出状态栏的高度
     * @param isImmerseMode
     */
    public void setImmerseMode(boolean isImmerseMode) {
        if (isImmerseMode) {
            mStatusBarHeight = AppConstant.statusBarHeight;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTargetView != null) {
            mTargetView.bringToFront();
        }

        if (mHeaderView != null) {
            mHeaderView.bringToFront();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final int childNum = getChildCount();
        if (childNum == 0) {
            // no child return
            return;
        } else {
            mTargetView = findViewById(R.id.refresh_target_view);
        }

        if (mTargetView == null) {
            return;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // header
        if (mHeaderContainerView != null) {
            measureChildWithMargins(mHeaderContainerView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
        // target
        if (mTargetView != null) {
            final View targetView = mTargetView;
            MarginLayoutParams lp = (MarginLayoutParams) mTargetView.getLayoutParams();
            Log.d(TAG, "mTargetView lp.topMargin=" + lp.topMargin);
            measureChildWithMargins(targetView, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }

        if (mHeaderView instanceof ViewGroup && ((ViewGroup) mHeaderView).getChildCount() > 0) {
            mHeaderHeight = ((ViewGroup) mHeaderView).getChildAt(0).getMeasuredHeight();
            if (mRefreshTriggerOffset < mHeaderHeight) {
                mRefreshTriggerOffset = mHeaderHeight + mStatusBarHeight;
                mTwoLevelTriggerOffset = mRefreshTriggerOffset * 1.5f;
                mPreTwoLevelTriggerOffset = mTwoLevelTriggerOffset * 3 / 4;
            }

            Log.d(TAG, "mRefreshTriggerOffset" + mRefreshTriggerOffset
                    + "\n mTwoLevelTriggerOffset=" + mTwoLevelTriggerOffset
                    + "\n mPreTwoLevelTriggerOffset=" + mPreTwoLevelTriggerOffset);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildren();

        mHasHeaderView = (mHeaderView != null);
    }

    void updateHeight() {
        if (mHeaderView != null && mHeaderView.getLayoutParams() != null) {
            ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) mHeaderView.getLayoutParams();
            lp.height = mHeaderOffset;
            Log.d(TAG, "mHeaderView lp.height=" + lp.height);
            mHeaderView.setLayoutParams(lp);
            //回调距离顶部位置
            mRefreshHeader.onUpdateScroll(mHeaderOffset);
            if (mRefreshStateListenerList != null) {
                for (IRefreshStateListener iRefreshStateListener : mRefreshStateListenerList) {
                    iRefreshStateListener.onUpdateScroll(mHeaderOffset);
                }
            }
        }
    }

    /**
     * TODO add gravity
     * LayoutParams of RefreshLoadMoreLayout
     */
    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new RefreshLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new RefreshLayout.LayoutParams(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new RefreshLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                // swipeToRefresh -> finger up -> finger down if the status is still swipeToRefresh
                // in onInterceptTouchEvent ACTION_DOWN event will stop the scroller
                // if the event pass to the child view while ACTION_MOVE(condition is false)
                // in onInterceptTouchEvent ACTION_MOVE the ACTION_UP or ACTION_CANCEL will not be
                // passed to onInterceptTouchEvent and onTouchEvent. Instead It will be passed to
                // child view's onTouchEvent. So we must deal this situation in dispatchTouchEvent
                onActivePointerUp();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isSetLastPullRefreshTime = false;
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                mInitDownY = mLastY = getMotionEventY(event, mActivePointerId);
                mInitDownX = mLastX = getMotionEventX(event, mActivePointerId);

                // if it isn't an ing status or default status
                if (STATUS.isSwipingToRefresh(mStatus)
                        || STATUS.isReleaseToRefresh(mStatus)
                        || STATUS.isReadyToRefresh(mStatus)) {
                    // abort autoScrolling, not trigger the method #autoScrollFinished()
                    mAutoScroller.abortIfRunning();
                    if (mDebug) {
                        Log.i(TAG, "Another finger down, abort auto scrolling, let the new finger handle");
                    }
                }

                if (STATUS.isSwipingToRefresh(mStatus)
                        || STATUS.isReleaseToRefresh(mStatus)
                        || STATUS.isReadyToRefresh(mStatus)) {
                    return true;
                }

                // let children view handle the ACTION_DOWN;

                // 1. children consumed:
                // if at least one of children onTouchEvent() ACTION_DOWN return true.
                // ACTION_DOWN event will not return to SwipeToLoadLayout#onTouchEvent().
                // but the others action can be handled by SwipeToLoadLayout#onInterceptTouchEvent()

                // 2. children not consumed:
                // if children onTouchEvent() ACTION_DOWN return false.
                // ACTION_DOWN event will return to SwipeToLoadLayout's onTouchEvent().
                // SwipeToLoadLayout#onTouchEvent() ACTION_DOWN return true to consume the ACTION_DOWN event.

                // anyway: handle action down in onInterceptTouchEvent() to init is an good option
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) {
                    return false;
                }
                float y = getMotionEventY(event, mActivePointerId);
                float x = getMotionEventX(event, mActivePointerId);
                final float yInitDiff = y - mInitDownY;
                final float xInitDiff = x - mInitDownX;
                mLastY = y;
                mLastX = x;
                boolean moved = Math.abs(yInitDiff) > Math.abs(xInitDiff)
                        && Math.abs(yInitDiff) > mTouchSlop;
                boolean triggerCondition =
                        // refresh trigger condition
                        (yInitDiff > 0 && moved && onCheckCanRefresh());

                if (ifCannotScrollVerticalWhenRefreshing()) {
                    return true;
                }

                if (triggerCondition) {
                    // if the refresh's or load more's trigger condition  is true,
                    // intercept the move action event and pass it to SwipeToLoadLayout#onTouchEvent()
                    return true;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP: {
                onSecondaryPointerUp(event);
                mInitDownY = mLastY = getMotionEventY(event, mActivePointerId);
                mInitDownX = mLastX = getMotionEventX(event, mActivePointerId);
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mActivePointerId = INVALID_POINTER;
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                return true;

            case MotionEvent.ACTION_MOVE:
                // take over the ACTION_MOVE event from SwipeToLoadLayout#onInterceptTouchEvent()
                // if condition is true
                final float y = getMotionEventY(event, mActivePointerId);
                final float x = getMotionEventX(event, mActivePointerId);

                final float yDiff = y - mLastY;
                final float xDiff = x - mLastX;
                mLastY = y;
                mLastX = x;

                if (Math.abs(xDiff) > Math.abs(yDiff) && Math.abs(xDiff) > mTouchSlop) {
                    return false;
                }

                //直接消费事件
                if (ifCannotScrollVerticalWhenRefreshing()) {
                    return true;
                }

                if (STATUS.isStatusDefault(mStatus)) {
                    if (yDiff > 0 && onCheckCanRefresh()) {
//                        if (mRefreshHeader != null) {
//                            mRefreshHeader.setLastRefreshTime(calculateLastPullRefreshTime());
//                        }
                        setStatus(STATUS.STATUS_REFRESH_READY);
                    }
                } else if (STATUS.isRefreshStatus(mStatus)) {
                    if (mTargetOffset <= 0) {
                        setStatus(STATUS.STATUS_DEFAULT);
                        fixCurrentStatusLayout();
                        return false;
                    }
                }

                if (STATUS.isSwipingToRefresh(mStatus)
                        || STATUS.isReleaseToRefresh(mStatus)
                        || STATUS.isReadyToRefresh(mStatus)
                        || STATUS.isReleaseToTwoLevel(mStatus)
                        || STATUS.isPreToTwoLevelStatus(mStatus)) {

                    if (STATUS.isReadyToRefresh(mStatus)) {
                        if (mTargetOffset >= mTwoLevelTriggerOffset && mTwoLevelEnabled) {
                            setStatus(STATUS.STATUS_RELEASE_TO_TWO_LEVEL);
                        } else if (mTargetOffset >= mPreTwoLevelTriggerOffset && mTwoLevelEnabled) {
                            setStatus(STATUS.STATUS_PRE_TO_TWO_LEVEL);
                        } else if (mTargetOffset >= mRefreshTriggerOffset) {
                            setStatus(STATUS.STATUS_RELEASE_TO_REFRESH);
                        }
                    } else {
                        if (mTargetOffset >= mTwoLevelTriggerOffset && mTwoLevelEnabled) {
                            setStatus(STATUS.STATUS_RELEASE_TO_TWO_LEVEL);
                        } else if (mTargetOffset >= mPreTwoLevelTriggerOffset && mTwoLevelEnabled) {
                            setStatus(STATUS.STATUS_PRE_TO_TWO_LEVEL);
                        } else if (mTargetOffset >= mRefreshTriggerOffset) {
                            setStatus(STATUS.STATUS_RELEASE_TO_REFRESH);
                        } else {
                            setStatus(STATUS.STATUS_SWIPING_TO_REFRESH);
                        }
                    }

                    fingerScroll(yDiff);
                }
                return true;

            case MotionEvent.ACTION_POINTER_DOWN: {
                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);
                if (pointerId != INVALID_POINTER) {
                    mActivePointerId = pointerId;
                }
                mInitDownY = mLastY = getMotionEventY(event, mActivePointerId);
                mInitDownX = mLastX = getMotionEventX(event, mActivePointerId);
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                onSecondaryPointerUp(event);
                mInitDownY = mLastY = getMotionEventY(event, mActivePointerId);
                mInitDownX = mLastX = getMotionEventX(event, mActivePointerId);
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mActivePointerId == INVALID_POINTER) {
                    return false;
                }
                mActivePointerId = INVALID_POINTER;
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * set debug mode(default value false)
     *
     * @param debug if true log on, false log off
     */
    public void setDebug(boolean debug) {
        this.mDebug = debug;
    }

    /**
     * is refresh function is enabled
     *
     * @return
     */
    public boolean isRefreshEnabled() {
        return mRefreshEnabled;
    }

    /**
     * switch refresh function on or off
     *
     * @param enable
     */
    public void setRefreshEnabled(boolean enable) {
        this.mRefreshEnabled = enable;
    }

    /**
     * 刷新是是否可以纵向滑动
     *
     * @param enable
     */
    public void setScrollVerticalEnableWhenRefrehing(boolean enable) {
        this.mScrollVerticalEnabledWhenRefreshing = enable;
    }


    /**
     * 二级刷新开关
     * @param enable
     */
    public void setTwoLevelEnabled(boolean enable) {
        this.mTwoLevelEnabled = enable;
    }

    /**
     * is current status refreshing
     *
     * @return
     */
    public boolean isRefreshing() {
        return STATUS.isRefreshing(mStatus);
    }


    /**
     * set the style of the refresh header
     *
     * @param style
     */
    public void setSwipeStyle(int style) {
        this.mStyle = style;
        requestLayout();
    }

    /**
     * set how hard to drag. bigger easier, smaller harder;
     *
     * @param dragRatio default value is {@link #DEFAULT_DRAG_RATIO}
     */
    public void setDragRatio(float dragRatio) {
        this.mDragRatio = dragRatio;
    }

    /**
     * set the value of {@link #mRefreshTriggerOffset}.
     * Default value is the refresh header view height {@link #mHeaderHeight}<p/>
     * If the offset you set is smaller than {@link #mHeaderHeight} or not set,
     * using {@link #mHeaderHeight} as default value
     *
     * @param offset
     */
    public void setRefreshTriggerOffset(int offset) {
        mRefreshTriggerOffset = offset;
    }

    /**
     * 设置二级刷新触发距离
     * 需要大于刷新触发距离
     * 默认为刷新触发距离两倍
     * @param offset
     */
    public void setTwoLevelTriggerOffset(int offset) {
        mTwoLevelTriggerOffset = mRefreshTriggerOffset + offset;
    }

    /**
     * 设置二级刷新预触发距离
     * 默认为二级刷新触发距离 3/4
     * @param offset
     */
    public void setPreTwoLevelTriggerOffset(int offset) {
        mPreTwoLevelTriggerOffset = mRefreshTriggerOffset + offset >= mTwoLevelTriggerOffset ?
                mRefreshTriggerOffset + (offset >> 1) : mRefreshTriggerOffset + offset;
    }

    /**
     * Set the final offset you can swipe to refresh.<br/>
     * If the offset you set is 0(default value) or smaller than {@link #mRefreshTriggerOffset}
     * there no final offset
     *
     * @param offset
     */
    public void setRefreshFinalDragOffset(int offset) {
        mRefreshFinalDragOffset = offset;
    }


    /**
     * set {@link #mSwipingToRefreshToDefaultScrollingDuration} in milliseconds
     *
     * @param duration
     */
    public void setSwipingToRefreshToDefaultScrollingDuration(int duration) {
        this.mSwipingToRefreshToDefaultScrollingDuration = duration;
    }

    /**
     * set {@link #mReleaseToRefreshToRefreshingScrollingDuration} in milliseconds
     *
     * @param duration
     */
    public void setReleaseToRefreshingScrollingDuration(int duration) {
        this.mReleaseToRefreshToRefreshingScrollingDuration = duration;
    }

    /**
     * set {@link #mRefreshCompleteDelayDuration} in milliseconds
     *
     * @param duration
     */
    public void setRefreshCompleteDelayDuration(int duration) {
        this.mRefreshCompleteDelayDuration = duration;
    }

    /**
     * set {@link #mRefreshCompleteToDefaultScrollingDuration} in milliseconds
     *
     * @param duration
     */
    public void setRefreshCompleteToDefaultScrollingDuration(int duration) {
        this.mRefreshCompleteToDefaultScrollingDuration = duration;
    }

    /**
     * set {@link #mDefaultToRefreshingScrollingDuration} in milliseconds
     *
     * @param duration
     */
    public void setDefaultToRefreshingScrollingDuration(int duration) {
        this.mDefaultToRefreshingScrollingDuration = duration;
    }


    /**
     * set an {@link OnRefreshListener} to listening refresh event
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mRefreshListener = listener;
    }


    /**
     * auto refresh or cancel
     *
     * @param refreshing
     */
    public void setRefreshing(boolean refreshing) {
        if (!isRefreshEnabled() || mHeaderView == null) {
            return;
        }
        if (refreshing) {
            if (STATUS.isStatusDefault(mStatus)) {
//                if (mRefreshHeader != null) {
//                    mRefreshHeader.setLastRefreshTime(calculateLastPullRefreshTime());
//                }
                setStatus(STATUS.STATUS_MANUAL_REFRESHING);
                scrollDefaultToRefreshing();
            }
        } else {
            if (STATUS.isRefreshing(mStatus)) {
                postDelayed(this::scrollRefreshingToDefault, mRefreshCompleteDelayDuration);
            } else if (STATUS.isOnTwoLevelStatus(mStatus)) {
                postDelayed(this::scrollTwoLevelToDefault, mRefreshCompleteDelayDuration);
            }
        }
    }

    /**
     * 200.300 已废弃
     */
    @Deprecated
    public void setNowRefreshTime() {
        long nowTime = System.currentTimeMillis();
        mRefreshTimeMap.put(pullRefreshTimeSaveKey, nowTime);
    }

    /**
     * 200.300 已废弃
     */
    @Deprecated
    public void setPullRefreshTimeSaveKey(String key) {
        pullRefreshTimeSaveKey = key;
        setNowRefreshTime();
    }

    public String calculateLastPullRefreshTime() {
        Log.d(TAG, "refreshTime -> pullRefreshTimeSaveKey:" + pullRefreshTimeSaveKey);
        long lastTime = 0;
        if (mRefreshTimeMap != null && mRefreshTimeMap.containsKey(pullRefreshTimeSaveKey)) {
            lastTime = mRefreshTimeMap.get(pullRefreshTimeSaveKey);
        }

        if (lastTime == 0)
            return "";

        long nowTime = System.currentTimeMillis();
        long diffTime = nowTime - lastTime;
        int seconds = (int) (diffTime / 1000);
        if (seconds < 60) {
            return getContext().getResources().getString(R.string.utility_seconds_ago);
        }
        int minutes = (seconds / 60);
        if (minutes < 60) {
            return minutes + getContext().getResources().getString(R.string.utility_minutes_ago);
        }

        Calendar lastCalendar = Calendar.getInstance();
        lastCalendar.setTimeInMillis(lastTime);
        int last_year = lastCalendar.get(Calendar.YEAR);
        int last_month = lastCalendar.get(Calendar.MONTH) + 1;
        int last_date = lastCalendar.get(Calendar.DATE);
        int last_hour = lastCalendar.get(Calendar.HOUR_OF_DAY);
        int last_minute = lastCalendar.get(Calendar.MINUTE);

        Calendar nowCalendar = Calendar.getInstance();
        int now_year = nowCalendar.get(Calendar.YEAR);

        if (last_year == now_year) {
            return last_month + "/" + last_date + " " +
                    (last_hour < 10 ? ("0" + last_hour) : last_hour) + ":" + (last_minute < 10 ?
                    ("0" + last_minute) : last_minute);
        }

        return last_year + "/" + last_month + "/" + last_date + " " +
                (last_hour < 10 ? ("0" + last_hour) : last_hour) + ":" + (last_minute < 10 ? ("0"
                + last_minute) : last_minute);
    }


    /**
     * copy from {@link SwipeRefreshLayout#canChildScrollUp()}
     *
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     */
    protected boolean canChildScrollUp() {
        if (mTargetView != null) {
            return mTargetView.canScrollVertically(-1);
        } else {
            return false;
        }
    }

    /**
     * Whether it is possible for the child view of this layout to
     * scroll down. Override this if the child view is a custom view.
     *
     * @return
     */
    protected boolean canChildScrollDown() {
        if (mTargetView != null) {
            return mTargetView.canScrollVertically(-1);
        } else {
            return false;
        }
    }

    /**
     * @see #onLayout(boolean, int, int, int, int)
     */
    private void layoutChildren() {
        Log.d(TAG, "layoutChildren mHeaderHeight=" + mHeaderHeight);
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();

        final int paddingLeft = getPaddingLeft();
        final int paddingTop = getPaddingTop();
        final int paddingRight = getPaddingRight();
        final int paddingBottom = getPaddingBottom();

        if (mTargetView == null) {
            return;
        }

        // layout header
        if (mHeaderContainerView != null) {
            final View headerContainerView = mHeaderContainerView;
            MarginLayoutParams lp = (MarginLayoutParams) headerContainerView.getLayoutParams();
            final int headerLeft = paddingLeft + lp.leftMargin;
            final int headerTop = paddingTop + lp.topMargin;
            final int headerRight = headerLeft + headerContainerView.getMeasuredWidth();
            final int headerBottom = headerTop + headerContainerView.getMeasuredHeight();
            headerContainerView.layout(headerLeft, headerTop, headerRight, headerBottom);
        }


        // layout target
        if (mTargetView != null) {
            final View targetView = mTargetView;
            MarginLayoutParams lp = (MarginLayoutParams) targetView.getLayoutParams();
            final int targetLeft = paddingLeft + lp.leftMargin;

            int targetTop = paddingTop + lp.topMargin;
            if (mHeaderContainerView != null) {
                targetTop += mTargetOffset;
            }
            final int targetRight = targetLeft + targetView.getMeasuredWidth();
            final int targetBottom = targetTop + targetView.getMeasuredHeight();
            targetView.layout(targetLeft, targetTop, targetRight, targetBottom);
        }
    }

    private void fixCurrentStatusLayout() {
        if (STATUS.isRefreshing(mStatus)) {
            mTargetOffset = (int) (mRefreshTriggerOffset + 0.5f);
            mHeaderOffset = mTargetOffset;
        } else if (STATUS.isStatusDefault(mStatus)) {
            mTargetOffset = 0;
            mHeaderOffset = 0;
        }
        updateHeight();
    }

    public int getTargetOffset() {
        return mTargetOffset;
    }

    public int getHeaderOffset() {
        return mHeaderOffset;
    }

    /**
     * scrolling by physical touch with your fingers
     *
     * @param yDiff
     */
    private void fingerScroll(final float yDiff) {
        float ratio = mDragRatio;
        float yScrolled = yDiff * ratio;

        // make sure (targetOffset>0 -> targetOffset=0 -> default status)
        // or (targetOffset<0 -> targetOffset=0 -> default status)
        // forbidden fling (targetOffset>0 -> targetOffset=0 ->targetOffset<0 -> default status)
        // or (targetOffset<0 -> targetOffset=0 ->targetOffset>0 -> default status)
        // I am so smart :)

        float tmpTargetOffset = yScrolled + mTargetOffset;
        if ((tmpTargetOffset > 0 && mTargetOffset < 0)
                || (tmpTargetOffset < 0 && mTargetOffset > 0)) {
            yScrolled = - mTargetOffset;
        }

        if (mRefreshFinalDragOffset >= mRefreshTriggerOffset && tmpTargetOffset > mRefreshFinalDragOffset) {
            yScrolled = mRefreshFinalDragOffset - mTargetOffset;
        }

        updateScroll(yScrolled);


    }

    private void autoScroll(final float yScrolled) {
        updateScroll(yScrolled);
    }

    /**
     * Process the scrolling(auto or physical) and append the diff values to mTargetOffset
     * I think it's the most busy and core method. :) a ha ha ha ha...
     *
     * @param yScrolled
     */
    private void updateScroll(final float yScrolled) {
        if (yScrolled == 0) {
            return;
        }
        mTargetOffset += yScrolled;

        if (STATUS.isRefreshStatus(mStatus) || STATUS.isOnTwoLevelStatus(mStatus)) {
            mHeaderOffset = mTargetOffset;
        }

        if (mDebug) {
            Log.i(TAG, "mTargetOffset = " + mTargetOffset);
        }
        updateHeight();
    }

    /**
     * on active finger up
     */
    private void onActivePointerUp() {
        if (STATUS.isSwipingToRefresh(mStatus) || STATUS.isReadyToRefresh(mStatus)) {
            // simply return
            scrollSwipingToRefreshToDefault();
        } else if (STATUS.isReleaseToRefresh(mStatus) || STATUS.isPreToTwoLevelStatus(mStatus)) {
            // return to header height and perform refresh
            scrollReleaseToRefreshToRefreshing();
        } else if (STATUS.isReleaseToTwoLevel(mStatus)) {
            autoScrollFinished();
        }
    }

    /**
     * on not active finger up
     *
     * @param ev
     */
    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    private void scrollDefaultToRefreshing() {
        mAutoScroller.autoScroll((int) (mRefreshTriggerOffset + 0.5f), mDefaultToRefreshingScrollingDuration);
    }

    private void scrollRefreshingToDefault() {
        mAutoScroller.autoScroll(-mHeaderOffset, mRefreshCompleteToDefaultScrollingDuration);
    }

    private void scrollSwipingToRefreshToDefault() {
        mAutoScroller.autoScroll(-mHeaderOffset, mSwipingToRefreshToDefaultScrollingDuration);
    }


    private void scrollReleaseToRefreshToRefreshing() {
        mAutoScroller.autoScroll((int) (mRefreshTriggerOffset - mHeaderOffset), mReleaseToRefreshToRefreshingScrollingDuration);
    }

    private void scrollTwoLevelToDefault() {
        mAutoScroller.autoScroll(-mHeaderOffset, mReleaseToTwoLevelScrollingDuration);
    }


    /**
     * invoke when {@link AutoScroller#finish()} is automatic
     */
    private void autoScrollFinished() {
        int mLastStatus = mStatus;

        if (STATUS.isReleaseToRefresh(mStatus)
                || STATUS.isManualRefresh(mStatus)
                || STATUS.isReleaseToTwoLevel(mStatus)
                || STATUS.isPreToTwoLevelStatus(mStatus)) {
            if (STATUS.isReleaseToTwoLevel(mStatus)) {
                if (mRefreshListener != null) {
                    setStatus(STATUS.STATUS_ON_TWO_LEVEL);
                    mRefreshListener.onTwoLevel();

                    //3s之后自动回去
                    if (mWeakReferenceHandler != null) {
                        mWeakReferenceHandler.sendEmptyMessageDelayed(MSG_TYPE_TWO_LEVEL_TIME_OUT, TWO_LEVEL_TIME_OUT);
                    }
                }
            } else {
                setStatus(STATUS.STATUS_REFRESHING);
                fixCurrentStatusLayout();
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                }
                //10s之后强制调用刷新结束方法
                if (mWeakReferenceHandler != null) {
                    mWeakReferenceHandler.sendEmptyMessageDelayed(MSG_TYPE_REFRESH_TIME_OUT, REFRESH_TIME_OUT);
                }
            }

            setNowRefreshTime();
        } else if (STATUS.isRefreshing(mStatus) || STATUS.isOnTwoLevelStatus(mStatus)) {
            setStatus(STATUS.STATUS_DEFAULT);
            fixCurrentStatusLayout();

        } else if (STATUS.isSwipingToRefresh(mStatus) || STATUS.isReadyToRefresh(mStatus)) {
            setStatus(STATUS.STATUS_DEFAULT);
            fixCurrentStatusLayout();

        } else if (STATUS.isStatusDefault(mStatus)) {

        } else {
            throw new IllegalStateException("illegal state: " + STATUS.getStatus(mStatus));
        }

        if (mDebug) {
            Log.i(TAG, STATUS.getStatus(mLastStatus) + " -> " + STATUS.getStatus(mStatus));
        }
    }

    /**
     * check if it can refresh
     *
     * @return
     */
    private boolean onCheckCanRefresh() {
        return mRefreshEnabled && !canChildScrollUp() && mHasHeaderView && mRefreshTriggerOffset > 0;
    }

    /**
     * check if it can scroll when refreshing
     *
     * @return
     */
    private boolean ifCannotScrollVerticalWhenRefreshing() {
        return isRefreshing() && !mScrollVerticalEnabledWhenRefreshing;
    }

    private float getMotionEventY(MotionEvent event, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(event, activePointerId);
        if (index < 0) {
            return INVALID_COORDINATE;
        }
        return MotionEventCompat.getY(event, index);
    }

    private float getMotionEventX(MotionEvent event, int activePointId) {
        final int index = MotionEventCompat.findPointerIndex(event, activePointId);
        if (index < 0) {
            return INVALID_COORDINATE;
        }
        return MotionEventCompat.getX(event, index);
    }

    private class AutoScroller implements Runnable {

        private Scroller mScroller;

        private int mmLastY;

        private boolean mRunning = false;

        private boolean mAbort = false;

        public AutoScroller() {
            mScroller = new Scroller(getContext());
        }

        @Override
        public void run() {
            boolean finish = !mScroller.computeScrollOffset() || mScroller.isFinished();
            int currY = mScroller.getCurrY();
            int yDiff = currY - mmLastY;
            if (finish) {
                finish();
            } else {
                mmLastY = currY;
                RefreshLayout.this.autoScroll(yDiff);
                post(this);
            }
        }

        /**
         * remove the post callbacks and reset default values
         */
        private void finish() {
            mmLastY = 0;
            mRunning = false;
            removeCallbacks(this);
            // if abort by user, don't call
            if (!mAbort) {
                autoScrollFinished();
            }
        }

        /**
         * abort scroll if it is scrolling
         */
        public void abortIfRunning() {
            if (mRunning) {
                if (!mScroller.isFinished()) {
                    mAbort = true;
                    mScroller.forceFinished(true);
                }
                finish();
                mAbort = false;
            }
        }

        /**
         * The param yScrolled here isn't final pos of y.
         * It's just like the yScrolled param in the
         * {@link #updateScroll(float yScrolled)}
         *
         * @param yScrolled
         * @param duration
         */
        private void autoScroll(int yScrolled, int duration) {
            removeCallbacks(this);
            mmLastY = 0;
            if (!mScroller.isFinished()) {
                mScroller.forceFinished(true);
            }
            mScroller.startScroll(0, 0, 0, yScrolled, duration);
            post(this);
            mRunning = true;
        }
    }

    /**
     * Set the current status for better control
     *
     * @param status
     */
    private void setStatus(int status) {
        if (mDebug) {
            STATUS.printStatus(status);
        }
        if (mStatus != status) {
            mStatus = status;
            Log.d(TAG, "setStatus mStatus=" + mStatus);
            if (mRefreshHeader != null) {
                mRefreshHeader.setState(mStatus);
            }
            if (mRefreshStateListenerList != null) {
                for (IRefreshStateListener iRefreshStateListener : mRefreshStateListenerList) {
                    iRefreshStateListener.setState(mStatus);
                }
            }
        }
    }

    /**
     * an inner util class.
     * enum of status
     */
    public final static class STATUS {
        public static final int STATUS_TWO_LEVEL_TO_DEFAULT = -1;
        public static final int STATUS_DEFAULT = 0;
        public static final int STATUS_REFRESH_READY = 1;
        public static final int STATUS_SWIPING_TO_REFRESH = 2;
        public static final int STATUS_RELEASE_TO_REFRESH = 3;
        public static final int STATUS_REFRESHING = 4;
        public static final int STATUS_RELEASE_TO_TWO_LEVEL = 5;
        public static final int STATUS_MANUAL_REFRESHING = 6;
        public static final int STATUS_REFRESH_RETURNING = 7;
        public static final int STATUS_ON_TWO_LEVEL = 8;
        public static final int STATUS_PRE_TO_TWO_LEVEL = 9;

        private static boolean isRefreshing(final int status) {
            return status == STATUS.STATUS_REFRESHING
                    || status == STATUS_MANUAL_REFRESHING;
        }

        private static boolean isReleaseToRefresh(final int status) {
            return status == STATUS.STATUS_RELEASE_TO_REFRESH;
        }

        private static boolean isSwipingToRefresh(final int status) {
            return status == STATUS.STATUS_SWIPING_TO_REFRESH;
        }

        private static boolean isReleaseToTwoLevel(final int status) {
            return status == STATUS.STATUS_RELEASE_TO_TWO_LEVEL;
        }

        private static boolean isReadyToRefresh(final int status) {
            return status == STATUS.STATUS_REFRESH_READY;
        }

        private static boolean isManualRefresh(final int status) {
            return status == STATUS.STATUS_MANUAL_REFRESHING;
        }

        private static boolean isRefreshStatus(final int status) {
            return status > STATUS.STATUS_DEFAULT;
        }

        private static boolean isStatusDefault(final int status) {
            return status == STATUS.STATUS_DEFAULT;
        }

        private static boolean isStatusTwoLevelToDefault(final int status) {
            return status == STATUS.STATUS_TWO_LEVEL_TO_DEFAULT;
        }

        private static boolean isOnTwoLevelStatus(final int status) {
            return status == STATUS.STATUS_ON_TWO_LEVEL;
        }

        private static boolean isPreToTwoLevelStatus(final int status) {
            return status == STATUS.STATUS_PRE_TO_TWO_LEVEL;
        }



        private static String getStatus(int status) {
            final String statusInfo;
            switch (status) {
                case STATUS_REFRESH_RETURNING:
                    statusInfo = "status_refresh_returning";
                    break;
                case STATUS_REFRESHING:
                    statusInfo = "status_refreshing";
                    break;
                case STATUS_RELEASE_TO_REFRESH:
                    statusInfo = "status_release_to_refresh";
                    break;
                case STATUS_RELEASE_TO_TWO_LEVEL:
                    statusInfo = "status_release_to_two_level";
                    break;
                case STATUS_SWIPING_TO_REFRESH:
                    statusInfo = "status_swiping_to_refresh";
                    break;
                case STATUS_DEFAULT:
                    statusInfo = "status_default";
                    break;
                case STATUS_TWO_LEVEL_TO_DEFAULT:
                    statusInfo = "status_two_level_to_default";
                    break;
                case STATUS_ON_TWO_LEVEL:
                    statusInfo = "status_on_two_level";
                    break;
                case STATUS_PRE_TO_TWO_LEVEL:
                    statusInfo = "status_pre_to_two_level";
                    break;
                case STATUS_REFRESH_READY:
                    statusInfo = "status_refresh_ready";
                    break;
                case STATUS_MANUAL_REFRESHING:
                    statusInfo = "status_manual_refreshing";
                    break;
                default:
                    statusInfo = "status_illegal!";
                    break;
            }
            return statusInfo;
        }

        private static void printStatus(int status) {
            Log.i(TAG, "printStatus:" + getStatus(status));
        }
    }

    public BaseRefreshHeader getRefreshHeader() {
        return mRefreshHeader;
    }

    @Override
    public void computeScroll() {
        if (mComputeScrollListenerList != null) {
            for (ComputeScrollListener computeScrollListener : mComputeScrollListenerList) {
                computeScrollListener.onComputeScroll();
            }
        }
        super.computeScroll();
    }

    private List<ComputeScrollListener> mComputeScrollListenerList = new ArrayList<>();

    /**
     * RefreshView 滚动监听 <br/>
     * [注] : 千万不要在这个里面做耗时操作!!!!
     */
    public interface ComputeScrollListener {
        void onComputeScroll();
    }

    public void addOnComputeScrollListener(ComputeScrollListener listener) {
        mComputeScrollListenerList.add(listener);
    }

    /**
     * 状态监听回调
     */
    private List<IRefreshStateListener> mRefreshStateListenerList = new ArrayList<>();

    public void addRefreshStateListener(IRefreshStateListener listener) {
        mRefreshStateListenerList.add(listener);
    }
    public void removeRefreshStateListener(IRefreshStateListener listener) {
        mRefreshStateListenerList.remove(listener);
    }
}

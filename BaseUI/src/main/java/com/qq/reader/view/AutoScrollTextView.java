package com.qq.reader.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.qq.reader.baseui.R;
import com.qq.reader.common.entity.TextAdvBean;
import com.qq.reader.common.utils.ImageUtils;
import com.qq.reader.widget.ReaderTextView;
import com.tencent.mars.xlog.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubin on 2017/11/21.
 */

public class AutoScrollTextView extends ViewSwitcher implements ViewSwitcher.ViewFactory {

    public static final String TAG = "AutoScrollTextView";
    private static final int FLAG_START_AUTO_SCROLL = 0;
    private Context mContext;
    private Handler mHandler;
    private List<TextAdvBean> mTextAdvBeans = new ArrayList<TextAdvBean>();
    private int mCurrentIndex = -1;
    private long mStillTime;
    private ExposureEventListener mOnExposureEventListener;
    private boolean mIsEnableScroll = true;


    public AutoScrollTextView(Context context, List<TextAdvBean> textAdvBeans) {
        super(context);
        this.mContext = context;
        this.mTextAdvBeans = textAdvBeans;
    }

    public AutoScrollTextView(Context context) {
        super(context);
        this.mContext = context;
    }

    public AutoScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }


    public void setData(List<TextAdvBean> textAdvBeans) {
        this.mTextAdvBeans = textAdvBeans;
    }

    @Override
    public View makeView() {
        return LayoutInflater.from(mContext).inflate(R.layout.view_auto_scroll_text, null);
    }

    public void setOnExposureEventListener(ExposureEventListener onExposureEventListener) {
        mOnExposureEventListener = onExposureEventListener;
    }

    public void setEnableScroll(boolean enableScroll) {
        mIsEnableScroll = enableScroll;
    }

    public void setAnimation(long duration, float toYDelta) {
        setFactory(this);
        Animation in = new TranslateAnimation(0, 0, toYDelta, 0);
        in.setDuration(duration);
        in.setInterpolator(new AccelerateDecelerateInterpolator());
        Animation out = new TranslateAnimation(0, 0, 0, -toYDelta);
        out.setDuration(duration);
        out.setInterpolator(new AccelerateDecelerateInterpolator());
        if (mIsEnableScroll) {
            setInAnimation(in);
            setOutAnimation(out);
        }
    }


    /**
     * 间隔时间
     * @param time
     */
    @SuppressLint("HandlerLeak")
    public void setTextStillTime(final long time){
        mStillTime = time;
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case FLAG_START_AUTO_SCROLL:
                        setNextView();
                        break;
                }
            }
        };
    }

    private void setNextView() {
        ReaderTextView text = (ReaderTextView) getNextView().findViewById(R.id.text);
        ImageView icon = (ImageView) getNextView().findViewById(R.id.icon);
        if (mIsEnableScroll && mTextAdvBeans.size() > 1) {
            mCurrentIndex ++;
            text.setText(mTextAdvBeans.get(mCurrentIndex % mTextAdvBeans.size()).getContent());
            String imgUrl = mTextAdvBeans.get(mCurrentIndex % mTextAdvBeans.size()).getIconUrl();
            ImageUtils.loadBookShelfTextAdIcon(icon, imgUrl);
            mOnExposureEventListener.doExposureEvent();
            
            mHandler.removeMessages(FLAG_START_AUTO_SCROLL);
            mHandler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL, mStillTime);
            showNext();
        }
    }

    private void setCurrentView() {
        ReaderTextView text = (ReaderTextView) getCurrentView().findViewById(R.id.text);
        ImageView icon = (ImageView) getCurrentView().findViewById(R.id.icon);
        if (mTextAdvBeans.size() > 0) {
            mCurrentIndex ++;
            text.setText(mTextAdvBeans.get(0).getContent());
            String imgUrl = mTextAdvBeans.get(0).getIconUrl();
            ImageUtils.loadBookShelfTextAdIcon(icon, imgUrl);
            mOnExposureEventListener.doExposureEvent();
        }
    }
    
    /**
     * 外部调用，直接设置icon,text
     */
    public void setCurrentView(TextAdvBean textAdvBean) {
        stopAutoScroll();
        ReaderTextView text = (ReaderTextView) getCurrentView().findViewById(R.id.text);
        ImageView icon = (ImageView) getCurrentView().findViewById(R.id.icon);
        text.setText(textAdvBean.getContent());
        if (textAdvBean.getIconPath() > 0) {
            icon.setImageResource(textAdvBean.getIconPath());
        } else {
            ImageUtils.loadBookShelfTextAdIcon(icon, textAdvBean.getIconUrl());
        }
    }
    

    /**
     * 开始滚动
     */
    public void startAutoScroll() {
        //初始化索引
        mCurrentIndex = -1;
        setCurrentView();
        mIsEnableScroll = true;

        Log.i(AutoScrollTextView.TAG, "AutoScrollTextView -> startAutoScroll-> mHandler ->" + mHandler);
        if (mHandler != null) {
            if (mHandler.hasMessages(FLAG_START_AUTO_SCROLL)) {
                mHandler.removeMessages(FLAG_START_AUTO_SCROLL);
            }
            mHandler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL, mStillTime);
        }
    }

    /**
     * 恢复滚动
     */
    public void resumeAutoScroll() {
        if (mHandler != null) {
            if (mHandler.hasMessages(FLAG_START_AUTO_SCROLL)) {
                mHandler.removeMessages(FLAG_START_AUTO_SCROLL);
            }
            mHandler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL, mStillTime);
        }
    }

    /**
     * 停止滚动
     */
    public void stopAutoScroll() {
        if (mHandler != null) {
            if (mHandler.hasMessages(FLAG_START_AUTO_SCROLL)) {
                mHandler.removeMessages(FLAG_START_AUTO_SCROLL);
            }
        }
    }

    public void destroyHandler() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }

    //获取当前滚动条目的索引
    public int getCurrentIndex() {
        return mCurrentIndex % mTextAdvBeans.size();
    }
    
    public interface ExposureEventListener {
        void doExposureEvent();
    }
}

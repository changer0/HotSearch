package com.qq.reader.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tencent.mars.xlog.Log;
import com.qq.reader.core.utils.WeakReferenceHandler;

/**
 * Created by p_jwcao on 2015/11/5.
 */
public class ReaderRatingBar extends RatingBar implements Handler.Callback {
    
    public static final int MSG_RATINGCHANGED = 1001;
    /**
     * 和ratingbar绑定的textview所显示的文字数组合集，比星星的数量大1
     */
    private String [] mRatingTextIntroArray = null;
    /**
     * 和ratingbar绑定的textview
     */
    private TextView mRatingText;
    /**
     * ratingbar自身的回调
     */
    private OnRatingBarChangeListener mOnRatingBarChangeListener;
    /**
     * 用户设置的ratingbar回调
     */
    private OnRatingBarChangeListener mOnRatingBarChangeListenerUserCallBack;
    /**
     *  评分改变后的延迟回调，降低评分改变触发的频率
     */
    private OnRatingBarDelayChangedListener mOnDelayChangedListener;
    private WeakReferenceHandler mHandler = new WeakReferenceHandler(this);
    /**
     * 延迟时间，默认0毫秒
     */
    private int mMsg_delay_time = 0;
    
    public ReaderRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
        initEvent();
    }

    public ReaderRatingBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initUI();
        initEvent();
    }

    public ReaderRatingBar(Context context) {
        super(context);
        initUI();
        initEvent();
    }

    /**
     * 默认5颗星，步长1
     */
    private void initUI(){
        setNumStars(5);
        setStepSize(1f);
        setIndeterminate(false);
    }

    /**
     * 绑定textview和textview显示的文字
     * @param textView
     * @param textArray
     */
    public void setRatingText(TextView textView,String[] textArray){
        mRatingText = textView;
        mRatingTextIntroArray = textArray;
        if (mRatingTextIntroArray.length != getNumStars() + 1){
            throw new IllegalStateException("the starts' size is not equls the textArray's lenght plus one");
        }
        mOnRatingBarChangeListener.onRatingChanged(this, getRating(), false);
    }

    private void initEvent(){
        
        mOnRatingBarChangeListener = new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (mRatingText != null){
                    try {
                        mRatingText.setText(mRatingTextIntroArray[(int) Math.ceil(Math.round(rating * getSubProgressPerStar()) * getStepSize())]);
                    }catch (Exception e){
                        Log.printErrStackTrace("ReaderRatingBar", e, null, null);
                        Log.e("star", Math.ceil(Math.round(rating * getSubProgressPerStar()) * getStepSize()) + "");
                        mRatingText.setText("");
                    }
                }
                if (mOnRatingBarChangeListenerUserCallBack != null){
                    mOnRatingBarChangeListenerUserCallBack.onRatingChanged(ratingBar,rating,fromUser);
                }
                if (fromUser){
                    if (mMsg_delay_time > 0){
                        mHandler.removeMessages(MSG_RATINGCHANGED);
                    }
                    if (mOnDelayChangedListener != null  && mOnDelayChangedListener.onRatingPreDelay(ReaderRatingBar.this,rating)){
                        return;
                    }else {
                        Message msg = mHandler.obtainMessage();
                        msg.what = MSG_RATINGCHANGED;
                        Bundle bundle = new Bundle();
                        bundle.putFloat("rating", rating);
                        msg.obj = bundle;
                        mHandler.sendMessageDelayed(msg, mMsg_delay_time);
                    }
                }
            }
        };
        super.setOnRatingBarChangeListener(mOnRatingBarChangeListener);
    }

    @Override
     public void setOnRatingBarChangeListener(OnRatingBarChangeListener onRatingBarChangeListener) {
        mOnRatingBarChangeListenerUserCallBack = onRatingBarChangeListener;
    }

    private float getSubProgressPerStar() {
        if (getNumStars() > 0) {
            return 1f * getMax() / getNumStars();
        } else {
            return 1;
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_RATINGCHANGED:
                if (mOnDelayChangedListener != null){
                    Bundle bundle = (Bundle) msg.obj;
                    mOnDelayChangedListener.onRatingDelayChanged(this, bundle.getFloat("rating"));
                }
                break;
        }
        return false;
    }
    
    public interface OnRatingBarDelayChangedListener {
        boolean onRatingPreDelay(RatingBar ratingBar, float rating);
        void onRatingDelayChanged(RatingBar ratingBar, float rating);            
    }

    public void setOnRatingBarDelayChangedListener(OnRatingBarDelayChangedListener onDelayChangedListener) {
        mOnDelayChangedListener = onDelayChangedListener;
    }

    public int getRatingChangedDelaytime() {
        return mMsg_delay_time;
    }

    public void setRatingChangedDelaytime(int msg_delay_time) {
        mMsg_delay_time = msg_delay_time;
    }
}

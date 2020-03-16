package com.qq.reader.core.view;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.qq.reader.core.view.toast.ToastCompat;

public final class ReaderToast {

    public static final int ICON_NONE = -1; // 无图标

    private Context context;
    private ToastCompat mToast;


    public ReaderToast(Context context, CharSequence msg, int duration) {
        this.context = context;
        mToast = ToastCompat.makeText(context, msg, duration);
    }

    /**
     * 设置Toast显示的文字.
     *
     * @param msg
     */
    public void setText(CharSequence msg) {
        if (mToast != null) {
            mToast.setText(msg);
        }
    }

    /**
     * 设置Toast显示的文字.
     *
     * @param
     */
    public void setText(int msgResId) {
        setText(context.getResources().getString(msgResId));
    }

    /**
     * 设置显示时间 Toast.LENGTH_LONG和Toast.LENGTH_SHORT
     *
     * @param duration
     */
    public void setDuration(int duration) {
        if (mToast != null) {
            mToast.setDuration(duration);
        }
    }


    /**
     * 显示这个PadQQToast 默认显示在系统tilte顶部，可调用此方法
     */
    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }


    /**
     * 配置一个PadQQToast
     *
     * @param context
     * @param iconType 文字左边的图标类型,可能的值为ICON_DEFAULT,ICON_ERROR,ICON_SUCCESS
     * @param msg
     * @param duration
     * @return
     */
    public static ReaderToast makeText(Context context, int iconType,
                                       CharSequence msg, int duration) {
        return new ReaderToast(context, msg, duration);
    }

    /**
     * 配置一个PadQQToast
     *
     * @param context
     * @param iconType 文字左边的图标类型,可能的值为ICON_DEFAULT,ICON_ERROR,ICON_SUCCESS
     * @param msgResId
     * @param duration
     * @return
     */
    public static ReaderToast makeText(Context context, int iconType,
                                       int msgResId, int duration) {
        return new ReaderToast(context, context.getResources().getString(msgResId), duration);
    }

    /**
     * 配置一个PadQQToast(默认图标)
     *
     * @param context
     * @param msg
     * @param duration
     * @return
     */
    public static ReaderToast makeText(Context context, CharSequence msg,
                                       int duration) {
        return makeText(context, ICON_NONE, msg, duration);
    }

    /**
     * 配置一个PadQQToast(默认图标)
     *
     * @param context
     * @param msgResId
     * @param duration
     * @return
     */
    public static ReaderToast makeText(Context context, int msgResId,
                                       int duration) {
        return makeText(context, ICON_NONE, msgResId, duration);
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }


    public void setMargin(float horizontalMargin, float verticalMargin) {
        if (mToast != null) {
            mToast.setMargin(horizontalMargin, verticalMargin);
        }
    }

    public void setView(View view) {
        if (mToast != null) {
            mToast.setView(view);
        }
    }

    public float getHorizontalMargin() {
        if (mToast != null) {
            return mToast.getHorizontalMargin();
        }

        return -1f;
    }


    public float getVerticalMargin() {
        if (mToast != null) {
            return mToast.getVerticalMargin();
        }

        return -1f;
    }


    public int getDuration() {
        if (mToast != null) {
            return mToast.getDuration();
        }

        return -1;
    }


    public int getGravity() {
        if (mToast != null) {
            return mToast.getGravity();
        }

        return -1;
    }


    public int getXOffset() {
        if (mToast != null) {
            return mToast.getXOffset();
        }

        return -1;
    }


    public int getYOffset() {
        if (mToast != null) {
            return mToast.getYOffset();
        }

        return -1;
    }


    public View getView() {
        if (mToast != null) {
            return mToast.getView();
        }

        return null;
    }


    public Toast getToast() {
        return mToast;
    }


    public void cancel() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}

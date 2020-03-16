package com.qq.reader.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qq.reader.baseui.R;
import com.qq.reader.core.config.AppConstant;
import com.qq.reader.core.utils.ToastUtils;
import com.qq.reader.widget.ReaderTextView;
import com.tencent.mars.xlog.Log;

/**
 * DeepLink顶部返回View
 *
 * @author p_bbinliu
 * @date 2019-06-12
 */
public class DeepLinkBackView extends LinearLayout {

    public static final String TAG = "DeepLinkBackView";
    private String mBackUrl = null;
    private ReaderTextView mBackTextView;
    public static final String PLATFORM_OPPO = "OPPO";
    public static final String PLATFORM_VIVO = "vivo";
    private static final String BACK_NAME_OPPO = "返回OPPO";
    private static final String BACK_NAME_VIVO = "返回vivo";

    public static String DEEP_LINK_BACK_URL = "backurl";
    public static String DEEP_LINK_BACK_NAME = "btn_name";

    private StatEventListener mStatEventListener;
    private onStateBarChangeListener mOnStateBarChangeListener;

    public DeepLinkBackView(@NonNull Context context) {
        super(context);
        initView();
    }

    public DeepLinkBackView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DeepLinkBackView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_deeplink_back, this);
        mBackTextView = (ReaderTextView) rootView.findViewById(R.id.tv_back_text);
        rootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStatEventListener != null) {
                    mStatEventListener.statClick();
                }
                if (!isEmpty(mBackUrl)) {
                    doBack();
                } else {
                    handleError();
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), AppConstant.statusBarHeight);
    }

    public void setBackUrl(String backUrl) {
        Log.i(TAG, "DeepLinkBackView -> setBackUrl -> mBackUrl=" + backUrl);
        mBackUrl = backUrl;
    }

    public void setBackText(String text) {
        if (mBackTextView == null || isEmpty(mBackUrl)) {
            return;
        }
        if (isEmpty(text)) {
            if (mBackUrl.contains(PLATFORM_OPPO)) {
                mBackTextView.setText(BACK_NAME_OPPO);
            } else if (mBackUrl.contains(PLATFORM_VIVO)) {
                mBackTextView.setText(BACK_NAME_VIVO);
            }
        } else {
            mBackTextView.setText(text);
        }
    }

    public static boolean isEmpty(String s) {
        return s == null ||
                s.equals("") ||
                s.equals("null") ||
                s.length() == 0 ||
                s.equals("__BACKURL__") ||
                s.equals("__BTN_NAME__");
    }



    //返回到上一个应用
    public void doBack() {
        boolean needJumpSafely = false;
        Log.i(TAG, "doBack start");
        if (!isEmpty(mBackUrl)) {
            if (mBackUrl.contains(PLATFORM_VIVO)) {
                needJumpSafely = true;
            }
            Log.i(TAG, "doBack backUrl=" + mBackUrl + " needJumpSafely=" + needJumpSafely);
            try {
                if (!needJumpSafely) {
                    Uri data = Uri.parse(mBackUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, data);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                } else {
                    Intent intent = (Intent) Intent.parseUri(mBackUrl, Intent.URI_INTENT_SCHEME);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setSelector(null);
                    intent.setComponent(null);
                    getContext().startActivity(intent);
                }

            } catch (Exception e) {
                handleError();
                Log.e(TAG, "返回失败，activity 跳转异常 exception=" + e.toString());
                e.printStackTrace();
            }
        } else {
            handleError();
        }
    }

    public void setStatEventListener(StatEventListener statEventListener) {
        mStatEventListener = statEventListener;
    }

    public void setOnStateBarChangeListener(onStateBarChangeListener onStateBarChangeListener) {
        mOnStateBarChangeListener = onStateBarChangeListener;
    }

    //异常时提示Toast,隐藏返回条，取消back键监听
    private void handleError() {
        ToastUtils.showToast_Short(R.string.back_error);
        mBackUrl = null;
        setVisibility(GONE);
        if (mOnStateBarChangeListener != null) {
            mOnStateBarChangeListener.show();
        }
    }

    public String getBackUrl() {
        return mBackUrl;
    }

    public interface StatEventListener {
        void statClick();
    }

    public interface onStateBarChangeListener {
        void hide();

        void show();
    }

}

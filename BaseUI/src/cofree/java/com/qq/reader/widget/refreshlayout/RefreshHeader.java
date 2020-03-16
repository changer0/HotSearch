package com.qq.reader.widget.refreshlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieListener;
import com.qq.reader.baseui.R;
import com.qq.reader.view.lottie.LottieDataLoader;
import com.qq.reader.view.lottie.LottieLoaderBean;
import com.qq.reader.widget.ReaderTextView;
import com.tencent.mars.xlog.Log;


/**
 * 默认刷新header
 * Created by p_bbinlliu on 2019/12/12.
 */
public class RefreshHeader extends BaseRefreshHeader {

    public static final String TAG = "RefreshHeader";
    private ReaderTextView mLoadingText;
    private LottieAnimationView mLottieAnimationView;

    public final static String REFRESH_HEADER_ANIMATION_RES = "lottie/refresh_layout/lottie_refresh_layout_loading.json";

    private View mRootView;
    private Context mContext;

    public RefreshHeader(Context context) {
        super(context);
        mContext = context;
    }

    public RefreshHeader(Context context, AttributeSet attrs) {
        super(context,attrs);
        mContext = context;
    }

    @Override
    public void initView() {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_refresh_header, null);
        mLoadingText = (ReaderTextView) mRootView.findViewById(R.id.loadingText);
        mLottieAnimationView = (LottieAnimationView) mRootView.findViewById(R.id.lottie_view);


        mLottieAnimationView.post(new Runnable() {
            @Override
            public void run() {
                mLottieAnimationView.setPivotX(mLottieAnimationView.getWidth()/2);
                mLottieAnimationView.setPivotY(mLottieAnimationView.getHeight());
            }
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0);

        addView(mRootView, lp);
    }


    @Override
    public void setState(int state) {
        switch (state) {
            case RefreshLayout.STATUS.STATUS_DEFAULT:
                mLoadingText.setText(R.string.refresh_pull_down_to_refresh);
                needToScale = true;
                stopAnimation();
                break;
            case RefreshLayout.STATUS.STATUS_REFRESH_READY:
                mLoadingText.setText(R.string.refresh_pull_down_to_refresh);
                startAnimation();
                break;
            case RefreshLayout.STATUS.STATUS_SWIPING_TO_REFRESH:
                mLoadingText.setText(R.string.refresh_pull_down_to_refresh);
                break;
            case RefreshLayout.STATUS.STATUS_RELEASE_TO_REFRESH:
                mLoadingText.setText(R.string.refresh_release_to_refresh);
                break;
            case RefreshLayout.STATUS.STATUS_MANUAL_REFRESHING:
            case RefreshLayout.STATUS.STATUS_REFRESHING:
                if (mLottieAnimationView.getProgress() == 0) {
                    startAnimation();
                }
                mLoadingText.setText(R.string.refresh_refreshing);
                break;
            case RefreshLayout.STATUS.STATUS_PRE_TO_TWO_LEVEL:
                mLoadingText.setText(R.string.refresh_pre_to_tow_level);
                break;
            case RefreshLayout.STATUS.STATUS_RELEASE_TO_TWO_LEVEL:
                mLoadingText.setText(R.string.refresh_release_to_two_level);
                break;
        }
    }



    private LottieListener<LottieComposition> lottieStartAnimListener = new LottieListener<LottieComposition>() {
        @Override
        public void onResult(LottieComposition result) {
            if (result == null || mLottieAnimationView == null) {
                return;
            }

            mLottieAnimationView.setComposition(result);

            mLottieAnimationView.playAnimation();
        }
    };

    private void stopAnimation() {
        if (mLottieAnimationView != null) {
            mLottieAnimationView.setProgress(0);
            mLottieAnimationView.cancelAnimation();
        }
    }

    private void startAnimation() {
        LottieDataLoader.getInstance().fromAssert(new LottieLoaderBean(mContext, REFRESH_HEADER_ANIMATION_RES, lottieStartAnimListener));
    }


//    public void setLastRefreshTime(String lastRefreshTime) {
//        if (mLastRefreshTime != null) {
//            mLastRefreshTime.setText(lastRefreshTime);
//        }
//    }


    /**
     * 优化下性能
     * 避免频繁执行
     */
    boolean needToScale = true;

    @Override
    public void onUpdateScroll(float diff) {
        if (mLottieAnimationView == null) {
            return;
        }

        mLottieAnimationView.post(() -> {
            float scale = diff / (mLottieAnimationView.getHeight() * 1.5f);
            Log.d(TAG, "onUpdateScroll scale=" + scale + " diff=" + diff);

            if (scale <= 1.0f) {
                mLottieAnimationView.setScaleX(scale);
                mLottieAnimationView.setScaleY(scale);
                needToScale = true;
                Log.d(TAG, "onUpdateScroll needToScale is true");
            } else {
                scale = 1.0f;
                if (needToScale) {
                    Log.d(TAG, "onUpdateScroll setScaleX Y when scale > 1");
                    mLottieAnimationView.setScaleX(scale);
                    mLottieAnimationView.setScaleY(scale);
                    needToScale = false;
                }
            }
        });
    }

    public View getView() {
        return mRootView;
    }
}

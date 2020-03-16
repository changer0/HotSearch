package com.qq.reader.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qq.reader.baseui.R;

public class GuidePopupView extends BaseDialog implements IGuide {

	private GuideShadowView mShadowView;
	private Activity mActivity;
	private DialogInterface.OnDismissListener monDismisslistener;
	private OnShowListener mOnShowlistener;
	private View mRootView;
	private TextView mText1;
	private TextView mText2;
	private boolean mIsOutsideTouchable;
	private int mLayoutResId;
	private ImageView mImage;


    private int mGrivity;
    private int mXoffset;
    private int mYoffset;
    private boolean mIsShowShadow;
	private HighLightInfo mHighLightRect;
	private int[] location;
	private boolean mIsFocusable = true;

	/**
	 * 提供布局资源中提供两个id为android.R.id.text1和android.R.id.text2的TextView，不提供的话没有点击事件，
	 * 点击事件需要额外设置
	 *
	 * @param act
	 * @param layoutResId
	 * @param isOutsideTouchable
	 */
	public GuidePopupView(Activity act, int layoutResId,boolean isOutsideTouchable, boolean mIsFocusable) {
		mActivity = act;
		mLayoutResId = layoutResId;
		mIsOutsideTouchable = isOutsideTouchable;
		this.mIsFocusable = mIsFocusable;
		initViews();
		initData();
	}

	/**
	 * 提供布局资源中提供两个id为android.R.id.text1和android.R.id.text2的TextView，不提供的话没有点击事件，
	 * 点击事件需要额外设置
	 *
	 * @param act
	 * @param layoutResId
	 * @param isOutsideTouchable
	 */
	public GuidePopupView(Activity act, int layoutResId,boolean isOutsideTouchable) {
		this(act, layoutResId, isOutsideTouchable, true);
	}

	public GuidePopupView(Activity act) {
		this(act, R.layout.new_common_tip, false);
	}

	public void setOnClickListener(OnClickListener listener){
		if(mRootView != null) {
			mRootView.setOnClickListener(listener);
		}
	}

	private void initViews() {
		initDialog(mActivity, null, mLayoutResId, BaseDialog.TYPE_CENTER, false);
		mRootView = mDialog.findViewById(R.id.root);
		mText1 = (TextView) mDialog.findViewById(android.R.id.text1);
		mText2 = (TextView) mDialog.findViewById(android.R.id.text2);
		mImage = (ImageView) mDialog.findViewById(R.id.guide_img);
	}

	private void initData() {
//		mPopupW.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		mDialog.setCanceledOnTouchOutside(mIsOutsideTouchable);
		mDialog.setCancelable(false);
		mDialog.getWindow().setWindowAnimations(R.style.Animation_alphaAnim);
		mDialog.setOnDismissListener(new GuideDismissListener());
        setText2OnClickListener(v -> dismiss());
	}

    @Override
    public void show() {
        showAtLocation(mGrivity,mXoffset,mYoffset,mIsShowShadow);
    }

    public void setText1(CharSequence text) {
		if (mText1 != null) {
			mText1.setText(text);
		}
	}

	public void setImageVisibility(int visibility) {
		if(mImage!=null) {
			mImage.setVisibility(visibility);
		}
	}

	public void setText2(CharSequence text) {
		if (mText2 != null) {
			mText2.setText(text);
		}
	}

	/**
	 * 使得文字在图片的右侧
	 * 默认在左侧
	 */
	public void setTextToRight() {
		if (mText1 != null && mText2 != null) {
			mText1.setVisibility(View.GONE);
			mText2.setVisibility(View.VISIBLE);
		}
	}

	public void setRootViewBg(int resId) {
		mDialog.findViewById(R.id.root).setBackgroundResource(resId);
	}

    public void setmGuideImageSize(int width, int height) {
        ImageView imageView = (ImageView) mDialog.findViewById(R.id.guide_img);
        LinearLayout rootView = (LinearLayout) mDialog.findViewById(R.id.root);
        try {
			if (imageView != null && rootView != null) {
				ViewGroup.LayoutParams lp = imageView.getLayoutParams();
				lp.height = height;// 设置图片的高度
				lp.width = width;
				imageView.setLayoutParams(lp);
			}
		}catch (Exception e){
        	e.printStackTrace();
		}

    }

	public void showAtLocation(int gravity, int xoffset,
			int yoffset, boolean isShowShadow) {
		WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
		lp.gravity = gravity;
		lp.x = xoffset;
		lp.y = yoffset;
		lp.dimAmount=0.0f;
		mDialog.getWindow().setAttributes(lp);
//		mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		mDialog.show();
		showBgMask(isShowShadow);
		if (mOnShowlistener != null) {
			mOnShowlistener.onShow();
		}
	}

	public void setHighLightRect(HighLightInfo rect) {
		mHighLightRect = rect;
	}

	/**
	 * 背景模糊，夜间模式不需要
	 *
	 * @param need
	 */
	public void showBgMask(boolean need) {
		if (need && mShadowView == null) {
			// mShadowView = new View(mActivity);
			// mShadowView.setBackgroundColor(0xB3000000);
			mShadowView = new GuideShadowView(mActivity);
			mShadowView.setHighLightRect(mHighLightRect);
			((ViewGroup) mActivity.getWindow().getDecorView())
					.addView(mShadowView);
			mShadowView.requestLayout();
		}
	}

	public View findViewById(int id) {
		return mDialog.findViewById(id);
	}

	public boolean isShowing() {
		return mDialog.isShowing();
	}

	public void setText1OnClickListener(OnClickListener mText1OnClickListener) {
		if (mText1 != null) {
			mText1.setOnClickListener(mText1OnClickListener);
		}
	}

	public void setText2OnClickListener(OnClickListener mText2OnClickListener) {
		if (mText2 != null) {
			mText2.setOnClickListener(mText2OnClickListener);
		}
	}

	public void setOnShowlistener(OnShowListener mOnShowlistener) {
		this.mOnShowlistener = mOnShowlistener;
	}

	public void setOnDismissListener(DialogInterface.OnDismissListener monDismisslistener){
		this.monDismisslistener = monDismisslistener;
	}

	@Override
	public void doGuid(int type) {

	}

	@Override
	public int[] getArea(int type) {
		if (location == null) {
			View view = findViewById(android.R.id.text2);
			location = new int[4];
			view.getLocationOnScreen(location);
			location[2] = location[0] + view.getWidth();
			location[3] = location[1] + view.getHeight();
		}
		return location;
	}

    @Override
    public void dismiss(int type) {

    }

	public void hideGuideImage() {
		ImageView imageView = (ImageView) mDialog.findViewById(R.id.guide_img);
		LinearLayout rootView = (LinearLayout) mDialog.findViewById(R.id.root);
		try {
			if (imageView != null && rootView != null) {
				imageView.setVisibility(View.GONE);
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}

    @Override
	public HighLightInfo getHighLightArea(int type) {
		return null;
	}

    public void setIsShowShadow(boolean isShowShadow) {
        mIsShowShadow = isShowShadow;
    }

    public void setYoffset(int yoffset) {
        mYoffset = yoffset;
    }

    public void setXoffset(int xoffset) {
        mXoffset = xoffset;
    }

    public void setGrivity(int grivity) {
        mGrivity = grivity;
    }

	public interface OnShowListener {
		public void onShow();
	}

	private class GuideDismissListener implements DialogInterface.OnDismissListener {

		@Override
		public void onDismiss(DialogInterface dialog) {
			if (monDismisslistener != null) {
				monDismisslistener.onDismiss(dialog);
			}
			if (mShadowView != null) {
				((ViewGroup) mActivity.getWindow().getDecorView())
						.removeView(mShadowView);
				mShadowView = null;
			}
		}
	}


	@Override
	public void typeCenterAction(WindowManager.LayoutParams lp) {
		if (!mIsFocusable) {
			lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND)| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		}
	}
}

package com.qq.reader.common.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.qq.reader.baseui.R;
import com.tencent.mars.xlog.Log;

public class NightModeUtil {

	private Activity mActivity;
	private Dialog mDialog;

	private ImageView mMaskView = null;

	private boolean isShowNightMask;
	private int mMaskType = -1;
	private final static int TYPE_ACTIVITY = 10001;
	private final static int TYPE_DIALOG = 10002;
	private int viewId = -1;
	public NightModeUtil(Activity act, boolean isShowNM) {
		mActivity = act;
		isShowNightMask = isShowNM;
		mMaskType = TYPE_ACTIVITY;
	}

	public NightModeUtil(Dialog dialog, boolean isShowNM) {
		mDialog = dialog;
		isShowNightMask = isShowNM;
		mMaskType = TYPE_DIALOG;
	}

	/**
	 * 此View必须为FramLayout或者RelativeLayout（或子类），LinearLayout不行
	 * @param viewId
	 */
	public void setViewId(int viewId) {
		this.viewId = viewId;
	}

	public void setIsNM(boolean isShowNM) {
		isShowNightMask = isShowNM;
	}

	public boolean isNM() {
		return isShowNightMask;
	}

	
	public void removeMask() {
		removeMask(false);
	}
	public void removeMask(boolean needAnim) {
		// Context context = null;
		if(mMaskView == null) {
			return;
		}
		Window window = null;
		if (mMaskType == TYPE_ACTIVITY) {
			// context = mActivity;
			window = mActivity.getWindow();
		} else if (mMaskType == TYPE_DIALOG) {
			// context = mDialog.getContext();
			window = mDialog.getWindow();
		} else {
			return;
		}
		if (mMaskView != null) {
			ViewGroup targetGroup;
			ViewGroup decor = (ViewGroup) window.getDecorView();
			if (viewId != -1) {
				targetGroup = (ViewGroup) decor.findViewById(viewId);
				if (targetGroup == null) {
					targetGroup = decor;
				}
			}else {
				targetGroup = decor;
			}
			if (needAnim) {
				Animation animation=AnimationUtils.loadAnimation(targetGroup.getContext(), R.anim.alpha_out);
				mMaskView.startAnimation(animation);
			}
			targetGroup.removeView(mMaskView);
			// WindowManager windowMgr = mActivity.getWindowManager();
			// windowMgr.removeViewImmediate(mMaskView);
			mMaskView = null;
		}
	}
	
	public void foce2ShowMask(boolean needAnim){
		if (null != mMaskView ) {
			return;
		}
		Context context = null;
		Window window = null;
		if (mMaskType == TYPE_ACTIVITY) {
			context = mActivity;
			window = mActivity.getWindow();
		} else if (mMaskType == TYPE_DIALOG) {
			context = mDialog.getContext();
			window = mDialog.getWindow();
		} else {
			return;
		}
		try {
			if (mMaskView == null) {
				mMaskView = new ImageView(context);
				mMaskView.setImageDrawable(new ColorDrawable(Color
						.parseColor("#77000000")));
				mMaskView.setPadding(0, paddingTop, 0, 0);
				mMaskView.setScaleType(ScaleType.FIT_XY);
				ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				mMaskView.setLayoutParams(lp);
				ViewGroup targetGroup;
				ViewGroup decor = (ViewGroup) window.getDecorView();
				if (viewId != -1) {
					targetGroup = (ViewGroup) decor.findViewById(viewId);
					if (targetGroup == null) {
						targetGroup = decor;
					}
				} else {
					targetGroup = decor;
				}
				if (needAnim) {
					Animation animation=AnimationUtils.loadAnimation(targetGroup.getContext(), R.anim.alpha_in);
					mMaskView.startAnimation(animation);
				}
				targetGroup.addView(mMaskView, mMaskView.getLayoutParams());
			}
		} catch (Throwable e) {
			Log.printErrStackTrace("NightModeUtil", e, null, null);
			// 在一些2.X机器上calendar有时候有异常，这里catch下
			e.printStackTrace();
		}
	}

	public void showMask() {
		showMask(false);
	}
	public void showMask(boolean needAnim) {
		boolean isNightMode = CommonConfig.isNightMode;
		if (null != mMaskView ) {
			if (!isNightMode) {
				removeMask(needAnim);
			}
			return;
		}
		if (!isNightMode || !isShowNightMask) {
			return;
		}
		foce2ShowMask(needAnim);
	}

	int paddingTop = 0;
	public void setPaddingTop(int paddingTop) {
		this.paddingTop = paddingTop;
	}
}

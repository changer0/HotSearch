package com.qq.reader.core.utils.compat;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.qq.reader.core.utils.Utils;


public abstract class UIStyleCompat {
	private static UIStyleCompat sInstance;
	public static final int EMUI_BRAND_COLOR_EMUI3X = 0xFF28c0c6;
	public static final int EMUI_BRAND_COLOR_EMUI40 = 0xFF0097a8;
	public static final int EMUI_CONTROL_COLOR_EMUI3X_DEFAULT = 0xFF28c0c6;
	public static final int EMUI_CONTROL_COLOR_EMUI40_DEFAULT = 0xFF00bcd5;
	
	
	public static UIStyleCompat getUIStyleCompat(Activity activity) {
		if (sInstance == null) {
			int EMUI_SDK_INT = Utils.getEMUISDKINT();
			switch (EMUI_SDK_INT) {
			case Utils.EMUI_VERSION_CODES.EMUI_3_0:
				sInstance = new EMUIStyle30(activity);
				break;
			case Utils.EMUI_VERSION_CODES.EMUI_3_1:
				sInstance = new EMUIStyle31(activity);
				break;
			case Utils.EMUI_VERSION_CODES.EMUI_4_0:
				sInstance = new EMUIStyle40(activity);
				break;
			default:
				if (EMUI_SDK_INT > Utils.EMUI_VERSION_CODES.EMUI_4_0) {
					sInstance = new EMUIStyle40(activity);
				} else {
					sInstance = new OtherUIStyle(activity);
				}
				break;
			}
		}
		sInstance.setActivity(activity);
		return sInstance;
	}
	
	/**
	 * called from onCreate of "MainActivity" to  
	 */
	public static void clearInstance() {
		sInstance = null;
	}
	
	public abstract void notifyNewBadgeForTab(ActionBar bar, int pos, boolean show);
	public abstract void setActivity(Activity activity);
	public abstract View getCustTitleView();
	public abstract int getStylePrimaryColor();
	public abstract int getControlColor();
	public abstract Drawable getColorfulCheckBox();
	public abstract void setColorForDrawable(Drawable d);
}

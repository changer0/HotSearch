package com.qq.reader.core.utils.compat;

import android.app.Activity;

public class EMUIStyle30 extends OtherUIStyle {
	
	EMUIStyle30(Activity activity) {
		super(activity);
		mPrimaryColor = UIStyleCompat.EMUI_BRAND_COLOR_EMUI3X;
	}

	private static final String TAG = "EMUIStyle30";
	
	@Override
	public int getStylePrimaryColor() {
		return UIStyleCompat.EMUI_BRAND_COLOR_EMUI3X;
	}
	
	@Override
	public int getControlColor() {
		// TODO Auto-generated method stub
		return UIStyleCompat.EMUI_CONTROL_COLOR_EMUI3X_DEFAULT;
	}
}

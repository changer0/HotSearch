package com.qq.reader.core.utils.compat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.TextView;

import com.qq.reader.baseui.R;
import com.qq.reader.core.utils.ReflectionUtils;


public class EMUIStyle40 extends EMUIStyle31 {

	public static final String SIGN_GETPRIMARYCOLOR = "com.huawei.android.immersion.ImmersionStyle:getPrimaryColor";
	public static final String SIGN_GETCOLORSTYLE = "com.huawei.android.immersion.ImmersionStyle:getSuggestionForgroundColorStyle";
	public static final String SIGN_GETCONTROLCOLOR = "com.huawei.android.immersion.ImmersionStyle:getControlColor";
	private static final String TAG = "EMUIStyle40";
	private int mColorStyle = Integer.MAX_VALUE;
	private int mControlColor = 0;
	private int mDarkStyleFontColor = 0xff000000;
	private int mLightStyleFontColor = 0xffffffff;
	private Drawable mColorfulCheckBox;
	EMUIStyle40(Activity activity) {
		super(activity);
		mPrimaryColor = 0;
	}

	@Override
	public int getStylePrimaryColor() {
		// TODO Auto-generated method stub
		if (mPrimaryColor == 0) {
			ReflectionUtils.MethodSign ms = new ReflectionUtils.MethodSign(SIGN_GETPRIMARYCOLOR, Context.class);
			ReflectionUtils.ObjectValue ov = new ReflectionUtils.ObjectValue(null, mActivity);
			Object obj = ReflectionUtils.invokeMethod(ms, ov);
			if (!(obj instanceof Integer)) {
				Log.w(TAG, "get primary color exception");
				return mPrimaryColor;
			}
			mPrimaryColor = Integer.valueOf(obj.toString());
		}
		return mPrimaryColor;
	}
	
	@Override
	public int getControlColor() {
		if (mControlColor == 0) {
			ReflectionUtils.MethodSign ms = new ReflectionUtils.MethodSign(SIGN_GETCONTROLCOLOR, Context.class);
			ReflectionUtils.ObjectValue ov = new ReflectionUtils.ObjectValue(null, mActivity);
			Object obj = ReflectionUtils.invokeMethod(ms, ov);
			if (!(obj instanceof Integer)) {
				Log.w(TAG, "get control color exception");
				mControlColor = UIStyleCompat.EMUI_CONTROL_COLOR_EMUI40_DEFAULT;
				return mControlColor;
			}
			mControlColor = Integer.valueOf(obj.toString());
		}
		if (mControlColor == 0) {
			mControlColor = UIStyleCompat.EMUI_CONTROL_COLOR_EMUI40_DEFAULT;
		}
		return mControlColor;
	}
	
	@Override
	protected void setTitleColor(TextView tv_num) {
		if (mPrimaryColor == 0) {
			ReflectionUtils.MethodSign ms = new ReflectionUtils.MethodSign(SIGN_GETPRIMARYCOLOR, Context.class);
			ReflectionUtils.ObjectValue ov = new ReflectionUtils.ObjectValue(null, mActivity);
			Object obj = ReflectionUtils.invokeMethod(ms, ov);
			if (!(obj instanceof Integer)) {
				super.setTitleColor(tv_num);
				return;
			}
			mPrimaryColor = Integer.valueOf(obj.toString());
		}
		if (mColorStyle == Integer.MAX_VALUE) {
			ReflectionUtils.MethodSign ms = new ReflectionUtils.MethodSign(SIGN_GETCOLORSTYLE, int.class);
			ReflectionUtils.ObjectValue ov = new ReflectionUtils.ObjectValue(null, mPrimaryColor);
			Object obj = ReflectionUtils.invokeMethod(ms, ov);
			if (!(obj instanceof Integer)) {
				super.setTitleColor(tv_num);
				return;
			}
			mColorStyle = Integer.valueOf(obj.toString());
		}
//		if (mColorStyle == 0) {
			//dark
//			tv.setTextColor(mDarkStyleFontColor);
//			tv_num.setTextColor(mDarkStyleFontColor);
//			tv_num.setBackgroundResource(R.drawable.system_bar_numeric_box_black);
//		} else {
//			tv.setTextColor(mLightStyleFontColor);
//			tv_num.setTextColor(mLightStyleFontColor);
//			tv_num.setBackgroundResource(R.drawable.system_bar_numeric_box_light);
//		}
//		tv_num.setPadding(6, 0, 6, 0);
	}

	public boolean isLightActionBar() {
		if (mPrimaryColor == 0) {
			ReflectionUtils.MethodSign ms = new ReflectionUtils.MethodSign(SIGN_GETPRIMARYCOLOR, Context.class);
			ReflectionUtils.ObjectValue ov = new ReflectionUtils.ObjectValue(null, mActivity);
			Object obj = ReflectionUtils.invokeMethod(ms, ov);
			mPrimaryColor = Integer.valueOf(obj.toString());
		}
		if (mColorStyle == Integer.MAX_VALUE) {
			ReflectionUtils.MethodSign ms = new ReflectionUtils.MethodSign(SIGN_GETCOLORSTYLE, int.class);
			ReflectionUtils.ObjectValue ov = new ReflectionUtils.ObjectValue(null, mPrimaryColor);
			Object obj = ReflectionUtils.invokeMethod(ms, ov);
			mColorStyle = Integer.valueOf(obj.toString());
		}
		if (mColorStyle == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public Drawable getColorfulCheckBox() {
		if (mColorfulCheckBox == null) {
			mControlColor = getControlColor();
			Drawable d = mActivity.getResources().getDrawable(R.drawable.btn_check_on_mask);
			d.setTint(mControlColor);
			Drawable d1 = mActivity.getResources().getDrawable(R.drawable.btn_check_on_element_dark);
			
			int right = d.getIntrinsicWidth();
			int bottom = d.getIntrinsicHeight();
			d.setBounds(0, 0, right, bottom);
			d1.setBounds(0, 0, right, bottom);
			Bitmap b = Bitmap.createBitmap(right, bottom, Config.ARGB_8888);
			Canvas c = new Canvas(b);
			d.draw(c);
			d1.draw(c);
			c.setBitmap(null);
			mColorfulCheckBox = new BitmapDrawable(mActivity.getResources(),b);
		}
		
		return mColorfulCheckBox;
	}
	
	@Override
	public void setColorForDrawable(Drawable d) {
		// TODO Auto-generated method stub
		if (d == null) {
			return;
		}
		mControlColor = getControlColor();
		d.setTint(mControlColor);
	}
}

package com.qq.reader.core.utils.compat;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qq.reader.baseui.R;


public class OtherUIStyle extends UIStyleCompat {

	private static final String TAG = "OtherUIStyle";
	protected Activity mActivity;
	protected int mPrimaryColor = 0;
	private static SparseArray<TextView> mTabBadgeTxts = new SparseArray<TextView>();
	OtherUIStyle(Activity activity) {
		mActivity = activity;
	}

	@Override
	public void notifyNewBadgeForTab(ActionBar bar, int pos, boolean show) {
		if (bar == null) {
			Log.w(TAG,"notifyNewBadgeForTab ActionBar is null");
			return;
		}
		int tabCnt = bar.getTabCount();
		if (pos < 0 || pos >= tabCnt) {
			Log.w(TAG,"notifyNewBadgeForTab position out of index tabCnt = "+tabCnt+" position = "+pos);
			return;
		}
		Tab tab = bar.getTabAt(pos);
		TextView tabBadgeTxt = mTabBadgeTxts.get(pos);
		if (show) {
			if (tabBadgeTxt == null) {
				ViewGroup v = (ViewGroup)mActivity.getLayoutInflater().inflate(R.layout.tab_badge_view, null);
				tabBadgeTxt = (TextView)v.findViewById(R.id.txt_tab_badge);
				mTabBadgeTxts.put(pos, tabBadgeTxt);
			}
			tabBadgeTxt.setText(tab.getText());
			tab.setCustomView(tabBadgeTxt);
		} else {
			if (tabBadgeTxt != null && tab.getCustomView() == tabBadgeTxt) {
				tab.setCustomView(null);
				tab.setText(tabBadgeTxt.getText());
			}
		}
		
	}

	@Override
	public void setActivity(Activity activity) {
		mActivity = activity;
	}

	public View getCustTitleView() {
		ViewGroup vg = (ViewGroup)LayoutInflater.from(mActivity).inflate(R.layout.actionbar_cust_title, null);
//		ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,CommonUtility.dip2px(48));
//		vg.setLayoutParams(vlp);
		vg.setPadding(0,0,0,0);
		vg.setBackgroundResource(0);
		ImageView tv = (ImageView)vg.findViewById(R.id.select_cancel);
		tv.setVisibility(View.GONE);
		TextView tv_num = (TextView)vg.findViewById(R.id.select_num);
		setTitleColor(tv_num);
		return vg;
	}

	protected void setTitleColor(TextView tv_num) {
//		tv.setTextColor(0xffffffff);
		tv_num.setTextColor(0xffffffff);
//		tv_num.setBackgroundResource(R.drawable.system_bar_numeric_box_light);
	}
	
	@Override
	public int getStylePrimaryColor() {
		return 0;
	}
	
	@Override
	public int getControlColor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Drawable getColorfulCheckBox() {
		// TODO Auto-generated method stub
		return mActivity.getResources().getDrawable(R.drawable.btn_check_on_emui);
	}
	
	@Override
	public void setColorForDrawable(Drawable d) {
		// TODO Auto-generated method stub
		
	}
}

package com.qq.reader.core.utils.compat;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.qq.reader.baseui.R;


public class EMUIStyle31 extends EMUIStyle30 {

	private static final String TAG = "EMUIStyle31";

	EMUIStyle31(Activity activity) {
		super(activity);
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
		Drawable label = show ? mActivity.getResources().getDrawable(R.drawable.bookshelf_news_newtip) : null;
		bar.getTabAt(pos).setTag("show-icon-right").setIcon(label); 
	}
}

package com.qq.reader.view.linearmenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qq.reader.baseui.R;


public class LinearBaseMenuItemView extends RelativeLayout {
	public LinearBaseMenuItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	private TextView mName;
	private ImageView mNew;
	private LinearLayout mRightLayout;
	private Drawable iLikeDrawable;

	public void init() {
		mName = (TextView) findViewById(R.id.linear_menu_name);
		mNew = (ImageView) findViewById(R.id.linear_menu_new);
		mRightLayout = (LinearLayout) findViewById(R.id.linear_menu_right);
	}

	public void setText(String text) {
		mName.setText(text);
		if (text.contains(getResources().getString(R.string.text_menu_cloud))) {
			iLikeDrawable = getResources().getDrawable(R.drawable.profile_cloudshelf_icon);
			mName.setCompoundDrawablesWithIntrinsicBounds(null, null,
					null, null);
		} else {
			mName.setCompoundDrawablesWithIntrinsicBounds(null, null, null,
					null);
		}
	}
	public LinearLayout getRightLayout(){
		return mRightLayout;
	}

	public void setNewShow(boolean show) {
		mNew.setVisibility(show ? View.VISIBLE : View.GONE);
	}

}
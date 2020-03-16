package com.qq.reader.view;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class GuideShadowView extends View {

	private Paint mPaint, mHighLightPaint;
	private List<HighLightInfo> mHighLightList;
	private HighLightInfo mHighLightInfo;

	public GuideShadowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public GuideShadowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GuideShadowView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(0xB3000000);
		mHighLightPaint = new Paint();
		mHighLightPaint.setAntiAlias(true);
		mHighLightPaint.setColor(0xB3000000);
		// mHighLightPaint.setStyle(Paint.Style.STROKE);
//		mPaint.setXfermode(new PorterDuffXfermode(Mode.LIGHTEN));
	}

	public void setHighLightRect(HighLightInfo info) {
		mHighLightInfo = info;
	}

	public void setHighLightRect(List<HighLightInfo> list) {
		mHighLightList = list;
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		if (mHighLightInfo != null) {
			Path path = new Path();
			RectF rectf = new RectF(mHighLightInfo.rect);
			if (mHighLightInfo.shape == HighLightInfo.OVAL) {
				path.moveTo(rectf.left, rectf.top);// 移动到rect的左上角
				path.lineTo(rectf.left, rectf.bottom);
				path.lineTo((rectf.right + rectf.left) / 2, rectf.bottom);
				path.arcTo(rectf, 90, 180, false);
				path.arcTo(rectf, 270, 180, false);
				path.lineTo(rectf.right, rectf.bottom);
				path.lineTo(rectf.right, rectf.top);
				path.lineTo((rectf.right + rectf.left) / 2, rectf.top);
				canvas.drawPath(path, mHighLightPaint);
			}
			// 画矩形
			Rect top = new Rect(0, 0, getRight(), mHighLightInfo.rect.top);
			Rect left = new Rect(0, mHighLightInfo.rect.top, mHighLightInfo.rect.left,
					mHighLightInfo.rect.bottom);
			Rect right = new Rect(mHighLightInfo.rect.right, mHighLightInfo.rect.top,
					getRight(), mHighLightInfo.rect.bottom);
			Rect bottom = new Rect(0, mHighLightInfo.rect.bottom, getRight(),
					getBottom());
			canvas.drawRect(top, mPaint);
			canvas.drawRect(left, mPaint);
			canvas.drawRect(right, mPaint);
			canvas.drawRect(bottom, mPaint);
		} else {
			canvas.drawRect(new Rect(getLeft(), getTop(), getRight(),
					getBottom()), mPaint);
		}
	}

}

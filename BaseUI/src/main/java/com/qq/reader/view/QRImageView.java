package com.qq.reader.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.qq.reader.baseui.R;
import com.qq.reader.core.BaseApplication;

public class QRImageView extends ImageView {

	private Rect bound = new Rect();
	private Paint bound_paint = new Paint();
//	private Context mContext;
	private Drawable mDrawable;
	private float shadow_width;
	private float shadow_height;

	public QRImageView(Context context) {
		super(context);
//		mContext = context;
		init();
	}

	public QRImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
//		mContext = context;
		init();
	}

	private void init() {
		bound_paint.setColor(BaseApplication.Companion.getINSTANCE().getResources().getColor(R.color.common_cover_bound_stroke_color));
		bound_paint.setStrokeWidth(1.2f);
		bound_paint.setStyle(Paint.Style.STROKE);
		shadow_width = getResources().getDimension(R.dimen.common_cover_shadow_width);
		shadow_height = getResources().getDimension(R.dimen.common_cover_shadow_height);
//		setPadding(shadow_width, shadow_height, shadow_width, shadow_height);
		mDrawable = getContext().getResources().getDrawable(R.drawable.common_cover_shadow);
		//setBackgroundResource(R.drawable.common_cover_shadow);

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
//		int height = getHeight();
//		int width = getWidth();
//		int left1 = getLeft();
//		int top1 = getTop();
		bound.set(0, 0,getWidth(), getHeight());

		mDrawable.setBounds(0 ,0, (int)(right - left + 2*shadow_width) ,(int)(bottom - top + shadow_height * 2));
	}

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.save();
		canvas.translate(-shadow_width, -shadow_height);
		mDrawable.draw(canvas);

		canvas.restore();
		super.onDraw(canvas);

		//bound_paint.setColor(getBoundColor());
		canvas.drawRect(bound,bound_paint);
	}

//	private int getBoundColor() {
//		if (bound.width() == getADimen(R.dimen.localstore_imgsize_super_bigcover_width)
//				&& bound.height() == getADimen(R.dimen.localstore_imgsize_super_bigcover_height)) {
//			return Color.GREEN;
//		}
//		if (bound.width() == getADimen(R.dimen.localstore_imgsize_bigcover_width)
//				&& bound.height() == getADimen(R.dimen.localstore_imgsize_bigcover_height)) {
//			return Color.RED;
//		}
//		if (bound.width() == getADimen(R.dimen.localstore_imgsize_mid_cover_width)
//				&& bound.height() == getADimen(R.dimen.localstore_imgsize_mid_cover_height)) {
//			return Color.BLUE;
//		}
//		if (bound.width() == getADimen(R.dimen.localstore_imgsize_smallcover_width)
//				&& bound.height() == getADimen(R.dimen.localstore_imgsize_smallcover_height)) {
//			return Color.YELLOW;
//		}
//		return ReaderApplication.getInstance().getResources().getColor(R.color.common_cover_bound_stroke_color);
//	}

//	private int getADimen(int resId) {
//		return ReaderApplication.getInstance().getResources().getDimensionPixelOffset(resId);
//	}
}

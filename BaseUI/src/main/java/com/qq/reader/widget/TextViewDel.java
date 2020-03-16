package com.qq.reader.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.qq.reader.baseui.R;
import com.qq.reader.common.utils.CommonUtility;

/**
 * 为了解决删除线颜色不能自定义问题
 * 
 * @author lvxinghe
 * 
 */
public class TextViewDel extends AppCompatTextView {

	boolean drawDelLine = false;

	public TextViewDel(Context context) {
		this(context,null);
	}

	public TextViewDel(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public TextViewDel(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.DrawDelLine);
		drawDelLine=array.getBoolean(R.styleable.DrawDelLine_delLine,false);
		array.recycle();
	}

	public void setDrawDelLine(boolean delLine){
		drawDelLine = delLine;
		invalidate();
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
			Paint paint = new Paint();
			paint.setColor(getCurrentTextColor());
			paint.setAntiAlias(true);
			paint.setStrokeWidth((float) CommonUtility.dip2px(1f));
			paint.setTextSize(getTextSize());
			String text = getText().toString();
			float width = paint.measureText(text);
			if (drawDelLine){
				canvas.drawLine(getPaddingLeft(), this.getHeight() / 2, width+getPaddingLeft(),
						this.getHeight() / 2, paint);
			}
	}
}

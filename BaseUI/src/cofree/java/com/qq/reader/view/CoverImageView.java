package com.qq.reader.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.qq.reader.baseui.R;
import com.tencent.mars.xlog.Log;

/**
 * Created by liuxiaoyang on 2018/7/17.
 */

public class CoverImageView extends AppCompatImageView {

    private float radius = 0;
    private boolean drawBorder = true;
    private int borderWidth = 1;       //描边的宽度
    private int borderColor = Color.parseColor("#c8c8c8");
    // 左上角是否为圆角
    private boolean corners_top_left = true;
    // 右上角是否为圆角
    private boolean corners_top_right = true;
    // 左下角是否为圆角
    private boolean corners_bottom_left = true;
    // 右下角是否为圆角
    private boolean corners_bottom_right = true;


    private final Paint restorePaint = new Paint();
    private final Paint maskXferPaint = new Paint();
    private final Paint canvasPaint = new Paint();
    private final Rect bounds = new Rect();
    private final RectF boundsf = new RectF();

    //边框
    private Paint borderPaint = new Paint();
    private RectF borderRectF = new RectF();
    private Path borderPath = new Path();

    public CoverImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CoverImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CoverImageView(Context context) {
        this(context,null);
    }

    private void init(AttributeSet attrs) {
        canvasPaint.setAntiAlias(true);
        canvasPaint.setColor(Color.argb(255, 255, 255, 255));
        restorePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        maskXferPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        //关闭硬件加速，否则父布局设置透明度<1时子View会直接隐藏
        setLayerType(View.LAYER_TYPE_HARDWARE, null);

        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CoverImageView);
            drawBorder = ta.getBoolean(R.styleable.CoverImageView_drawBorder, true);
            corners_top_left = ta.getBoolean(R.styleable.CoverImageView_top_left, true);
            corners_top_right = ta.getBoolean(R.styleable.CoverImageView_top_right, true);
            corners_bottom_left = ta.getBoolean(R.styleable.CoverImageView_bottom_left, true);
            corners_bottom_right = ta.getBoolean(R.styleable.CoverImageView_bottom_right, true);
            radius = ta.getDimension(R.styleable.CoverImageView_imageRadius, getResources().getDimension(R.dimen.radius_m));
            Log.d("CoverImageView", "init radius " + radius);
            ta.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.getClipBounds(bounds);
        boundsf.set(bounds);
        super.onDraw(canvas);
        canvas.saveLayer(boundsf, maskXferPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(boundsf, radius, radius, canvasPaint);
        if (!corners_top_left) {
            canvas.drawRect(0, 0, radius, radius, canvasPaint);
        }
        if (!corners_top_right) {
            canvas.drawRect(boundsf.right - radius, 0, boundsf.right, radius, canvasPaint);
        }
        if (!corners_bottom_left) {
            canvas.drawRect(0, boundsf.bottom - radius, radius, boundsf.bottom, canvasPaint);
        }
        if (!corners_bottom_right) {
            canvas.drawRect(boundsf.right - radius, boundsf.bottom - radius, boundsf.right, boundsf.bottom, canvasPaint);
        }
        canvas.restore();
        //优化滑动帧率 暂时注释掉描边，描边和绘制圆角在一起使用会影响 滑动流畅性
//        if (drawBorder) {
//            borderRectF.set(0, 0, getWidth() - borderWidth / 2, getHeight() - borderWidth / 2);
//            borderPath.addRoundRect(borderRectF, radius, radius, Path.Direction.CW);
//            borderPaint.setAntiAlias(true);
//            borderPaint.setStyle(Paint.Style.STROKE);
//            borderPaint.setStrokeWidth(borderWidth);
//            borderPaint.setColor(borderColor);
//            canvas.drawPath(borderPath, borderPaint);
//        }
    }

}

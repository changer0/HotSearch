package com.qq.reader.view;

/**
 * Created by dongxiaolong on 2016/7/2.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qq.reader.baseui.R;


public class CustomTailIconTextview extends FrameLayout {
    //单行显示
    private static final int SINGLE_LINE = 0x01;
    //多行显示
    private static final int MULTI_LINE = 0x02;
    //显示到下一行
    private static final int NEXT_LINE = 0x03;
    //单行超出宽度
    private static final int SINGLE_LINE_OUT_BOUND = 0x04;
    //显示样式
    private int type;
    //绘制文字最后一行的顶部坐标
    private int lastLineTop;
    //绘制文字最后一行的右边坐标
    private float lastLineRight;
    //绘制文字的行数
    private int maxlines;


    public CustomTailIconTextview(Context context) {
        super(context);
    }

    public CustomTailIconTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTailIconTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EmptyPage, 0, 0);
        try {
            int maxlines = a.getInt(R.styleable.CustomTailIconTextview_maxLine, -1);
            setMaxlines(maxlines);
        } finally {
            a.recycle();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int w = MeasureSpec.getSize(widthMeasureSpec);
        if (childCount == 2) {
            //测量子view的宽高
            measureChildren(widthMeasureSpec, heightMeasureSpec);

            TextView tv = null;
            if (getChildAt(0) instanceof TextView) {
                tv = (TextView) getChildAt(0);
                initTextParams(tv);
            } else {
                throw new RuntimeException("CustomLayout first child view not a TextView");
            }

            View sencodView = getChildAt(1);

            LayoutParams v1Lp = (LayoutParams) tv.getLayoutParams();
            LayoutParams v2Lp = (LayoutParams) sencodView.getLayoutParams();
            int v1horiMargin = v1Lp.leftMargin + v1Lp.rightMargin;
            int v1vertMargin = v1Lp.topMargin + v1Lp.bottomMargin;
            int v2horiMargin = v2Lp.leftMargin + v2Lp.rightMargin;

            if (sencodView.getVisibility() == View.GONE) {
                setMeasuredDimension(tv.getMeasuredWidth() + v1horiMargin, tv.getMeasuredHeight() + v1vertMargin);
                return;
            }

            //两个子view宽度相加小于该控件宽度的时候
            if (tv.getMeasuredWidth() + sencodView.getMeasuredWidth() + v1horiMargin + v2horiMargin <= w) {
                int width = tv.getMeasuredWidth() + sencodView.getMeasuredWidth() + v1horiMargin + v2horiMargin;
                //计算高度
                int height = Math.max(tv.getMeasuredHeight() + v1vertMargin, sencodView.getMeasuredHeight());
                //设置该viewgroup的宽高
                setMeasuredDimension(width, height);
                type = SINGLE_LINE;
                return;
            }
            if (getChildAt(0) instanceof TextView) {
                if (lastLineRight + sencodView.getMeasuredWidth() > w) {
                    if (tv.getLineCount() == 1 && getMaxlines() < 2) {
                        type = SINGLE_LINE;
                        setMeasuredDimension(w, tv.getMeasuredHeight());
                        final int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                                getPaddingLeft() + getPaddingRight(), w - sencodView.getMeasuredWidth() - v2horiMargin);
                        final int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                                getPaddingLeft() + getPaddingRight(), tv.getMeasuredHeight() + v1vertMargin);
                        tv.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                        initTextParams(tv);
                    } else {
                        //最后一行文字的宽度加上第二个view的宽度大于viewgroup宽度时第二个控件换行显示
                        setMeasuredDimension(w, tv.getMeasuredHeight() + sencodView.getMeasuredHeight() + v1vertMargin);
                        type = NEXT_LINE;
                    }
                } else {
                    int height = Math.max(tv.getMeasuredHeight() + v1vertMargin, lastLineTop + sencodView.getMeasuredHeight());
                    setMeasuredDimension(w, height);
                    type = MULTI_LINE;
                }
            }
        } else {
            throw new RuntimeException("CustomLayout child count must is 2");
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        TextView tv = (TextView) getChildAt(0);
        LayoutParams v1Lp = (LayoutParams) tv.getLayoutParams();
        View v1 = getChildAt(1);
        int v1LeftMargin = ((LayoutParams) v1.getLayoutParams()).leftMargin;
        tv.layout(v1Lp.leftMargin, v1Lp.topMargin, tv.getMeasuredWidth() + v1Lp.leftMargin, tv.getMeasuredHeight() + v1Lp.topMargin);
        if (v1.getVisibility() == GONE) {
            return;
        }
        if (type == SINGLE_LINE || type == MULTI_LINE || type == SINGLE_LINE_OUT_BOUND) {
            //设置第二个view在Textview文字末尾位置
            int left;
            if (type == SINGLE_LINE) {
                left = tv.getMeasuredWidth() + v1Lp.leftMargin + v1LeftMargin;
            } else {
                left = (int) lastLineRight + v1Lp.topMargin + v1LeftMargin;
            }
            int top = lastLineTop;
            //最后一行的高度 注:通过staticLayout得到的行高不准确故采用这种方式
            int lastLineHeight = tv.getBottom() - tv.getPaddingBottom() - lastLineTop;
            //当第二view高度小于单行文字高度时竖直居中显示
            if (v1.getMeasuredHeight() < lastLineHeight) {
                top = lastLineTop + v1Lp.topMargin + (lastLineHeight - v1.getMeasuredHeight()) / 2;
            }
            v1.layout(left, top, left + v1.getMeasuredWidth(), top + v1.getMeasuredHeight());
        } else if (type == NEXT_LINE) {
            //设置第二个view换行显示
            v1.layout(v1Lp.leftMargin + v1LeftMargin, tv.getMeasuredHeight() + v1Lp.topMargin, v1.getMeasuredWidth() + v1Lp.leftMargin + v1LeftMargin, tv.getMeasuredHeight() + v1.getMeasuredHeight() + v1Lp.topMargin);
        }
    }


    /**
     * 得到Textview绘制文字的基本信息
     */
    private void initTextParams(TextView tv) {
        Layout layout = tv.getLayout();
        if (layout == null) {
            return;
        }
        int lineCount = layout.getLineCount();
        lastLineTop = layout.getLineTop(lineCount - 1);
        lastLineRight = layout.getLineRight(lineCount - 1);
    }

    public int getMaxlines() {
        return maxlines;
    }

    public void setMaxlines(int maxlines) {
        this.maxlines = maxlines;
    }
}
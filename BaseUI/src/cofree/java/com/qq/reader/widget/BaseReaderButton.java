package com.qq.reader.widget;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by liuxiaoyang on 2018/6/14.
 * TODO 后续华为搞好了应该继承自 HwButton
 */

public class BaseReaderButton extends AppCompatTextView {
//    private static final String TAG = "Button";
/*    private float mTextSize;
    private float mMinSize;
    private float mSizeStep;
    private StaticLayout mStaticLayout;
    private TextPaint mTextPaint;*/

    public BaseReaderButton(Context context) {
        this(context, null);
    }

    public BaseReaderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseReaderButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //initialise(context, attrs, defStyleAttr);
    }


}

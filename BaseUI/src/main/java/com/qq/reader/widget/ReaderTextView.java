package com.qq.reader.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.qq.reader.baseui.R;
import com.qq.reader.common.utils.TypefaceUtils;

/**
 * Created by liuxiaoyang on 2018/6/8.
 * 为了适配厂商需求，对TextView做统一封装
 */

public class ReaderTextView extends BaseReaderTextView {

    private static final int QQ_FONT_SIYUANSONG = 0;
    private static final int QQ_FONT_DIN_BOLD = 1;

    public ReaderTextView(Context context) {
        super(context);
    }

    public ReaderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ReaderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet atts){
        TypedArray array=context.obtainStyledAttributes(atts, R.styleable.ReaderTextView);
        if(array != null) {
            int type = array.getInt(R.styleable.ReaderTextView_qq_font,-1);
            String fontName = "";
            switch (type) {
                case QQ_FONT_SIYUANSONG:
                    fontName = "siyuansong";
                break;
                case QQ_FONT_DIN_BOLD:
                    fontName = "din_bold";
                    break;
            }
            array.recycle();
            if(!TextUtils.isEmpty(fontName)){
                setTypeface(TypefaceUtils.getTypeFace(fontName,true));
            }
        }
    }
}

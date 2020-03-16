package com.qq.reader.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by liuxiaoyang on 2018/6/14.
 * 为了适配厂商需求，对Button做统一封装
 * !import 继承自TextView
 * 主要是华为的破需求……
 */

public class ReaderButton extends BaseReaderButton {

    public ReaderButton(Context context) {
        this(context, null);
    }

    public ReaderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReaderButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //initialise(context, attrs, defStyleAttr);
    }
}

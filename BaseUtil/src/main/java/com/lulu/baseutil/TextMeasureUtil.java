package com.lulu.baseutil;

import android.graphics.Paint;

/**
 * 文本测量工具
 */
public class TextMeasureUtil {
    public static int measureMaxTextSize(Paint paint, float maxwidth,
                                         String text) {
        float width = paint.measureText(text);
        if (width < maxwidth)
            return text.length();
        int end = text.length();
        for (; ; ) {
            if (end <= 0)
                return 0;
            end--;
            width = paint.measureText(text.subSequence(0, end).toString());
            if (width < maxwidth) {
                break;
            }
        }
        return end;
    }

    public static float measureTextWidth(Paint paint, String text) {
        return paint.measureText(text);
    }

    public static int measureTextHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }
}

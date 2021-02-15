package com.lulu.baseutil;

import android.graphics.Bitmap;
import android.graphics.Color;

public class ColorUtil {

    /**
     * 从 Bitmap 中获取颜色值
     * @param bm
     * @param ww
     * @param hh
     * @return
     */
    public static int getColorFromBitmap(Bitmap bm, int ww, int hh) {
        if (bm == null || bm.isRecycled()) {
            return 0;
        }

        ww = Math.min(bm.getWidth(), ww);
        hh = Math.min(bm.getHeight(), hh);

        long r = 0, g = 0, b = 0;
        for (int i = 0; i < ww; ++i) {
            for (int j = 0; j < hh; ++j) {
                int color = bm.getPixel((bm.getWidth() - ww) / 2 + i,
                        (bm.getHeight() - hh) / 2 + j);
                r += color & 0xFF0000;
                g += color & 0x00FF00;
                b += color & 0x0000FF;
            }
        }
        r /= ww * hh;
        g /= ww * hh;
        b /= ww * hh;
        r >>= 16;
        g >>= 8;
        return Color.rgb((int) (r & 0xFF), (int) (g & 0xFF), (int) (b & 0xFF));
    }
}

package com.lulu.baseutil;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;


public class BitmapUtil {


    /**
     * 高斯模糊转换
     * @param bm 待转换 bitmap
     * @param color 待转换 颜色
     * @param isBookCover 是否书封
     * @return 转换结果
     */
    public static Bitmap convertGaussianBlur(Bitmap bm, int color, boolean isBookCover) {
        return convertGaussianBlur(bm, color, isBookCover, 25);
    }

    /**
     * 高斯模糊转换
     * @param bm 待转换 bitmap
     * @param color 待转换 颜色
     * @return 转换结果
     */
    public static Bitmap convertGaussianBlur(Bitmap bm, int color) {
        return convertGaussianBlur(bm, color, true);
    }

    /**
     * 高斯模糊转换
     * @param bm 待转换 bitmap
     * @param color 待转换 颜色
     * @param isBookCover 是否书封
     * @param gaussRadius 半径
     * @return 转换结果
     */
    public static Bitmap convertGaussianBlur(Bitmap bm, int color, boolean isBookCover, int gaussRadius) {
        //防止OOM
        try {

            int left = 0;
            int top = bm.getHeight() / 5;
            int right = bm.getWidth();
            int bottom = bm.getHeight() * 3 / 5;

            if (!isBookCover) {
                //非书封设置最大高度，宽度
                int maxWidth = 200;
                int maxHeight = 200;
                left = 0;
                top = bm.getHeight() > maxHeight ? maxHeight / 5 : bm.getHeight() / 5;
                right = bm.getWidth() > maxWidth ? maxWidth : bm.getWidth();
                bottom = bm.getHeight() > maxHeight ? maxHeight * 3 / 5 : bm.getHeight() * 3 / 5;
            }

            Bitmap scaleDown = Bitmap.createBitmap(bm, left, top, right, bottom);

            Bitmap outputBitmap = doBlur(scaleDown, gaussRadius, true);

            if (color == -1) {
                return outputBitmap;
            }

            Canvas canvas = new Canvas(outputBitmap);
            Paint p = new Paint();
            p.setColor(color);

            canvas.drawRect(
                    new Rect(0, 0, outputBitmap.getWidth(), outputBitmap
                            .getHeight()), p);
            return outputBitmap;
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        } catch (OutOfMemoryError e) {
            Log.e("Err", e.getMessage());
        }

        return bm;
    }

    /**
     * BitMap高斯模糊处理
     * 原图缩放，增加虚化程度
     */
    public static Bitmap convertGaussianBlur(Bitmap bm, int color, int gaussRadius) {
        //防止OOM
        try {

            int left = 0;
            int top = 0;
            int right = bm.getWidth();
            int bottom = bm.getHeight();

            Bitmap scaleDown = Bitmap.createScaledBitmap(bm, bm.getWidth() / 5, bm.getHeight() / 5, false);

            Bitmap outputBitmap = doBlur(scaleDown, gaussRadius, true);

            if (color == -1) {
                return outputBitmap;
            }

            Canvas canvas = new Canvas(outputBitmap);
            Paint p = new Paint();
            p.setColor(color);

            canvas.drawRect(
                    new Rect(0, 0, outputBitmap.getWidth(), outputBitmap
                            .getHeight()), p);
            return outputBitmap;
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        } catch (OutOfMemoryError e) {
            Log.e("Err", e.getMessage());
        }

        return bm;
    }

    public static Bitmap doBlur(Bitmap sentBitmap, int radius,
                                boolean canReuseInBitmap) {
        Bitmap bitmap;
        if (canReuseInBitmap) {
            bitmap = sentBitmap;
        } else {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
                        | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

    /**
     * 给bitmap 换色
     * Utility.UIUtils.recolorNormalBitmap
     *
     * @param bitmap   mutable的bitmap
     * @param rgbColor rgb颜色
     */
    public static Bitmap recolorNormalBitmap(Bitmap bitmap, int rgbColor) {
        float b = ((rgbColor) & 0xFF);
        float g = ((rgbColor >> 8) & 0xFF);
        float r = ((rgbColor >> 16) & 0xFF);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        ColorMatrix matrix = new ColorMatrix();
        float[] arrays = {
                0, 0, 0, 0, r,
                0, 0, 0, 0, g,
                0, 0, 0, 0, b,
                0, 0, 0, 1, 0};
        matrix.set(arrays);
        paint.setColorFilter(new ColorMatrixColorFilter(matrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bitmap;
    }

    /**
     * Bitmap 缩放
     * @param src bitmap 来源
     * @param height 高度
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap src, int height) {
        if (src != null) {
            if (src.getHeight() == height) {
                return src;
            } else if (height > 0) {
                float sH = ((float) height) / ((float) src.getHeight());
                Matrix m = new Matrix();
                m.postScale(sH, sH);
                return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, true);
            }
        }
        return src;
    }
}

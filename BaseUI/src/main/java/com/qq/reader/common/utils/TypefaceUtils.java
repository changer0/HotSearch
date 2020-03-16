package com.qq.reader.common.utils;

import android.graphics.Typeface;

import com.qq.reader.common.utils.typeface.TypefaceCache;
import com.tencent.mars.xlog.Log;

import java.io.File;

/**
 * Created by qiaoshouqing on 2018/7/31.
 */

public class TypefaceUtils {

    public static final Typeface REGULAR = Typeface.create("sans-serif", Typeface.NORMAL);
    public static final Typeface MEDIUM = Typeface.create("sans-serif-medium", Typeface.NORMAL);
    public static final Typeface HW_MEDIUM = Typeface.create("HwChinese-medium", Typeface.NORMAL);

    public static Typeface getTypeFace(String fontId) {
        return getTypeFace(fontId, false);
    }


    public static Typeface getTypeFace(String fontId, boolean cache) {
        try {
            File destFontFile = getDestFontFile(fontId);
            if (destFontFile != null && destFontFile.exists()) {
                if (cache) {
                    return TypefaceCache.createFromFile(destFontFile.getPath());
                } else {
                    return Typeface.createFromFile(destFontFile);
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static File getDestFontFile(String fontId) {
        try {
            //内置字体，并且后台没有相关信息
            String path = getFontPath(fontId);
            File file = new File(path);
            if (file.exists()) {
                return file;
            }
        } catch (Exception e) {
            Log.printErrStackTrace("Utility", e, null, null);
            e.printStackTrace();
            // TODO: handle exception
        }
        return null;
    }


    public static String getFontPath(String name) {
        StringBuffer sb = new StringBuffer();
        sb.append(FontConstant.FONT_STYLE_PATH);
        sb.append(name);
        sb.append(FontConstant.FONT_FILE_SUFFIX);
        return sb.toString();
    }


}

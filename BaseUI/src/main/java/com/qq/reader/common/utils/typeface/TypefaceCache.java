package com.qq.reader.common.utils.typeface;

import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建第三方字体
 */

public class TypefaceCache {

    //使用频率较高的字体应该缓存下来
    private static Map<String, Typeface> typeArray = new HashMap<String, Typeface>();

    //思源宋体
    public static Typeface createFromFile(String fontPath) {
        Typeface typeface = typeArray.get(fontPath);
        if (typeface != null) {
            return typeface;
        }
        typeface = Typeface.createFromFile(fontPath);
        if (typeface != null) {
            typeArray.put(fontPath, typeface);
        }
        return typeface;
    }
}

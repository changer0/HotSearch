package com.lulu.baseutil;

import android.text.TextUtils;

import androidx.annotation.Nullable;

/**
 * 字符串处理工具类
 *
 * @author xujierui
 */
public final class StringUtil {
    /**
     * 每个线程分配一个StringBuilder
     */
    private static final ThreadLocal<StringBuilder> STRING_BUILDER = new ThreadLocal<StringBuilder>() {
        @Override
        protected StringBuilder initialValue() {
            return new StringBuilder();
        }
    };

    /**
     * 获取全局StringBuilder对象
     */
    public static StringBuilder getStringBuilder() {
        return getStringBuilder(null);
    }

    /**
     * 获取全局StringBuilder对象
     *
     * @param initStr 初始字符串
     */
    public static StringBuilder getStringBuilder(@Nullable final String initStr) {
        StringBuilder stringBuilder = STRING_BUILDER.get();
        if (stringBuilder == null) {
            if (initStr == null || initStr.length() == 0) {
                stringBuilder = new StringBuilder();
            } else {
                stringBuilder = new StringBuilder(initStr);
            }
            STRING_BUILDER.set(stringBuilder);
        } else {
            stringBuilder.setLength(0);
            if (!TextUtils.isEmpty(initStr)) {
                stringBuilder.append(initStr);
            }
        }
        return stringBuilder;
    }

    /**
     * 拼接字符串
     *
     * @param strPartArray 字符串碎片数组
     * @return 拼接好的字符串
     */
    public static String spliceStrings(@Nullable final String... strPartArray) {
        if (strPartArray == null) {
            return null;
        }

        final StringBuilder strBuilder = getStringBuilder();
        for (String singleStrPart : strPartArray) {
            if (TextUtils.isEmpty(singleStrPart)) {
                continue;
            }

            strBuilder.append(singleStrPart);
        }

        return strBuilder.toString();
    }

    /**
     * 比较aStr和bStr是否严格相同
     * aStr和bStr都不为空或空字符串并且内容相同
     *
     * @param aStr a字符串
     * @param bStr b字符串
     * @return 是否严格相同
     */
    public static boolean strictEquals(final String aStr, final String bStr) {
        return !TextUtils.isEmpty(aStr) && !TextUtils.isEmpty(bStr) && aStr.equals(bStr);
    }

    /**
     * 比较aStr和bStr是否相同
     * 1.aStr和bStr都为空或者空字符串
     * 2.aStr和bStr内容相同
     *
     * @param aStr a字符串
     * @param bStr b字符串
     * @return 是否相同
     */
    public static boolean equals(final String aStr, final String bStr) {
        if (aStr == null && bStr == null) {
            return true;
        } else if (aStr != null && bStr != null) {
            return aStr.equals(bStr);
        }
        return false;
    }
}

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

//    public static final class StringBuilderWrapper {
//        private final StringBuilder stringBuilder;
//
//        public StringBuilderWrapper(final StringBuilder stringBuilder) {
//            this.stringBuilder = stringBuilder;
//        }
//
//        public StringBuilderWrapper append(Object obj) {
//            return append(String.valueOf(obj));
//        }
//
//        public StringBuilderWrapper append(String str) {
//            stringBuilder.append(str);
//            return this;
//        }
//
//        public StringBuilderWrapper append(StringBuffer sb) {
//            stringBuilder.append(sb);
//            return this;
//        }
//
//        public StringBuilderWrapper append(CharSequence s) {
//            stringBuilder.append(s);
//            return this;
//        }
//
//        public StringBuilderWrapper append(CharSequence s, int start, int end) {
//            stringBuilder.append(s, start, end);
//            return this;
//        }
//
//        public StringBuilderWrapper append(char[] str) {
//            stringBuilder.append(str);
//            return this;
//        }
//
//        public StringBuilderWrapper append(char[] str, int offset, int len) {
//            stringBuilder.append(str, offset, len);
//            return this;
//        }
//
//        public StringBuilderWrapper append(boolean b) {
//            stringBuilder.append(b);
//            return this;
//        }
//
//        public StringBuilderWrapper append(char c) {
//            stringBuilder.append(c);
//            return this;
//        }
//
//        public StringBuilderWrapper append(int i) {
//            stringBuilder.append(i);
//            return this;
//        }
//
//        public StringBuilderWrapper append(long lng) {
//            stringBuilder.append(lng);
//            return this;
//        }
//
//        public StringBuilderWrapper append(float f) {
//            stringBuilder.append(f);
//            return this;
//        }
//
//        public StringBuilderWrapper append(double d) {
//            stringBuilder.append(d);
//            return this;
//        }
//
//        public StringBuilderWrapper appendCodePoint(int codePoint) {
//            stringBuilder.appendCodePoint(codePoint);
//            return this;
//        }
//
//        public StringBuilderWrapper delete(int start, int end) {
//            stringBuilder.delete(start, end);
//            return this;
//        }
//
//        public StringBuilderWrapper deleteCharAt(int index) {
//            stringBuilder.deleteCharAt(index);
//            return this;
//        }
//
//        public StringBuilderWrapper replace(int start, int end, String str) {
//            stringBuilder.replace(start, end, str);
//            return this;
//        }
//
//        public StringBuilderWrapper insert(int index, char[] str, int offset,
//                                           int len) {
//            stringBuilder.insert(index, str, offset, len);
//            return this;
//        }
//
//        public StringBuilderWrapper insert(int offset, Object obj) {
//            stringBuilder.insert(offset, obj);
//            return this;
//        }
//
//        public StringBuilderWrapper insert(int offset, String str) {
//            stringBuilder.insert(offset, str);
//            return this;
//        }
//
//        public StringBuilderWrapper insert(int offset, char[] str) {
//            stringBuilder.insert(offset, str);
//            return this;
//        }
//
//        public StringBuilderWrapper insert(int dstOffset, CharSequence s) {
//            stringBuilder.insert(dstOffset, s);
//            return this;
//        }
//
//        public StringBuilderWrapper insert(int dstOffset, CharSequence s,
//                                           int start, int end) {
//            stringBuilder.insert(dstOffset, s, start, end);
//            return this;
//        }
//
//        public StringBuilderWrapper insert(int offset, boolean b) {
//            stringBuilder.insert(offset, b);
//            return this;
//        }
//
//        public StringBuilderWrapper insert(int offset, char c) {
//            stringBuilder.insert(offset, c);
//            return this;
//        }
//
//        public StringBuilderWrapper insert(int offset, int i) {
//            stringBuilder.insert(offset, i);
//            return this;
//        }
//
//        public StringBuilderWrapper insert(int offset, long l) {
//            stringBuilder.insert(offset, l);
//            return this;
//        }
//
//        public StringBuilderWrapper insert(int offset, float f) {
//            stringBuilder.insert(offset, f);
//            return this;
//        }
//
//        public StringBuilderWrapper insert(int offset, double d) {
//            stringBuilder.insert(offset, d);
//            return this;
//        }
//
//        public int indexOf(String str) {
//            return stringBuilder.indexOf(str);
//        }
//
//        public int indexOf(String str, int fromIndex) {
//            return stringBuilder.indexOf(str, fromIndex);
//        }
//
//        public int lastIndexOf(String str) {
//            return stringBuilder.lastIndexOf(str);
//        }
//
//        public int lastIndexOf(String str, int fromIndex) {
//            return stringBuilder.lastIndexOf(str, fromIndex);
//        }
//
//        public StringBuilderWrapper reverse() {
//            stringBuilder.reverse();
//            return this;
//        }
//
//        @Override
//        public String toString() {
//            final String result = stringBuilder.toString();
//            STRING_BUILDER.remove();
//            return result;
//        }
//    }
}

package com.qq.reader.utils;

import androidx.annotation.StringRes;

import com.qq.reader.common.utils.CommonUtility;
import com.qq.reader.core.BaseApplication;

/**
 * Created by hexiaole on 2020/1/20.
 */
public class Utility extends CommonUtility {
    /**
     * 字符串提取
     *
     * @param resId R.string.xxx
     * @return 字符串
     */

    public static String getStringById(@StringRes int resId) {
        return BaseApplication.Companion.getINSTANCE().getResources()
                .getString(resId);
    }
}

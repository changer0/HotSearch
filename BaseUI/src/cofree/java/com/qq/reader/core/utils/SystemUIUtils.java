package com.qq.reader.core.utils;

import android.content.Context;

import com.qq.reader.common.utils.AbsSystemUIUtils;

/**
 * Created by qiaoshouqing on 2018/6/5.
 *
 * 华为项目特有的方法。
 */

public class SystemUIUtils extends AbsSystemUIUtils {

    public static int getEmuiSdkInt() {
        return Utils.getEMUISDKINT();
    }

    /**
     * 去网络设置页面
     */
    public static void gotoNetSetting(Context context) {
        Utils.openWifiOrDataStrings(context);
    }

}

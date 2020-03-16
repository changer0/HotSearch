package com.qq.reader.appconfig;

import android.content.Context;

import com.qq.reader.core.utils.BaseConfig;

/**
 * Cofree分支独有
 *
 * @author p_bbinliu
 * @date 2019/3/4
 */
public class BaseUIConfig extends BaseConfig {

    private static String CONFIG_NAME = "SETTING";

    private static String getConfigFileName() {
        return CONFIG_NAME;
    }

    public static void setLastPullRefreshTime(Context c, String key, long time) {
        putLong(getConfigFileName(),key, time);
    }

    public static long getLastPullRefreshTime(Context c, String key) {
        return getLong(getConfigFileName(),key, 0);
    }

    /**
     * 福利tab展示红点标记
     * @param show
     */
    public static void setShouldShowMyTabReddot(boolean show) {
        putBoolean(getConfigFileName(), "WebTabReddot", show);
    }

    public static boolean getShouldShowMyTabReddot() {
        return getBoolean(getConfigFileName(), "WebTabReddot", true);
    }
}

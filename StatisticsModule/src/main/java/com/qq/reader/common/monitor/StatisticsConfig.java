package com.qq.reader.common.monitor;

import android.content.Context;

import com.qq.reader.common.utils.ServerUrl;
import com.qq.reader.core.utils.BaseConfig;
import com.qq.reader.statistics.BuildConfig;
import com.tencent.mars.xlog.Log;

/**
 * Created by liuxiaoyang on 2017/5/17.
 */

public class StatisticsConfig extends BaseConfig {

    // 默认是否弃用LOG，debug模式
    public static final boolean DEFAULTENABLEDEBUG = BuildConfig.DEBUG;
    public static final boolean DEFAULTENABLELOG = BuildConfig.DEBUG;

    public static final int TYPE_BEACON = 1;
    public static final int TYPE_READER = 2;
    public static final int TYPE_QM = 3;
    final static String PARA_SAFEKEY = "safekey";

    // 外部传入 默认使用BuildConfig
    boolean isDebug = false;
    boolean enableLog = false;
    // 非主进程灯塔不上报，自有未实现
    boolean isUploadProcess = true;
    int[] supporter = {TYPE_BEACON, TYPE_READER, TYPE_QM};
    int[] supporter2 = {TYPE_BEACON, TYPE_READER};

    // import com.qq.reader.appconfig.ServerUrl;
    static String DEBUG_SERVER_URL = "";
    static String SERVER_URL = ServerUrl.DOMAINNAME_STATISTIC;

    private StatisticsConfig() {
    }

    public static class Builder {
        private boolean isDebug = DEFAULTENABLEDEBUG; // BuildConfig.DEBUG;
        private boolean enableLog = DEFAULTENABLELOG;// BuildConfig.DEBUG;
        private boolean isUploadProcess = true;
        private int[] supporterTypes = {TYPE_BEACON, TYPE_READER, TYPE_QM};

        private String DEBUG_SERVER_URL = ServerUrl.DOMAINNAME_STATISTIC;
        private String SERVER_URL = ServerUrl.DOMAINNAME_STATISTIC;

        public Builder setIsDebug(boolean isDebug) {
            //StatisticsConfig config = new StatisticsConfig();
            this.isDebug = isDebug;
            return this;
        }

        public Builder setEnableLog(boolean enableLog) {
            this.enableLog = enableLog;
            return this;
        }

        public Builder setSupporterTypes(int[] types) {
            this.supporterTypes = types;
            return this;
        }

        public Builder setIsUploadProcess(boolean isUploadProcess) {
            this.isUploadProcess = isUploadProcess;
            return this;
        }

        public Builder setServerUrl(String serverUrl) {
            this.SERVER_URL = serverUrl;
            return this;
        }

        public Builder setDebugServerUrl(String debugServerUrl) {
            this.DEBUG_SERVER_URL = debugServerUrl;
            return this;
        }

        public StatisticsConfig build() {
            StatisticsConfig config = new StatisticsConfig();
            config.isDebug = isDebug;
            config.enableLog = enableLog;
            config.isUploadProcess = isUploadProcess;
            config.supporter = supporterTypes;
            config.DEBUG_SERVER_URL = DEBUG_SERVER_URL;
            config.SERVER_URL = SERVER_URL;
            return config;
        }
    }

    public String getServerUrl() {
        String ret = "";
        if (isDebug) {
            ret = DEBUG_SERVER_URL;
        } else {
            ret = SERVER_URL;
        }
        Log.d("StatisticsConfig", "getServerUrl = " + ret);
        return ret;
    }

    private static String getConfigFileName() {
        return "STATISICS_CONFIG";
    }

    private final static String STATISICDELAY = "STATISICDELAY";

    static long getStatisicDelay() {
        return getLong(getConfigFileName(), STATISICDELAY, StatisticsManager.INVALlL_TIME);
    }

    static void setStatisicDelay(long delay) {
        putLong(getConfigFileName(), STATISICDELAY, delay);
    }

    public static void setTiem(Context c, String value) {
        putString("rdm_data", "time", value);
    }

    public static String getTime(Context c) {
        return getString("rdm_data", "time", "");
    }

}

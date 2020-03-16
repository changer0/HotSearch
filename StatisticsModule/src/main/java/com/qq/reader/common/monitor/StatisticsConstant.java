package com.qq.reader.common.monitor;

import com.qq.reader.core.config.AppConstant;

/**
 * @author zhanglulu on 2019/9/20.
 * for
 */
public class StatisticsConstant {
    //上报启动
    public static boolean hasReportStart = false;

    public static final String STAT_PARAM_DB = AppConstant.ROOT_PATH + "statparam.db";// 上报参数持久化数据库

    public static final int STAT_LEVEL_HIGH = 0;
    public static final int STAT_LEVEL_NORMAL = 1;
    public static final int STAT_LEVEL_LOW = 2;
    public static final int STAT_LEVEL_HISTRYY = 3;
}

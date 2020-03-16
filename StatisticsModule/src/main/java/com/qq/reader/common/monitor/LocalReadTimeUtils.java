package com.qq.reader.common.monitor;

import com.tencent.mars.xlog.Log;

import java.util.List;

/**
 * 获取用户阅读时长工具类，当前该方法依赖自有统计，如果有需要未来可能会抽离，业务相关
 * Created by liuxiaoyang on 2017/5/23.
 */

public class LocalReadTimeUtils {

    /**
     * 获取本地日志中的阅读时长（未上报到服务端的）
     * 单位毫秒
     *
     * @return
     */
    public static long getLocalReadTime() {
        long localtime = 0;
        try {
            List<Node> localtimeList = ReadTimeUploadManager.Companion.getINSTANCE().getStatistics(101);
            int size = localtimeList.size();
            Node tmpNode;

            for (int i = 0; i < size; i++) {
                tmpNode = localtimeList.get(i);
                try {
                    localtime += tmpNode.other.optLong("readTime", 0);
                } catch (Exception e) {
                    Log.e("LocalReadTimeUtils", e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localtime;
    }

    /**
     * 保存当前阅读时长
     *
     * @param readTime 阅读时长，增量
     */
    public static void setLocalReadTime(long readTime, String statParam) {
        setLocalReadTime(readTime, 0, statParam);
    }

    /**
     * 保存当前阅读时长
     *
     * @param readTime 阅读时长，增量
     */
    public static void setLocalReadTime(long readTime, long bid, String statParam) {
        Node node = new Node();
        node.setKV("readTime", readTime)
                .setKV("bid", bid)
                .setStatParamString(statParam)
                .setType(101);
        ReadTimeUploadManager.Companion.getINSTANCE()
                .commit(node, StatisticsConstant.STAT_LEVEL_HIGH);
    }
}

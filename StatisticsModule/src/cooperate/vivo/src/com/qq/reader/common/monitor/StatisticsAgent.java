package com.qq.reader.common.monitor;

import android.content.Context;

/**
 * Created by liuxiaoyang on 2017/5/19.
 * <p>
 * 合作统计调用，继承StatisticsAgentImpl；如果需要可以Override方法插入白牌手机需要的代码
 * 例如init中可以初始化白牌特定的统计SDK
 */
public class StatisticsAgent extends StatisticsAgentImpl {

    static StatisticsAgent instance;

    public synchronized static StatisticsAgent getInstance() {
        if (instance == null) {
            instance = new StatisticsAgent();
        }
        return instance;
    }

    @Override
    public void init(Application application) {
        init(application, null);
    }

    @Override
    public void init(Application application, StatisticsConfig config) {
        super.init(application, config);
        //TODO VIVO统计
    }

}

package com.yuewen.reader.zebra.loader;

import com.yuewen.reader.zebra.Zebra;
import com.yuewen.reader.zebra.ZebraConfig;
import com.yuewen.reader.zebra.cache.CacheController;
import com.yuewen.reader.zebra.cache.core.IoUtils;
import com.yuewen.reader.zebra.log.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhanglulu on 2019/3/2.
 * for 网络中拉取数据
 */
@SuppressWarnings("rawtypes")
public class LoadNetDataTask implements Runnable {
    private static final String TAG = "LoadNetDataTask";
    private final Zebra zebra;
    private LoadDataListener loadDataListener;

    public LoadNetDataTask(Zebra zebra) {
        this.zebra = zebra;
    }

    @Override
    public void run() {

        InputStream resultStream = null;
        try {
            resultStream = ZebraConfig.getNetQuestAdapter().syncRequest(zebra.getNetQuestParams());
            Logger.i("url", zebra.getNetQuestParams().getUrl());
            String str = IoUtils.getString(resultStream);
            zebra.parseData(str);
            zebra.buildViewBindItem();
            //数据填充结束, 通知页面刷新
            if (loadDataListener != null) {
                loadDataListener.onLoadNetDataSuccess(zebra);
            }
            CacheController.getInstance().save(zebra.getRequestKey(), str);
        } catch (Exception e) {
            if (loadDataListener != null) {
                loadDataListener.onLoadNetDataFailed(e);
            }
            Logger.e(TAG, "LoadNativePageDataTask");
        } finally {
            if (resultStream != null) {
                try {
                    resultStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setLoadDataListener(LoadDataListener loadDataListener) {
        this.loadDataListener = loadDataListener;
    }

    /**
     * 数据加载回调
     */
    public interface LoadDataListener {
        void onLoadNetDataSuccess(Zebra provider);
        void onLoadNetDataFailed(Throwable throwable);
    }
}

package com.qq.reader.provider.task;

import android.text.TextUtils;

import com.qq.reader.provider.BaseDataProvider;
import com.qq.reader.provider.DataProviderConfig;
import com.qq.reader.provider.cache.core.IoUtils;
import com.qq.reader.provider.loader.DataProviderLoader;
import com.qq.reader.provider.log.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhanglulu on 2019/3/2.
 * for 网络中拉取数据
 */
public class LoadNetDataTask implements Runnable {
    private static final String TAG = "LoadNetDataTask";
    private BaseDataProvider mDataProvider;
    private LoadDataListener loadDataListener;

    public LoadNetDataTask(BaseDataProvider dataProvider) {
        mDataProvider = dataProvider;
    }

    @Override
    public void run() {

        InputStream resultStream = null;
        try {
            resultStream = DataProviderConfig.getNetQuestAdapter().syncRequest(mDataProvider);
            String str = IoUtils.getString(resultStream);
            mDataProvider.parseData(str);
            mDataProvider.fillData();
            //数据填充结束, 通知页面刷新
            if (loadDataListener != null) {
                loadDataListener.onLoadNetDataSuccess(mDataProvider);
            }
            //resultStream 已经读完了，变成 ByteArrayOutputStream 存入缓存
            ByteArrayOutputStream baos = null;
            ByteArrayInputStream bais = null;
            try {
                baos = new ByteArrayOutputStream();
                if (!TextUtils.isEmpty(str)) {
                    baos.write(str.getBytes("UTF-8"));
                }
                bais = new ByteArrayInputStream(baos.toByteArray());
                DataProviderLoader.getInstance().save(mDataProvider.getCacheKey(), bais);
            } finally {
                if (bais != null) {
                    bais.close();
                }
                if (baos != null) {
                    baos.close();
                }
            }
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
        void onLoadNetDataSuccess(BaseDataProvider provider);
        void onLoadNetDataFailed(Throwable throwable);
    }
}

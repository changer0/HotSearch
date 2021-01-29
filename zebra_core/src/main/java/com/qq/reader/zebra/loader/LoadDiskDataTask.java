package com.qq.reader.zebra.loader;

import com.qq.reader.zebra.Zebra;
import com.qq.reader.zebra.cache.CacheController;
import com.qq.reader.zebra.cache.core.IoUtils;
import com.qq.reader.zebra.log.Logger;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author zhanglulu on 2019/3/2.
 * for 从磁盘中获取数据
 */
@SuppressWarnings("rawtypes")
public class LoadDiskDataTask implements Runnable {
    private static final String TAG = "LoadDiskDataTask";

    private final Zebra mZebra;
    private final InputStream inputStream;
    private LoadDataListener mLoadListener;
    private LoadExpiredDataListener mLoadExpiredListener;
    private boolean isLoadExpired = false;

    /**
     * @param isLoadExpired true 为加载过期文件
     */
    public LoadDiskDataTask(Zebra mZebra, InputStream inputStream, boolean isLoadExpired) {
        this.mZebra = mZebra;
        this.inputStream = inputStream;
        this.isLoadExpired = isLoadExpired;
    }

    @Override
    public void run() {
        InputStream is = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            is = new BufferedInputStream(inputStream);
            IoUtils.copyStream(is, baos, null);
            String jsonStr = new String(baos.toByteArray(), StandardCharsets.UTF_8);
            mZebra.parseData(jsonStr);
            if (mZebra.isExpired() && !isLoadExpired) {
                if (mLoadListener != null) {
                    mLoadListener.onLoadDiskDataFailed(mZebra);
                }
            } else {
                //只有加载成功后才会填充数据
                mZebra.buildViewBindItem();
                if (isLoadExpired) {
                    //回调过期数据
                    if (mLoadExpiredListener != null) {
                        mLoadExpiredListener.onLoadDiskExpiredDataSuccess(mZebra);
                    }
                } else {
                    //回调未过期数据
                    if (mLoadListener != null) {
                        mLoadListener.onLoadDiskDataSuccess(mZebra);
                    }
                }
            }
        } catch (Exception e) {
            if (isLoadExpired) {
                if (mLoadExpiredListener != null) {
                    mLoadExpiredListener.onLoadDiskExpiredDataFailed(mZebra);
                }
            } else {
                if (null != mLoadListener) {
                    mLoadListener.onLoadDiskDataFailed(mZebra);
                }
            }
            //此时说明可能保存了错误的缓存数据及时删除
            CacheController.getInstance().remove(mZebra.getRequestKey());
            Logger.e(TAG, "LoadDiskPageDataTask");
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
                //不要忘记关掉快照
                inputStream.close();
            } catch (Exception e) {
                Logger.e(TAG, "LoadDiskPageDataTask");
                e.printStackTrace();
            }
        }
    }

    public void setLoadDataListener(LoadDataListener mLoadListener) {
        this.mLoadListener = mLoadListener;
    }

    /**
     * 过期数据回调
     * @param loadDiskExpiredDataListener
     */
    public void setLoadExpiredDataListener(LoadExpiredDataListener loadDiskExpiredDataListener) {
        this.mLoadExpiredListener = loadDiskExpiredDataListener;
    }

    /**
     * 数据加载回调
     */
    public interface LoadDataListener {
        void onLoadDiskDataSuccess(Zebra provider);
        void onLoadDiskDataFailed(Zebra provider);
    }

    /**
     * 过期数据加载回调
     */
    public interface LoadExpiredDataListener {
        void onLoadDiskExpiredDataSuccess(Zebra provider);
        void onLoadDiskExpiredDataFailed(Zebra provider);
    }


}

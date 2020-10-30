package com.qq.reader.provider.task;

import com.qq.reader.provider.BaseDataProvider;
import com.qq.reader.provider.cache.core.DiskLruCache;
import com.qq.reader.provider.cache.core.IoUtils;
import com.qq.reader.provider.log.Logger;
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

    private BaseDataProvider mDataProvider;
    private DiskLruCache.Snapshot mCacheSnapshot;
    private LoadDataListener mLoadListener;
    private LoadExpiredDataListener mLoadExpiredListener;
    private boolean isLoadExpired = false;

    /**
     * @param isLoadExpired true 为加载过期文件
     */
    public LoadDiskDataTask(BaseDataProvider mDataProvider, DiskLruCache.Snapshot snapshot, boolean isLoadExpired) {
        this.mDataProvider = mDataProvider;
        this.mCacheSnapshot = snapshot;
        this.isLoadExpired = isLoadExpired;
    }

    @Override
    public void run() {
        InputStream is = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            is = new BufferedInputStream(mCacheSnapshot.getInputStream(0));
            IoUtils.copyStream(is, baos, null);
            String jsonStr = new String(baos.toByteArray(), StandardCharsets.UTF_8);
            mDataProvider.parseData(jsonStr);
            if (mDataProvider.isExpired() && !isLoadExpired) {
                if (mLoadListener != null) {
                    mLoadListener.onLoadDiskDataFailed(mDataProvider);
                }
            } else {
                //只有加载成功后才会填充数据
                mDataProvider.fillData();
                if (isLoadExpired) {
                    //回调过期数据
                    if (mLoadExpiredListener != null) {
                        mLoadExpiredListener.onLoadDiskExpiredDataSuccess(mDataProvider);
                    }
                } else {
                    //回调未过期数据
                    if (mLoadListener != null) {
                        mLoadListener.onLoadDiskDataSuccess(mDataProvider);
                    }
                }
            }
        } catch (Exception e) {
            if (isLoadExpired) {
                if (mLoadExpiredListener != null) {
                    mLoadExpiredListener.onLoadDiskExpiredDataFailed(mDataProvider);
                }
            } else {
                if (null != mLoadListener) {
                    mLoadListener.onLoadDiskDataFailed(mDataProvider);
                }
            }
            Logger.e(TAG, "LoadDiskPageDataTask");
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
                //不要忘记关掉快照
                mCacheSnapshot.close();
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
        void onLoadDiskDataSuccess(BaseDataProvider provider);
        void onLoadDiskDataFailed(BaseDataProvider provider);
    }

    /**
     * 过期数据加载回调
     */
    public interface LoadExpiredDataListener {
        void onLoadDiskExpiredDataSuccess(BaseDataProvider provider);
        void onLoadDiskExpiredDataFailed(BaseDataProvider provider);
    }


}

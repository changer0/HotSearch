package com.yuewen.reader.zebra.loader;

import com.yuewen.reader.zebra.Zebra;
import com.yuewen.reader.zebra.cache.CacheController;
import com.yuewen.reader.zebra.log.Logger;

/**
 * @author zhanglulu on 2019/3/2.
 * for 从磁盘中获取数据
 */
@SuppressWarnings("rawtypes")
public class LoadDiskDataTask implements Runnable {
    private static final String TAG = "LoadDiskDataTask";

    private final Zebra zebra;
    private LoadDataListener mLoadListener;
    private LoadExpiredDataListener mLoadExpiredListener;
    private boolean isLoadExpired;

    /**
     * @param isLoadExpired true 为加载过期文件
     */
    public LoadDiskDataTask(Zebra zebra, boolean isLoadExpired) {
        this.zebra = zebra;
        this.isLoadExpired = isLoadExpired;
    }

    @Override
    public void run() {
        try {
            zebra.parseData(CacheController.getInstance().getString(zebra.getRequestKey()));
            if (zebra.isExpired() && !isLoadExpired) {
                if (mLoadListener != null) {
                    mLoadListener.onLoadDiskDataFailed(zebra);
                }
            } else {
                //只有加载成功后才会填充数据
                zebra.buildViewBindItem();
                if (isLoadExpired) {
                    //回调过期数据
                    if (mLoadExpiredListener != null) {
                        mLoadExpiredListener.onLoadDiskExpiredDataSuccess(zebra);
                    }
                } else {
                    //回调未过期数据
                    if (mLoadListener != null) {
                        mLoadListener.onLoadDiskDataSuccess(zebra);
                    }
                }
            }
        } catch (Exception e) {
            if (isLoadExpired) {
                if (mLoadExpiredListener != null) {
                    mLoadExpiredListener.onLoadDiskExpiredDataFailed(zebra);
                }
            } else {
                if (null != mLoadListener) {
                    mLoadListener.onLoadDiskDataFailed(zebra);
                }
            }
            //此时说明可能保存了错误的缓存数据及时删除
            CacheController.getInstance().remove(zebra.getRequestKey());
            Logger.e(TAG, "LoadDiskPageDataTask");
            e.printStackTrace();
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

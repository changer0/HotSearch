package com.qq.reader.common.monitor;

import android.text.TextUtils;

import com.qq.reader.common.monitor.net.UploadStatiticsTask;
import com.qq.reader.core.readertask.ReaderTaskHandler;
import com.tencent.mars.xlog.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * *
 *
 * @author haleyzheng
 */
public class StatisticsManager extends BaseStatisticsManager{

    private static final String TAG = "StatisticsManager";

    private static StatisticsManager mInstance = null;

    // 全加载了就不从文件捞了 这里默认map文件只有我们自己在存，且新来数据的同时都在内存里
    // 即本地文件全部加载出来后不会再有新的数据产生在文件里（都会先在内存里）
    private boolean isHighLevelHistoryAllLoaded = false;
    private boolean isHistoryAllLoaded = false;

    public synchronized static StatisticsManager getInstance() {

        if (null == mInstance) {
            mInstance = new StatisticsManager();
            Log.d(TAG, " getInstance");
        }
        return mInstance;
    }

    private StatisticsManager() {
        super();
    }

    // 懒加载本地数据 防止OOM 当然如果用户一直不联网，单次上报超级多也没办法，暂时不考虑
    LocalHashMap.OnLoadMoreListener onLoadMoreListener = new LocalHashMap.OnLoadMoreListener() {
        @Override
        public void onLoad(int type, int size) {
            Log.d(TAG, "onLoad " + size + " type " + type);
            if (size > 0) {
                mHandler.removeMessages(MESSAGE_SEND);
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SEND));
            } else {
                if (type == LocalHashMap.STAT_LEVEL_HIGH) {
                    isHighLevelHistoryAllLoaded = true;
                    Log.d(TAG, "onLoad " + size + " isHighLevelHistoryAllLoaded " + isHighLevelHistoryAllLoaded);
                } else if (type == LocalHashMap.STAT_LEVEL_HISTORY) {
                    isHistoryAllLoaded = true;
                }
            }
        }
    };

    /**
     * 处理数据，往内存list写数据
     */
    public void commit(Node node) {
        commit(node, false);
    }

    @Override
    protected void addData2Map(int level, String key, ArrayList<Node> data){
        // 高优先级 中优先级直接扔到map里 普通优先级的放到
        switch (level) {
            case StatisticsConstant.STAT_LEVEL_HIGH:
                if (data.size() > 0) {
                    mHighLevelCacheMap.put(key, data.clone());
                }
                break;
            case StatisticsConstant.STAT_LEVEL_NORMAL:
                if (data.size() > 0) {
                    mNormalLevelCacheMap.put(key, data.clone());
                }
                break;
            case StatisticsConstant.STAT_LEVEL_LOW:
                mCurrStatisticsList.addAll(data);
                break;
        }
    }

    protected int dealSendData(int sendTaskSize){

        Log.d(TAG, "sendOrWriteFile HIGH ");
        sendTaskSize = sendByLevel(mHighLevelCacheMap, sendTaskSize, mUploadTastSizeEachTime);
        Log.d(TAG, "mSendTaskSize " + sendTaskSize +
                " isHighLevelHistoryAllLoaded " + isHighLevelHistoryAllLoaded);
        if (sendTaskSize <= 0 && !isHighLevelHistoryAllLoaded) {
            // 默认只有历史缓存中存在更多数据 感觉要用回调啊
            Log.d(TAG, "sendOrWriteFile HIGH tryLoadMore");
            mHighLevelCacheMap.tryLoadMore(onLoadMoreListener);
        }
        if (sendTaskSize < mUploadTastSizeEachTime) {
            Log.d(TAG, "sendOrWriteFile NORMAL ");
            sendTaskSize = sendByLevel(mNormalLevelCacheMap, sendTaskSize, mUploadTastSizeEachTime);
        }
        if (sendTaskSize < mUploadTastSizeEachTime) {
            Log.d(TAG, "sendOrWriteFile LOW ");
            sendTaskSize = sendByLevel(mLowLevelCacheMap, sendTaskSize, mUploadTastSizeEachTime);
        }
        if (sendTaskSize < mUploadTastSizeEachTime) {
            Log.d(TAG, "sendOrWriteFile HISTORY ");
            sendTaskSize = sendByLevel(mHistoryCacheMap, sendTaskSize, mUploadTastSizeEachTime);
        }
        // 发现没历史数据了尝试从本地拉
        if (sendTaskSize <= 0 && !isHistoryAllLoaded) {
            // 默认只有历史缓存中存在更多数据 感觉要用回调啊
            mHistoryCacheMap.tryLoadMore(onLoadMoreListener);
        }

        return sendTaskSize;
    }

    protected void removeUploadDataFromMap(String key) {
        Log.d(TAG, "MESSAGE_UPLOAD_SUCCESS " + key);
        if (!TextUtils.isEmpty(key)) {
            try {
                int level = Integer.valueOf("" + key.charAt(0));
                switch (level) {
                    case StatisticsConstant.STAT_LEVEL_HIGH:
                        mHighLevelCacheMap.remove(key);
                        break;
                    case StatisticsConstant.STAT_LEVEL_NORMAL:
                        // 可能在histroy中  两个都尝试移除
                        mNormalLevelCacheMap.remove(key);
                        mHistoryCacheMap.remove(key);
                        break;
                    case StatisticsConstant.STAT_LEVEL_LOW:
                        // 可能在histroy中 两个都尝试移除
                        mLowLevelCacheMap.remove(key);
                        mHistoryCacheMap.remove(key);
                        break;
                    default:
                        mHistoryCacheMap.remove(key);
                        break;
                }

                mStartedSet.remove(key);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected JSONArray buildUploadContent(ArrayList<Node> list){

        JSONArray statisticsJsonarray = new JSONArray();
        try {
            for (Node node : list) {
                JSONObject json = new JSONObject();
                json.put("lm_f", node.lmf);
                Log.d("stat", "build " + node.stat_params);
                if (node.stat_params != null) {
                    for (Iterator<String> iter = node.stat_params.keys(); iter
                            .hasNext(); ) {
                        String _key = iter.next();
                        json.put(_key, node.stat_params.get(_key));
                    }
                }
                if (node.other != null) {
                    for (Iterator<String> iter = node.other.keys(); iter
                            .hasNext(); ) {
                        String _key = iter.next();
                        json.put(_key, node.other.get(_key));
                    }
                }
                if (!json.has("type")) {
                    json.put("type", 1);
                }
                if (!json.has("frombid")) {
                    json.put("frombid", 0);
                }
                statisticsJsonarray.put(json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return statisticsJsonarray;
    }

    protected void uploadStatiticsDelay(JSONObject json, String key) {
        Log.d(TAG, "uploadStatiticsDelay " + key);
        UploadStatiticsTask task = new UploadStatiticsTask(new BaseStatisticsManager.StatiticsReaderNetTaskListener(key), json);
        ReaderTaskHandler.getInstance().addTask(task);
    }
}

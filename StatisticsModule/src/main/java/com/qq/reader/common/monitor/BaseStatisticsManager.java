package com.qq.reader.common.monitor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.qq.reader.core.BaseApplication;
import com.qq.reader.core.readertask.tasks.ReaderJSONNetTaskListener;
import com.qq.reader.core.readertask.tasks.ReaderProtocolTask;
import com.qq.reader.core.readertask.tasks.ReaderShortTask;
import com.qq.reader.core.utils.NetUtils;
import com.tencent.mars.xlog.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * 统计和阅读时长上报的共有逻辑
 *
 * @author lvxinghe
 */
public abstract class BaseStatisticsManager {
    public final static int INVALlL_TIME = 2 * 60 * 1000; // 默认2min

    private static final String TAG = "BaseStatisticsManager";

    private final static int MAXSIZE = 12;// 当大于12条则不管时间是否到了都立即上传

    protected static final int MESSAGE_SEND = 10001;
    private static final int MESSAGE_WRITELOCAL = 10002;
    private static final int MESSAGE_UPLOAD_SUCCESS = 10003;
    //    private static final int MESSAGE_COMMITE = 10004;
    private static final int MESSAGE_COMMITE_NOW = 10005;
    private static final int MESSAGE_UPLOAD_ERROR = 10006;
    private static final int MESSAGE_CLEARLMF = 10007;
    // 检查网络是否成功
    private static final int MESSAGE_CHECK_TASKS_SUCCESS = 20001;

    private BaseStatisticsManager.StatisticsHandlerThread handlerThread;
    protected Handler mHandler;

    /**
     * 容错处理相关在这里
     */
    // 一次加20个Task到任务队列里，成功后再进行下一步
    protected static final int mUploadTastSizeEachTime = 20;
    private static int mCurrentTastSize = 0;
    // 200s 所有任务都没完成算作上报失败
    private static final long mUploadFailTimeOut = 20 * 1000 * 10;//
    private boolean isUploading = false;
    // 网络与服务是否稳定
    private boolean isNetAndServerOn = true;
    private static final long NET_SERVER_OFF_RETRY_TIME = 10 * 60 * 1000;// TODO 10分钟  10 * 测试临时修改
    private static final long NET_SERVER_OFF_RETRY_CHECK = 30 * 1000;// TODO 30s检查一次  测试临时修改
    private long lastTryTimeStamp;
    // 已完成的请求 用于计算成功失败率
    private int mCurrentFinishTask = 0;
    // 已完成的成功的请求
    private int mSuccessFinishTaskCount = 0;
    private Queue<Boolean> mSuccessTaskQueue = new LinkedList<>();
    private final int mCheckFinishTask = 20;


    /**
     * 当前任务队列
     */
    protected Set<String> mStartedSet = Collections.synchronizedSet(new HashSet<String>());


    public BaseStatisticsManager() {
        mDelay = StatisticsConfig.getStatisicDelay();
        handlerThread = new BaseStatisticsManager.StatisticsHandlerThread("StatisticsThread");
        handlerThread.setPriority(Thread.MIN_PRIORITY);
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper(), handlerThread);
        registerNetworkBroadcast();
    }

    @SuppressLint("MissingPermission")
    private void registerNetworkBroadcast() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            //网络监听
            ConnectivityManager.NetworkCallback mNetworkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    Log.d(TAG, "NetworkCallback onAvailable");
                    // 网络链接后尝试重写连接
                    mHandler.removeMessages(MESSAGE_COMMITE_NOW);
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_COMMITE_NOW));
                }

                @Override
                public void onLost(Network network) {
                    super.onLost(network);
                    Log.d(TAG, "NetworkCallback onLost");
                }
            };
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.Companion.getINSTANCE()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                connectivityManager.registerNetworkCallback(request, mNetworkCallback);
            }
        }
    }


    private boolean isSuspendUpload = false;

    /**
     * 停止所有网络请求
     */
    public void removeUploadTask() {
        if (mHandler != null) {
            if (mHandler.hasMessages(MESSAGE_SEND)) {
                Log.i(Log.LOGGER_TASK, "remove statistic message send");
                mHandler.removeMessages(MESSAGE_SEND);
                isSuspendUpload = true;
            }
        }
    }

    public void resumeUpload() {
        isSuspendUpload = false;
        if (mHandler != null) {
            mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_COMMITE_NOW));
            Log.i(Log.LOGGER_TASK, "resumeUpload");
        }
    }

    /**
     * 上报 除掉这个上边还有3个方法叫commit……
     *
     * @param send
     */
    private void sendOrWriteFile(boolean send) {
        Log.d(TAG, "sendOrWriteFile " + send);
        if (!isUploading && !isSuspendUpload) {
            // 这里应该处理高优先级，再处理中优先级，然后处理低优先级 最后处理历史内容
            int mSendTaskSize = mCurrentTastSize;
            putLowLevelListToMap();
            // 每次上报20条成功后重写调用一遍来判断是否还有新任务
            if (send) {
                mCurrentTastSize = dealSendData(mSendTaskSize);
                if (mCurrentTastSize > 0) {
                    isUploading = true;
                }
                // 发消息 mUploadFailTimeOut 后查是否成功
                mHandler.removeMessages(MESSAGE_CHECK_TASKS_SUCCESS);
                if (isUploading) {
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_CHECK_TASKS_SUCCESS)
                            , mUploadFailTimeOut);
                }
            }
        }
    }



    protected int sendByLevel(Map<String, Object> map, int currentendTaskSize, int maxSize) {
        Log.d(TAG, "sendByLevel " + currentendTaskSize);
        Iterator iter = map.entrySet().iterator();
        // 每次上报20条成功后重写调用一遍来判断是否还有新任务
        while (iter.hasNext() && currentendTaskSize < maxSize) {
            HashMap.Entry entry = (HashMap.Entry) iter.next();
            String _key = (String) entry.getKey();
            ArrayList<Node> list = (ArrayList<Node>)entry.getValue();
            if (list == null) {
                // 容错处理 发现null,删除
                iter.remove();
                continue;
            }
            if (!mStartedSet.contains(_key)) {
                // 网络/服务端异常情况下暂停掉
                if (isNetAndServerOn && !isSuspendUpload) {
                    boolean success = buildAndSendStatitics(_key, list, true);
                    if (success) {
//                        if(this instanceof ReadTimeUploadManager) {
//                            Log.i("adfasdfadsfa", "upload key:" + _key);
//                        }
                        mStartedSet.add(_key);
                        currentendTaskSize++;
                    }
                } else {
                    isUploading = false;
                }
            }
        }
        Log.d(TAG, "sendByLevel over " + currentendTaskSize);
        return currentendTaskSize;
    }

    private class StatisticsHandlerThread extends HandlerThread implements Handler.Callback {

        public StatisticsHandlerThread(String name) {
            super(name);
        }

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SEND:
                    sendOrWriteFile(true);
                    break;
                case MESSAGE_WRITELOCAL:
                    sendOrWriteFile(false);
                    break;
                case MESSAGE_UPLOAD_ERROR:
                    onTaskOver(false);
                    if (msg.obj != null && msg.obj instanceof String) {
                        String mKey = (String) msg.obj;
                        mStartedSet.remove(mKey);
                    }
                    break;
                case MESSAGE_UPLOAD_SUCCESS:
                    onTaskOver(true);
                    if (msg.obj != null && msg.obj instanceof String) {
                        String mKey = (String) msg.obj;
                        removeUploadDataFromMap(mKey);
                    }
                    break;
                case MESSAGE_COMMITE_NOW:
                    tryUploadStat(true);
                    break;
                case MESSAGE_CLEARLMF:
                    lmflist.clear();
                    break;
                case MESSAGE_CHECK_TASKS_SUCCESS:
                    checkTaskSuccess();
                    break;
            }
            return true;
        }
    }



    /**
     * 超过时间判断失败
     */
    private void checkTaskSuccess() {
        Log.d(TAG, "checkTaskSuccess " + isUploading);
        if (isUploading) {
            isUploading = false;
            setNetAndServerOn(false);
        }
    }

    /**
     * 设置当前网络，服务器是否可用
     *
     * @param netAndServerOn
     */
    private void setNetAndServerOn(boolean netAndServerOn) {
        Log.d(TAG, "setNetAndServerOn " + netAndServerOn);
        if (netAndServerOn) {
            isNetAndServerOn = true;
        } else {
            if (isNetAndServerOn) {
                isNetAndServerOn = false;
                lastTryTimeStamp = System.currentTimeMillis();
                // 定时任务，重新执行上报工作
                mHandler.removeMessages(MESSAGE_COMMITE_NOW);
                mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_COMMITE_NOW), NET_SERVER_OFF_RETRY_CHECK);
            }
        }
    }

    // private Lock mWriteLock;
    private int code;
    ReadWriteLock lock = new ReentrantReadWriteLock();
    private long mDelay = -1;
    private long mLastCommittime = 0;
    private LinkedList<String> lmflist = new LinkedList<String>();
    public long fromBid = 0;

    // 低基本的上报，node先存在list中，达到数目再上报
    protected ArrayList<Node> mCurrStatisticsList = new ArrayList<>();

    // 高优先级别的数据上报
    protected LocalHashMap mHighLevelCacheMap =
            new LocalHashMap(3, LocalHashMap.STAT_LEVEL_HIGH);

    // 高优先级别的数据上报
    protected LocalHashMap mReadTimeHighLevelCacheMap =
            new LocalHashMap(3, LocalHashMap.STAT_LEVEL_HIGH,"readtime/");

    // 普通级别的数据上报
    protected LocalHashMap mNormalLevelCacheMap =
            new LocalHashMap(3, LocalHashMap.STAT_LEVEL_NORMAL);

    // 低级别的数据上报
    protected LocalHashMap mLowLevelCacheMap =
            new LocalHashMap(3, LocalHashMap.STAT_LEVEL_LOW);

    // 历史数据加载 需要存到内存里么？
    protected LocalHashMap mHistoryCacheMap =
            new LocalHashMap(3, LocalHashMap.STAT_LEVEL_HISTORY);


    @Deprecated
    public void clearLmfList() {
        mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_CLEARLMF));
    }

    @Deprecated
    public void addLmf(String lmf) {
        Log.d("test addLmf", lmf);
        Message msg = mHandler.obtainMessage();
        mHandler.sendMessage(msg);
        msg.obj = lmf;
    }

    /**
     * 上报type node 内部创建 其实最好干掉-。-，外部构建Node
     * 但确实使用方便一点…… 先留着吧
     *
     * @param type
     */
    public void statType(int type) {
        Node node = new Node();
        try {
            node.other.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        commit(node, StatisticsConstant.STAT_LEVEL_HIGH);
    }


    /**
     * 通过 EventName map上报 默认高优先级
     *
     * @param _eventName
     * @param _extraMap
     */
    public void statEvent(String _eventName, Map<String, String> _extraMap) {
        statEvent(_eventName, _extraMap, StatisticsConstant.STAT_LEVEL_HIGH);
    }

    /**
     * 通过 EventName map上报
     *
     * @param _eventName 事件名称
     * @param _extraMap  map存储额外参数
     * @param level      上报等级
     */
    public void statEvent(String _eventName, Map<String, String> _extraMap, final int level) {
        //一般行为上报的同时，会触发大量工作，且统计的JSON串拼接耗时。因此将统计的上报延迟500ms，避免抢占CPU。
        final String eventName = _eventName;
        final Map<String, String> extraMap;
        if (_extraMap != null) {
            extraMap = new HashMap<>(_extraMap);
        } else {
            extraMap = null;
        }
        mHandler.postDelayed(new ReaderShortTask() {
            @Override
            public void run() {
                super.run();
                Node node = new Node();
                try {
                    node.other.putOpt("event", eventName);
                    node.other.put("type", 100);
                    node.putExtra(extraMap);
                    commit(node, level);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }


    /**
     * type不同所以重新写一个方法，以后处理一些数据上报可用这个方法
     * 111 本地书上报
     *
     * @param eventName
     * @param extraMap
     */
    public void statDateEvent(final String eventName, final Map<String, String> extraMap) {
        //一般行为上报的同时，会触发大量工作，且统计的JSON串拼接耗时。因此将统计的上报延迟5秒，避免抢占CPU。
        mHandler.postDelayed(new ReaderShortTask() {
            @Override
            public void run() {
                super.run();
                Node node = new Node();
                try {
                    node.other.putOpt("event", eventName);
                    node.other.put("type", 111);
                    node.putExtra(extraMap);
                    // 本地书上报默认为低优先级
                    commit(node, StatisticsConstant.STAT_LEVEL_LOW);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, 500);
    }

    /**
     * 处理数据，往内存list写数据
     */
    public void commit(Node node, int level) {
        commit(node, false, level);
    }

    /**
     * 处理数据，往内存list写数据
     */
    public void commit(Node node, boolean immediately) {
        Log.d("stat", "commit node :  "
                + node.toString());
        try {
            tryCommit(node, immediately);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理数据，往内存list写数据
     */
    private void commit(Node node, boolean immediately, int level) {
        Log.d("stat", "commit node :  level " + level +
                " " + node.toString());
        try {
            tryCommit(node, immediately, level);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void commitNow() {
        mHandler.sendEmptyMessage(MESSAGE_COMMITE_NOW);
    }

    /**
     * 直接带Node commit,内存中维护1个node容易数据混乱
     *
     * @param node
     * @param immediately
     */
    private void tryCommit(Node node, boolean immediately) {
        tryCommit(node, immediately, StatisticsConstant.STAT_LEVEL_NORMAL);
    }

    /**
     * 直接带Node commit,内存中维护1个node容易数据混乱
     *
     * @param node
     * @param immediately
     */
    protected void tryCommit(Node node, boolean immediately, int level) {
        Log.d(TAG, "tryCommit immediately " + immediately + " level " + level);
        // 高优先级 普通优先级的都立即上报
        if (level < StatisticsConstant.STAT_LEVEL_LOW) {
            immediately = true;
        }
        if (mDelay <= 0)
            mDelay = INVALlL_TIME;

        if (lmflist.size() > 0) {
            try {
                node.other.put("lm_f", lmflist.getLast());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            node.lmf = lmflist.getFirst();
            lmflist.clear();
        }
        String pagename = null;
        try {
            pagename = node.other.optString(Node.KEY_S_PAGENAME);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Node> tempNodeList = new ArrayList<>();
        tempNodeList.add(node);
        String key = "" + level + System.currentTimeMillis()
                + tempNodeList.hashCode();
        Log.d(TAG, "tryCommit key " + key);

        addData2Map(level,key, tempNodeList);

        try {
            lmflist.add(pagename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tryUploadStat(immediately);
    }



    private void putLowLevelListToMap() {
        Log.d(TAG, "putLowLevelListToMap 低优先级数据放到map中");
        if (mCurrStatisticsList.size() > 0) {
            ArrayList<Node> tempNodes = new ArrayList<>(mCurrStatisticsList);
            mCurrStatisticsList.clear();
            String key = "" + StatisticsConstant.STAT_LEVEL_LOW + System.currentTimeMillis()
                    + tempNodes.hashCode();
            mLowLevelCacheMap.put(key, tempNodes);
        }
    }

    /**
     * 这个方法负责通知进行尝试上报，作为生成消费模型的消费者通知接口
     * 通过Handler进行延迟操作
     * 之前是doCommitNow……
     *
     * @param immediately 是否立即上报
     */
    private void tryUploadStat(boolean immediately) {
        Log.d(TAG, "tryUploadStat isNetAndServerOn " + isNetAndServerOn + " mDelay " + mDelay);
        boolean isNetAvaiable = NetUtils.isNetworkConnected();
        // isNetAndServerOn 网络不正常或服务器异常 等待10分支后再上报，重写尝试是
        // 记录上一次失败时间点，每30s发送广播查是否到时间了，到了就重置参数，重新请求上报
        if (!isNetAndServerOn) {
            long currentTimeStamp = System.currentTimeMillis();
            long diff = (currentTimeStamp - lastTryTimeStamp);
            Log.d(TAG, "tryUploadStat currentTimeStamp " + currentTimeStamp + " diff " + diff);
            if (diff >= NET_SERVER_OFF_RETRY_TIME) {
                Log.d(TAG, "tryUploadStat 超过检测时间则可以重试了 ");
                // 超过检测时间则可以重试了 充值各种参数
                isNetAndServerOn = true;
                lastTryTimeStamp = 0;
                mCurrentFinishTask = 0;
                mSuccessFinishTaskCount = 0;
                mSuccessTaskQueue.clear();
            } else {
                mHandler.removeMessages(MESSAGE_SEND);
                mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_COMMITE_NOW)
                        , NET_SERVER_OFF_RETRY_CHECK);
                // LowLevel 存文件
                if (mCurrStatisticsList.size() > 0) {
                    // 低级别日志存在也直接存文件了
                    putLowLevelListToMap();
                }
                return;
            }
        }
        Log.d(TAG, "tryUploadStat isNetAvaiable " + isNetAvaiable);
        if (isNetAvaiable) {
            boolean isWifi = NetUtils.isWifi();
            if (isWifi || immediately) {
                // 在Wifi开启的情况下
                mHandler.removeMessages(MESSAGE_SEND);
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SEND));
            } else {
                // 其他网络情况
                if (mCurrStatisticsList.size() > MAXSIZE) {// 在非wifi情况下 如果条数超过
                    mHandler.removeMessages(MESSAGE_SEND);
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SEND));
                } else {
                    if (!mHandler.hasMessages(MESSAGE_SEND)) {
                        mHandler.sendMessageDelayed(
                                mHandler.obtainMessage(MESSAGE_SEND), mDelay);
                    }
                }
            }
        } else {
            if (mCurrStatisticsList.size() > MAXSIZE) {// 在非wifi情况下 如果条数超过
                // > MAXSIZE则存本地
                putLowLevelListToMap();
                // 超时时间后重试
                mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_COMMITE_NOW), mDelay);
            } else {
                mHandler.removeMessages(MESSAGE_SEND);
                mHandler.sendMessageDelayed(
                        mHandler.obtainMessage(MESSAGE_COMMITE_NOW), mDelay);
            }
        }
    }


    /**
     * 根据类型获取所有未上报的日志
     * 阅读时长只存在与高优先级中，只从mHighLevelCacheMap 中取就可以了
     * 现在只支持捞阅读时长的，其他不要用
     *
     * @param type
     * @return
     */
    public List<Node> getStatistics(int type) {
        List<Node> currentTypelist = new ArrayList<Node>();
        //从内存cache中捞取
        Node tmpNode;
        //从file cache中捞取
        ArrayList<Node> tmpList;
        //
        for (Map.Entry<String, Object> entry : mHighLevelCacheMap.entrySet()) {
            tmpList = (ArrayList<Node>) entry.getValue();
            if (tmpList != null) {
                int size = tmpList.size();
                for (int i = 0; i < size; i++) {
                    tmpNode = tmpList.get(i);
                    if (tmpNode.other.optInt("type") == type) {
                        currentTypelist.add(tmpNode);
                    }
                }
            }
        }

        return currentTypelist;
    }

    /**
     * 构建日志网络上报Task
     *
     * @param key      标识上报一条数据，用于删除内存，文件中该条内容
     * @param list     上报Node列表
     * @param isUpload 是否上报
     * @return 是否成功，用来记录当前上报条数，稳定性依赖于此…… 如果不对可能上报20条后一直等待
     */
    private boolean buildAndSendStatitics(String key, ArrayList<Node> list,
                                          boolean isUpload) {
        Log.d(TAG, "buildAndSendStatitics");
        JSONObject statisticsTotal = new JSONObject();

        boolean ok = false;
        try {
            JSONArray statisticsJsonarray = buildUploadContent(list);
            if (isUpload) {
                if (statisticsJsonarray.length() > 0) {
                    statisticsTotal.put("rc", statisticsJsonarray);
                    uploadStatiticsDelay(statisticsTotal, key);
                    // 这里失败的也要删除文件
                    ok = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        if (!ok) {
            //key
            removeUploadDataFromMap(key);
        }
        return ok;
    }

    public class StatiticsReaderNetTaskListener implements ReaderJSONNetTaskListener {

        String mKey;

        public StatiticsReaderNetTaskListener(String key) {
            mKey = key;
        }

        @Override
        public void onConnectionRecieveData(ReaderProtocolTask t, String str,
                                            long contentLength) {
            Log.d(TAG, "onConnectionRecieveData ");
            try {
                JSONObject json = new JSONObject(str);
                code = json.optInt("code");
                if (!json.isNull("rt")) {
                    long newDelay = json.optInt("rt") * 1000;
                    if (newDelay > INVALlL_TIME) {
                        mDelay = newDelay;
                        StatisticsConfig.setStatisicDelay(mDelay);
                    }
                }
                if (code == 0) {
                    Message m = mHandler.obtainMessage();
                    m.what = MESSAGE_UPLOAD_SUCCESS;
                    m.obj = mKey;
                    mHandler.sendMessage(m);
                    // mCacheMap.remove(mKey);
                } else {
                    onError();
                }
            } catch (Exception e) {
                e.printStackTrace();
                onError();
            }
        }

        @Override
        public void onConnectionError(ReaderProtocolTask t, Exception e) {
            Log.d("stat", "UploadError  " + e + " key：  " + mKey, true);
            onError();
        }


        public void onError() {
            Message m = mHandler.obtainMessage();
            m.what = MESSAGE_UPLOAD_ERROR;
            m.obj = mKey;
            mHandler.sendMessage(m);
        }
    }

    private void onTaskOver(boolean success) {
        Log.d(TAG, "onTaskOver " + success + " " + mCurrentTastSize);
        // 记录成功失败 计算比率
        mCurrentTastSize--;
        if (mCurrentFinishTask >= mCheckFinishTask) {
            mCurrentFinishTask = mCheckFinishTask;
            Boolean bool = mSuccessTaskQueue.poll();
            if (bool != null && bool) {
                mSuccessFinishTaskCount--;
            }
        } else {
            mCurrentFinishTask++;
        }
        mSuccessTaskQueue.offer(success);
        if (success) {
            mSuccessFinishTaskCount++;
        }

        if (mCurrentFinishTask >= mCheckFinishTask) {
            double k = (double) mSuccessFinishTaskCount / mCurrentFinishTask * 100;
            Log.d(TAG, "mSuccessFinishTask Rate " + k);
            if (k < 70) {
                setNetAndServerOn(false);
                // 失败时候容错处理
            } else {
                // 临时设置为全部出错
                setNetAndServerOn(true);
            }
        }


        Log.d(TAG, "mCurrentTastSize " + mCurrentTastSize);
        // 一次上报20条 全部加到任务队列里，全部完成后发送消息，重新上报
        if (mCurrentTastSize <= 0) {
            isUploading = false;
            mCurrentTastSize = 0;
            mHandler.removeMessages(MESSAGE_CHECK_TASKS_SUCCESS);
            // 上报成功后继续上报
            tryUploadStat(true);
        }
    }

    protected abstract void addData2Map(int level, String key, ArrayList<Node> data);

    protected abstract int dealSendData(int sendTaskSize);

    protected abstract void removeUploadDataFromMap(String key);

    protected abstract void uploadStatiticsDelay(JSONObject json, String key);

    protected abstract JSONArray buildUploadContent(ArrayList<Node> list);
}

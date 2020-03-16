package com.qq.reader.common.monitor;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.qq.reader.common.utils.CommonUtility;
import com.qq.reader.core.BaseApplication;
import com.qq.reader.core.http.ReaderNetStatisticeManager;
import com.qq.reader.core.readertask.ReaderTask;
import com.qq.reader.core.readertask.ReaderTaskHandler;
import com.qq.reader.core.readertask.tasks.ReaderNetTask;
import com.qq.reader.core.readertask.tasks.ReaderNetTaskStatisticsListener;
import com.qq.reader.core.utils.NetUtils;
import com.qq.reader.statistics.BuildConfig;
import com.tencent.beacon.event.UserAction;
import com.tencent.beacon.qimei.IAsyncQimeiListener;
import com.tencent.beacon.upload.UploadStrategy;
import com.tencent.mars.xlog.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * 统计上报方法实现类
 * <p>
 * Created by liuxiaoyang on 2017/5/19.
 */

class StatisticsAgentImpl implements IStatisticsAgent {

    private Application application;
    private boolean init = false;
    private StatisticsConfig config;
    // 是否使用灯塔，自有统计，QM
    private boolean useBeacon = false;
    private boolean useReader = false;
    private boolean useQM = false;
    public static String mManufacturer = "";

    public StatisticsConfig getConfig() {
        return config;
    }

    public void init(Application application) {
        init(application, null, null);
    }

    public void init(Application application, StatisticsConfig config, QQDispatchQimeiListener beaconQimeiListener) {
        this.application = application;
        if (config == null) {
            config = new StatisticsConfig.Builder().build();
        }
        this.config = config;
        int[] supporterTypes = config.supporter;
        for (Integer i : supporterTypes) {
            if (i == StatisticsConfig.TYPE_BEACON) {
                useBeacon = true;
            }
            if (i == StatisticsConfig.TYPE_READER) {
                useReader = true;
            }
            if (i == StatisticsConfig.TYPE_QM) {
                useQM = true;
            }
        }
        if (useBeacon) {
            fixHuaweiBug();
//            if(config.isUploadProcess){//如果是上报进程再初始化
            // Log.d("UserAction", "isUploadProcess" + config.isUploadProcess + " enableLog " + config.enableLog);
            UserAction.initUserAction(application, config.isUploadProcess);

            //灯塔在华为的android10.0设备有crash，这里禁止灯塔获取设备mac
            UserAction.setCollectMAC(false);

            //是否开启严苛模式，帮助开发者在上线前进行检查SDK使用的规范性
            UserAction.setStrictMode(true);

            //灯塔QIMEI终端唯一身份标识ID获取接口，保证网络正常的情况下一定从服务端异步返回
            UserAction.getQimei(new IAsyncQimeiListener() {
                @Override
                public void onQimeiDispatch(String s) {
                    // 可能在子线程中返回
                    beaconQimeiListener.onQimeiDispatch();
                }
            });
            // 是否打log 是否实时联调
            UserAction.setLogAble(false, false);
            //            UserAction.setLogAble(true, true);

            //设置灯塔渠道ID
            final String channelId = CommonUtility.getChannelId();
            //            Log.d("test", "channelId = " + channelId);
            setChannelID(channelId);

            String deviceFlag = android.os.Build.MANUFACTURER;
            if(deviceFlag.equalsIgnoreCase("oppo")){
                mManufacturer="newop";
            } else if(deviceFlag.equalsIgnoreCase("oneplus")){
                mManufacturer="oneplus";
            } else if(deviceFlag.equalsIgnoreCase("realme")){
                mManufacturer="realme";
            } else {
                mManufacturer="newop";
            }


        }
//        }
        if (useQM) {
            QMStatisticsUtils.init(BaseApplication.Companion.getINSTANCE());
        }

        // APP默认协议上报监听者初始化
        ReaderNetStatisticeManager.getInstance().setDefaultTaskStatisticsListener(new ReaderNetTaskStatisticsListener() {
            // 使用Handler维护所有上报事件
            ConnectionEventHandler handler = new ConnectionEventHandler();

            @Override
            public int onTaskStart(String url) {
                ConnectionEvent event = new ConnectionEvent(url);
                event.onStart();
                int index = handler.addEvent(event);
//                System.out.println("ProtocolTaskRDMListener url = " + url + " index = " + index);
                return index;
            }

            @Override
            public void onTaskSuccess(boolean isTryed, int index,long exeTime) {
//                System.out.println("ProtocolTaskRDMListener onSuccess index = " + index);
                handler.popEvent(index).onSuccess();
            }

            @Override
            public void onTaskFail(boolean isTryed, Throwable e, int index,long exeTime) {
 //               System.out.println("ProtocolTaskRDMListener onFail index = " + index);
                if (isTryed) {
                    ConnectionEvent event = handler.popEvent(index);
                    if (event != null) {
                        event.onFail(e);
                    }
                }
            }
        });
        init = true;
    }

    public void onSplashEvent() {
        if (init) {
            if (useBeacon) {
                //UserAction.onSplashEvent();
            }
        }
    }

    public void setUserID(String userID) {
        if (init) {
            if (useBeacon) {
                UserAction.setUserID(userID);
            }
        }
    }

    public void setQQ(String qq) {
        if (init) {
            if (useBeacon) {
                UserAction.setQQ(qq);
            }
        }
    }

    public void setChannelID(String channelID) {
        if (init) {
            if (useBeacon) {
                UserAction.setChannelID(channelID);
            }
        }
    }


    public void commitNow() {
        if (useReader) {
            StatisticsManager.getInstance().commitNow();
        }
        if (useBeacon) {
            // 灯塔日志上报
//            new Thread(new Runnable() {
//                public void run() {
//                }
//            }).start();
            ReaderTask dbTask = new ReaderNetTask() {
                public void run() {
                    super.run();
                    try {
                        UserAction.doUploadRecords();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            };
            ReaderTaskHandler.getInstance().addTask(dbTask);
        }
    }

    /**
     * oppo统计需要，其他渠道未做处理
     *
     * @param context
     */
    @Override
    public void onPause(Context context) {
//        Log.e("StatisticsAgentImpl", "onPause " + useQM);
        if (useQM) {
            QMStatisticsUtils.onPause(context);
        }
    }

    /**
     * oppo统计需要，其他渠道未做处理
     *
     * @param context
     */
    @Override
    public void onResume(Context context) {
//        Log.e("StatisticsAgentImpl", "onResume " + useQM);
        if (useQM) {
            QMStatisticsUtils.onResume(context);
        }
    }

    @Override
    public void onConnectionEvent(String eventName, boolean isOk, String url
            , Boolean response, Throwable e, long elapse, long size, Map<String, String> extraMap
            , boolean needCheckNet, boolean isRealTime) {
        // 0 为成功
        int failCode = 0;
        if (!isOk) {
            if (e != null) {
                failCode = HttpErrorUtils.getFailCode(e);
                Log.d("StatisticsAgentImpl", "failCode = " + failCode);
            }
        }
        onConnectionEvent(eventName, isOk, url, response, e, failCode, elapse, size
                , extraMap, needCheckNet, isRealTime);
    }

    @Override
    public void onConnectionEvent(String eventName, boolean isOk, String url
            , Boolean response, Throwable e, int errorCode, long elapse, long size
            , Map<String, String> extraMap, boolean needCheckNet, boolean isRealTime) {
        if (extraMap == null) {
            extraMap = new HashMap<String, String>();
        }
        if (!isOk) {
            if (e != null) {
                extraMap.put("Exception", e.toString() + " || " + e.getMessage());
                //int failCode = HttpErrorUtils.getFailCode(e);
                extraMap.put(RDM.PARAM_FAILCODE, "" + errorCode);
            }
            if (response != null) {
                extraMap.put("conn", String.valueOf(response));
            }
        }
        if (!TextUtils.isEmpty(url)) {
            extraMap.put("url", url);
        }
        boolean isNetWorkConnected = NetUtils.isNetworkConnected();
        boolean submitAction = false;
        if (!needCheckNet || isNetWorkConnected) {//if ((needCheckNet && isNetWorkConnected) || !needCheckNet) {
            submitAction = true;
        }
        if (submitAction) {
            Log.d("test", "onUserAction eventName = " + eventName + " isrealtime : "
                    + isRealTime + " isOk = " + isOk + " elapse " + elapse);
//            UserAction.onUserAction(eventName, isOk, elapse, size, extraMap, isRealTime);
            UserAction.onUserAction(eventName, isOk, elapse, size, extraMap, isRealTime);// isRealTime);
//            Log.d("test", "onUserAction ret = " + ret);
        }

    }

    @Override
    public void onConnectionEvent(String eventName, boolean isOk, String url
            , Boolean response, Throwable e, long elapse, long size, Map<String, String> extraMap) {
        onConnectionEvent(eventName, isOk, url
                , response, e, elapse, size, extraMap
                , true, false);
    }

    @Override
    public void onConnectionEvent(String eventName, boolean isOk, String url
            , Boolean response, Throwable e) {
        onConnectionEvent(eventName, isOk, url
                , response, e, -1, -1, null
                , true, false);
    }

    @Override
    public void onConnectionEvent(String eventName, boolean isOk
            , String url, Boolean response, int errorCode) {
        onConnectionEvent(eventName, isOk, url
                , response, null, errorCode, -1, -1, null
                , true, false);
    }

    @Override
    public void onConnectionSuccess(String eventName) {
        onConnectionSuccess(eventName, "");
    }

    @Override
    public void onConnectionSuccess(String eventName, long elapse) {
        onConnectionEvent(eventName, true, ""
                , true, null, elapse, -1, null
                , true, false);
    }

    @Override
    public void onConnectionSuccess(String eventName, String url) {
        onConnectionEvent(eventName, true, url
                , true, null, -1, -1, null
                , true, false);
    }

    @Override
    public void onConnectionFail(String eventName, String url
            , Boolean response, Throwable e) {
        onConnectionEvent(eventName, false, url
                , response, e, -1, -1, null
                , true, false);
    }

    @Override
    public void onConnectionFail(String eventName, String url
            , Throwable e) {
        onConnectionFail(eventName, url
                , null, e);
    }

    @Override
    public void onConnectionFail(String eventName, String url
            , Boolean response, int errorCode) {
        onConnectionEvent(eventName, false, url, response, errorCode);
    }

    @Override
    public void onConnectionFail(String eventName, String url, int errorCode) {
        onConnectionEvent(eventName, false, url, null, errorCode);
    }


    /**
     *
     * 灯塔在华为机器出现crash，这里代码是替灯塔加的
     *
     */
    public static void fixHuaweiBug() {
        //华为5.1 6.0 关闭反作弊 解决crash
        if (isHuaWeiManufacturer() &&
                ((Build.VERSION.SDK_INT == Build.VERSION_CODES.M) ||
                        Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1)) {
            UploadStrategy.DEFAULT_CC_ENABLE = false;
        }
    }

    public static boolean isHuaWeiManufacturer() {
        return (Build.MODEL != null && Build.MODEL.toUpperCase().contains("HUAWEI")) || (Build.MANUFACTURER != null && Build.MANUFACTURER.toUpperCase().compareTo("HUAWEI") == 0);
    }
}

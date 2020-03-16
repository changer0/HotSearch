package com.qq.reader.common.monitor;

import android.app.Application;
import android.content.Context;

import com.q.Qt;
import com.qq.reader.common.utils.CommonUtility;
import com.sijla.callback.QtCallBack;
import com.tencent.mars.xlog.Log;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by liuxiaoyang on 2017/6/26.
 */

public class QMStatisticsUtils {

    public static void onResume(Context context) {
        /**
         * 方法已废弃，调用和不掉用是一样的
         */
        Qt.appStart(context);
    }

    public static void onPause(Context context) {
        /**
         * 方法已废弃，调用和不掉用是一样的
         */
        Qt.appHidden(context);
    }

    public static void init(Application application) {
        String uid = CommonUtility.getQIMEI();
        //Qt.setDeviceUniqueID(uid);
        Qt.init(application
                , CommonUtility.getChannelId()
                ,uid
                , new QtCallBack() {
                    @Override
                    public void uploadCallBack(JSONObject jsonObject) {
                        Log.d("QM", "QtCallBack = " + jsonObject.toString());
                        HashMap map = new HashMap();
//                        map.put("lifecycle", lifeCycle); // 网络慢会存在lifecycle错误问题，仅供参考吧
                        try {
                            String result = jsonObject.getString("uploadStatus");
                            // 安装QM接口人说，uploadStatus,只可能存在 startUpload,success,fail
                            if (result.contains("startUpload")) {
                                // 开始上报
                                Log.i("QM", "EVENT_QM_START");
                                RDM.stat(EventNames.EVENT_QM_START, null);
                            } else if (result.contains("success")) {
                                // 只有成功才上报成功
//                                Log.d("QM", "QtCallBack success state " + lifeCycle);
                                Log.i("QM", "EVENT_QM_SUCCESS");
                                RDM.stat(EventNames.EVENT_QM_SUCCESS, null);
                            } else {
                                // API没提供返回状态信息啊
                                // reportRDM = true;
//                                Log.d("QM", "QtCallBack else " + result + " state " + lifeCycle);
                                map.put("uploadStatus", result);
                                // 任何非success startUpload 上报失败，仅供供参考
                                RDM.stat(EventNames.EVENT_QM_ERROR, map);
                                Log.e("QM", "EVENT_QM_ERROR " + result);
                            }
                        } catch (Exception e) {
                            Log.e("QM", e.toString());//.printStackTrace();
                            // 异常上报错误
                            map.put("exception", e.toString());
                            map.put("response", jsonObject.toString());
                            RDM.stat(EventNames.EVENT_QM_ERROR, map);
                        }
                    }
                });
    }
}

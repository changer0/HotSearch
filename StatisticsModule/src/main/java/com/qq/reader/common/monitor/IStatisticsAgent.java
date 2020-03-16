package com.qq.reader.common.monitor;

import android.app.Application;
import android.content.Context;

import java.util.Map;

/**
 * Created by liuxiaoyang on 2017/5/19.
 */

public interface IStatisticsAgent {

    public StatisticsConfig getConfig();

    public void init(Application application);

    public void init(Application application, StatisticsConfig config, QQDispatchQimeiListener beaconQimeiListener);

    public void setUserID(String userID);

    public void setQQ(String qq);

    public void onSplashEvent();

    public void setChannelID(String channelID);

//    public void onEvent(String eventName);
//
//    public void onEvent(String eventName, Map<String, String> extraMap);
//
//    public void onEvent(String eventName, int[] supporter);
//
//    public void onEvent(String eventName, Map<String, String> extraMap, int[] supporter);
//
//    public void onEvent(String eventName, boolean isOk, long elapse
//            , long size, Map<String, String> extraMap);
//
//    public void onEvent(String eventName, boolean isOk, long elapse
//            , long size, Map<String, String> extraMap, boolean needCheckNet, boolean isRealTime);
//
//    public void onEvent(String eventName, boolean isOk, long elapse
//            , long size, Map<String, String> extraMap, int[] supporter);
//
//    public void onEvent(String eventName, boolean isOk, long elapse
//            , long size, Map<String, String> extraMap
//            , boolean needCheckNet, boolean isRealTime, int[] supporter);

    public void commitNow();

    public void onPause(Context context);

    public void onResume(Context context);

    /**
     * 网络请求成功率统计,使用灯塔上报，在失败时传入Exception，解析错误并上报
     *
     * @param eventName    事件名称
     * @param isOk         是否成功
     * @param url          请求网络地址
     * @param response     返回结果
     * @param e            请求错误
     * @param elapse       耗时
     * @param size         网络消耗
     * @param extraMap     额外数据，暂时可能都不会用到
     * @param needCheckNet 无网络是否上传,应该是false
     * @param isRealTime   实时上传,不实时上传
     */
    void onConnectionEvent(String eventName, boolean isOk, String url
            , Boolean response, Throwable e, long elapse, long size
            , Map<String, String> extraMap, boolean needCheckNet, boolean isRealTime);

    /**
     * 网络请求成功率统计,使用灯塔上报，在失败时传入Exception，解析错误并上报
     *
     * @param eventName    事件名称
     * @param isOk         是否成功
     * @param url          请求网络地址
     * @param response     返回结果
     * @param errorCode    错误码
     * @param elapse       耗时
     * @param size         网络消耗
     * @param extraMap     额外数据，暂时可能都不会用到
     * @param needCheckNet 无网络是否上传,应该是false
     * @param isRealTime   实时上传,不实时上传
     */
    void onConnectionEvent(String eventName, boolean isOk, String url
            , Boolean response, Throwable e, int errorCode, long elapse, long size
            , Map<String, String> extraMap, boolean needCheckNet, boolean isRealTime);

    /**
     * 网络请求成功率统计,使用灯塔上报，在失败时传入Exception，解析错误并上报
     *
     * @param eventName 事件名称
     * @param isOk      是否成功
     * @param url       请求网络地址
     * @param response  返回结果
     * @param e         请求错误
     * @param elapse    耗时
     * @param size      网络消耗
     * @param extraMap  额外数据，暂时用不到，预留方便扩展
     */
    public void onConnectionEvent(String eventName, boolean isOk, String url
            , Boolean response, Throwable e, long elapse, long size
            , Map<String, String> extraMap);

    public void onConnectionEvent(String eventName, boolean isOk, String url
            , Boolean response, Throwable e);

    public void onConnectionEvent(String eventName, boolean isOk, String url
            , Boolean response, int errorCode);

    public void onConnectionSuccess(String eventName);

    public void onConnectionSuccess(String eventName, long elapse);

    public void onConnectionSuccess(String eventName, String url);

    public void onConnectionFail(String eventName, String url
            , Boolean response, Throwable e);

    public void onConnectionFail(String eventName, String url
            , Throwable e);

    public void onConnectionFail(String eventName, String url
            , Boolean response, int errorCode);

    public void onConnectionFail(String eventName, String url
            , int errorCode);
}

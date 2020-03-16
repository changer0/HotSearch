package com.qq.reader.common.monitor;

import android.text.TextUtils;

import com.tencent.mars.xlog.Log;

import java.net.URI;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuxiaoyang on 2017/6/6.
 * <p>
 * 网络接口事件，便于开发者上报网络协议成功率事件
 */

public class ConnectionEvent {

    private long elapse = -1;
    private long onCreateStart = 0;
    private String eventName = null;

    public ConnectionEvent(String url) {
        //onCreateStart = System.currentTimeMillis();
        // 使用域名+地址作为eventName
        eventName = formatEventName(url);
    }

    /**
     * 过滤版本号  /6  /6_0 /5_3 /v6/ /v6_0 /6_01
     *
     * @param url
     * @return
     */
    private String filterVersion(String url) {
//        url = url.replaceFirst(, "/");
        String regEx = "/(v|V)?[0-9]+_?[0-9]*/";
        //String regEx = "/v[(0-9)]?_?[(0-9)]/";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(url);
        return m.replaceAll("/").trim();
//        return url;
    }

    // 生成eventName
    private String formatEventName(String url) {
        String eventName = url;
        try {
            URI uri = URI.create(url);
            eventName = uri.getPath(); //uri.getHost()
            // boolean isFile = false;
            int start = 0; // = eventName.startsWith("/") ? 1 : 0;
            int end = eventName.length();
            if (eventName.contains(".")) {
                end = eventName.lastIndexOf("/");
            }
            eventName = eventName.substring(start, end);
            if (TextUtils.isEmpty(eventName)) {
                eventName = uri.getPath();
            }
            // 过滤版本号
            eventName = filterVersion(eventName);
        } catch (Exception e) {
        }
        Log.d("ConnectionEvent", "eventName = " + eventName);
        return eventName;
    }


//    public ConnectionEvent(String eventName) {
//        onCreateStart = System.currentTimeMillis();
//        this.eventName = eventName;
//    }

    public void onStart() {
        onCreateStart = System.currentTimeMillis();
    }

    /**
     * 网络请求成功率统计,使用灯塔上报
     *
     * @param eventName    事件名称
     * @param url          请求网络地址
     * @param response     返回结果
     * @param elapse       耗时
     * @param size         网络消耗
     * @param extraMap     额外数据，暂时可能都不会用到
     * @param needCheckNet 无网络是否上传
     * @param isRealTime   实时上传,不实时上传
     */
    public void onSuccess(String eventName, String url, Boolean response, long elapse
            , long size, Map<String, String> extraMap, boolean needCheckNet, boolean isRealTime) {
        //elapse = System.currentTimeMillis() - onCreateStart;
        StatisticsAgent.getInstance().onConnectionEvent(eventName, true, url
                , response, null, elapse, size, extraMap, needCheckNet, isRealTime);
    }

    public void onSuccess(String url, Boolean response, long elapse
            , long size, Map<String, String> extraMap, boolean needCheckNet, boolean isRealTime) {
        onSuccess(eventName, url, response, elapse
                , size, extraMap, needCheckNet, isRealTime);
    }

    /**
     * 网络请求成功率统计,使用灯塔上报
     *
     * @param eventName 事件名称
     * @param url       请求网络地址
     * @param response  返回结果
     * @param elapse    耗时
     * @param size      网络消耗
     * @param extraMap  额外数据，暂时可能都不会用到
     */
    public void onSuccess(String eventName, String url, Boolean response, long elapse
            , long size, Map<String, String> extraMap) {
        onSuccess(eventName, url
                , response, elapse, size, extraMap, true, false);
    }

    public void onSuccess(String eventName, Map<String, String> extraMap) {
        elapse = -1;
        if (onCreateStart >= 0) {
            elapse = System.currentTimeMillis() - onCreateStart;
        }
        onSuccess(eventName, null
                , true, elapse, -1, extraMap, true, false);
    }

    public void onSuccess(Map<String, String> extraMap) {
        onSuccess(eventName, extraMap);
    }

    public void onSuccess(String eventName, long elapse) {
        onSuccess(eventName, null
                , true, elapse, -1, null, true, false);
    }

    public void onSuccess(String eventName) {
        elapse = -1;
        if (onCreateStart >= 0) {
            elapse = System.currentTimeMillis() - onCreateStart;
        }
        onSuccess(eventName, elapse);
    }

    public void onSuccess() {
//        System.out.println("ConnectionEvent onSuccess " + eventName);
        onSuccess(eventName);
    }

    public void onFail(String eventName, String url, Boolean response
            , Throwable e, long elapse, long size, Map<String, String> extraMap
            , boolean needCheckNet, boolean isRealTime) {
        StatisticsAgent.getInstance().onConnectionEvent(eventName, false, url
                , response, e, elapse, size, extraMap, needCheckNet, isRealTime);
    }

    public void onFail(String url, Boolean response
            , Throwable e, long elapse, long size, Map<String, String> extraMap
            , boolean needCheckNet, boolean isRealTime) {
        //elapse = System.currentTimeMillis() - onCreateStart;
        onFail(eventName, url
                , response, e, elapse, size, extraMap, needCheckNet, isRealTime);
    }

    public void onFail(String eventName, String url, Boolean response
            , Throwable e, long elapse, long size, Map<String, String> extraMap) {
        //elapse = System.currentTimeMillis() - onCreateStart;
        onFail(eventName, url
                , response, e, elapse, size, extraMap, true, false);
    }

    public void onFail(String url, Boolean response
            , Throwable e, long elapse, long size, Map<String, String> extraMap) {
        //elapse = System.currentTimeMillis() - onCreateStart;
        onFail(eventName, url
                , response, e, elapse, size, extraMap, true, false);
    }

    public void onFail(String eventName, String url, Boolean response
            , Throwable e) {
        elapse = -1;
        if (onCreateStart >= 0) {
            elapse = System.currentTimeMillis() - onCreateStart;
        }
        onFail(eventName, url
                , response, e, elapse, -1, null, true, false);
    }

    public void onFail(String url, Boolean response
            , Throwable e) {
        onFail(eventName, url
                , response, e);
    }

    public void onFail(String eventName, Throwable e) {
        onFail(eventName, null
                , null, e);
    }

    public void onFail(Throwable e) {
//        System.out.println("ConnectionEvent onFail " + eventName);
        onFail(eventName, e);
    }
}

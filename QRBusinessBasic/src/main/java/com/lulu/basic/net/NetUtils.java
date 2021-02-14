package com.lulu.basic.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;


import com.lulu.basic.Init;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by River on 2016/11/22.
 */

public class NetUtils {

    public final static String PARA_SAFEKEY = "safekey";

    public static String AVATAR_URL_HOST = "";//第三方帐号，头像不做处理 ，三星的逻辑 在oppo华为上也没用了，赋值为空

    private static final String TAG_NETWORK_STATUS = "NETWORK_STATUS";

    public static final String BROADCASTRECEIVER_NETWORK_CONNECTED = "com.qq.reader.network" + ".connected";
    public static final String BROADCASTRECEIVER_NETWORK_DISCONNECTED = "com.qq.reader.network" + ".disconnected";

    // 网络类型 2G 3G WIFI
    private static int networkType = 0;
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    public static final int NET_TYPE_WIFI = 1;
    public static final int NET_TYPE_2G = 2;
    public static final int NET_TYPE_3G = 3;
    public static final int NET_TYPE_4G = 4;


    public static boolean isNetworkAvaiable() {
        try {
            ConnectivityManager cm = (ConnectivityManager) Init.app
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isAvailable()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isNetworkConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) Init.app
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断当前网络是否为wifi
     *
     * @return
     */
    public static boolean isWifi() {
        try {
            ConnectivityManager cm = (ConnectivityManager) Init.app
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    // 类似的wifi是否打开
    public static boolean isWifiEnabled() {
        try {
            ConnectivityManager mgrConn = (ConnectivityManager) Init.app
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            TelephonyManager mgrTel = (TelephonyManager) Init.app
                    .getSystemService(Context.TELEPHONY_SERVICE);

            NetworkInfo info = mgrConn.getActiveNetworkInfo();

            if (mgrConn == null || info == null || info.getState() == null
                    || mgrTel == null) {
                return false;
            }

            return info.getState() == NetworkInfo.State.CONNECTED
                    || mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS;

        } catch (Throwable e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean isMobile() {
        try {
            ConnectivityManager cm = (ConnectivityManager) Init.app
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    /**
     * 无网or2G，则认为不是一个好的网络状态
     * @return
     */
    public static boolean isBadNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) Init.app
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {// 有网时，请求网络数据
            NetworkInfo[] networkInfos = connectivityManager
                    .getAllNetworkInfo();
            for (int i = 0; i < networkInfos.length; i++) {
                NetworkInfo.State state = networkInfos[i].getState();
                if (NetworkInfo.State.CONNECTED == state) {
                    break;
                }
            }
            NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
            if (activeInfo != null
                    && activeInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                if (activeInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE
                        || activeInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA
                        || activeInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 是否把所有协议强制切到http，给测试放在debug入口里面使用的。
     */
    public static boolean isSwitchHttp = false;

    /**
     * 因为dns host获取ip为会导致主线程堵死，但是在放在拦截器里做 会导致与https ssl校验冲突 因此老大与运维商议尝试 去掉dns处理改为https
     *
     * @param url
     * @return
     */
    public static int getHttpMode(String url) {
        //如果打开了切换http的开关，所有协议都走http的。
        if (isSwitchHttp) {
            return Http.HTTP_MODE_HTTP;
        }

        int httpMode = Http.HTTP_MODE_HTTP;

//        if (url.startsWith("https://api.weixin.qq.com")||url.startsWith(AVATAR_URL_HOST)) {
//            httpMode = Http.HTTP_MODE_HTTPS;
//        }
        URL tempUrl = null;
        try {
            tempUrl = new URL(url);
            if (tempUrl.getProtocol().equalsIgnoreCase("http")) {
                httpMode = Http.HTTP_MODE_HTTP;
            } else {
                httpMode = Http.HTTP_MODE_HTTPS;
            }
        } catch (MalformedURLException e) {

            e.printStackTrace();
        }

        return httpMode;
    }



    /**
     * 获取当前网络连接的类型信息，原生
     *
     * @return
     */
    public static int getConnectedType() {
        if (Init.app != null) {
            //获取手机所有连接管理对象
            ConnectivityManager manager = (ConnectivityManager) Init.app.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                //返回NetworkInfo的类型
                return networkInfo.getType();
            }
        }
        return -1;
    }


    /**
     * 获取当前的网络状态，中国特色
     *
     * @return
     */
    public static int getAPNType() {
        //结果返回值
        int netType = NETWORK_TYPE_UNKNOWN;
        try {
            //获取手机所有连接管理对象
            ConnectivityManager manager = (ConnectivityManager) Init.app.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //NetworkInfo对象为空 则代表没有网络
            if (networkInfo == null) {
                return netType;
            }
            //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
            int nType = networkInfo.getType();
            if (nType == ConnectivityManager.TYPE_WIFI) {
                //WIFI
                netType = NET_TYPE_WIFI;
            } else if (nType == ConnectivityManager.TYPE_MOBILE) {
                int nSubType = networkInfo.getSubtype();
                TelephonyManager telephonyManager = (TelephonyManager) Init.app.
                        getSystemService(Context.TELEPHONY_SERVICE);
                if (telephonyManager.isNetworkRoaming()) {
                    netType = NET_TYPE_2G;
                } else {
                    switch (nSubType) {
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            netType = NET_TYPE_4G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                            //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
                            netType = NET_TYPE_3G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
                            netType = NET_TYPE_2G;
                            break;
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG_NETWORK_STATUS, e.toString());
        }catch (Throwable t){
            Log.d(TAG_NETWORK_STATUS, t.toString());
        }
        return netType;
    }

    /**
     * （4）、用户网络类型wifi/4g/3g/2g（需求强烈，用以判断用户网络质量）
     */
    public static String obtainNetworkState() {
        int apnState = getAPNType();
        StringBuilder sb = new StringBuilder("Net TYPE : ");
        switch (apnState) {
            case NETWORK_TYPE_UNKNOWN:
                sb.append("UNKNOWN");
                break;
            case NET_TYPE_WIFI:
                sb.append("WLAN");
                break;
            case NET_TYPE_2G:
                sb.append("2G");
                break;
            case NET_TYPE_3G:
                sb.append("3G");
                break;
            case NET_TYPE_4G:
                sb.append("4G");
                break;
        }
        return sb.toString();
    }

    /**
     * Converts the specified string to a byte array.  If the charset is not supported the
     * default system charset is used.
     *
     * @param data the string to be encoded
     * @param charset the desired character encoding
     * @return The resulting byte array.
     */
    public static byte[] getBytes(final String data, final String charset) {
        if (data == null) {
            throw new IllegalArgumentException(charset + " may not be null");
        }

        if (charset == null || charset.length() == 0) {
            throw new IllegalArgumentException(charset + " may not be null");
        }

        try {
            return data.getBytes(charset);
        } catch (final UnsupportedEncodingException e) {
            return data.getBytes();
        }
    }

}

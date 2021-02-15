package com.lulu.basic.net;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;


import com.lulu.baseutil.Init;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

public class HttpNetUtil {
    private static Uri PREFERRED_APN_URI = Uri
            .parse("content://telephony/carriers/preferapn");

    public static String APN_TYPE_CTNET = "ctnet";

    public static String APN_TYPE_CTWAP = "ctwap";

    public static String APN_TYPE_CMNET_ALIAS = "epc.tmobile.com";

    public static String APN_TYPE_CMNET = "cmnet";

    public static String APN_TYPE_CMWAP = "cmwap";

    public static String APN_TYPE_UNINET = "uninet";

    public static String APN_TYPE_UNIWAP = "uniwap";

    public static String APN_TYPE_3G_NET = "3gnet";

    public static String APN_TYPE_3G_WAP = "3gwap";


    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return (info != null) && info.isAvailable();
    }


    public static InetSocketAddress getProxy() {
        String strProxyHost = android.net.Proxy.getDefaultHost();
        int proxyport = android.net.Proxy.getDefaultPort();

        InetSocketAddress pxy = null;
        Context context= Init.context;
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                int type = activeNetworkInfo.getType();

                // TODO 防止wifi下读取移动的代理
                if (type == ConnectivityManager.TYPE_MOBILE) {
                    // 暂时先不加
                    if (activeNetworkInfo.getExtraInfo() != null) {
                        String apnName = activeNetworkInfo.getExtraInfo()
                                .toLowerCase();
                        if (apnName.startsWith(APN_TYPE_CMNET_ALIAS)
                                || apnName.startsWith(APN_TYPE_CTNET)
                                || apnName.startsWith(APN_TYPE_CMNET)
                                || apnName.startsWith(APN_TYPE_UNINET)
                                || apnName.startsWith(APN_TYPE_3G_NET)) {
                            return null;
                        }
                    }

                    strProxyHost = android.net.Proxy.getHost(context);
                    proxyport = android.net.Proxy.getPort(context);

                    if (strProxyHost == null
                            || strProxyHost.trim().length() == 0
                            || proxyport <= 0) {
                        strProxyHost = android.net.Proxy.getDefaultHost();
                        proxyport = android.net.Proxy.getDefaultPort();

                        if (strProxyHost == null
                                || strProxyHost.trim().length() == 0
                                || proxyport <= 0) {

                            return getProxyByApn(context);
                        }
                    }
                } else {
                    return null;
                }
            }
        }

        if (null != strProxyHost && strProxyHost.trim().length() > 0) {
            // pxy = new InetSocketAddress(strProxyHost, proxyport);
            // by ivanyang 不使用InetAddress避免host那么被赋空
            pxy = InetSocketAddress.createUnresolved(strProxyHost, proxyport);
        }

        return pxy;
    }

    private static InetSocketAddress getProxyByApn(Context context) {
        String address = null;
        int port = 0;
        String apn = null;

        Cursor c = null;
        try {
            c = context.getContentResolver().query(PREFERRED_APN_URI, null,
                    null, null, null);
            c.moveToFirst();

            address = c.getString(c.getColumnIndex("proxy"));
            if (address != null) {
                address.toLowerCase();
            }
            String portStr = c.getString(c.getColumnIndex("port"));
            if (portStr == null) {
                return null;
            }
            try {
                port = Integer.parseInt(portStr);
            } catch (Exception e) {
                port = -1;
            }

            apn = c.getString(c.getColumnIndex("apn"));
            if (apn != null) {
                apn.toLowerCase();
            }
            c.close();

            if (apn != null && apn.startsWith(APN_TYPE_CTWAP)) {
                if (address == null || address.length() == 0
                        || Integer.valueOf(port) <= 0) {
                    address = "10.0.0.200";
                    port = 80;
                }
            } else if (apn != null && apn.startsWith(APN_TYPE_CMWAP)) {
                if (address == null || address.length() == 0
                        || Integer.valueOf(port) <= 0) {
                    address = "10.0.0.172";
                    port = 80;
                }
            } else if (apn != null && apn.startsWith(APN_TYPE_UNIWAP)) {
                if (address == null || address.length() == 0
                        || Integer.valueOf(port) <= 0) {
                    address = "10.0.0.172";
                    port = 80;
                }
            } else if (apn != null && apn.startsWith(APN_TYPE_3G_WAP)) {
                if (address == null || address.length() == 0
                        || Integer.valueOf(port) <= 0) {
                    address = "10.0.0.172";
                    port = 80;
                }
            } else if (address == null || address.trim().length() <= 0
                    || port <= 0) {
                // 某些手机，3gnet下获取的address = " " port = 80
                return null;
            }

            // return new InetSocketAddress(address, port);
            // by ivanyang 不使用InetAddress避免host那么被赋空
            return InetSocketAddress.createUnresolved(address, port);

        } catch (Exception e) {
            Log.e("getProxyByApn", e.toString());
        } finally {
            if (c != null) {
                c.close();
                c = null;
            }
        }
        return null;
    }

    public static String getApn(Context context) {
        String apnName = "";
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            int type = activeNetworkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                WifiManager mWifi = (WifiManager) context
                        .getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = mWifi.getConnectionInfo();
                String ssid = "";
                if (null != wifiInfo) {
                    ssid = wifiInfo.getSSID();
                }
                apnName = "wifi_" + ssid;
            } else {
                if (activeNetworkInfo.getExtraInfo() != null) {
                    apnName = activeNetworkInfo.getExtraInfo().toLowerCase();
                }
                if (apnName == null) {
                    Cursor c = null;
                    try {
                        c = context.getContentResolver().query(
                                PREFERRED_APN_URI, null, null, null, null);
                        c.moveToFirst();

                        apnName = c.getString(c.getColumnIndex("apn"));
                        if (apnName != null) {
                            apnName.toLowerCase();
                        }
                    } catch (Exception e) {
                        apnName = "";
                    } finally {
                        try {
                            if (c != null) {
                                c.close();
                            }
                        } catch (Throwable e) {

                        }
                    }

                }
            }
        }
        return apnName;
    }


    public static String getString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw e;
        }

        return sb.toString();
    }

    public static String getWholeString(InputStream is) throws IOException {

        BufferedInputStream bais = new BufferedInputStream(is);
        byte[] buf = new byte[1024];

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        while ((len = bais.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        String _returnStr = new String(baos.toByteArray(), "UTF-8");

        return _returnStr;
    }
}

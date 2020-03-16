package com.qq.reader.common.monitor;

import android.content.Context;

import com.qq.reader.core.http.HttpErrorException;
import com.qq.reader.core.http.HttpResponseException;
import com.qq.reader.core.utils.NetUtils;
import com.qq.reader.core.utils.SysDeviceUtils;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by liuxiaoyang on 2017/6/5.
 */

public class HttpErrorUtils {

    public static String getFailReason(Context context, Throwable e,
                                       int faileCode) {
        String errorStr = "";
        if (faileCode == RDM.FAILCODE_SDCARD_FULL) {
            errorStr = "存储卡已满，建议清理存储卡";
        }else if(faileCode == RDM.FAILCODE_SDCARD_NOT_AVAIABLE) {
            errorStr = "存储卡不可用，无法阅读";
        } else if (!NetUtils.isNetworkAvaiable()) {
            errorStr = "网络不好，请检查网络设置";
        } else if (e instanceof HttpErrorException) {
            errorStr = "服务异常：" + ((HttpErrorException) e).getStateCode();
        }else {
            errorStr = "网络异常，请稍候再试";
        }
        return errorStr;
    }

    public static int getFailCode(Throwable e) {
        int failCode = RDM.FAILCODE_OTHER;
        try {

            if (e instanceof HttpResponseException) {
                failCode = ((HttpResponseException) e).getStateCode();
            } else if (e instanceof HttpHostConnectException) {
                failCode = RDM.FAILCODE_HTTP_HOST_CONNECT_EX;
            } else if (e instanceof ConnectTimeoutException) {
                failCode = RDM.FAILCODE_CONNECT_TIME_OUT;
            } else if (e instanceof SocketTimeoutException) {
                failCode = RDM.FAILCODE_SOCKET_TIME_OUT;
            } else if (e instanceof HttpErrorException) {
                failCode = ((HttpErrorException) e).getStateCode();
            } else if (e instanceof SocketException) {
                if (e instanceof ConnectException) {
                    if (e.getMessage() != null
                            && e.getMessage().indexOf(
                            "failed to connect to /10.0.0.172") != -1
                            && e.getMessage().indexOf("ECONNREFUSED") != -1) {
                        failCode = RDM.FAILCODE_WAP_CONNECT_REFUSED;
                    }
                } else if (e.getMessage() != null
                        && e.getMessage().indexOf("No route") != -1) {
                    failCode = RDM.FAILCODE_NO_ROUTE;
                } else {
                    failCode = RDM.FAILCODE_COMMON_SOCKETERROR;
                }
            } else if (e instanceof IOException) {
                String errorMsg = e.getMessage();
                if (errorMsg != null) {
                    if(!SysDeviceUtils.hasSDcard()) {
                        failCode = RDM.FAILCODE_SDCARD_NOT_AVAIABLE;
                    } else if (e.getMessage().indexOf("No space left") != -1) {
                        failCode = RDM.FAILCODE_SDCARD_FULL;
                    } else if (errorMsg
                            .indexOf("onlineUnZip failed unknown format") != -1) {
                        failCode = RDM.FAILCODE_UNZIP_FORMAT_ERROR;
                    } else if (errorMsg.indexOf("onlineUnZip failed") != -1
                            && errorMsg.indexOf("No such file") != -1) {
                        failCode = RDM.FAILCODE_UNZIP_NO_FILE;
                    } else if (e instanceof UnknownHostException) {
                        failCode = RDM.FAILCODE_UNKNOWHOST_ERROR;
                    }else if(e instanceof FileNotFoundException) {
                        failCode = RDM.FAILCODE_SDCARD_NOT_AVAIABLE;
                    }else {
                        failCode = RDM.FAILCODE_COMMON_IO;
                    }
                }

            } else if (e instanceof IllegalArgumentException) {
                failCode = RDM.FAILCODE_ILLEGALARGUMENT;
            } else if (e instanceof SecurityException) {
                failCode = RDM.FAILCODE_COMMON_SECURITYEXCEPTION;
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        return failCode;
    }
}

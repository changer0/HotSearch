package com.qq.reader.qurl;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qq.reader.common.config.FlavorConfig;
import com.qq.reader.common.utils.ServerUrl;
import com.qq.reader.define.ActivityCodeConstant;
import com.qq.reader.dispatch.R;
import com.qq.reader.dispatch.RouteConstant;
import com.qq.reader.dispatch.RoutePath;
import com.qq.reader.qurl.impl.URLServerOfAccInfo;
import com.qq.reader.qurl.impl.URLServerOfAdSdk;
import com.qq.reader.qurl.impl.URLServerOfAuthor;
import com.qq.reader.qurl.impl.URLServerOfBack;
import com.qq.reader.qurl.impl.URLServerOfBook;
import com.qq.reader.qurl.impl.URLServerOfCategory;
import com.qq.reader.qurl.impl.URLServerOfChannel;
import com.qq.reader.qurl.impl.URLServerOfClient;
import com.qq.reader.qurl.impl.URLServerOfCoin;
import com.qq.reader.qurl.impl.URLServerOfCommont;
import com.qq.reader.qurl.impl.URLServerOfDiscover;
import com.qq.reader.qurl.impl.URLServerOfFindBook;
import com.qq.reader.qurl.impl.URLServerOfGene;
import com.qq.reader.qurl.impl.URLServerOfInfoStream;
import com.qq.reader.qurl.impl.URLServerOfNewBook;
import com.qq.reader.qurl.impl.URLServerOfProps;
import com.qq.reader.qurl.impl.URLServerOfPublisher;
import com.qq.reader.qurl.impl.URLServerOfRank;
import com.qq.reader.qurl.impl.URLServerOfSearch;
import com.qq.reader.qurl.impl.URLServerOfTag;
import com.qq.reader.qurl.impl.URLServerOfTopic;
import com.qq.reader.qurl.impl.URLServerOfVip;
import com.qq.reader.qurl.impl.URLServerOfWebPage;
import com.qq.reader.qurl.impl.URLServerOfWebWelfare;
import com.qq.reader.service.app.AppRouterService;
import com.tencent.mars.xlog.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangtao on 2015/8/20.
 * <p/>
 * 统一URL：
 * 例如：
 * 新增消息系统数据请求url：
 * http://android.reader.qq.com/v5_3/nativepage/message/get?starttime=xxx&endtime=xxx
 * 服务器页面可以指定跳转为&客户端内部跳转为：
 * qurl=" uniteqqreader://nativepage/message/get?starttime=xxx&endtime=xxx"
 * 外部指定跳转为：
 * uniteqqreader://nativepage/message/get?starttime=xxx&endtime=xxx
 *
 * // 今日精选（今日必读）二级页
 * uniteqqreader://nativepage/infostream/dailyreading?plan=2
 */
public class URLCenter {

    public static String QULSCHEME = ServerUrl.QULSCHEME;
    public static final String QULSUB = QULSCHEME + "://";
    public static final String HTTPSCHEME = "http://";
    public static final String HTTPSCHEMES = "https://";
    public static final String HOST_WEB_ALL = QULSUB+"webpage/";
    // 华为支持两种qurl 其他分支 OtherQULSCHEME与QULSCHEME一致
    public static String OtherQULSCHEME = ServerUrl.OtherQULSCHEME;
    public static final String OtherQULSUB = OtherQULSCHEME + "://";
    private static final String SERVER_URL_DOMAIN = ServerUrl.PROTOCOL_SERVER_URL;
    private static final String HOST_NATIVE = "nativepage";
    private static final String HOST_WEB = "webpage";

    /**
     * 通过跳转URL获取数据请求URL
     *
     * @param URL
     * @return
     */
    public static String getHttpUrlWithQURL(String URL) {
        if (URL == null) {
            return null;
        }
        String _returnURL = new String(URL);
        if (_returnURL.length() == 0) {
            return _returnURL;
        }

        if (_returnURL.startsWith(QULSUB)) {
            _returnURL.replace(QULSUB, SERVER_URL_DOMAIN);
        }
        return _returnURL;
    }

    public static boolean isMatchQURL(String excuteQURL) {
        if (TextUtils.isEmpty(excuteQURL)) {
            return false;
        }
        return excuteQURL.startsWith(URLCenter.QULSUB) || excuteQURL.startsWith(OtherQULSUB) || excuteQURL.startsWith(HTTPSCHEME) || excuteQURL.startsWith(HTTPSCHEMES);
    }

    public static boolean isMatchOnlyQURL(String excuteQURL) {
        if (TextUtils.isEmpty(excuteQURL)) {
            return false;
        }
        return excuteQURL.startsWith(URLCenter.QULSUB) || excuteQURL.startsWith(URLCenter.OtherQULSUB);
    }

    public static void excuteURL(final Activity fromActivity, final String URL, final JumpActivityParameter jp) {
        excuteURL(fromActivity, URL, null, jp);
    }
    
    public static void excuteURL(Activity fromActivity, String URL) {
        excuteURL(fromActivity, URL, null, null);
    }

    /**
     * 同步方法，提供了URLCallBack用以业务需求的细分，默认传null
     *
     * @param qurl
     * @param callBack
     */
    public static void excuteURL(final Activity fromActivity, final String qurl
            , final URLCallBack callBack, final JumpActivityParameter jp) {
        excuteURL(fromActivity, qurl, null, callBack, jp);
    }

    /**
     * 同步方法，提供了URLCallBack用以业务需求的细分，默认传null
     *
     * @param qurl
     * @param callBack
     * @param fromTitle 来源页面名称，返回按钮添加文字
     *                  // TODO fromTitle native的需要分别处理 ？
     */
    public static void excuteURL(final Activity fromActivity, final String qurl
            , String fromTitle
            , final URLCallBack callBack, final JumpActivityParameter jp) {
        if (qurl == null || qurl.length() == 0 || fromActivity == null) {
            return;
        }

        try {
            String serverName = null;
            String serverAction = null;
            if (qurl.startsWith(QULSUB) || qurl.startsWith(OtherQULSUB)) {
                String subURL = null;
                if (qurl.startsWith(QULSUB)) {
                    subURL = qurl.substring(QULSUB.length());
                } else if (qurl.startsWith(OtherQULSUB)) {
                    subURL = qurl.substring(OtherQULSUB.length());
                }
                String[] splitsubURL = subURL.split("\\?");
                //逻辑url部分
                String logicOfUrl = splitsubURL[0];
                //参数部分
                String parameterOfUrl = null;
                if (splitsubURL.length > 1) {
                    parameterOfUrl = subURL.substring(logicOfUrl.length() + 1);
                }
                String[] splitStr = logicOfUrl.split("/");
                if (splitStr != null && splitStr.length > 0) {
                    URLServer urlServer = null;
                    String dataQurl = null;
                    String fixHttpScheme = HTTPSCHEME;
                    if (HOST_WEB.equals(splitStr[0])) {
                        boolean isFullUrl = subURL.contains(HTTPSCHEMES);
                        if (isFullUrl) {
                            fixHttpScheme = HTTPSCHEMES;
                        } else {
                            isFullUrl = subURL.contains(HTTPSCHEME);
                        }
                        // web
                        if (isFullUrl) {
                            String[] httpSplit = subURL.split(fixHttpScheme);
                            String[] urlSreverSplit = httpSplit[0].split("/");
                            serverName = urlSreverSplit[0];
                            if (urlSreverSplit.length > 1) {
                                serverAction = urlSreverSplit[1];
                            }
                            urlServer = buildURLServer(fromActivity,
                                    serverName, serverAction, parameterOfUrl);
                            dataQurl = fixHttpScheme + httpSplit[1];
                        } else {
                            urlServer = buildURLServer(fromActivity,
                                    HOST_WEB, null, null);
                            dataQurl = subURL.substring(HOST_WEB.length() + 1);
                        }

                    } else if (HOST_NATIVE.equals(splitStr[0])) {
                        //native
                        if (splitStr.length > 1) {
                            serverName = splitStr[1];
                        }
                        if (splitStr.length > 2) {
                            serverAction = splitStr[2];
                        }
                        urlServer = buildURLServer(fromActivity,
                                serverName, serverAction, parameterOfUrl);
                        if (subURL.contains("&statInfo")) {
                            dataQurl = ServerUrl.PROTOCOL_SERVER_URL + subURL.substring(0, subURL.indexOf("&statInfo"));
                        } else {
                            dataQurl = ServerUrl.PROTOCOL_SERVER_URL + subURL;
                        }
                    }
                    if (urlServer != null) {
                        try {
                            urlServer.setDataQURL(dataQurl);
                            urlServer.bindJumpActivityParameter(jp);
                            urlServer.setURLCallBack(callBack);

                            /**
                             * 检测ServerAction是否支持业务
                             */
                            if (!urlServer.isMatch()) {
                                urlServer.doExcuteNotMatchURL();
                                return;
                            }

                            if (!urlServer.parserURL()) {
                                urlServer.doExcuteNotMatchURL();
                            }

                        } catch (Exception e) {
                            Log.printErrStackTrace("URLCenter", e, null, null);
                            Log.e("URLCenter", e.getMessage());
                            String logStr = "[URL :" + subURL + "] : " + e.toString();
                        }
                    } else {
                        /**
                         * urlServer 没有匹配上
                         */
                        doExcuteNotMatchURL(fromActivity, jp);
                    }
                }

            } else if (qurl.startsWith(HTTPSCHEME) || qurl.startsWith(HTTPSCHEMES)) {
                ARouter.getInstance().build(RoutePath.WEB_BROWER)
                        .withString(RouteConstant.WEBCONTENT, qurl)
                        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .withString(RouteConstant.FROM_TITLE, fromTitle)
                        .withTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        .navigation(fromActivity, ActivityCodeConstant.FORRESULT_DEFAULT_CODE);
            }
        } catch (Exception e) {
            Log.printErrStackTrace("URLCenter", e, null, null);
            Log.e("URLCenter", "excuteURL :  qurl = " + qurl + " \n" + e.toString());
        }
    }

    /**
     * 处理那些因为版本不支持，而没有匹配到的urlServer
     *
     * @param fromActivity
     * @throws Exception
     */
    private static void doExcuteNotMatchURL(final Activity fromActivity, JumpActivityParameter jp) throws Exception {
        AppRouterService.checkUpgradeManual(fromActivity);
    }

    private static final URLServer buildURLServer(final Activity activity,
                                                  final String serverName, final String serverAction, final String parameter) {
        URLServer returnURLServer = null;
        if (serverName != null) {
            int ServerNameValue = ServerName.getServerNameValue(serverName);
            switch (ServerNameValue) {
                case ServerName.SERVER_NAME_WEBPAGE:
                    returnURLServer = new URLServerOfWebPage(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_BOOK:
                    returnURLServer = new URLServerOfBook(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_TOPIC:
                    returnURLServer = new URLServerOfTopic(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_COIN:
                    returnURLServer = new URLServerOfCoin(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_VIP:
                    returnURLServer = new URLServerOfVip(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_COMMENT:
                    returnURLServer = new URLServerOfCommont(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_CLIENT:
                    returnURLServer = new URLServerOfClient(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_READGENE:
                    returnURLServer = new URLServerOfGene(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_GETACCINFO:
                    returnURLServer = new URLServerOfAccInfo(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_INFOSTREAM:
                    returnURLServer = new URLServerOfInfoStream(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_CATEGORY:
                    returnURLServer = new URLServerOfCategory(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_DISCOVER:
                    returnURLServer = new URLServerOfDiscover(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_RANK:
                    returnURLServer = new URLServerOfRank(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_FINDBOOK:
                    returnURLServer = new URLServerOfFindBook(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_AUTHORS:
                    returnURLServer = new URLServerOfAuthor(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_SEARCH:
                    returnURLServer = new URLServerOfSearch(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_TAG:
                    returnURLServer = new URLServerOfTag(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_PUBLISHER:
                    returnURLServer = new URLServerOfPublisher(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_NEW_BOOK:
                    returnURLServer = new URLServerOfNewBook(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_CHANNEL:
                    returnURLServer = new URLServerOfChannel(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_PROPS:
                    returnURLServer = new URLServerOfProps(activity, serverAction, parameter);
                    break;
                case ServerName.SERVER_NAME_ADSDK:
                    returnURLServer = new URLServerOfAdSdk(activity,serverAction,parameter);
                    break;
                case ServerName.SERVER_NAME_WEB_WELFARE:
                    returnURLServer = new URLServerOfWebWelfare(activity,serverAction,parameter);
                    break;
                case ServerName.SERVER_NAME_BACK:
                    returnURLServer = new URLServerOfBack(activity,serverAction,parameter);
                    break;
            }
        }
        //@ TODO 这里可以考虑跳转到一个公共的调试页面，打印出需要调试的参数，已供开发使用；这里我还没想好，看有时间再增加 @15.8.24 by yangtao
        return returnURLServer;
    }

    public static Map<String, String> getQueryStringMap(String queryString) {
        try {
            if (TextUtils.isEmpty(queryString)) {
                return null;
            }
            Map<String, String> queryStringMap = null;
            String[] queryStringSplit = queryString.split("&");
            queryStringMap = new HashMap<String, String>(
                    queryStringSplit.length);
            String[] queryStringParam;
            for (String qs : queryStringSplit) {
                queryStringParam = qs.split("=");
                if (queryStringParam.length > 1) {
                    tryDecode(queryStringParam);
                    queryStringMap.put(queryStringParam[0], queryStringParam[1]);
                }
            }
            return queryStringMap;
        } catch (Exception e) {
            Log.printErrStackTrace("URLCenter", e, null, null);
            return null;
        }
    }

    private static void tryDecode(String[] queryStringParam) {
        if (queryStringParam == null || queryStringParam.length != 2 || queryStringParam[0] == null || queryStringParam[1] == null) {
            return;
        }
        boolean isNeedDecoder = false;

        String encodePerSub = "encode_";
        if (queryStringParam[0].startsWith(encodePerSub)) {
            queryStringParam[0] = queryStringParam[0].substring(encodePerSub.length());
            isNeedDecoder = true;
        } else {
            //deepLink返回相关，如果以后有其他参数需要decode，直接在这加
            if (queryStringParam[0].startsWith(RouteConstant.DEEP_LINK_BACK_NAME)
                    || queryStringParam[0].startsWith(RouteConstant.DEEP_LINK_BACK_URL)) {
                isNeedDecoder = true;
            }
        }

        if (isNeedDecoder) {
            try {
                queryStringParam[1] = URLDecoder.decode(queryStringParam[1], "UTF-8");
            } catch (UnsupportedEncodingException e) {
                Log.printErrStackTrace("URLCenter", e, null, null);
                e.printStackTrace();
            }
        }
    }
}

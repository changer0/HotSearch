package com.qq.reader.qurl.impl;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.qq.reader.common.utils.CommonConfig;
import com.qq.reader.define.ActivityCodeConstant;
import com.qq.reader.dispatch.RouteConstant;
import com.qq.reader.qurl.JumpActivityParameter;
import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.ServerAction;
import com.qq.reader.qurl.URLCenter;
import com.qq.reader.qurl.URLServer;
import com.tencent.mars.xlog.Log;

import java.util.List;

/**
 * Created by yangtao on 2015/8/24.
 */
public class URLServerOfWebPage extends URLServer {
//    private final String SERVER_ACTION_WEBPAGE_USERLIKE = "userlike";
//    private final String SERVER_ACTION_WEBPAGE_GAME = "game";
//    private final String SERVER_ACTION_WEBPAGE_FULLSCREEN = "fullscreen";
    private static final String TAG = "URLServerOfWebPage";

    public URLServerOfWebPage(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    public void initMatchServerActionPool(List<String> matchServerActionPool) {
//        matchServerActionPool.add(SERVER_ACTION_WEBPAGE_USERLIKE);
//        matchServerActionPool.add(SERVER_ACTION_WEBPAGE_GAME);
//        matchServerActionPool.add(SERVER_ACTION_WEBPAGE_FULLSCREEN);
    }

    /**
     * webview网页内容的匹配原有逻辑不存在匹配不到的情况，这里特殊处理一下。
     * 如果网页链接后面带版本号，并且版本号高于当前版本号则认为不匹配。
     * @return
     */
    @Override
    public boolean isMatch() {
        try {
            Uri uri = Uri.parse(getDataQURL());
            String query = uri.getQuery();
            if (query!=null && query.contains("&")){
                String[] params = query.split("&");
                for (String param : params) {
                    if(param.contains("version_num")) {
                        int versionCode = param.split("=").length == 2 ? Integer.parseInt(param.split("=")[1]) : 0;
                        //如果链接里带的版本号高于当前版本号，代表不匹配需要升级。
                        if(versionCode > CommonConfig.getVersionCode()) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return true;
        }
        //如果没带版本号是匹配的
        return true;
    }

    @Override
    public boolean parserURL() throws Exception {
        String serverAction = getServerAction();
        String targetUrl = getDataQURL();
        if (TextUtils.isEmpty(targetUrl)) {
            return false;
        }

        //H5链接后面的deepLink相关的参数
        //url: https://yuedu.reader.qq.com/coopactivity/1_0_0/article.html?type=20002&id=UpQlw9QXHOWUD0eBOLUyWw==&backurl=intenturl&btn_name=返回VIVO
        int deepLinkStart = targetUrl.indexOf("&backurl");
        if (deepLinkStart > 0) {
            targetUrl = targetUrl.substring(0, deepLinkStart);
        }

        if (serverAction == null) {
            goCustomWebContent(targetUrl);
            return true;
        } else {
            if (!getDataQURL().startsWith(URLCenter.HTTPSCHEME)) {
                return false;
            }
            int value = ServerAction.getActionValue(serverAction);
            switch (value) {
                case ServerAction.SERVER_ACTION_WEBPAGE_USERLIKE:
                    goCustomWebContent(goUserLikeWebContent(targetUrl));
                    return true;
                case ServerAction.SERVER_ACTION_WEBPAGE_GAME:
                    goGameWebContent(goUserLikeWebContent(targetUrl));
                    return true;
            }
        }
        return false;
    }

    public void goGameWebContent(final String targetUrl) {
//        Intent intent = new Intent();
//        intent.setClass(getBindActivity(), H5GameActivity.class);
//        intent.putExtra(Constant.WEBCONTENT, targetUrl);
//        //在QURL支持配置requestcode 之前，这里暂时改成startActivityForResult，5.9要用到游戏闪屏
//        //因为目前闪屏启动的activity必须是ForResult方式 add by p_jwcao @16/4/15
//        getBindActivity().startActivityForResult(intent, ActivityCodeCostant.FORRESULT_DEFAULT_CODE);
        JumpActivityUtil.goH5Game(getBindActivity(), targetUrl, false, new JumpActivityParameter().setRequestCode(ActivityCodeConstant.FORRESULT_DEFAULT_CODE));
    }

    public void goCustomWebContent(final String targetUrl) {
//        Intent intent = new Intent();
//        intent.setClass(getBindActivity(), WebBrowserForContents.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.putExtra(Constant.WEBCONTENT, targetUrl);
//        if (targetUrl.contains("topicV2.html")) {
//            intent.putExtra(Constant.WEBCONTENT_NEED_RECORD_HISTORY, true);
//        }
//        getBindActivity().startActivity(intent);
        JumpActivityParameter jp = getJumpActivityParameter();
        if (jp == null) {
            jp = new JumpActivityParameter();
        }
        Bundle bundle = new Bundle();
        //是否由外部deepLink唤起
        if (getParameterMap() != null) {
            bundle.putString(RouteConstant.DEEP_LINK_BACK_URL, getParameterMap().get(RouteConstant.DEEP_LINK_BACK_URL));
            bundle.putString(RouteConstant.DEEP_LINK_BACK_NAME, getParameterMap().get(RouteConstant.DEEP_LINK_BACK_NAME));
        }
        JumpActivityUtil.goWebBrowserForContents(getBindActivity(), targetUrl, bundle, jp.setFlag(Intent.FLAG_ACTIVITY_NEW_TASK).setRequestCode(ActivityCodeConstant.FORRESULT_DEFAULT_CODE));
    }

    public String goUserLikeWebContent(final String targetUrl) {
        StringBuilder sb = new StringBuilder();
        sb.append(targetUrl);
        if (targetUrl.contains("?")) {
            sb.append("&");
        } else {
            sb.append("?");
        }
        sb.append("sex=");
        sb.append(CommonConfig.getWebUserLike());
        return sb.toString();
    }
}

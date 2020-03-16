package com.qq.reader.qurl.impl;

import android.app.Activity;

import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;
import java.util.Map;

/**
 * Created by zhaodanyang on 2019/08/13.
 */
public class URLServerOfAdSdk extends URLServer {
    public static final String ACTION_ID = "actionId";

    public URLServerOfAdSdk(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> mMatchServerActionPool) {

    }

    @Override
    public boolean parserURL() throws Exception {
        Map<String, String> parameterMap = getParameterMap();
        String actionId = null;
        if (null != parameterMap) {
            actionId = parameterMap.get(ACTION_ID);
        }
        return JumpActivityUtil.showGameView(getBindActivity(), actionId);
    }

    @Override
    public boolean isMatch() {
        return true;
    }
}

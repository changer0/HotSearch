package com.qq.reader.qurl.impl;

import android.app.Activity;

import com.qq.reader.common.utils.FlavorUtils;
import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangqingning on 2016/5/13.
 */
public class URLServerOfTag extends URLServer {

    private static final String ACTION_ID = "actionId";
    private static final String KEYWORD = "key";
    private static final String ACTION_TAG = "actionTag";


    public URLServerOfTag(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> mMatchServerActionPool) {

    }


    @Override
    public boolean parserURL() throws Exception {
        handleDefaultTag();
        return true;
    }


    private void handleDefaultTag() {
        Map<String, String> lParameterMap = getParameterMap();
        String actionId = lParameterMap.get(ACTION_ID);
        String actionTag = lParameterMap.get(ACTION_TAG);

        if (FlavorUtils.isCoFree()) {
            String searchKey = lParameterMap.get(KEYWORD);
            JumpActivityUtil.goNewClassifyPage(getBindActivity()
                    , actionTag, actionId, searchKey, getJumpActivityParameter());
        } else {
            String searchFrom = getParameterMap().get("searchfrom");
            if (searchFrom != null) {
                JumpActivityUtil.goLabAct(getBindActivity(), actionId, actionTag, null, searchFrom);
            } else {
                JumpActivityUtil.goLabAct(getBindActivity(), actionId, actionTag, null);
            }
        }
    }

    @Override
    public boolean isMatch() {
        return true;
    }
}

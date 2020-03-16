package com.qq.reader.qurl.impl;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.qq.reader.define.JumpParameterConstants;
import com.qq.reader.define.SecondLevelConstant;
import com.qq.reader.qurl.JumpActivityParameter;
import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;
import java.util.Map;

/**
 * @author zhanglulu on 2019/3/26.
 * for 频道页相关
 */
public class URLServerOfChannel extends URLServer {
    //频道首页
    private final String SERVER_ACTION_INDEX = "index";
    //频道二级页
    private final String SERVER_ACTION_TWO_LEVEL = "secondlevel";

    public static final String ACTION_ID = "actionId";
    private static final String TITLE = "title";
    private static final String TYPE = "type";
    private static final String EXTRA = "extra";

    public URLServerOfChannel(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> mMatchServerActionPool) {
        mMatchServerActionPool.add(SERVER_ACTION_INDEX);
        mMatchServerActionPool.add(SERVER_ACTION_TWO_LEVEL);
    }

    @Override
    public boolean parserURL() throws Exception {
        String serverAction = getServerAction();
        if (TextUtils.equals(serverAction, SERVER_ACTION_INDEX)) {
            goIndex();
            return true;
        } else if (TextUtils.equals(serverAction, SERVER_ACTION_TWO_LEVEL)) {
            goTwoLevel();
            return true;
        }
        return false;
    }

    private void goIndex() {
        JumpActivityUtil.goFeed(getBindActivity(),  new JumpActivityParameter().setJsonParamater(getParameterMap()));
    }

    private void goTwoLevel() {
        Map<String, String> lParameterMap = getParameterMap();
        String actionId = lParameterMap.get(ACTION_ID);
        String title = lParameterMap.get(TITLE);
        String type = lParameterMap.get(TYPE);
        String extra = lParameterMap.get(EXTRA);

        Bundle bundle = new Bundle();
        bundle.putString(JumpParameterConstants.CHANNEL_TAB_BUNDLE_ID, actionId);
        bundle.putString(JumpParameterConstants.CHANNEL_TAB_BUNDLE_TITLE, title);
        bundle.putString(JumpParameterConstants.CHANNEL_TAB_BUNDLE_TYPE, type);
        bundle.putString(JumpParameterConstants.CHANNEL_TAB_BUNDLE_EXTRA, extra);

        try {
//            if(Integer.parseInt(type) == SecondLevelConstant.SECOND_LEVEL_TAG
//                    || Integer.parseInt(type) == SecondLevelConstant.SECOND_LEVEL_SIMILAR_BOOK
//                    ||Integer.parseInt(type) == SecondLevelConstant.SECOND_LEVEL_CHANNEL_RECOMMEND){
//                JumpActivityUtil.goChannelTwoPageActivity(getBindActivity(), bundle);
//            }else {
                JumpActivityUtil.goCommonBookListPageActivity(getBindActivity(), bundle);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

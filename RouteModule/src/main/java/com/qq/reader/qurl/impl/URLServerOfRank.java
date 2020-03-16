package com.qq.reader.qurl.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.qq.reader.core.BaseApplication;
import com.qq.reader.define.JumpParameterConstants;
import com.qq.reader.dispatch.R;
import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;
import java.util.Map;

/**
 * Created by yangtao on 2015/8/24.
 */
public class URLServerOfRank extends URLServer {
    private final String SERVER_ACTION_INDEX = "index";
    private final String SERVER_ACTION_LIST = "list";

    public static final String ACTION_ID = "actionId";
    public static final String ACTION_TAG = "actionTag";
    private static final String TITLE = "title";
    private static final String TYPE = "type";
    private static final String EXTRA = "extra";


    public URLServerOfRank(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> matchServerActionPool) {
        matchServerActionPool.add(SERVER_ACTION_INDEX);
        matchServerActionPool.add(SERVER_ACTION_LIST);
    }

    @Override
    public boolean parserURL() throws Exception {
        String value = getServerAction();
        switch (value) {
            case SERVER_ACTION_INDEX:
                goRankMain();
                return true;
            case SERVER_ACTION_LIST:
                goRankListNew();

//                goRankList();
                return true;
        }
        return false;

    }

    public void goRankMain() {
        JumpActivityUtil.goRank(getBindActivity(), null, getParameterMap().get("rankFlag"), getJumpActivityParameter().setFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                BaseApplication.getInstance().getResources().getString(R.string.bookrecommend));
    }

    /**
     * 跳转榜单独立二级页
     */
    public void goRankListNew() {
        Map<String, String> lParameterMap = getParameterMap();
        String actionId = lParameterMap.get(ACTION_ID);
        String actionTag = lParameterMap.get(ACTION_TAG);
        String title = lParameterMap.get(TITLE);
//        String type = lParameterMap.get(TYPE);
        String extra = lParameterMap.get(EXTRA);

        //目前只有男1、女2，
        String sex = "1";
        if (!TextUtils.equals(actionTag, "boy")) {
            sex = "2";
        }

        Bundle bundle = new Bundle();
        bundle.putString(JumpParameterConstants.CHANNEL_TAB_BUNDLE_ID, actionId);
        bundle.putString(JumpParameterConstants.CHANNEL_TAB_BUNDLE_RANK_SEX, sex);
        bundle.putString(JumpParameterConstants.CHANNEL_TAB_BUNDLE_TITLE, title);
        bundle.putString(JumpParameterConstants.CHANNEL_TAB_BUNDLE_TYPE, "104");  //独立榜单二级页
        bundle.putString(JumpParameterConstants.CHANNEL_TAB_BUNDLE_EXTRA, extra);

        JumpActivityUtil.goCommonBookListPageActivity(getBindActivity(), bundle);
    }

    public void goRankList() {
        if (getParameterMap() != null) {
            String actionId = getParameterMap().get("actionId");
            String actionTag = getParameterMap().get("actionTag");
            String oldRank = getParameterMap().get("oldRank");
            String title = getParameterMap().get("title");
            if (TextUtils.equals("1", oldRank)) {
                //跳转旧版排行榜
                JumpActivityUtil.goRank_Detail_old(getBindActivity(), title, actionId, actionTag, getJumpActivityParameter().setFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return;
            }
            JumpActivityUtil.goRank_Detail(getBindActivity(), null, actionId, actionTag, getJumpActivityParameter().setFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

}

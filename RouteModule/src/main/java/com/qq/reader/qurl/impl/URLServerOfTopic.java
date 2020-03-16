package com.qq.reader.qurl.impl;

import android.app.Activity;

import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;

/**
 * Created by yangtao on 2015/8/24.
 */
public class URLServerOfTopic extends URLServer {
    private final String SERVER_ACTION_DETAIL = "detail";
    private final String SERVER_ACTION_REPLYLIST = "replylist";

    public URLServerOfTopic(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> matchServerActionPool) {
        matchServerActionPool.add(SERVER_ACTION_DETAIL);
        matchServerActionPool.add(SERVER_ACTION_REPLYLIST);
    }

    @Override
    public boolean parserURL() throws Exception {
        String value = getServerAction();
        switch (value) {
            case SERVER_ACTION_DETAIL:
                goTopicPage();
                return true;
            case SERVER_ACTION_REPLYLIST:
                goTopicReplyList();
                return true;
        }
        return false;
    }

    /**
     * 跳转精华书评
     */
    public void goTopicPage() {
        if (getParameterMap() != null) {
            String topicId = getParameterMap().get("tid");
            String ctype = getParameterMap().get("ctype");
            JumpActivityUtil.goSelectedComment(getBindActivity(), topicId, ctype, getJumpActivityParameter().setQurl(getDataQURL()));
        }
    }

    /**
     * 跳转新闻专题的全部回复列表
     */
    public void goTopicReplyList() {
        if (getParameterMap() != null) {
            String tid = getParameterMap().get("tid");
            String ctype = getParameterMap().get("ctype");
            JumpActivityUtil.goTopicComment(getBindActivity(), tid, ctype, getJumpActivityParameter());
        }
    }
}

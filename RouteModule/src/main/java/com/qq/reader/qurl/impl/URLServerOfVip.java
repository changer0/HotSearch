package com.qq.reader.qurl.impl;

import android.app.Activity;
import android.content.Intent;

import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;

/**
 * Created by yangtao on 2015/8/24.
 */
public class URLServerOfVip extends URLServer {
    private final String SERVER_ACTION_PRIVILEGE = "privilege";
    private final String SERVER_ACTION_OPEN = "open";

    public URLServerOfVip(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> matchServerActionPool) {
        matchServerActionPool.add(SERVER_ACTION_PRIVILEGE);
        matchServerActionPool.add(SERVER_ACTION_OPEN);
    }

    @Override
    public boolean parserURL() throws Exception {
        String value = getServerAction();
        switch (value) {
            case SERVER_ACTION_PRIVILEGE:
                goVipPrivilegePage(getBindActivity());
                return true;
            case SERVER_ACTION_OPEN:
                openPayVipPage();
                return true;
        }
        return false;
    }

    /**
     * 包月特权页
     *
     * @param activity
     */
    public void goVipPrivilegePage(Activity activity) {
        JumpActivityUtil.goMonthPrivilege(activity, getJumpActivityParameter().setFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP).setQurl(getDataQURL()),"");
    }

    public void openPayVipPage() {
        JumpActivityUtil.goVIPOpen(getBindActivity());
    }
}

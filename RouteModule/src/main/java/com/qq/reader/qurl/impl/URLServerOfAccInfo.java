package com.qq.reader.qurl.impl;

import android.app.Activity;

import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;

/**
 * Created by yangtao on 2015/8/24.
 */
public class URLServerOfAccInfo extends URLServer {
    public URLServerOfAccInfo(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public boolean parserURL() throws Exception {
        goAccountInfo();
        return true;
    }


    public void goAccountInfo() {
        JumpActivityUtil.goAccountActivity(getBindActivity(), getJumpActivityParameter());
    }

    @Override
    public void initMatchServerActionPool(List<String> mMatchServerActionPool) {

    }

    public boolean isMatch() {
        return true;
    }

}

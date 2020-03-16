package com.qq.reader.qurl.impl;

import android.app.Activity;

import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;

/**
 * Created by liujian on 2019/12/30
 */
public class URLServerOfBack extends URLServer {

    public URLServerOfBack(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> mMatchServerActionPool) {

    }

    @Override
    public boolean isMatch() {
        return true;
    }

    @Override
    public boolean parserURL() throws Exception {
        JumpActivityUtil.goBackFinish(getBindActivity());
        return true;
    }
}

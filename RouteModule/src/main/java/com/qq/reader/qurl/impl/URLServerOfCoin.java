package com.qq.reader.qurl.impl;

import android.app.Activity;

import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;

/**
 * Created by yangtao on 2015/8/24.
 */
public class URLServerOfCoin extends URLServer {
    private final String SERVER_ACTION_RECHARGE = "recharge";


    public URLServerOfCoin(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> matchServerActionPool) {
        matchServerActionPool.add(SERVER_ACTION_RECHARGE);
    }

    @Override
    public boolean parserURL() throws Exception {
        String value = getServerAction();
        switch (value) {
            case SERVER_ACTION_RECHARGE:
                goRecharge();
                return true;
        }
        return false;
    }

    public void goRecharge() {
        JumpActivityUtil.goCoinrecharge(getBindActivity());
    }
}

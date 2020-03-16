package com.qq.reader.qurl.impl;

import android.app.Activity;

import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangqingning on 2016/5/13.
 */
public class URLServerOfPublisher extends URLServer {
    private final String KEY_WORD = "key";

    public URLServerOfPublisher(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> mMatchServerActionPool) {

    }

    @Override
    public boolean parserURL() throws Exception {
        Map<String, String> lParameterMap = getParameterMap();
        String s = lParameterMap.get(KEY_WORD);
        JumpActivityUtil.goNormalAuthProduct(getBindActivity(), s, 14, getJumpActivityParameter());
        return true;
    }

    @Override
    public boolean isMatch() {
        return true;
    }
}

package com.qq.reader.qurl.impl;

import android.app.Activity;

import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;

/**
 * Created by yangtao on 2015/8/24.
 */
public class URLServerOfFindBook extends URLServer {
    private final String SERVER_ACTION_INDEX = "index";

    public URLServerOfFindBook(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> matchServerActionPool) {
        matchServerActionPool.add(SERVER_ACTION_INDEX);
    }

    @Override
    public boolean parserURL() throws Exception {
        String value = getServerAction();
        switch (value) {
            case SERVER_ACTION_INDEX:
                goFindBook();
                return true;
        }
        return false;
    }


    public void goFindBook() {
        JumpActivityUtil.goSearchToolMain(getBindActivity(), getJumpActivityParameter().setQurl(getDataQURL()));
    }


}

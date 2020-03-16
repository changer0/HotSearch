package com.qq.reader.qurl.impl;

import android.app.Activity;

import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;

/**
 * Created by yangtao on 2015/8/24.
 */
public class URLServerOfGene extends URLServer {


    public URLServerOfGene(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> matchServerActionPool) {

    }

    @Override
    public boolean parserURL() throws Exception {
        goGene();
        return true;
    }

    @Override
    public boolean isMatch() {
        return true;
    }

    public void goGene() {
        JumpActivityUtil.goGeneActivity(getBindActivity(), getJumpActivityParameter());
    }


}

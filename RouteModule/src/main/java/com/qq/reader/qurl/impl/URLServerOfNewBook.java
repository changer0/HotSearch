package com.qq.reader.qurl.impl;

import android.app.Activity;

import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;

/**
 * Description:
 * Data：2018/11/22-14:57
 * Author: vivien
 */
public class URLServerOfNewBook  extends URLServer {

    public URLServerOfNewBook(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> matchServerActionPool) {
    }

    @Override
    public boolean isMatch() {
        return true;
    }

    @Override
    public boolean parserURL() throws Exception {
        goNewBook();
        return true;
    }

    public void goNewBook() {
        JumpActivityUtil.goNewBookStore(getBindActivity(),null);
    }
}
package com.qq.reader.qurl.impl;

import android.app.Activity;

import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;
import com.qq.reader.service.search.ISearchActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by hulk on 2015/11/12.
 */
public class URLServerOfSearch extends URLServer {
    public static final String KEY = "key";

    public URLServerOfSearch(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> mMatchServerActionPool) {

    }

    @Override
    public boolean parserURL() throws Exception {
        Map<String, String> parameterMap = getParameterMap();
        String keywords = null;
        if (null != parameterMap) {
            keywords = parameterMap.get(KEY);
        }
        Activity activity = getBindActivity();
        if (activity instanceof ISearchActivity) {
            ((ISearchActivity) activity).doSearch(keywords);
        } else {
            JumpActivityUtil.goCommonSearch(getBindActivity(), keywords);
        }
        return true;
    }

    @Override
    public boolean isMatch() {
        return true;
    }
}

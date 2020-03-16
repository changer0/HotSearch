package com.qq.reader.qurl.impl;

import android.app.Activity;
import android.os.Bundle;

import com.qq.reader.dispatch.RouteConstant;
import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;
import java.util.Map;

/**
 * Created by liujian on 2019/12/16
 */
public class URLServerOfWebWelfare extends URLServer {

    //要展示的tab
    private final String WELFARE_TAB_INDEX = "tabIndex";
    //要跳转的页面  pageLevel 1：福利tab  2：每日福利
    private final String WELFARE_PAGE_LEVEL = "pageLevel";

    public URLServerOfWebWelfare(Activity activity, String serverAction, String parameter) {
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
        Map<String, String> parameterMap = getParameterMap();
        String tabIndex = "0";
        String pageLevel = "2";
        if (parameterMap != null) {
            if (parameterMap.containsKey(WELFARE_TAB_INDEX)) {
                tabIndex = parameterMap.get(WELFARE_TAB_INDEX);
            }

            if (parameterMap.containsKey(WELFARE_PAGE_LEVEL)) {
                pageLevel = parameterMap.get(WELFARE_PAGE_LEVEL);
            }
        }
        try {
            int pageLevelInt = Integer.parseInt(pageLevel);
            Bundle bundle = new Bundle();
            bundle.putInt(RouteConstant.ONLINE_PAGE_EXTRA_PRAMS_TABINDEX, Integer.parseInt(tabIndex));
            bundle.putInt(RouteConstant.ONLINE_PAGE_EXTRA_PRAMS_PAGE_LEVEL, pageLevelInt);
            if (pageLevelInt == 1) {
                //跳转福利tab
                JumpActivityUtil.goFind(getBindActivity(), null, bundle);
                return true;
            } else if (pageLevelInt == 2) {
                //跳转每日福利
                JumpActivityUtil.goWelfareActivity(getBindActivity(), bundle);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

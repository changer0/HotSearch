package com.qq.reader.qurl.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;
import java.util.Map;

/**
 * @author zhanglulu on 2019/3/26.
 * for 道具相关
 */
public class URLServerOfProps extends URLServer {
    //使用状态
    private final String PROPS_MY_CONVERT = "myconvert";
    private final String PROPS_MY_CONVERT_TAB_INDEX = "tabIndex";
    private final String PROPS_MY_CONVERT_TITLE = "title";


    public URLServerOfProps(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> mMatchServerActionPool) {
        mMatchServerActionPool.add(PROPS_MY_CONVERT);
    }

    @Override
    public boolean parserURL() throws Exception {
        String serverAction = getServerAction();
        if (TextUtils.equals(serverAction, PROPS_MY_CONVERT)) {
            Map<String, String> parameterMap = getParameterMap();
            String tabIndex = "0";
            String title = null;
            if (parameterMap != null) {
                if (parameterMap.containsKey(PROPS_MY_CONVERT_TAB_INDEX)) {
                    tabIndex = parameterMap.get(PROPS_MY_CONVERT_TAB_INDEX);
                }

                if (parameterMap.containsKey(PROPS_MY_CONVERT_TITLE)) {
                    title = parameterMap.get(PROPS_MY_CONVERT_TITLE);
                }
            }
            try {
                JumpActivityUtil.goMyTicketConvert(getBindActivity(), Integer.valueOf(tabIndex), title);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}

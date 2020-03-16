package com.qq.reader.qurl.impl;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.qq.reader.common.monitor.statparam.OriginStatParam;
import com.qq.reader.dispatch.RouteConstant;
import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;
import com.tencent.mars.xlog.Log;

import java.util.List;

/**
 * Created by yangtao on 2015/8/24.
 */
public class URLServerOfBook extends URLServer {
    private final String SERVER_ACTION_DETAIL = "detail";

    public URLServerOfBook(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> matchServerActionPool) {
        matchServerActionPool.add(SERVER_ACTION_DETAIL);
    }

    @Override
    public boolean parserURL() throws Exception {
        String value = getServerAction();
        switch (value) {
            case SERVER_ACTION_DETAIL:
                goBookDetail();
                return true;
        }
        return false;
    }

    public void goBookDetail() {
        if (getParameterMap() != null) {
            try {
                String bid = getParameterMap().get("bid");
                String alg = getParameterMap().get("alg");
                String origin = getParameterMap().get("origin");
                Bundle extraBundle = new Bundle();
                //是否直接下载
                String directdownload = getParameterMap().get("directdownload");
                if (TextUtils.equals(directdownload, "1")) {
                    extraBundle.putInt(RouteConstant.NOTIFY_TAG,
                            RouteConstant.CHAPTER_BAT_DOWNLOAD_FAILED);
                }

                //是否由外部deepLink唤起
                extraBundle.putString(RouteConstant.DEEP_LINK_BACK_URL, getParameterMap().get(RouteConstant.DEEP_LINK_BACK_URL));
                extraBundle.putString(RouteConstant.DEEP_LINK_BACK_NAME, getParameterMap().get(RouteConstant.DEEP_LINK_BACK_NAME));

                JumpActivityUtil.goBookDetail(getBindActivity(), bid, new OriginStatParam().setAlg(alg).setOrigin(origin), extraBundle, getJumpActivityParameter());
            } catch (Exception e) {
                Log.printErrStackTrace("URLServerOfBook", e, null, null);
                Log.e("error", e.getMessage());
            }

        }
    }
}

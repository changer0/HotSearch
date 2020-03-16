package com.qq.reader.qurl.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.qq.reader.core.BaseApplication;
import com.qq.reader.core.readertask.ReaderTaskHandler;
import com.qq.reader.core.readertask.tasks.ReaderJSONNetTaskListener;
import com.qq.reader.core.readertask.tasks.ReaderProtocolJSONTask;
import com.qq.reader.core.readertask.tasks.ReaderProtocolTask;
import com.qq.reader.dispatch.R;
import com.qq.reader.dispatch.RouteConstant;
import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLCenter;
import com.qq.reader.qurl.URLServer;
import com.tencent.mars.xlog.Log;

import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.List;

/**
 * Created by yangtao on 2015/8/24.
 */
public class URLServerOfClient extends URLServer {
    private static final String SERVER_ACTION_REWARD = "reward";
    private static final String SERVER_ACTION_RECOMMEND = "recommend";
    private static final String SERVER_ACTION_MONTHLYTICKET = "monthlyticket";
    private static final String SERVER_ACTION_SUGGESTION = "suggestion";
    private static final String SERVER_ACTION_READEPAGE = "readepage";
    private static final String SERVER_ACTION_BOOKSHELF = "bookshelf";
    private static final String SERVER_ACTION_TOAST = "toast";
    private static final String SERVER_ACTION_ADV_JUMP = "advjump";
    private static final String SERVER_ACTION_SKIN_LIST = "skinlist";
    private static final String SERVER_ACTION_SKIN_DETAIL = "skin";
    private static final String SERVER_ACTION_PLAYBOOK = "listenpage";
    private static final String SERVER_ACTION_INTERACTIVE = "interactive";
    private static final String SERVER_ACTION_RECOMMEND_PAGE = "recommendpage";
    private static final String SERVER_ACTION_USERCENTER_PAGE = "usercenterpage";
    private static final String SERVER_ACTION_ACTIVITY_AREA = "activityarea";
    private static final String SERVER_ACTION_ME = "me";

    public URLServerOfClient(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> matchServerActionPool) {
        matchServerActionPool.add(SERVER_ACTION_REWARD);
        matchServerActionPool.add(SERVER_ACTION_RECOMMEND);
        matchServerActionPool.add(SERVER_ACTION_MONTHLYTICKET);
        matchServerActionPool.add(SERVER_ACTION_SUGGESTION);
        matchServerActionPool.add(SERVER_ACTION_READEPAGE);
        matchServerActionPool.add(SERVER_ACTION_BOOKSHELF);
        matchServerActionPool.add(SERVER_ACTION_TOAST);
        matchServerActionPool.add(SERVER_ACTION_ADV_JUMP);
        matchServerActionPool.add(SERVER_ACTION_SKIN_LIST);
        matchServerActionPool.add(SERVER_ACTION_SKIN_DETAIL);
        matchServerActionPool.add(SERVER_ACTION_PLAYBOOK);
        matchServerActionPool.add(SERVER_ACTION_INTERACTIVE);
        matchServerActionPool.add(SERVER_ACTION_RECOMMEND_PAGE);
        matchServerActionPool.add(SERVER_ACTION_USERCENTER_PAGE);
        matchServerActionPool.add(SERVER_ACTION_ACTIVITY_AREA);
        matchServerActionPool.add(SERVER_ACTION_ME);
    }

    @Override
    public boolean parserURL() throws Exception {
        String value = getServerAction();
        switch (value) {
            case SERVER_ACTION_MONTHLYTICKET:
                return true;
            case SERVER_ACTION_RECOMMEND:
                return true;
            case SERVER_ACTION_REWARD:
                return true;
            case SERVER_ACTION_SUGGESTION:
                goSuggestion();
                return true;
            case SERVER_ACTION_BOOKSHELF:
                gotoBookShelf();
                return true;
            case SERVER_ACTION_READEPAGE:
                goReadPage();
                return true;
            case SERVER_ACTION_TOAST:
                goToast();
                return true;
            case SERVER_ACTION_ADV_JUMP:
                goAdvJump();
                return true;
            case SERVER_ACTION_SKIN_LIST:
                gotoSkinList();
                return true;
            case SERVER_ACTION_SKIN_DETAIL:
                //TODO zdy 先注掉，飞读目前没有换肤功能
//                gotoSkinDetail();
                return true;
            case SERVER_ACTION_PLAYBOOK:
                goPlayBook();
                return true;
            case SERVER_ACTION_INTERACTIVE:
                return true;
            case SERVER_ACTION_RECOMMEND_PAGE:
                goRecommendPage();
                return true;
            case SERVER_ACTION_USERCENTER_PAGE:
                gotoUserCenterPage();
                return true;
            case SERVER_ACTION_ACTIVITY_AREA:
                goActivityArea();
                return true;
            case SERVER_ACTION_ME:
                goMine();
                return true;
        }
        return false;
    }

    public void gotoSkinList() {
        JumpActivityUtil.goSkinManage(getBindActivity(), getJumpActivityParameter());
    }

    public void goAdvJump() {
        if (getParameterMap() != null) {
            final String protocol_qurl = getParameterMap().get("pro_qurl");
            final String success_jump_qurl = getParameterMap().get("suc_qurl");

            if (!TextUtils.isEmpty(protocol_qurl)) {
                ReaderProtocolJSONTask netTask = new ReaderProtocolJSONTask(new ReaderJSONNetTaskListener() {
                    @Override
                    public void onConnectionRecieveData(ReaderProtocolTask t, String str, long contentLength) {
                        if (!TextUtils.isEmpty(str)) {
                            try {
                                //加上简单的回调
                                Message callbackmsg = Message.obtain();
                                callbackmsg.obj = str;
                                if (doURLJumpReslut(callbackmsg)) {
                                    return;
                                }

                                //默认的回调
                                JSONObject json = new JSONObject(str);
                                boolean isSuc = json.optBoolean("suc", false);
                                final String msg = json.optString("msg");
                                Activity act = getBindActivity();
                                if (isSuc) {
                                    if (!TextUtils.isEmpty(success_jump_qurl)) {
                                        if ((act != null && !act.isFinishing())) {
                                            act.runOnUiThread(() -> URLCenter.excuteURL(getBindActivity(), success_jump_qurl));
                                        }
                                    } else {
                                        if (!TextUtils.isEmpty(msg) && act != null && !act.isFinishing()) {
                                            act.runOnUiThread(() -> Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_SHORT).show());
                                        }
                                    }
                                } else {
                                    if (!TextUtils.isEmpty(msg) && act != null && !act.isFinishing()) {
                                        act.runOnUiThread(() -> Toast.makeText(BaseApplication.getInstance(), msg, Toast.LENGTH_SHORT).show());
                                    }
                                }
                            } catch (Exception e) {
                                Log.printErrStackTrace("URLServerOfClient", e, null, null);
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onConnectionError(ReaderProtocolTask t, Exception e) {
                        Activity act = getBindActivity();
                        if (act != null && !act.isFinishing()) {
                            act.runOnUiThread(() -> Toast.makeText(BaseApplication.getInstance(), R.string.net_not_available, Toast.LENGTH_SHORT).show());
                        }
                    }
                });
                netTask.setUrl(protocol_qurl);
                //netTask.setmHeaders(CommonNetUtils.getCommonHeaders());
                ReaderTaskHandler.getInstance().addTask(netTask);
            }
        }
    }

    public void goToast() {
        if (getParameterMap() != null) {
            String content = getParameterMap().get("content");
            if (!TextUtils.isEmpty(content)) {
//                try {
//                    content = java.net.URLDecoder.decode(content, "utf-8");
                Toast.makeText(BaseApplication.getInstance(), content, Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }

    }

    public void goReadPage() {
        if (getParameterMap() != null) {
            String bid = getParameterMap().get("bid");
            String cidStr = getParameterMap().get("cid");
            String offestStr = getParameterMap().get("offest");

            //M栈调起浏览器搜索引擎来源
            String msiteSearchEngine = getParameterMap().get("mobile");

            //单书调起来源
            String startAppFrom = getParameterMap().get("from");

            int cid = -1;
            int offset = -1;
            try {
                cid = Integer.valueOf(cidStr);
            } catch (Exception e) {
                Log.printErrStackTrace("URLServerOfClient", e, null, null);
                cid = -1;
            }
            try {
                offset = Integer.valueOf(offestStr);
            } catch (Exception e) {
                Log.printErrStackTrace("URLServerOfClient", e, null, null);
                offset = -1;
            }
            //是否由外部deepLink唤起
            Bundle bundle = new Bundle();
            bundle.putString(RouteConstant.DEEP_LINK_BACK_URL, getParameterMap().get(RouteConstant.DEEP_LINK_BACK_URL));
            bundle.putString(RouteConstant.DEEP_LINK_BACK_NAME, getParameterMap().get(RouteConstant.DEEP_LINK_BACK_NAME));
            bundle.putBoolean(RouteConstant.NEED_SEX_DIALOG, Boolean.parseBoolean(getParameterMap().get(RouteConstant.NEED_SEX_DIALOG)));
            bundle.putString(RouteConstant.FIRST_START_APP_FROM_MSITE_SEARCH_ENGINE, msiteSearchEngine);
            bundle.putString(RouteConstant.TYPE_FIRST_START_APP, startAppFrom);
            JumpActivityUtil.goReaderPage(getBindActivity(), bid, cid, offset, getJumpActivityParameter().setFlag(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    public void gotoBookShelf() {
        Bundle bundle = new Bundle();
        //是否由外部deepLink唤起
        if (getParameterMap() != null) {
            bundle.putString(RouteConstant.DEEP_LINK_BACK_URL, getParameterMap().get(RouteConstant.DEEP_LINK_BACK_URL));
            bundle.putString(RouteConstant.DEEP_LINK_BACK_NAME, getParameterMap().get(RouteConstant.DEEP_LINK_BACK_NAME));
        }
        JumpActivityUtil.goBookshelf(getBindActivity(), bundle, getJumpActivityParameter());
    }


    public void goSuggestion() {
        JumpActivityUtil.goFeedBack(getBindActivity(), getJumpActivityParameter());
    }

    public void goPlayBook() {
        if (getParameterMap() != null) {
            String bid = getParameterMap().get("mediaId");
            JumpActivityUtil.goPlayerActivity(getBindActivity(), Long.valueOf(bid), getJumpActivityParameter());
        }
    }

    public void goRecommendPage() {
        if (getParameterMap() != null) {
            String bid = getParameterMap().get("bid");
            JumpActivityUtil.goRecommendPage(getBindActivity(), bid, getJumpActivityParameter());
        }
    }

    public void gotoUserCenterPage() {
        if (getParameterMap() != null) {
            try {
                String userId = getParameterMap().get("userId");
                String userNickName = URLDecoder.decode(getParameterMap().get("userNickName"), "utf-8");
                String userIconUrl = getParameterMap().get("userIconUrl");
                JumpActivityUtil.goUserCenter(getBindActivity(),
                        userId,
                        userNickName,
                        userIconUrl,
                        getJumpActivityParameter());
            } catch (Exception e) {
                Log.printErrStackTrace("URLServerOfClient", e, null, null);
            }
        }
    }

    //活动专区
    public void goActivityArea() {
        String index = "0";
        if (getParameterMap() != null) {
            index = getParameterMap().get("index");

            if (TextUtils.isEmpty(index) || (!index.equals("0") && !index.equals("1"))) {
                index = "0";
            }
        }
        JumpActivityUtil.goActivityArea(false, getBindActivity(), Integer.parseInt(index));
    }

    //我的
    public void goMine() {
        JumpActivityUtil.goMine(getBindActivity(), getJumpActivityParameter());
    }
}

package com.qq.reader.qurl.impl;

import android.app.Activity;
import android.os.Bundle;

import com.qq.reader.dispatch.NativeAction;
import com.qq.reader.dispatch.R;
import com.qq.reader.dispatch.RouteConstant;
import com.qq.reader.qurl.JumpActivityParameter;
import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;
import com.qq.reader.utils.Utility;

import java.util.List;

/**
 * Created by yangtao on 2015/8/24.
 */
public class URLServerOfInfoStream extends URLServer {
    private final String SERVER_ACTION_LIST = "list";
    private final String SERVER_ACTION_BOUTIQUE = "boutiques";
    private final String SERVER_ACTION_DAILYREAD = "dailyreading";
    private final String SERVER_ACTION_PERSONALITY = "individualbooklist";
    private final String SERVER_ACTION_RECOMMEND = "virtualrecommend";


    public URLServerOfInfoStream(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> matchServerActionPool) {
        matchServerActionPool.add(SERVER_ACTION_LIST);
        matchServerActionPool.add(SERVER_ACTION_BOUTIQUE);
        matchServerActionPool.add(SERVER_ACTION_DAILYREAD);
        matchServerActionPool.add(SERVER_ACTION_PERSONALITY);
        matchServerActionPool.add(SERVER_ACTION_RECOMMEND);
    }

    @Override
    public boolean parserURL() throws Exception {

        String serverAction = getServerAction();
        if (SERVER_ACTION_LIST.equalsIgnoreCase(serverAction)) {
            goInfoStream();
            return true;
        } else if (SERVER_ACTION_BOUTIQUE.equalsIgnoreCase(serverAction)) {
            goBoutique();
            return true;
        }
//        else if (SERVER_ACTION_COLUMN.equalsIgnoreCase(serverAction)) {
//            goSpecialColumn();
//        }else if(SERVER_ACTION_RECOMMEND.equalsIgnoreCase(serverAction)){
//            goRecommend();
//        }
        else if (SERVER_ACTION_DAILYREAD.equals(serverAction)) {
            goDailyreading();
            return true;
        } else if (SERVER_ACTION_RECOMMEND.equalsIgnoreCase(serverAction)) {
            goRecommend();
            return true;
        } else if (SERVER_ACTION_PERSONALITY.equals(serverAction)) {
            goPersonality();
            return true;
        }
//         else if (SERVER_ACTION_SECONDPAGE.equals(serverAction)) {
//            goSecondPage();
//        } else if (SERVER_ACTION_THIRDPAGE.equals(serverAction)) {
//            goThirdPage();
//        }

//        int value = ServerAction.getActionValue(getServerAction());
//        switch (value) {
//            case ServerAction.SERVER_ACTION_LIST:
//                goInfoStream();
//                break;
//            case ServerAction.SERVER_ACTION_BOUTIQUE:
//                goBoutique();
//                break;
//        }
        return false;
    }

    private void goRecommend() {
        JumpActivityUtil.goVirtualRecommendTabPage(getBindActivity()
                ,getJumpActivityParameter());
    }

    private void goDailyreading() {
        Bundle bundle = new Bundle();
        String title = Utility.getStringById(R.string.today_selection);
        String fromTitle = "";
        if (getParameterMap() != null) {
            String cloumnId = getParameterMap().get(RouteConstant.LOCAL_CLOUMN_ID);
            String bid = getParameterMap().get(NativeAction.URL_BUILD_PERE_BIDS);
            title = getParameterMap().get(RouteConstant.LOCAL_STORE_IN_TITLE);
            fromTitle = getParameterMap().get(RouteConstant.FROM_TITLE);
//            String plan = getParameterMap().get(NativeAction.URL_BUILD_PERE_PLAN);
            bundle.putString(NativeAction.URL_BUILD_PERE_BIDS, bid);
            bundle.putString(RouteConstant.LOCAL_CLOUMN_ID, cloumnId);
//            bundle.putString(NativeAction.PARA_ABTEST_PARAM, plan);
        }
        bundle.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_NATIVE_TODAYREAD);
        bundle.putString(RouteConstant.LOCAL_STORE_IN_TITLE, title);
        bundle.putString(RouteConstant.FROM_TITLE, fromTitle);
        JumpActivityUtil.goTodayRecommond(getBindActivity(), bundle, getJumpActivityParameter());
    }

    private void goPersonality() {
        // TODO 跳转到专属推荐
        Bundle bundle = new Bundle();
//        if (getParameterMap() != null) {
//            String bid = getParameterMap().get(URL_BUILD_PERE_BIDS);
//            String needGeneInfo = getParameterMap().get("needGeneInfo");
//            String fromgene = getParameterMap().get("fromgene");
//            if ("2".equals(needGeneInfo)) {
//                JumpActivityUtil.goBookListChangeGeneNeedFresh(getBindActivity());
//                return;
//            } else {
//                bundle.putString(URL_BUILD_PERE_BIDS, bid);
//                bundle.putString("needGeneInfo", needGeneInfo);
//                bundle.putString(Constant.PREFERENCE_FROM_GENE, fromgene);
//            }
//
//        }
//        bundle.putString(NativeAction.KEY_JUMP_PAGENAME, Constant.PAGE_NAME_NATIVE_PERSONALITY_BOOKS);
//        bundle.putString(Constant.LOCAL_STORE_IN_TITLE,  "专属推荐");
//        JumpActivityUtil.goTwoLevelActivity(getBindActivity(), bundle, getJumpActivityParameter());
    }

    public void goBoutique() {
//        if (getParameterMap() != null) {
        JumpActivityUtil.goWellChosenBookStore(getBindActivity(), null, getJumpActivityParameter());
//        }
    }

    public void goInfoStream() {
        Bundle bundle = new Bundle();
        //是否由外部deepLink唤起
        if (getParameterMap() != null) {
            bundle.putString(RouteConstant.DEEP_LINK_BACK_URL, getParameterMap().get(RouteConstant.DEEP_LINK_BACK_URL));
            bundle.putString(RouteConstant.DEEP_LINK_BACK_NAME, getParameterMap().get(RouteConstant.DEEP_LINK_BACK_NAME));
        }
        JumpActivityParameter jumpActivityParameter = getJumpActivityParameter();
        if (jumpActivityParameter == null) {
            jumpActivityParameter = new JumpActivityParameter();
            jumpActivityParameter.setJsonParamater(getParameterMap());
        }
        JumpActivityUtil.goFeed(getBindActivity(), bundle, jumpActivityParameter);
    }


}

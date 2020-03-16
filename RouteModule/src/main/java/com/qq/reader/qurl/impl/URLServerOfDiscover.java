package com.qq.reader.qurl.impl;

import android.app.Activity;

import com.qq.reader.core.BaseApplication;
import com.qq.reader.dispatch.R;
import com.qq.reader.qurl.JumpActivityUtil;
import com.qq.reader.qurl.URLServer;

import java.util.List;

/**
 * Created by yangtao on 2015/8/24.
 */
public class URLServerOfDiscover extends URLServer {
    private final String SERVER_ACTION_INDEX = "index";
    private final String SERVER_ACTION_VIPZONE = "vipzone";
    private final String SERVER_ACTION_TODAYFREE = "todayfree";
    private final String SERVER_ACTION_LISTENZONE = "listenzone";
    private final String SERVER_ACTION_SPECIALTOPIC = "specialtopic";
    private final String SERVER_ACTION_FINISH = "finishbook";
    private final String SERVER_ACTION_CLASSIC = "classic";
    private final String SERVER_ACTION_FLASH_BUY = "limittimediscountbuy";
    private final String SERVER_ACTION_EDITOR_CHOIC = "editorchoice";
    private final String SERVER_ACTION_CLASSIC_INDEX = "authorsindex";


    public URLServerOfDiscover(Activity activity, String serverAction, String parameter) {
        super(activity, serverAction, parameter);
    }

    @Override
    public void initMatchServerActionPool(List<String> matchServerActionPool) {
        matchServerActionPool.add(SERVER_ACTION_INDEX);
        matchServerActionPool.add(SERVER_ACTION_VIPZONE);
        matchServerActionPool.add(SERVER_ACTION_TODAYFREE);
        matchServerActionPool.add(SERVER_ACTION_LISTENZONE);
        matchServerActionPool.add(SERVER_ACTION_SPECIALTOPIC);
        matchServerActionPool.add(SERVER_ACTION_FINISH);
        matchServerActionPool.add(SERVER_ACTION_CLASSIC);
        matchServerActionPool.add(SERVER_ACTION_FLASH_BUY);
        matchServerActionPool.add(SERVER_ACTION_EDITOR_CHOIC);
        matchServerActionPool.add(SERVER_ACTION_CLASSIC_INDEX);
    }

    @Override
    public boolean parserURL() throws Exception {
        String value = getServerAction();
        switch (value) {
            case SERVER_ACTION_INDEX:
                goFindTab();
                return true;
            case SERVER_ACTION_VIPZONE:
                goVipZone();
                return true;
            case SERVER_ACTION_TODAYFREE:
                gotoTodayFree();
                return true;
            case SERVER_ACTION_LISTENZONE:
                goListenZone();
                return true;
            case SERVER_ACTION_SPECIALTOPIC:
                goSpecialTopic();
                return true;
            case SERVER_ACTION_FINISH:
                goEndBook();
                return true;
            case SERVER_ACTION_CLASSIC:
                goClassic();
                return true;
            case SERVER_ACTION_FLASH_BUY:
                goLimitTimeDiscountBuy();
                return true;
            case SERVER_ACTION_EDITOR_CHOIC:
                goEditorChoice();
                return true;
            case SERVER_ACTION_CLASSIC_INDEX:
                goClassicIndex();
                return true;
        }
        return false;
    }

    public void gotoTodayFree() {
        JumpActivityUtil.goTodayFree(getBindActivity(), getJumpActivityParameter(), BaseApplication.getInstance().getResources().getString(R.string.bookrecommend));
    }

    public void goFindTab() {
        JumpActivityUtil.goFind(getBindActivity(), getJumpActivityParameter());
    }

    public void goVipZone() {
        JumpActivityUtil.goMonthArea(getBindActivity(), getJumpActivityParameter(), BaseApplication.getInstance().getResources().getString(R.string.bookrecommend));
    }

    public void goEndBook() {
        JumpActivityUtil.goEndBookTwoLevel(getBindActivity(), null);
    }

    public void goListenZone() {
        JumpActivityUtil.goListenZone(getBindActivity(), false, getJumpActivityParameter());
    }

    public void goSpecialTopic() {
        JumpActivityUtil.goSpecialTopic(getBindActivity(), null, "0", null);
    }

    public void goClassic() {
        JumpActivityUtil.goClassic(getBindActivity(), null);
    }

    public void goClassicIndex() {
        JumpActivityUtil.goClassicAuthorIndex(getBindActivity(), null);
    }

    public void goLimitTimeDiscountBuy(){
        JumpActivityUtil.goLimitTimeDiscountBuy(getBindActivity(), null);
    }

    public void goEditorChoice(){
        JumpActivityUtil.goEditorChoice(getBindActivity(), null);
    }
}

package com.qq.reader.qurl;

import androidx.collection.SimpleArrayMap;

import com.tencent.mars.xlog.Log;

/**
 * Created by yangtao on 2015/8/21.
 */
public class ServerAction {
    public static final int SERVER_ACTION_DETAIL = 1;
    public static final int SERVER_ACTION_PRIVILEGE = 2;
    public static final int SERVER_ACTION_OPEN = 3;
    public static final int SERVER_ACTION_RECHARGE = 4;
    public static final int SERVER_ACTION_INDEX = 5;
    public static final int SERVER_ACTION_REWARD = 6;
    public static final int SERVER_ACTION_RECOMMEND = 7;
    public static final int SERVER_ACTION_MONTHLYTICKET = 8;
    public static final int SERVER_ACTION_SUGGESTION = 9;
    public static final int SERVER_ACTION_ADDCOMMENT = 10;
    public static final int SERVER_ACTION_INDEXFORCOMMONZONE = 11;
    public static final int SERVER_ACTION_READEPAGE = 12;
    public static final int SERVER_ACTION_BOOKSHELF = 13;
    public static final int SERVER_ACTION_LIST = 14;
    public static final int SERVER_ACTION_VIPZONE = 15;
    public static final int SERVER_ACTION_TODAYFREE = 16;
    public static final int SERVER_ACTION_BOUTIQUE = 17;
    public static final int SERVER_ACTION_ZONELIST = 18;
    public static final int SERVER_ACTION_TOAST = 19;
    public static final int SERVER_ACTION_ADV_JUMP = 20;
    public static final int SERVER_ACTION_WEBPAGE_USERLIKE = 21;
    public static final int SERVER_ACTION_WEBPAGE_GAME = 22;
    public static final int SERVER_ACTION_SKIN_LIST = 23;
    public static final int SERVER_ACTION_SKIN_DETAIL = 24;
    public static final int SERVER_ACTION_CHAPTER = 25;
    public static final int SERVER_ACTION_REPLYLIST = 26;
    public static final int SERVER_ACTION_PLAYBOOK = 27;
    public static final int SERVER_ACTION_INTERACTIVE = 28;
    public static final int SERVER_ACTION_LISTENZONE = 29;
    public static final int SERVER_ACTION_RECOMMEND_PAGE = 30;
    public static final int SERVER_ACTION_AUTHOR_PRODUCT = 31;
    public static final int SERVER_ACTION_AUTHOR_MAINPAGE= 32;
    public static final int SERVER_ACTION_USERCENTER_PAGE = 33;
    public static final int SERVER_ACTION_SPECIALTOPIC = 34;
    public static final int SERVER_ACTION_FINISH = 35;
    public static final int SERVER_ACTION_CLASSIC = 36;
    public static final int SERVER_ACTION_FLASH_BUY = 37;
    public static final int SERVER_ACTION_EDITOR_CHOIC = 38;
    public static final int SERVER_ACTION_AUTHOR_ALL_BOOKS = 39;
    public static final int SERVER_ACTION_CLASSIC_INDEX = 40;
    public static final int SERVER_ACTION_ACTIVITY_AREA = 41;

    private static SimpleArrayMap<String, Integer> mServerActionMap = new SimpleArrayMap<String, Integer>(40);


    private static synchronized void init() {
        if (mServerActionMap.size() > 0) {
            return;
        }
        mServerActionMap.put("detail", SERVER_ACTION_DETAIL);
        mServerActionMap.put("privilege", SERVER_ACTION_PRIVILEGE);
        mServerActionMap.put("open", SERVER_ACTION_OPEN);
        mServerActionMap.put("recharge", SERVER_ACTION_RECHARGE);
        mServerActionMap.put("index", SERVER_ACTION_INDEX);
        mServerActionMap.put("reward", SERVER_ACTION_REWARD);
        mServerActionMap.put("recommend", SERVER_ACTION_RECOMMEND);
        mServerActionMap.put("monthlyticket", SERVER_ACTION_MONTHLYTICKET);
        mServerActionMap.put("suggestion", SERVER_ACTION_SUGGESTION);
        mServerActionMap.put("addcomment", SERVER_ACTION_ADDCOMMENT);
        mServerActionMap.put("indexforcommonzone", SERVER_ACTION_INDEXFORCOMMONZONE);
        mServerActionMap.put("readepage", SERVER_ACTION_READEPAGE);
        mServerActionMap.put("bookshelf", SERVER_ACTION_BOOKSHELF);
        mServerActionMap.put("list", SERVER_ACTION_LIST);
        mServerActionMap.put("vipzone", SERVER_ACTION_VIPZONE);
        mServerActionMap.put("todayfree", SERVER_ACTION_TODAYFREE);
        mServerActionMap.put("boutiques", SERVER_ACTION_BOUTIQUE);
        mServerActionMap.put("zonelist", SERVER_ACTION_ZONELIST);
        mServerActionMap.put("toast", SERVER_ACTION_TOAST);
        mServerActionMap.put("advjump", SERVER_ACTION_ADV_JUMP);
        mServerActionMap.put("userlike", SERVER_ACTION_WEBPAGE_USERLIKE);
        mServerActionMap.put("game", SERVER_ACTION_WEBPAGE_GAME);
        mServerActionMap.put("skinlist", SERVER_ACTION_SKIN_LIST);
        mServerActionMap.put("skin", SERVER_ACTION_SKIN_DETAIL);
        mServerActionMap.put("chapter", SERVER_ACTION_CHAPTER);
        mServerActionMap.put("replylist", SERVER_ACTION_REPLYLIST);
        mServerActionMap.put("listenpage", SERVER_ACTION_PLAYBOOK);
        mServerActionMap.put("interactive", SERVER_ACTION_INTERACTIVE);
        mServerActionMap.put("listenzone", SERVER_ACTION_LISTENZONE);
        mServerActionMap.put("recommendpage", SERVER_ACTION_RECOMMEND_PAGE);
        mServerActionMap.put("product", SERVER_ACTION_AUTHOR_PRODUCT);
        mServerActionMap.put("mainpage", SERVER_ACTION_AUTHOR_MAINPAGE);
        mServerActionMap.put("usercenterpage", SERVER_ACTION_USERCENTER_PAGE);
        mServerActionMap.put("specialtopic", SERVER_ACTION_SPECIALTOPIC);
        mServerActionMap.put("finishbook", SERVER_ACTION_FINISH);
        mServerActionMap.put("classic", SERVER_ACTION_CLASSIC);
        mServerActionMap.put("limittimediscountbuy", SERVER_ACTION_FLASH_BUY);
        mServerActionMap.put("editorchoice", SERVER_ACTION_EDITOR_CHOIC);
        mServerActionMap.put("allbooks", SERVER_ACTION_AUTHOR_ALL_BOOKS);
        mServerActionMap.put("authorsindex", SERVER_ACTION_CLASSIC_INDEX);
        mServerActionMap.put("activityarea", SERVER_ACTION_ACTIVITY_AREA);
    }

    public static int getActionValue(String name) {
        try {
            if (mServerActionMap.size() == 0) {
                init();
            }
            return mServerActionMap.get(name).intValue();
        } catch (Exception e) {
            Log.printErrStackTrace("ServerAction", e, null, null);
            return -1;
        }
    }
}
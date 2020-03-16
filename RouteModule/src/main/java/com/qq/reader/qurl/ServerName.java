package com.qq.reader.qurl;

import androidx.collection.SimpleArrayMap;

import com.tencent.mars.xlog.Log;

/**
 * Created by yangtao on 2015/8/21.
 */
public class ServerName {
    public static final int SERVER_NAME_BOOK = 1;
    public static final int SERVER_NAME_TOPIC = 2;
    public static final int SERVER_NAME_COIN = 3;
    public static final int SERVER_NAME_VIP = 4;
    public static final int SERVER_NAME_COMMENT = 5;
    public static final int SERVER_NAME_CLIENT = 6;
    public static final int SERVER_NAME_READGENE = 7;
    public static final int SERVER_NAME_INFOSTREAM = 8;
    public static final int SERVER_NAME_CATEGORY = 9;
    public static final int SERVER_NAME_DISCOVER = 10;
    public static final int SERVER_NAME_RANK = 11;
    public static final int SERVER_NAME_GETACCINFO = 12;
    public static final int SERVER_NAME_FINDBOOK = 13;
    public static final int SERVER_NAME_AUTHORS = 14;
    public static final int SERVER_NAME_WEBPAGE = 15;
    public static final int SERVER_NAME_SEARCH = 16;
    public static final int SERVER_NAME_TAG = 17;
    public static final int SERVER_NAME_PUBLISHER = 18;
    public static final int SERVER_NAME_FREE_MONTH_SEARCH = 19;//免费，包月区搜索作者相关的免费，包月书
    public static final int SERVER_NAME_NEW_BOOK = 20;//新书
    public static final int SERVER_NAME_CHANNEL = 21;//频道页
    public static final int SERVER_NAME_PROPS = 22;//道具列表
    public static final int SERVER_NAME_ADSDK = 23;//广告SDK
    public static final int SERVER_NAME_WEB_WELFARE = 24;//福利页
    public static final int SERVER_NAME_BACK = 25;//finish当前页面qurl
    private static SimpleArrayMap<String, Integer> mServerNameMap = new SimpleArrayMap<String, Integer>(24);

    private static synchronized void init() {
        if (mServerNameMap.size() > 0) {
            return;
        }
        mServerNameMap.put("book", SERVER_NAME_BOOK);
        mServerNameMap.put("topic", SERVER_NAME_TOPIC);
        mServerNameMap.put("coin", SERVER_NAME_COIN);
        mServerNameMap.put("vip", SERVER_NAME_VIP);
        mServerNameMap.put("comment", SERVER_NAME_COMMENT);
        mServerNameMap.put("client", SERVER_NAME_CLIENT);
        mServerNameMap.put("readgene", SERVER_NAME_READGENE);
        mServerNameMap.put("infostream", SERVER_NAME_INFOSTREAM);
        mServerNameMap.put("category", SERVER_NAME_CATEGORY);
        mServerNameMap.put("discover", SERVER_NAME_DISCOVER);
        mServerNameMap.put("rank", SERVER_NAME_RANK);
        mServerNameMap.put("getAcctInfo", SERVER_NAME_GETACCINFO);
        mServerNameMap.put("findbook", SERVER_NAME_FINDBOOK);
        mServerNameMap.put("authors", SERVER_NAME_AUTHORS);
        mServerNameMap.put("webpage", SERVER_NAME_WEBPAGE);
        mServerNameMap.put("search", SERVER_NAME_SEARCH);
        mServerNameMap.put("tag", SERVER_NAME_TAG);
        mServerNameMap.put("publisher", SERVER_NAME_PUBLISHER);
        mServerNameMap.put("authorfree", SERVER_NAME_FREE_MONTH_SEARCH);
        mServerNameMap.put("bookstore", SERVER_NAME_NEW_BOOK);
        mServerNameMap.put("channel", SERVER_NAME_CHANNEL);
        mServerNameMap.put("props", SERVER_NAME_PROPS);
        mServerNameMap.put("adsdk", SERVER_NAME_ADSDK);
        mServerNameMap.put("webwelfare", SERVER_NAME_WEB_WELFARE);
        mServerNameMap.put("back", SERVER_NAME_BACK);
    }


    public static int getServerNameValue(String name) {
        try {
            if (mServerNameMap.size() == 0) {
                init();
            }
            return mServerNameMap.get(name).intValue();
        } catch (Exception e) {
            Log.printErrStackTrace("ServerName", e, null, null);
            return -1;
        }
    }
}

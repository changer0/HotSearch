package com.lulu.hotsearch;

import com.lulu.basic.kvstorage.KVStorage;
import com.lulu.hotsearch.define.Constant;

/**
 * Author: zhanglulu
 * Time: 2021/2/15
 */
public class HotSearchKVStorage extends KVStorage {
    private static final String SP_NAME = "SP_NAME_HOT_SEARCH";


    public static final String LAST_TYPE = "LAST_TYPE";

    public static void setLastType(String lastType) {
        doCommit(obtainEditor(SP_NAME).putString(LAST_TYPE, lastType));
    }
    public static String getLastType() {
        return obtainSP(SP_NAME).getString(LAST_TYPE, Constant.HOT_SEARCH_WB);
    }

}

package com.qq.reader.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类、搜索模块要使用的工具类
 */
public class SearchModuleUtils {

    /**
     * 新分类二级页用来传参的
     */
    public final static String KEY_CATE_PAGE_TAG = "KEY_CATE_PAGE_TAG";//标签
    public final static String KEY_CATE_PAGE_STATUS = "KEY_CATE_PAGE_STATUS";//状态：0连载，1完结，2节选
    public final static String KEY_CATE_PAGE_CHARGE_TYPE = "KEY_CATE_PAGE_CHARGE_TYPE";//收费类型：0免费，1包月，6收费
    public final static String KEY_CATE_PAGE_UPDATETIME = "KEY_CATE_PAGE_UPDATETIME";//更新时间：3天以内：0 7天以内：1 15天以前：2 一个月以前
    public final static String KEY_CATE_PAGE_WORDS = "KEY_CATE_PAGE_WORDS";//字数：20w内：11  20-30w：12 30-50w：3  50-100w：24 100-200w：25  30w内： 1  50w以上 ：2 200w以上
    public final static String KEY_CATE_PAGE_SEARCH_ORDER = "KEY_CATE_PAGE_SEARCH_ORDER";//排序：2更新，3收藏，9字数，10畅销，6人气

    /**
     * 将老的分类二级页参数转换成新的，获取标签id。
     *
     * @return 返回的List，要么为空，要么至少有6个item。
     * 6个 item 分别对应：标签、状态、收费类型、更新时间、字数、排序。
     */
    public static List<String> getParamFromOldAction(String oldActionTag) {
        if (oldActionTag == null) {
            return null;
        }
        String[] split = oldActionTag.split(",");
        if (split.length < 6) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (String str : split) {
            if (TextUtils.isEmpty(str)) {
                list.add("");
                continue;
            }
            if (!str.contains(":")) {
                list.add(str);
                continue;
            }
            String[] items = str.split(":");
            list.add(items[0]);
        }
        return list;
    }

}

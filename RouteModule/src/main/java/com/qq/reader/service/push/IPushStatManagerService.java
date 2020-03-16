package com.qq.reader.service.push;

import android.content.Intent;

import com.alibaba.android.arouter.facade.template.IProvider;

import org.json.JSONObject;

import java.util.Map;

/**
 * @author zhanglulu on 2020-02-11.
 * for
 */
public interface IPushStatManagerService extends IProvider {


    //push上报相关参数
    //参数：jobkey，qurl；type=0通知栏，1消息；from=0运营平台，1功能性push
    public static final String KEY_STAT_PUSH_JOBKEY = "jobKey";
    public static final String KEY_STAT_PUSH_QURL = "qurl";
    public static final String KEY_STAT_PUSH_TYPE = "type";
    public static final String KEY_STAT_PUSH_FROM = "from";
    public static final String KEY_STAT_PUSH_SRC_TYPE = "push_srctype";
    public static final String KEY_STAT_PUSH_ORIGIN = "push_origin";
    public static final String KEY_STAT_PUSH_ID = "push_id";
    public static final String KEY_STAT_PUSH_BID = "push_bid";
    public static final String KEY_STAT_PUSH_ORIGIN_JSON = "push_origin_json";


    public static final String PUSH_TYPE_NOTIFICATION = "0";
    public static final String PUSH_TYPE_THROUGH = "1";

    public static final int PUSH_FROM_OPERATIVE = 0;
    public static final int PUSH_FROM_FUNCTION = 1;

    public static final int PUSH_ACTION_RECEIVER = 0;
    public static final int PUSH_ACTION_CLICK = 1;


    //PUSH 转化率统计所需字段
    public static final String PUSH_STAT_RECOMMEND = "JX";//精选
    public static final String PUSH_STAT_DETAIL = "XQ_0";//详情
    public static final String PUSH_STAT_BOOKSHELF = "SJ";//书架
    public static final String PUSH_STAT_RANK = "PH";//排行
    public static final String PUSH_STAT_CLASSIFY = "FL";//分类
    public static final String PUSH_STAT_TOPIC = "ZT";//专题
    public static final String PUSH_STAT_MORE = "MORE";//其他


    /**
     * PUSH 到达统计
     * @param content
     * @param pushType
     * @param actionType
     */
    void pushArrivalStat(String content, int pushType, int actionType);

    ///////////////////////////////////////////////////////////////////////////
    // 工具方法
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 生成含有PUSH_ORIGIN字段的JSON字符串
     * @return
     */
    JSONObject generatePushOriginJSONObject(String pushOrigin, String pushId, String pushBid, String pushSrcType);

    /**
     * 生成含有PUSH_ORIGIN字段的JSON字符串
     * @param intent
     * @return
     */
    String generatePushOriginJSONString(Intent intent);


    /**
     * 生成含有PUSH_ORIGIN字段的JSON字符串
     * @return
     */
    String generatePushOriginJSONString(String pushOrigin, String pushId, String pushBid, String pushSrcType);

    /**
     * 生成含有PUSH_ORIGIN字段的Map
     * @param intent
     * @param type
     * @return
     */
    Map<String, String> generatePushOriginMap(Intent intent, String type);

    /**
     * 生成含有PUSH_ORIGIN字段的Map
     * @param pushOriginJson
     * @return
     */
    Map<String, String> generatePushOriginMap(String pushOriginJson,String type);

    /**
     * 生成含有PUSH_ORIGIN字段的Map
     * @param pushOrigin
     * @return
     */
    Map<String, String> generatePushOriginMap(String pushOrigin, String pushId, String pushBid,String pushSrcType, String type);

    /**
     * 清除当前Activity Intent 中的 PUSH_ORIGIN
     * @param intent
     */
    void clearIntentPushOrigin(Intent intent);

}

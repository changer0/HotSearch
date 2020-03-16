package com.qq.reader.common.monitor.statparam;


import android.text.TextUtils;
import org.json.JSONObject;

/**
 * @author zhanglulu on 2019/9/21.
 * for 工具方法
 */
public class OriginStatParamUtils {

    /**
     * 优先取 从上一个页面取过来的 Alg -> 否则去缓存的 Alg
     * @param bid
     * @param fromStatParam
     * @return
     */
    public static String getOriginParamPriorityFromAlg(String bid, OriginStatParam fromStatParam) {
        String alg = "";
        if (fromStatParam != null) {
            //优先取上个页面传过来的 ALG
            alg = fromStatParam.getAlg();
        }
        if (TextUtils.isEmpty(alg)) {
            //取不到可能是从书架来的，查缓存
            alg = OriginStatParamUtils.getOriginParamOnlyAlg(bid);
        }
        return alg;
    }

    /**
     * 优先取 从上一个页面取过来的 Origin -> 否则去缓存的 Origin
     * @param bid
     * @param fromStatParam
     * @return
     */
    public static String getOriginParamPriorityFromOrigin(String bid, OriginStatParam fromStatParam) {
        String origin = "";
        if (fromStatParam != null) {
            //优先取上个页面传过来的 ORIGIN
            origin = fromStatParam.getOrigin();
        }
        if (TextUtils.isEmpty(origin)) {
            //取不到可能是从书架来的，查缓存
            origin = OriginStatParamUtils.getOriginParamOnlyOrigin(bid);
        }
        return origin;
    }


    /**
     * 只取 Alg
     */
    public static String getOriginParamOnlyAlg(String bid) {
        OriginStatParam originStatParam = OriginStatParamHandle.getInstance().getStatParamCacheByBid(bid);
        if (originStatParam != null) {
            return originStatParam.getAlg();
        }
        return "";
    }

    /**
     * 只取 Origin
     */
    public static String getOriginParamOnlyOrigin(String bid) {
        OriginStatParam originStatParam = OriginStatParamHandle.getInstance().getStatParamCacheByBid(bid);
        if (originStatParam != null) {
            return originStatParam.getOrigin();
        }
        return "";
    }

    /**
     * 拼接 StatParam 到 qurl
     */
    public static String generateQUrl(String qurl,OriginStatParam statParam) {
        if (statParam == null || TextUtils.isEmpty(qurl)) {
            return qurl;
        }
        StringBuilder builder = new StringBuilder(qurl);
        if (!TextUtils.isEmpty(statParam.getAlg())) {
            builder.append("&alg=");
            builder.append(statParam.getAlg());
        }
        if (!TextUtils.isEmpty(statParam.getOrigin())) {
            builder.append("&origin=");
            builder.append(statParam.getOrigin());
        }
        return builder.toString();
    }


    /**
     * 添加统计字段
     * @param param
     */
    public static void addOriginParam(String bid, OriginStatParam param) {
        OriginStatParamHandle.getInstance().add(bid, param, null);
    }

    /**
     * originParamToJson
     */
    public static String originParamToJson(OriginStatParam param) {
        if (param == null) {
            return "";
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("origin", param.getOrigin());
            jsonObject.put("alg", param.getAlg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
    /**
     * JsonToOriginParam
     */
    public static OriginStatParam jsonToOriginParam(String json) {
        OriginStatParam originStatParam = new OriginStatParam();
        if (TextUtils.isEmpty(json)) {
            return originStatParam;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            originStatParam.setOrigin(jsonObject.optString("origin"));
            originStatParam.setAlg(jsonObject.optString("alg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return originStatParam;
    }

    /**
     * 把 DB 中的上报参数 保存到缓存中
     */
    public static void copyStatParamDBToCache() {
        OriginStatParamHandle.getInstance().copyStatParamDBToCache();
    }

}

package com.qq.reader.common.monitor;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * 上传日志结构体
 */
public class Node implements Serializable {

    private static final String KEY_S_EXSTRING = "exstring";
    public static final String KEY_S_PAGENAME = "pagename";
    public static final String KEY_S_ACTION_TAG = "act_tag";
    public static final String KEY_S_ACTION_ID = "act_id";
    public static final String KEY_S_BOOK_ID = "bid";
    public static final String KEY_S_PAGEINDEX = "pageindex";
    public static final String KEY_S_CARD_ID = "c_id";
    public static final String KEY_S_FROM_BID = "frombid";
    public static final String KEY_S_ADVLISTID = "advid";
    public static final String KEY_S_ITEMID = "itemid";
    public static final String KEY_S_ALG = "alg";

    // int type;
    // String pageUrl;
    String lmf;
    JSONObject stat_params;
    // long frombid;
    // String exid;
    // long bid;
    // String exstring;
    public JSONObject other;

    public Node(Node n) {
        // type = n.type;
        // pageUrl = n.pageUrl;
        lmf = n.lmf;
        stat_params = n.stat_params;
        // frombid = n.frombid;
        // exid = n.exid;
        // exstring = n.exstring;
        other = n.other;
        // bid = n.bid;
        // frombid = n.frombid;
    }

    public Node() {
        other = new JSONObject();
        try {
            JSONObject json = new JSONObject();
            json.put("optime", System.currentTimeMillis());
            other.put(KEY_S_EXSTRING, json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Node setKV(String key, Object Value) {
        try {
            other.putOpt(key, Value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Node setPageUrl(String pagename) {
        try {
            other.put(KEY_S_PAGENAME, pagename);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }


    /**
     * @param type （1页面访问日志，详情页操作日志 2-在线阅读（按钮跟目录） 3-下载 4-添加书架 , 5 充值 6 开通VIP,8--从阅读页加入书架？）
     *             -@param pageUrl  （访问页面）| ----- 如果是1，则是访问的页面名称，如果是其他则是请求的类型
     * @param type
     * @return
     */
    public Node setType(int type) {
        // mTmpNode.type = type;
        try {
            other.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Node setBid(String bid) {
        // mTmpNode.type = type;
        try {
            other.put("bid", bid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Node setOrigin(String origin) {
        try {
            other.put("origin", origin);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }


    public Node setAlg(String alg) {
        try {
            other.put("alg", alg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Node setSearchKey(String exString) {
        // mTmpNode.exstring = exString;
        try {
            other.put("searchkey", exString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Node setStatParamString(String str) {
        // Log.d("stat","setStatParam " + str);
        if (TextUtils.isEmpty(str)) {
            return this;
        }
        JSONObject json;
        try {
            json = new JSONObject(str);
            stat_params = json;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void putExtra(Map<String, String> extraMap) {
        if (extraMap == null || extraMap.size() == 0) {
            return;
        }
        try {
            String jsonStr = other.optString(KEY_S_EXSTRING);
            JSONObject json = new JSONObject(jsonStr);
            if (json != null) {
                Iterator<String> stringIterator = extraMap.keySet().iterator();
                while (stringIterator.hasNext()) {
                    String key = stringIterator.next();
                    String value = extraMap.get(key);
                    json.put(key, value);
                }
            }
            other.put(KEY_S_EXSTRING, json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        try {
            // json.put("type", type);
            // json.put("pageurl", pageUrl);
            json.put("lm_f", lmf);
            // if (frombid > 0) {
            // json.put("frombid", frombid);
            // }
            // json.put("exid", exid);
            // json.put("exstring", exstring);
            // // Log.e("stat","build " + node.stat_params);
            if (stat_params != null) {
                for (Iterator<String> iter = stat_params.keys(); iter
                        .hasNext(); ) {
                    String _key = iter.next();
                    json.put(_key, stat_params.get(_key));
                }
            }
            if (other != null) {

                for (Iterator<String> iter = other.keys(); iter.hasNext(); ) {
                    String _key = iter.next();
                    json.put(_key, other.get(_key));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}

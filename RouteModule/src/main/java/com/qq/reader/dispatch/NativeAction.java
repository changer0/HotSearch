package com.qq.reader.dispatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.qq.reader.common.monitor.Node;
import com.qq.reader.common.monitor.StatisticsConstant;
import com.qq.reader.common.monitor.StatisticsManager;
import com.qq.reader.common.utils.ServerUrl;
import com.qq.reader.service.push.IPushStatManagerService;
import com.tencent.mars.xlog.Log;

public class NativeAction {

    public final static int FORRESULT_DEFAULT_CODE = 10000;

    /**
     * Action 携带的所有参数
     */
    protected Bundle mActionParams = null;

    /**
     * 页面间的自定义参数
     */
    public final static String PARA_TYPE_STAT_PARAMS = "stat_params";
    public final static String PARA_TYPE_PACKAGE_ID = "package_id";
    public final static String PARA_TYPE_PAGE_LIST = "pagelist";
    public final static String PARA_TYPE_SEARCH_KEY = "searchkey";//找书神器自定义组合
    public final static String PARA_TYPE_SEARCH_OPTION = "search_option";//找书神器自定义组合
    public final static String PARA_TYPE_SEARCH_OPTION_TEXT = "search_option_text";
    public final static String PARA_TYPE_SEARCH_RESULT_ORDER = "search_result_order";
    public static final String PARA_TYPE_REPLY_CARD_POSITION = "PARA_TYPE_REPLY_CARD_POSITION";//回复列表的滚动位置记录
    public static final String PARA_TYPE_CATEGORY_TAG_ALL = "-1,-1,6";// 此数字表示取分类中不筛选，默认的全部数据。
    public static final String PARA_TYPE_CATEGORY_TAG_ALL_NEW = ",-1,-1,-1,-1,6";// 此数字表示取分类中不筛选，默认的全部数据。
    public final static String PARA_TYPE_BOOLEAN = "PARA_TYPE_BOOLEAN";
    public final static String PARA_TYPE_BOOK_TITLE = "PARA_TYPE_BOOK_TITLE";
    public final static String PARA_TYPE_BOOK_NAME = "PARA_TYPE_BOOK_NAME";
    public final static String PARA_TYPE_COMMENT_UID = "PARA_TYPE_COMMENT_UID";// 发表某一个评论的作者QQ号
    public final static String PARA_TYPE_REWARD_TAB_INDEX = "PARA_TYPE_REWARD_TAB_INDEX";
    public final static String PARA_TYPE_SWIPE_REFRESH_ENABLE = "PARA_TYPE_SWIPE_REFRESH_ENABLE";
    public final static String PARA_TYPE_REWARD_EXTRA_URL_PARAMS = "PARA_TYPE_REWARD_EXTRA_URL_PARAMS";
    public final static String PARA_TYPE_TOPIC_CONTENT = "PARA_TYPE_TOPIC_CONTENT";//发表评论页的评论内容
    public final static String PARA_TYPE_ISFROM_SCHEME = "PARA_TYPE_ISFROM_SCHEME";//是否是从scheme跳转
    public final static String PARA_TYPE_FROM_SEARCH = "PARA_TYPE_FROM_SEARCH";//来源于search
    public final static String PARA_TYPE_IS_MANUAL = "PARA_TYPE_IS_MANUAL";//是否用户手动操作
    public final static String PARA_TYPE_IS_ONLY_TODAY_READ = "PARA_TYPE_IS_ONLY_TODAY_READ";//是否只请求今日必读


    public final static String PARA_TYPE_REFRESH = "PARA_TYPE_REFRESH";
    /**
     * nativeaction 中使用default requestcode，这种方式隐患很大，不建议使用，如果以后一定要使用，希望附带requestcode
     */
    @Deprecated
    public static final String PARA_TYPE_NEW_ACTIVITY_WITH_RESULT = "newactivitywithresult";
    /**
     * requestcode
     */
    public static final String PARA_TYPE_NEW_ACTIVITY_WITH_RESULT_REQUESTCODE = "newactivitywithresult_requestcode";
    public static final String PARA_TYPE_RENT_PRICE = "rent_price";
    public static final String PARA_TYPE_RENT_DAYS = "rent_days";

    /**
     * 服务器action
     */
    public static final String SERVER_ACTION_TOPIC_STREAM = "topicstream";
    public static final String SERVER_ACTION_RANK = "rank";// 排行
    public static final String SERVER_ACTIONR_HALL = "hall";
    public static final String SERVER_ACTION_DETAIL = "detail";
    public static final String SERVER_ACTION_WEBPAGE = "webpage";
    public static final String SERVER_ACTION_CATEGORY = "categoryV3";// 分类列表
    public static final String SERVER_ACTION_TAG = "tagV2";// 标签直达区列表
    public static final String SERVER_ACTION_TOPIC = "topic";
    public static final String SERVER_ACTIONR_AUTHOR_CATEGORY_BOOKS = "sameauthorcategorybooks";
    public static final String SERVER_ACTIONR_BRIGHT_POINT = "bright_point";
    public static final String SERVER_ACTIONR_COLUMN = "column";

    public static final String SERVER_ACTION_AUDIO_CATEGORY = "audiocategory";


    /**
     * 本地自定义action
     */
//    public static final String LOCAL_ACTION_AUTHORBOOKS = "authorbooks";
    public static final String LOCAL_ACTION_NEW_ALL_AUTHOR_BOOKS = "sameauthorallbooks";
    public static final String LOCAL_ACTION_ALL_AUTHOR_BOOKS_BY_CATEGORY = "sameauthorcategorybooks";
    public static final String LOCAL_ACTION_RECOMMEND_BOOKS = "recommendbooks"; // 读过本书的读者还读过
    public static final String LOCAL_ACTION_AUTHORBOOKS = "sameauthorbooks";//作者还写过使用新的方式
    public static final String LOCAL_ACTION_DETAIL_2_CHAPTER = "detail_2_chapter";
    public static final String LOCAL_ACTION_DETAIL_2_COMMENT = "detail_2_comment";
    public static final String LOCAL_ACTION_DETAIL_2_REWARD = "detail_2_reward";
    public static final String LOCAL_ACTION_DETAIL_2_OPENVIP = "detail_2_openvip";
    public static final String LOCAL_ACTION_DETAIL_2_TOPIC_MAIN = "detail_2_topic_main";
    public static final String LOCAL_ACTION_SHOW_SHARE_DIALOG = "show_share_dialog";
    public static final String LOCAL_ACTION_DETAIL_2_TOPIC = "detail_2_topic";
    public static final String LOCAL_ACTION_DETAIL_2_OPEN_PACKAGE_VIP = "detail_2_open_package_vip";
    public static final String LOCAL_ACTION_DETAIL_2_OPENVIP_COLOMN_BOOKS = "columbooks";// 同类热门书，换一换
    public static final String LOCAL_ACTION_DETAIL_2_OPENVIP_EXPECT_BOOKS = "expRec";// 书友还读过，换一换
    public static final String LOCAL_ACTION_DETAIL_2_RENT_BOOK = "rent_book";// 租书
    public static final String LOCAL_ACTION_DETAIL_2_READERPAGE = "detail_2_readerpage";// 阅读页



    public final static String KEY_JUMP_PAGENAME = "KEY_JUMP_PAGENAME";//跳转的page
    public final static String KEY_ACTION = "KEY_ACTION";//服务器下发的action；可以调用getJumpPageNameWithServerAction方法获取对应的跳转page
    public static final String KEY_CARD_ID = "KEY_CARD_ID";
    public final static String KEY_TASK_KEY = "KEY_TASK_KEY";
    public static final String KEY_EXECUTE_TYPE = "function_type";//行为 类型
    public static final String KEY_NEED_START_MAIN = "need_start_main";//返回时是否需要启动首页

    public static final String LOCAL_STORE_IN_TITLE ="LOCAL_STORE_IN_TITLE";

    /**
     * 列表标签
     */
    public final static String KEY_ACTION_TAG = "KEY_ACTIONTAG";
    /**
     * 列表id，字符型，排行：栏目ID，分类：分类ID，栏目：栏目ID，搜索：搜索词
     */
    public final static String KEY_ACTION_ID = "KEY_ACTIONID";

    /**
     * KEY_EXECUTE_TYPE的取值范围
     */
    public static final int VALUE_EXECUTE_JUMP = 0;
    public static final int VALUE_EXECUTE_UILOGIC = 1;
    public static final int VALUE_EXECUTE_JUMP_AND_UILOGIC = 2;
    public static final int VALUE_EXECUTE_LOGIN = 3;
    public static final int VALUE_EXECUTE_REFRESH = 4;// refresh UI
    public static final int VALUE_EXECUTE_PUBLISH_COMMENT = 5;// 评论
    public static final int VALUE_EXECUTE_LOADMORE = 6;// 加载更多

    public final static String URL_DATA_QURL = "URL_DATA_QURL";

    /**
     * 拼接URL的参数
     */
    public final static String URL_BUILD_PERE_COLS = "URL_BUILD_PERE_COLS";//栏目ID
    public final static String URL_BUILD_PERE_ADVS = "URL_BUILD_PERE_ADVS";//广告ID
    public final static String URL_BUILD_PERE_ACTION_TAG = "KEY_ACTIONTAG";//列表标签
    public final static String URL_BUILD_PERE_ACTION_ID = "KEY_ACTIONID";//列表ID
    public final static String URL_BUILD_PERE_BOOK_ID = "URL_BUILD_PERE_BOOK_ID";//书ID
    public final static String URL_BUILD_PERE_CHAPTER_UUID = "URL_BUILD_PERE_CHAPTER_UUID";
    public final static String URL_BUILD_PERE_CHAPTER_ID = "URL_BUILD_PERE_CHAPTER_ID";//章节ID
    public final static String URL_BUILD_PERE_LCOLS = "URL_BUILD_PERE_LCOLS";//听书栏目ID
    public final static String URL_BUILD_PERE_FINISH = "URL_BUILD_PERE_FINISH";//完结专区分类
    public final static String URL_BUILD_PERE_MONTH_VIP_ZONE_CLASSIFY= "URL_BUILD_PERE_MONTH_VIP_ZONE_CLASSIFY";//包月专区分类
    public final static String URL_BUILD_PERE_BOOK_ZONE_CLASSIFY_PREFER = "URL_BUILD_PERE_BOOK_ZONE_CLASSIFY_PREFER";//完结书分类偏好
    /**
     * 个性书单三本书id
     */
    public final static String URL_BUILD_PERE_BIDS = "bids";
//    public final static String URL_BUILD_PERE_PLAN = "plan";
    /**
     * 是否查询智能推荐 猜你喜欢
     * <p/>
     * recflag：1查询，其他不查询
     * <p/>
     * recflag = 1
     */
    public final static String URL_BUILD_PERE_ISRECFLAG = "URL_BUILD_PERE_ISRECFLAG";

    /**
     * 是否查询强力推荐的数据
     * <p/>
     * isStrRecFlag: 1查询，其他不查询
     * <p/>
     * strRecFlag = 1
     */
    public final static String URL_BUILD_PERE_ISSTRRECFLAG = "URL_BUILD_PERE_ISSTRRECFLAG";

    /**
     * 是否查询排行首页分类数据 （rankFlag=1）
     * <p/>
     * isRankFlag: 1查询，其他不查询
     * <p/>
     * rankFlag=1
     */
    public final static String URL_BUILD_PERE_ISRANKFLAG = "URL_BUILD_PERE_ISRANKFLAG";

    /**
     * lmtcids：限免的栏目ID，查询限免数据（lmtcids = xxx） （本周集赞胜出）
     * <p/>
     * 本周限免
     */

    public final static String URL_BUILD_PERE_ISLMTCIDS = "URL_BUILD_PERE_ISLMTCIDS";

    /**
     * isJzqmcids : 是否查询集赞求免数据数据（jzqmcids=xxx） （"集赞求免",）
     */
    public final static String URL_BUILD_PERE_ISJZQMCIDS = "URL_BUILD_PERE_ISJZQMCIDS";

    /**
     * 分页
     */
    public final static String URL_BUILD_PERE_PAGESTAMP = "KEY_PAGEINDEX";

    /**
     * 分类页
     */
    public final static String URL_BUILD_PERE_CATEGORY = "URL_BUILD_PERE_CATEGORY";

    /**
     * 听书书库分类页
     * */
    public final static String URL_BUILD_PERE_AUDIO_CATEGORY = "URL_BUILD_PERE_AUDIO_CATEGORY";

    /**
     * 排行页
     */
    public final static String URL_BUILD_PERE_RANK = "URL_BUILD_PERE_RANK";


    /**
     * URL中的备注信息
     */
    public final static String URL_BUILD_PERE_SIGNAL = "URL_BUILD_PERE_SIGNAL";

    /**
     * 专题页 每页card个数
     */
    public final static String URL_BUILD_PERE_PAGESIZE = "URL_BUILD_PERE_PAGESIZE";
    /**
     * 专题页 页码
     */
    public final static String URL_BUILD_PERE_PAGENUMBER = "URL_BUILD_PERE_PAGENUMBER";
    /**
     * 专题页 拉取截止时间
     */
    public final static String URL_BUILD_PERE_END_TIME = "URL_BUILD_PERE_END_TIME";
    /**
     * 书单
     */
    public final static String URL_BUILD_PERE_BOOK_COLLECT = "URL_BUILD_PERE_BOOK_COLLECT";
    /**
     * 超值包
     */
    public final static String URL_BUILD_PERE_BOOK_PACK = "URL_BUILD_PERE_BOOK_PACK";
    /**
     * 来源栏目号
     */
    public final static String URL_BUILD_PERE_ORIGIN = "origin";
    /**
     * 新闻、专题的评论（js调起）
     */
    public static final String URL_BUILD_PERE_COMMENT_TID = "topiccomments_tid";
    /**
     * 用户喜好
     */
    public final static String URL_BUILD_USER_LIKE = "sex";

    /**
     * 作者的全部作品，按分类
     */
    public final static String URL_BUILD_PERE_SAME_CATEGORY_TYPE="SAME_CATEGORY_TYPE";

    public NativeAction(Bundle b) {
        if (b != null) {
            mActionParams = b;
        } else {
            mActionParams = new Bundle();
        }
    }

    public synchronized Bundle getActionParams() {
        if (mActionParams == null) {
            mActionParams = new Bundle();
        }
        return mActionParams;
    }

    public static String getJumpPageNameWithServerAction(String action) {
        if (action == null || action.length() == 0) {
            return "";
        }
        if (SERVER_ACTIONR_HALL.equals(action)) {
            return RouteConstant.PAGE_NAME_HALLOFFAME;
        } else if (SERVER_ACTION_DETAIL.equals(action)) {
            return RouteConstant.PAGE_NAME_DETAIL;
        } else if (SERVER_ACTION_CATEGORY.equals(action)) {
            return RouteConstant.PAGE_NAME_CLASSIFY;
        } else if (SERVER_ACTION_TOPIC_STREAM.equals(action)) {
            return RouteConstant.PAGE_NAME_DISCOVERY_TOPIC;
        } else if (SERVER_ACTION_WEBPAGE.equals(action)) {
            return RouteConstant.PAGE_NAME_WEBCONTENT;
        } else if (SERVER_ACTION_TOPIC.equals(action)) {
            return RouteConstant.PAGE_NAME_BOOKCLUB_MAIN;
        } else if (SERVER_ACTIONR_AUTHOR_CATEGORY_BOOKS.equals(action)){
            return RouteConstant.PAGE_NAME_SAME_AUTHOR_CATEGORY_BOOKS;
        } else if(SERVER_ACTIONR_BRIGHT_POINT.equals(action)){
            return RouteConstant.PAGE_NAME_BRIGHT_POINT;
        } else if(LOCAL_ACTION_RECOMMEND_BOOKS.equals(action)) {
            return RouteConstant.PAGE_NAME_RECOMMEND_BOOKS;
        }
        return "";
    }

    public void doExecute(IEventListener eventListener) {
        if (eventListener == null) {
            return;
        }
        int functionType = mActionParams.getInt(KEY_EXECUTE_TYPE);
        switch (functionType) {
            case VALUE_EXECUTE_UILOGIC:
                eventListener.doFunction(mActionParams);
                break;
            case VALUE_EXECUTE_JUMP:
                jumpActivity(eventListener);
                break;
            case VALUE_EXECUTE_JUMP_AND_UILOGIC:
                eventListener.doFunction(mActionParams);
                jumpActivity(eventListener);
                break;
            case VALUE_EXECUTE_LOGIN:
                eventListener.doFunction(mActionParams);
                break;
            default:
                eventListener.doFunction(mActionParams);
        }
    }

    private boolean jumpActivity(IEventListener eventListener) {
        if (eventListener == null) {
            return false;
        }
        String doPageName = mActionParams.getString(KEY_JUMP_PAGENAME);
        String action = mActionParams.getString(KEY_ACTION);
        android.util.Log.d("NativeAction", "jumpActivity " + doPageName + " action " + action);
        Activity context = eventListener.getFromActivity();
        String routerPath = "";
        if (context == null) {
            return false;
        }

        // 对于server下发的只有action的行为在这里对应上page
        if (doPageName == null || doPageName.length() == 0) {
            getActionParams().putString(KEY_JUMP_PAGENAME, getJumpPageNameWithServerAction(action));
            doPageName = mActionParams.getString(KEY_JUMP_PAGENAME);
        }

        if (RouteConstant.PAGE_NAME_DETAIL.equals(doPageName)) {
            routerPath = RoutePath.READER_BOOK_DETAIL;
        } else if (RouteConstant.PAGE_NAME_BOOKCLUB_TOPIC.equals(doPageName)) {
            routerPath = RoutePath.BOOK_STORE_COMIT_COMMENT;
        } else if (RouteConstant.PAGE_NAME_WEBCONTENT.equals(doPageName)) {
            routerPath = RoutePath.WEB_BROWER;
            mActionParams.putString(RouteConstant.WEBCONTENT,
                    mActionParams.getString(RouteConstant.WEBCONTENT));
        } else if (RouteConstant.PAGE_NAME_AUTHOR_MAIN.equals(doPageName)) {
            routerPath = RoutePath.BOOK_STORE_AUTHOR_PAGE;
        } else {
            routerPath = RoutePath.BOOK_STORE_TWO_LEVEL;
        }

        Postcard postcard = ARouter.getInstance().build(routerPath)
                .with(mActionParams)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        if (RouteConstant.PAGE_NAME_WEBCONTENT.equals(doPageName)) {
            postcard.withString(RouteConstant.WEBCONTENT,
                    mActionParams.getString(RouteConstant.WEBCONTENT));
        }

        boolean new_activity_with_result = mActionParams.getBoolean(
                PARA_TYPE_NEW_ACTIVITY_WITH_RESULT, false);
        int requestcode = mActionParams.getInt(PARA_TYPE_NEW_ACTIVITY_WITH_RESULT_REQUESTCODE, FORRESULT_DEFAULT_CODE);
        if (new_activity_with_result) {
            postcard.navigation(context, requestcode);
        } else {
            postcard.navigation(context);
        }
        if (!RouteConstant.PAGE_NAME_DETAIL.equals(doPageName)) {
            StatisticsManager.getInstance().commit(compose(mActionParams).setType(1), StatisticsConstant.STAT_LEVEL_HIGH);
        }
        return true;
    }

    public String buildUrl(String perUrl) {
        return buildUrl(ServerUrl.PROTOCOL_SERVER_URL, perUrl);
    }

    public String buildUrl(String protocolUrl, String perUrl) {
        StringBuilder sbUrl = new StringBuilder(protocolUrl);
        sbUrl.append(perUrl);
        // TODO 微信登录的区分
//        String sid = AccountConfig.getSID() + "";
//        if (sid.equals("0")) {
//            sid = "";
//        }
        // 单独的数据请求,usid放到header中
        // sbUrl.append("usid=");
        // sbUrl.append(sid);

        String cols = mActionParams.getString(URL_BUILD_PERE_COLS);
        if (cols != null && cols.length() > 0) {
            sbUrl.append("&cids=");
            sbUrl.append(cols);
        }
        String lcols = mActionParams.getString(URL_BUILD_PERE_LCOLS);
        if (lcols != null && lcols.length() > 0) {
            sbUrl.append("&lcids=");
            sbUrl.append(lcols);
        }
        String advs = mActionParams.getString(URL_BUILD_PERE_ADVS);
        if (advs != null && advs.length() > 0) {
            sbUrl.append("&adids=");
            sbUrl.append(advs);
        }
        String actionTag = mActionParams.getString(URL_BUILD_PERE_ACTION_TAG);
        if (actionTag != null && actionTag.length() > 0) {
            sbUrl.append("&actionTag=");
            sbUrl.append(actionTag);
        }
        String actionId = mActionParams.getString(URL_BUILD_PERE_ACTION_ID);
        if (actionId != null && actionId.length() > 0) {
            sbUrl.append("&actionId=");
            sbUrl.append(actionId);
        }
        long bookid = mActionParams.getLong(URL_BUILD_PERE_BOOK_ID, 0);
        if (bookid != 0) {
            sbUrl.append("&bid=");
            sbUrl.append(bookid);
        }
        int chapterid = mActionParams.getInt(URL_BUILD_PERE_CHAPTER_ID, 0);
        if (chapterid != 0) {
            sbUrl.append("&chapterid=");
            sbUrl.append(chapterid);
        }

        String jzqmcids = mActionParams.getString(URL_BUILD_PERE_ISJZQMCIDS);
        if (jzqmcids != null && jzqmcids.length() > 0) {
            sbUrl.append("&jzqmcids=");
            sbUrl.append(jzqmcids);
        }

        String lmtcids = mActionParams.getString(URL_BUILD_PERE_ISLMTCIDS);
        if (lmtcids != null && lmtcids.length() > 0) {
            sbUrl.append("&lmtcids=");
            sbUrl.append(lmtcids);
        }

        String strRecFlag = mActionParams.getString(URL_BUILD_PERE_ISSTRRECFLAG);
        if (strRecFlag != null && strRecFlag.length() > 0) {
            sbUrl.append("&strRecFlag=");
            sbUrl.append(strRecFlag);
        }

        String recflag = mActionParams.getString(URL_BUILD_PERE_ISRECFLAG);
        if (recflag != null && recflag.length() > 0) {
            sbUrl.append("&recFlag=");
            sbUrl.append(recflag);
        }

        String rankFlag = mActionParams.getString(URL_BUILD_PERE_ISRANKFLAG);
        if (rankFlag != null && rankFlag.length() > 0) {
            sbUrl.append("&rankFlag=");
            sbUrl.append(rankFlag);
        }

        String action = mActionParams.getString(KEY_ACTION);
        if (action != null && action.length() > 0) {
            sbUrl.append("&action=");
            sbUrl.append(action);
        }

        long pageStamp = mActionParams.getLong(URL_BUILD_PERE_PAGESTAMP, 1);
        if (pageStamp > 0) {
            // sbUrl.append("&page=");
            sbUrl.append("&pagestamp=");
            sbUrl.append(pageStamp);
        }

        String category = mActionParams.getString(URL_BUILD_PERE_CATEGORY);
        if (category != null && category.length() > 0) {
            sbUrl.append("&categoryFlag=");
            sbUrl.append(category);
        }

        String audioCategory = mActionParams.getString(URL_BUILD_PERE_AUDIO_CATEGORY);
        if (audioCategory != null && audioCategory.length() > 0) {
            sbUrl.append("&audioCategoryFlag=");
            sbUrl.append(audioCategory);
        }

        String rank = mActionParams.getString(URL_BUILD_PERE_RANK);
        if (rank != null && rank.length() > 0) {
            sbUrl.append("&rankFlag=");
            sbUrl.append(rank);
        }
        String book_collectFlag = mActionParams.getString(URL_BUILD_PERE_BOOK_COLLECT);
        if (book_collectFlag != null && book_collectFlag.length() > 0) {
            sbUrl.append("&hastopic=");
            sbUrl.append(book_collectFlag);
        }
        String book_PackFlag = mActionParams.getString(URL_BUILD_PERE_BOOK_PACK);
        if (book_PackFlag != null && book_PackFlag.length() > 0) {
            sbUrl.append("&hasbag=");
            sbUrl.append(book_PackFlag);
        }
        String commentid = mActionParams.getString(RouteConstant.COMMENT_ID);
        if (commentid != null && commentid.length() > 0) {
            sbUrl.append("&commentid=");
            sbUrl.append(commentid);
        }

        String signal = mActionParams.getString(URL_BUILD_PERE_SIGNAL);
        if (signal != null && signal.length() > 0) {
            sbUrl.append("&signal=");
            sbUrl.append(signal);
        }

        String pageSize = mActionParams.getString(URL_BUILD_PERE_PAGESIZE);
        if (pageSize != null && pageSize.length() > 0) {
            sbUrl.append("&ps=");
            sbUrl.append(pageSize);
        }

        String pageNumber = mActionParams.getString(URL_BUILD_PERE_PAGENUMBER);
        if (pageNumber != null && pageNumber.length() > 0) {
            sbUrl.append("&pn=");
            sbUrl.append(pageNumber);
        }

        String endTime = mActionParams.getString(URL_BUILD_PERE_END_TIME);
        if (endTime != null && endTime.length() > 0) {
            sbUrl.append("&time=");
            sbUrl.append(endTime);
        }
        String tid = mActionParams.getString(URL_BUILD_PERE_COMMENT_TID);
        if (!TextUtils.isEmpty(tid)) {
            sbUrl.append("&tid=");
            sbUrl.append(tid);
        }
        int topictype = mActionParams.getInt(RouteConstant.CTYPE);
        if (topictype > 0) {
            sbUrl.append("&ctype=");
            sbUrl.append(topictype);
        }

        String topicid = mActionParams.getString(RouteConstant.TOPIC_ID);
        if (!TextUtils.isEmpty(topicid)) {
            sbUrl.append("&tid=");
            sbUrl.append(topicid);
        }

//        String statParams = mActionParams.getString(PARA_TYPE_STAT_PARAMS);
//        String statUrlParams = StatUtils.getStatUrlParams(statParams);
//        if (!TextUtils.isEmpty(statUrlParams)) {
//            sbUrl.append("&");
//            sbUrl.append(statUrlParams);
//        }
        String finishParams = mActionParams.getString(URL_BUILD_PERE_FINISH);
        if (!TextUtils.isEmpty(finishParams)) {
            sbUrl.append("&secondPage=");
            sbUrl.append(finishParams);
        }

        int monthVipZoneParams = mActionParams.getInt(URL_BUILD_PERE_MONTH_VIP_ZONE_CLASSIFY, -1);
        if (monthVipZoneParams != -1) {
            sbUrl.append("&vipCategory=");
            sbUrl.append(monthVipZoneParams);

            //包月专区请求添加 810
            sbUrl.append("&month=1");
        }


        String preferParams = mActionParams.getString(URL_BUILD_PERE_BOOK_ZONE_CLASSIFY_PREFER);
        if (!TextUtils.isEmpty(preferParams)) {
            sbUrl.append("&prefer=");
            sbUrl.append(preferParams);
        }

        String bids = mActionParams.getString(URL_BUILD_PERE_BIDS);
        if (!TextUtils.isEmpty(bids)) {
            sbUrl.append("&bids=");
            sbUrl.append(bids);
        }


        String url = sbUrl.toString();
        if (url.indexOf("?&") != -1) {
            url = url.replace("?&", "?");
        }
        Log.d("native", "url " + url);
        return url;
    }


    /**
     * 先这样写，为了StatisticsManager内部和NativeAction解耦，以后再把NativeAction的变量独立出来
     *
     * @param bundle
     * @return
     */
    public static Node compose(Bundle bundle) {
        Node node = new Node();
        if (bundle == null) {
            return node;
        }
        String doPageName = bundle.getString(NativeAction.KEY_JUMP_PAGENAME);// 这个东西太恶心了，
        // 先去jumppagename
        // 没有的话取statisicsname，最后取action
        if (doPageName == null || doPageName.length() == 0) {
            String action = bundle.getString(NativeAction.KEY_ACTION);
//			if (NativeAction.LOCAL_ACTION_DETAIL_2_OPENVIP_DETAIL.equals(action)) {
//				doPageName = Constant.PAGE_NAME_DETAIL;
//			} else
//            if (NativeAction.SERVER_ACTION_WEBPAGE.equals(action)) {
//                doPageName = Constant.PAGE_NAME_WEBCONTENT;
//            } else {
//                doPageName = action;
//            }
            doPageName = NativeAction.getJumpPageNameWithServerAction(action);
        }

        String actionTag = bundle.getString(NativeAction.URL_BUILD_PERE_ACTION_TAG);// aaa
        if (actionTag != null && actionTag.length() > 0) {
            node.setKV(Node.KEY_S_ACTION_TAG, actionTag);
        }

        String actionId = bundle.getString(NativeAction.URL_BUILD_PERE_ACTION_ID);// aaa
        if (actionId != null && actionId.length() > 0) {
            node.setKV(Node.KEY_S_ACTION_ID, actionId);
        }
        long bookid = bundle.getLong(NativeAction.URL_BUILD_PERE_BOOK_ID, 0);
        if (bookid != 0) {
            node.setKV(Node.KEY_S_BOOK_ID, bookid);
        }
        int pageIndex = bundle.getInt(NativeAction.URL_BUILD_PERE_PAGESTAMP, 1);// aaa
        if (pageIndex > 0) {
            node.setKV(Node.KEY_S_PAGEINDEX, pageIndex);
        }
        String cid = bundle.getString(NativeAction.KEY_CARD_ID);
        if (!TextUtils.isEmpty(cid)) {
            node.setKV(Node.KEY_S_CARD_ID, cid);
        }
        long frombid = bundle.getLong(Node.KEY_S_FROM_BID);
        if (frombid > 0) {
            node.setKV(Node.KEY_S_FROM_BID, frombid);
        }

        String origin = bundle.getString(NativeAction.URL_BUILD_PERE_ORIGIN);
        if (!TextUtils.isEmpty(origin)) {
            node.setKV(NativeAction.URL_BUILD_PERE_ORIGIN, origin);
        }

        String advid = bundle.getString(Node.KEY_S_ADVLISTID);
        if (!TextUtils.isEmpty(advid)) {
            node.setKV(Node.KEY_S_ADVLISTID, advid);
        }

        if (!TextUtils.isEmpty(doPageName)) {
            node.setKV(Node.KEY_S_PAGENAME, doPageName);
        }

        String alg = bundle.getString(Node.KEY_S_ALG);
        if (!TextUtils.isEmpty(alg)) {
            node.setKV(Node.KEY_S_ALG, alg);
        }

        String itemid = bundle.getString(Node.KEY_S_ITEMID);
        if (!TextUtils.isEmpty(itemid)) {
            node.setKV(Node.KEY_S_ITEMID, itemid);
        }
        String searchkey = bundle.getString(NativeAction.PARA_TYPE_SEARCH_KEY);
        if (!TextUtils.isEmpty(searchkey)) {
            node.setSearchKey(searchkey);
        }

        String stat_params = bundle.getString(NativeAction.PARA_TYPE_STAT_PARAMS);
        node.setStatParamString(stat_params);


        //key表示搜索关键词
        String key = bundle.getString("key");
        if (!TextUtils.isEmpty(key)) {
            node.setKV("key", key);
        }
        //详情页上报，从搜索过来的一些数据
        //page 表示 当前的页码
        String page = bundle.getString("page");
        if (!TextUtils.isEmpty(page)) {
            node.setKV("page", page);
        }
        //index 表示当前item在当前页的位置，page=0既第一页才会上报此字段
        String index = bundle.getString("index");
        if (!TextUtils.isEmpty(index)) {
            node.setKV("index", index);
        }

        return node;
    }

    /**
     * 检查来源Intent, 如果发现包含PUSH_ORIGIN字段就传递下去
     * @param i
     * @param context
     */
    private void checkPushOrigin(Intent i, Context context) {
        if (context instanceof Activity) {
            Intent intent = ((Activity) context).getIntent();
            String pushStatOriginJson = intent.getStringExtra(IPushStatManagerService.KEY_STAT_PUSH_ORIGIN_JSON);
            if (!TextUtils.isEmpty(pushStatOriginJson)) {
                i.putExtra(IPushStatManagerService.KEY_STAT_PUSH_ORIGIN_JSON, pushStatOriginJson);
            }
        }
    }
}

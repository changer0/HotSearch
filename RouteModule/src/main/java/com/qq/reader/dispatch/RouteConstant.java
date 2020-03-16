package com.qq.reader.dispatch;

/**
 * Created by hexiaole on 2017/8/15.
 *
 * 页面配置的常量值
 * activity name
 */

public class RouteConstant {
    public static final String ACTION_TAG_VIRTUAL_RECOMMEND  = "editorrec";

    public static final String CATEGORYBOOK_ALL_TYPE = "1";         //包含所有书籍
    public static final String CATEGORYBOOK_EXCLUSIVE_TYPE = "2";   //不包含传递bid书籍,少一本
    //网页承载activity
    public static final int WEBBROWSERFORCONTENTS = 100000;

    public static final String WEBCONTENT = "com.qq.reader.WebContent";
    public static final String WEBCONTENT_TITLE = "com.qq.reader.WebContent.title";
    public static final String FROM_TITLE = "FROM_TITLE"; // 跳转来的页面Title,显示于左上角返回按钮右侧
    public static final String FROM_JUMP = "from_jump";
    public static final String FROM_MESSAGE = "from"; //从消息界面跳转
    public static final String CURRENT_ITEM = "CURRENT_ITEM";

    public static final String PAGE_NAME_HALLOFFAME = "HallOfFamePage";
    public static final String PAGE_NAME_DETAIL = "DetailPage";
    public static final String PAGE_NAME_CLASSIFY = "classify";
    public static final String PAGE_NAME_DISCOVERY_TOPIC = "topicpage";//发现-专题
    public static final String PAGE_NAME_WEBCONTENT = "webpage";
    public static final String PAGE_NAME_BOOKCLUB_MAIN = "bookclubmain";//书评区
    public static final String PAGE_NAME_SAME_AUTHOR_CATEGORY_BOOKS = "sameauthorcategorybooks";//同一作者全部书籍，按照category分组
    public static final String PAGE_NAME_BRIGHT_POINT = "brightpoint";//作品亮点
    public static final String PAGE_NAME_RECOMMEND_BOOKS = "recommendbooks";// 书友还读过
    public static final String PAGE_NAME_BOOKCLUB_TOPIC = "bookclubtopic";
    public static final String PAGE_NAME_AUTHOR_MAIN = "GoodWriter_MainPage";
    public static final String COMMENT_ID = "COMMENT_ID";
    public static final String CTYPE = "CTYPE";
    public static final String TOPIC_ID = "TOPIC_ID";
    public static final String SEARCH_TYPE_VALUE_AUDIO = "听书";

    //作家主页 作家ID
    public final static String AUTHORPAGE_KEY_AVATAR_URL = "AUTHORPAGE_KEY_AVATAR_URL";
    public final static String AUTHORPAGE_KEY_AUTHORID = "AUTHORPAGE_KEY_AUTHORID";
    public final static String AUTHORPAGE_KEY_AUTHOR_NAME = "AUTHORPAGE_KEY_AUTHOR_NAME";
    public static final String QURL_AUTHOR_MAIN_PAGE_PREFIX = "uniteqqreader://nativepage/authors/mainpage?authorId=%s&name=%s&iconUrl=%s";
    public static final int CTYPE_BOOK = 0;//书籍相关的回复
    public static final int CTYPE_COMMENT = 4;//公共书评区相关评论
    public static final int CTYPE_TOPIC = 5;//专题相关的回复
    //书评激活恢复框
    public static final String ACTIVE_REPLY_LAYOUT = "active_reply_layout";
    public static final String LOCAL_STORE_IN_TITLE = "LOCAL_STORE_IN_TITLE";

    public static final String JUMP_SOURCE = "jump_source";
    public static final String FORM_NOTIFICATION = "from_notification";

    public static final int ACTIVITY_REQUESTCODE_SEND_COMMENT = 1002;
    public static final int FLOOR_SOFA = 2;//沙发楼层，2楼
    public static final int FLOOR_NEXT_UP_VALUE = 20;
    public static final String FLOOR_ISLOCATE = "lcoate";
    //弹出输入法
    public static final String SHOW_KEYBOARD = "show_keyboard";
    public static final String LOCAL_STORE_USE_CACHE = "LOCAL_STORE_USE_CACHE";
    /**
     * 今日精选 主线今日必读
     */
    public static final String PAGE_NAME_NATIVE_TODAYREAD = "today_read";
    /**
     * 今日秒杀
     */
    public static final String PAGE_NAME_LIMIT_TIME_DISCOUNT_BUY = "Limit_time_discount_buy";

    //分类tab相关
    public static final String PAGE_NAME_CATEGORY_BOY = "BookLibCategory_boy";
    public static final String PAGE_NAME_CATEGORY_GIRL = "BookLibCategory_girl";
    public static final String PAGE_NAME_CATEGORY_AUDIO = "BookLibCategory_audio";
    public static final String PAGE_NAME_CATEGORY_PUBLISH = "BookLibCategory_publish";

    public static final String PAGE_NAME_RANK_BOY = "BookLibTopRank_boy";
    public static final String PAGE_NAME_RANK_GIRL = "BookLibTopRank_girl";
    public static final String PAGE_NAME_RANK_PUBLISH = "BookLibTopRank_publish";
    public static final String PAGE_NAME_RANK_LISTEN = "BookLibTopRank_listen";

    public static final String PAGE_NAME_PAYMONTH_BOY = "PayMonth_Boy";
    public static final String PAGE_NAME_PAYMONTH_GIRL = "PayMonth_Girl";
    public static final String PAGE_NAME_PAYMONTH_PUBLISH = "PayMonth_Publish";

    public static final String PAGE_NAME_FREE_BOY = "FreePage_Boy";
    public static final String PAGE_NAME_FREE_GIRL = "FreePage_Girl";
    public static final String PAGE_NAME_FREE_PUBLISH = "FreePage_Publish";

    public static final String PAGE_NAME_END_BOY = "End_Book_Boy";
    public static final String PAGE_NAME_END_GIRL = "End_Book_Girl";
    public static final String PAGE_NAME_END_PUBLISH = "End_Book_Publish";

    public static final String PAGE_NAME_EDITORCHOICE_BOY = "EditorChoice_Book_Boy";
    public static final String PAGE_NAME_EDITORCHOICE_GIRL = "EditorChoice_Book_Girl";
    public static final String PAGE_NAME_EDITORCHOICE_PUBLISH = "EditorChoice_Book_Publish";

    public static final String PAGE_NAME_CLASSIC_BOY = "Classic_Boy";
    public static final String PAGE_NAME_CLASSIC_GIRL = "Classic_Girl";
    public static final String PAGE_NAME_CLASSIC_PUBLISH = "Classic_Publish";

    public static final String MAIN_TAB_TAG = "main_tab_tag";
    public static final String CLASSIFY_TAB_TAG = "classify_tab_tag";

    public static final String PAGE_NAME_LISTEN_ZONE = "Listen_Zone";

    //热搜榜单
    public static final String PAGE_NAME_HOT_SEARCH_RANK = "hot_search_rank";

    public static final String JUMP_PARAMETER_AUTHORS_INDEX = "jump_parameter_authors_index";

    public static final String WEBCONTENT_COLLECT = "com.qq.reader.WebContent_collect";
    public static final String WEBCONTENT_SHARE = "com.qq.reader.WebContent_share";
    public static final String WEBCONTENT_NEED_RECORD_HISTORY = "com.qq.reader.Need_record_history";

    public static final String ACTIVITY_TYPE = "ACTIVITY_TYPE";
    public static final String ACTIVITY_TYPE_MONTH_PAY = "ACTIVITY_TYPE_MONTH_PAY";
    public static final String POP_RIGHT_ICON_FROM = "POP_RIGHT_ICON_FROM";
    public static final String POP_RIGHT_ICON_FROM_MONTH = "POP_RIGHT_ICON_FROM_MONTH";
    public static final String ACTIVITY_TYPE_TODAY_FREE = "ACTIVITY_TYPE_TODAY_FREE";
    public static final String POP_RIGHT_ICON_FROM_FREE = "POP_RIGHT_ICON_FROM_FREE";

    public static final String PAGE_NAME_DISCOVERY_COMMENT_DETAIL = "discovery_comment_detail";
    public static final String SHOWCOMMENTACTIVITY = "SHOWCOMMENTACTIVITY";
    public static final String PLUGIN_ID = "plugin_id";
    public static final String PAGE_NAME_SELECTED_COMMENT = "selected_comment";//精华书评
    public static final String PAGE_NAME_WELLCHOSEN_BOOKSTORE = "WellChosenBookStore"; //精选书城
    public static final String PAGE_NAME_BOOKCLUB_REPLY = "bookclubreply";//书评详情
    public static final String CLASSIFY_FORM_MOVIE_AREA = "classify_from_movie_area";//从影视专区书库入口进入分类
    public static final String CLASSIFY_FORM = "classify_from";//进入分类页面的来源
    public static final String PAGE_NAME_BOOKCLUB_REPLYLIST = "bookclubreplylist";//专题全部回复列表
    public static final String PAGE_NAME_BOOKCLUB_DISCUSSLIST = "bookclubdiscusslist";//专题全部讨论列表
    public static final String PAGE_NAME_BOOKCLUB_CHAPTER = "bookclubchapter";//单章评论
    public static final String LOCAL_STORE_KEY_IS_FINISH = "LOCAL_STORE_KEY_IS_FINISH";
    public static final String PAGE_NAME_SEARCH_TOOL_MORE = "searchToolMore";//搜书神器更多-大家都在搜
    public static final String NATIVE_LISTVIEW_DIVIDOR_HEIGHT = "NATIVE_LISTVIEW_DIVIDOR_HEIGHT";
    public static final String NATIVE_LISTVIEW_DIVIDOR_RES = "NATIVE_LISTVIEW_DIVIDOR_RES";
    public static final String PAGE_NAME_SEARCH_TOOL_MAIN = "SearchToolMain";
    public static final String PAGE_NAME_SEARCH_TOOL_RESULT = "searchToolResult";//搜书神器结果页
    public static final String MARK_BID = "com.qq.reader.mark.bid";
    public static final String BOOK_CHAPTER_ID = "book_chapterid";
    public static final String PAGE_NAME_RECOMMENDPAGE = "RecommendPage";//详情页快速入口跳转的推荐页面
    //付费教育
    public static final String NEW_USER_REWARD_NUM = "NEW_USER_REWARD_NUM";
    public static final String NEW_USER_REWARD_INTRO = "NEW_USER_REWARD_INTRO";
    public static final String GET_NEW_USER_REWARD_FROM_BOOKSHELF = "GET_NEW_USER_REWARD_FROM_BOOKSHELF";

    public static final String EVENT_NAME = "statEventName";
    public static final String SEARCH_KEY = "searchkey";
    public static final String SEARCH_HINT = "searchhint";
    public static final String SEARCH_TYPE = "search_type";
    public static final String PAGE_NAME_SEARCH_LABEL = "search_label";
    public static final String SEARCH_NEED_DIRECT = "needDirect";
    public static final String PAGE_NAME_PUBLISHER_AND_AUTHOR = "publisher_and_author";
    public static final String NATIVE_BG_COLOR_Resource = "NATIVE_BG_COLOR_Resource";

    public static final String PAGE_NAME_USER_CENTER_MAIN = "UserCenterPage";
    public static final String USER_ID = "userId";
    public static final String USER_NICKNAME = "userNickName";
    public static final String USER_ICONURL = "userIconUrl";

    public static final String PAGE_NAME_TOPICVOTE_DETAIL="page_name_topicvote_detail";
    public static final String PAGE_NAME_TOPICVOTE_OFFICIAL="page_name_topicvote_official";
    public static final String PAGE_NAME_OFFICIAL_OPPO = "officialforoppo";// OPPO 官方主页
    public static final String PAGE_NAME_MY_FAVOURITE = "myfocus";

    // 主编推荐二级页面
    public static final String PAGE_NAME_VIRTUAL_RECOMMEND_TAB = "virtual_recommend_page";//虚拟人物推荐Tab页
    // 主编推荐三级页面
    public static final String PAGE_NAME_VIRTUAL_RECOMMEND_THREE_LEVEL = "virtual_recommend_three_level_page";//虚拟人物推荐三级页
    public static final String ID = "_id";

    public static final String MOVIE_AREA_ACTION_ID = "13121";

    public static final String FLOOR_INDEX = "floor_index";
    public static final String FLOOR_NEXT = "floor_next";

    public static final String NATIVE_PREMIUM_CONTENT_INDEX = "NATIVE_PREMIUM_CONTENT_INDEX";
    public static final String PAGE_NAME_EXCLUSIVE_RECOMMEND = "exclusiverecommend";//专属推荐

    public static final String FROM_TYPE_TAG = "fromType";
    public static final String FROM_TYPE_READERPAGE = "readerpage";

    public static final String JUMP_FROM = "JUMP_FROM";

    public static final String PARA_READER_PAGE_CONTEXT_ONCLICK_EVENT_TYPE = "PARA_READER_PAGE_CONTEXT_ONCLICK_EVENT_TYPE";
    public static final String READER_PAGE_AUTHOR_WORDS_ICON_ONCLICK = "READER_PAGE_AUTHOR_WORDS_ICON_ONCLICK";
    public static final String PAGE_NAME_NATIVE_COLUMN = "column";
    public static final String AUDIO_CHAPTER_NEED_BUY = "need_buy";





    public static String DEEP_LINK_BACK_URL = "backurl";
    public static String DEEP_LINK_BACK_NAME = "btn_name";
    public static String NEED_SEX_DIALOG = "sexdialog";

    public static final String RECOMEND_CHANNELID = "actionId";

    public static String WEB_URL_DEFAULT = "book_url";

    public static final String PAGE_NAME_DISCOVERY_TOPIC_INTERST = "topicpage_interest";//发现-专题

    public static final String BOOK_STORE_ZONE_PARAMS = "BOOK_STORE_ZONE_PARAMS";
    public static final String BOOK_STORE_ZONE_TYPE = "BOOK_STORE_ZONE_TYPE";

    //从M站调起是搜索引擎类别
    public static final String FIRST_START_APP_FROM_MSITE_SEARCH_ENGINE = "msite_search_engine";

    //单书调起类型
    public static final String TYPE_FIRST_START_APP = "first_start_app";

    public static final String MARK = "com.qq.reader.mark";

    public static final String BOOK_DETAIL_BID = "book_detail_bid";

    public static final String NOTIFY_TAG = "notification_tag";

    public static final byte CHAPTER_BAT_DOWNLOAD_SUCCESS = 25;
    public static final byte CHAPTER_BAT_DOWNLOAD_FAILED = 26;
    public static final String LOCAL_CLOUMN_ID = "LOCAL_CLOUMN_ID";
    //网页扩展参数，切换到某个tab
    public static final String ONLINE_PAGE_EXTRA_PRAMS_TABINDEX = "tab_index";
    //网页扩展参数，福利页执行qurl跳转的页面层级
    public static final String ONLINE_PAGE_EXTRA_PRAMS_PAGE_LEVEL = "page_level";










}

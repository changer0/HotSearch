package com.qq.reader.dispatch;

/**
 * Created by hexiaole on 2017/6/19.
 *
 * 路由路径
 * 1.路径至少需要有两级，/xx/xx    默认第一级为分组名
 * 2.Router允许一个module中存在多个分组，但是不允许多个module中存在相同的分组，会导致映射文件冲突
 */

public class RoutePath {

    ///////////////////////////////////////////////////////////////////////////
    // 页面路由
    ///////////////////////////////////////////////////////////////////////////

    //新版书籍详情页
    public static final String READER_BOOK_DETAIL = "/reader/bookDetail";
    //书籍目录页
    public static final String BOOK_CHAPTER = "/reader/bookChapter/online/onlineChapter";
    //批量下载页
    public static final String BOOK_BATDOWNLOAD = "/reader/batDownload";
    // 承载2级页面的Activity
    public static final String BOOK_STORE_TWO_LEVEL = "/bookStore/twoLevel";
    // 承载2级页面的Activity,其顶部拥有popupMenu的样式
    public static final String BOOK_STORE_ZONE_TWO_LEVEL = "/bookStore/popTwoLevel";
    //专题的全部回复列表、评价
    public static final String BOOK_STORE_END_PAGE = "/bookStore/endPage";
    //今日秒杀
    public static final String BOOK_STORE_LIMIT_TIME_DISCOUNT_BUY = "/bookStore/limitTimeDiscountBuy";
    // 经典
    public static final String BOOK_STORE_CLASSIC = "/bookStore/classic";
    //作家主页
    public static final String BOOK_STORE_AUTHOR_PAGE = "/bookStore/authorPage";
    //用户首页
    public static final String BOOK_STORE_USER_CENTER = "/bookStore/userCenter";
    //云书架
    public static final String BOOK_STORE_CLOUD_BOOK_LIST = "/bookStore/cloudBookList";
    //本地导入书
    public static final String READER_LOCAL_IMPORT = "/reader/localImport";
    // 浏览历史
    public static final String PROFILE_HISTORY = "/profile/history";
    //我的等级
    public static final String PROFILE_LEVEL = "/profile/level";

    //签到详情
    public static final String PROFILE_SIGN_DETAILS = "/profile/signDetails";
    
    //我的帐户
    public static final String PROFILE_ACCOUNT = "/profile/account";
    //MainActivity
    public static final String MAIN_ACTIVITY = "/main/mainActivity";
    //书评广场
    public static final String BOOK_COMMENT_INDEX = "/bookStore/bookCommentIndex";
    //名人堂
    public static final String BOOK_STORE_HALL_OF_FAME = "/bookStore/hallOfFame";
    //意见反馈
    public static final String PROFILE_NATIVE_SUGGEST = "/profile/suggest";
    //阅读基因
    public static final String PROFILE_NATIVE_READING_GENE = "/profile/readingGene";
    //主题详情
    public static final String PROFILE_NATIVE_SKIN_DETAIL = "/profile/nativeSkinDetail";
    //精华书评
    public static final String BOOK_STORE_SELECTED_COMMENT = "/bookStore/nativeSelectedComment";

    // 我的基因设置
    public static final String FEED_MYPREFERENCE_GENSET = "/feed/myperference/genset";

    // 专区书库
    public static final String BOOK_STORE_ZONE_BOOK = "/bookStore/bookstorezone";

    //专属推荐二级页
    public static final String BOOK_STORE_EXCLUSIVE_RECOMMEND = "/bookStore/exclusiverecommedn";

    //登录选择页面
    public static final String LOGIN_CHOOSE = "/login/choose";

    //消息
    public static final String PROFILE_MESSAGE = "/profile/message";

    //福利
    public static final String PROFILE_WELFARE = "/profile/welfare";

    //隐私设置
    public static final String PROFILE_PRIVACY_SETTING = "/profile/privacysetting";


    /**************** 充值 **************/
    public static final String PAY_CHARGE = "/pay/charge/coin";
    public static final String PAY_MONTH = "/pay/charge/month";
    public static final String PAY_MONTH_HW = "/pay/charge/month_hw";

    /***************pay service******************/
    public static final String PAY_SERVICE = "/pay/payService";
    public static final String PAY_MOUDLE_SERVICE = "/pay/payMoudleService";

    /**************** 听书 ***************/
    //听书页
    public static final String TING_PLAYER = "/ting/player";
    public static final String TING_AREA = "/ting/listenzone";
    public static final String TING_DOWNLOAD = "/ting/download";
    // 听书目录页 和 BOOK_CHAPTER 是同一个页面

    /**************** 排行榜 ***************/
    // 新版排行榜
    public static final String BOOK_STORE_NEW_RANK = "/bookStore/newRank";

    /************* WebModule ***********/
    public static final String WEB_BROWER = "/webpage/webbrower";

    /***************搜索******************/
    public static final String BOOK_STORE_SEARCH_COMMON = "/bookStore/commonSearch";
    public static final String BOOK_STORE_SEARCH_LISTEN = "/bookStore/listenSearch";
    public static final String BOOK_STORE_SEARCH_MONTH = "/bookStore/monthSearch";
    public static final String BOOK_STORE_SEARCH_FREE = "/bookStore/freeSearch";
    //搜书神器主页
    public static final String BOOK_STORE_SEARCH_TOOL = "/bookStore/searchTool";
    //定义组合搜索
    public static final String BOOK_STORE_SEARCH_OPTION = "/bookStore/searchOption";
    //搜书神器跳转结果页
    public static final String BOOK_STORE_SEARCH_RESULTS = "/bookStore/searchResults";

    /**********通用多tab Activity***********/
    public static final String COMMON_EXACTTAB = "/common/exacttab";

    /***************message******************/
    public static final String MESSAGE_DATABASE_HANDLER = "/message/messageDatabaseHandler";

    public static final String PRIVACY = "privacy/webpage";

    /***************Plugin Module******************/
    public static final String PLUGIN_LIST = "/plugin/list";
    public static final String PLUGIN_DOWNLOAD = "/plugin/download";
    public static final String PLUGIN_FONTS_LIST = "/plugin/fonts";
    //皮肤管理
    public static final String PROFILE_NATIVE_SKIN_MANAGER = "/plugin/skinManager";
    public static final String PLUGIN_SKIN_SERVICE= "/plugin/skin/service";
    //目录,想法,笔记
    public static final String READER_CONTENTNOTS = "/readerengine/contentnots";


    ///////////////////////////////////////////////////////////////////////////
    // 服务路由
    ///////////////////////////////////////////////////////////////////////////

    /***************push******************/
    public static final String PUSH_ACTION_HANDLE = "/bookStore/actionHandle";
    public static final String PUSH_MANAGER = "/push/manager";
    public static final String PUSH_STAT_MANAGER = "/push/statManger";

    /****** AdvModule ********/
    // AdvManager
    public static final String ADV_MANAGER = "/adv/service/manager";

    /***************频道页******************/
    public static final String CHANNEL_TWO_PAGE = "/main/channelTwo/";//频道二级页

    /***************单列表书籍通用二级页 替换频道二级页******************/
    public static final String COMMON_BOOK_LIST_TWO_PAGE = "/main/commonbooklist/";//频道二级页

    /***************道具******************/
    public static final String PROPS_MANAGER = "/propsservice/manager";//道具管理

    /***************登录******************/
    public static final String LOGIN_MOUDLE_SERVICE = "/loginModule/loginMoudleService";
    public static final String LOGIN_INFO_SERVICE = "/loginservice/logininfo";//登录相关服务
    public static final String USER_INFO_MANAGER_SERVICE = "/apploginservice/userInfoManager";

    /***************书架批量管理******************/
    public static final String BOOKSHELF_BATCH_MANAGER = "/bookshelf/batchManager";
    /***************分享******************/
    public static final String SHARE_MODULE_SERVICE = "/shareModule/share";

    //TODO zdy重构 因为飞读还没有AudioModule，所以暂时先放到这里。
    /***************Audio Book Module******************/
    public static final String AUDIO_BOOK_PLAYER_SERVICE = "/audioBookModule/player/service";

    /*************** APP MODULE 下未拆分的 Service******************/
    public static final String APP_MODULE_SERVICE = "/app/appModuleService";

    /*************** APP MODULE 新手特权 Service******************/
    public static final String PRIVILEGE_MODULE_SERVICE = "/app/privilegeModuleService";

    //提交评论
    public static final String BOOK_STORE_COMIT_COMMENT = "/bookStore/comitComment";

    /***********搜索模块 新的分类二级页 **************************/
    public static final String SEARCH_BOOK_CLASSIFY_PAGE = "/search/bookclassify";
}

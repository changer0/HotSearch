package com.example.providermoduledemo;

import com.qq.reader.appconfig.AppConfig;
import com.qq.reader.appconfig.AppDebug;
import com.qq.reader.common.login.define.LoginConstant;
import com.qq.reader.common.utils.CommonConstant;
import com.qq.reader.common.utils.ServerUrl;
import com.qq.reader.core.BaseApplication;
import com.qq.reader.core.config.AppConstant;

/**
 * Created by River on 16/10/8.
 */

public class FlavorConfig {

    public static final String C_PLATFORM="cofree";
    public static final String SDCARD_DIRECTORY=null;//为null则使用AppConstant.ROOT_PATH默认的路径
    public static final String CUR_VERSION="cofree_2.0.2.300_android_cooperate";//"oppobook_3.0.3.300_android_oppo";
    public static final String VERSION_NAME_NOW="2.0.2.300";
    public static final String SHARE_ICON_72 = "https://16dd-advertise-1252317822.file.myqcloud.com/common_file/1122_2019-01-11/1547191673037_627671.png";

    public static final String QULSCHEME = "unitecofree";
    public static final String OtherQULSCHEME = "uniteCofree";

//    public static final String WXSCHEME0 = "AndroidHNReader";
//    public static final String WXSCHEME1 = "androidhnreader";

    //Bugly相关
    public static final String BUGLY_APPID = "0c21bb6d09";
    public static final String BUGLY_APPKEY = "15b4e90f-7984-4b36-be5a-c29f1e5320d1";

    public static final String WX_SHARE_APPID = "wx601a4a1d543be28b";


    public static  String APPID = LoginConstant.YWLOGIN_APPID;
    public static  String AREAID = LoginConstant.YWLOGIN_APPAREAID;


    // 信息流顶部广告下方是否带倒影 oppo3.1大主题适配添加
    public static boolean isFeedHeadAdvWithReflection = false;
    public static boolean isCornersCard = false;


    public static String DOMAINNAME_IYUEDU = null;
    public static String DOMAINNAME_WX = null;

    /**
     /v6/nativepage/book/{id}
     /ChapBatAuthWithPDV2
     /v6/readover
     /v6/chapterOver
     /v6/chapterOverAd
     /v6/nativepage/book/detailExt
     /querybookinfo
     */
    public static String DOMAINNAME_ALLREADER = null;//飞读阅读相关功能的域名


    //    public static String DOMAINNAME_ACTIVE = null;
//    public static String DOMAINNAME_QQREADER_3G = null;//废弃
//    public static String DOMAINNAME_EBOOKFU_3G = null;
//    public static String DOMAINNAME_G_BOOK = null;//废弃
//    public static String DOMAINNAME_SUPPORT = null;//废弃
    public static String DOMAINNAME_ZXAUTIOCMP = null;//联想，听书和本地书
    public static String DOMAINNAME_ZXSEARCH = null;//搜索热词，搜索
    public static String GETHOTWORDS = null;//搜索热词102版本
    public static String DOMAINNAME_COMMON = null;//飞读通用域名，替换coopmain、coopmainpage、cooperatecommon、cooperateext这个三个域名
    public static String DOMAINNAME_RECENT = null;//私密阅读
    public static String DOMAINNAME_STATISTIC = null;//日志上报
    //后续应该可以和DOMAINNAME_LOG合并成一个。
    //DOMAINNAME_LOG 未在使用 @20171121
    public static String URL_VERSION = "6_0";
    public static String URL_VERSION2 = "6";
    public static String SERVER_URL_VERSION = "1_0_0";
    public static String DOMAINNAME_CDKEY_ACTIVE_URL = null;//激活码中心

    //排行榜
    public static String QQREADER_QUERY_OPEN_VIP_MONTH_PATH = null;
    public static String QQREADER_OPEN_VIP_PATH = null;
    public static String DOMAINNAME_OPENMONTH = null;//开通包月,飞读废弃
    public static String DOMAINNAME_CLOUD = null;//云笔记

    //书架轮播广告
    public static String URL_BOOKSHELF_HEADER_ADV = null;

    //书架内置书
    public static String URL_BOOKSHELF_INTERNAL_BOOK = null;

    //飞读获取偏好
    public static String USER_DEGREE = null;

    //飞读春节领红包
    public static String URL_OBTAIN_RED_PACKET = null;

    //查询阅读时长
    public static String QUERY_READTIME = null;

    //听书
    public static String READONLINE_MUSIC_URL = null;

    /**
     * 我的页面激活码兑换地址
     * 跟后台沟通过，没有测试环境(by yanxuehui) 保险起见加一个c_platform
     */
    public static String PROFILE_EXCHANGE_URL = null;

    //专属推荐
    public static int MIN_COVER_NUM = 18;

    //包月特权
    public static String VIP_OPEN_BASE_URL = "/privilege2.html";

    //在线客服域名
    public static String DOMAINNAME_ONLINE_CUSTOMER = null;

    //在线客服url
    public static String URL_ONLINE_CUSTOMER = null;




    //白牌品读趣读H5特殊协议域名,飞读未使用
    public static String DOMAINNAME_INTERESTING_READ = null;

    //每日福利页面
    public static String DAILY_WELFARE = null;

    //积分提现页面
    public static String CASH_OUT = null;

    //我的积分页面
    public static String MY_INTEGRAL = null;

    //首页赚积分web tab
    public static String MAIN_WEB_EARN_INTEGRAL_TAB = null;
    //首页兑好礼web tab
    public static String MAIN_WEB_EXCHANGE_GIFT_TAB = null;

    //积分商城首页
    public static String MALL_HOME = null;


    public static String URL_SIGN_OBTAIN_AD_REWARD = null;

    public static String GET_USER_INFO = null;

    public static String OBTAIN_TASK_VIDEO_REWARD = null;

    public static String OBTAIN_USER_TASK = null;//领取奖励接口

    //飞读阅读尾页数据相关
    public static String PROTOCOL_SERVER_URL_READER = null;

    //换肤权限
    public static final String SKIN_PERMISSION = "com.yuewen.cooperate.reader.free.theme.permission";
    //全局广播权限
    public static final String BROADCAST_PERMISSION = "com.yuewen.cooperate.reader.free.whitecard.permission";


    //106新增 书架福利card
    public static String URL_BOOKSHELF_WELFARE = null;

    //108新增 书架底部推荐card
    public static String URL_BOOKSHELF_RECOMMEND = null;


    /**
     * 获取分类页配置接口
     */
    public static String URL_OBTAIN_CATEGORY_CONFIG = null;

    /**
     * 获取导航下全部分类
     */
    public static String URL_OBTAIN_CATEGORY_ALL = null;

    /**
     * 获取导航下热门标签
     */
    public static String URL_OBTAIN_CATEGORY_HOT_TAG = null;

    /**
     * 获取排行版数据
     */
    public static String URL_OBTAIN_CATEGORY_RANK = null;

    /**
     * 获取排行版左侧列表数据
     */
    public static String URL_OBTAIN_RANK_LEFT_TAB_INFO = null;

    /**
     * 获取排行版右侧数据
     */
    public static String URL_OBTAIN_RANK_RIGHT_INFO = null;

    /**
     * 我的书友
     * 109 新增 by p_bbinlliu
     */
    public static String URL_MY_BOOK_FRIENDS = null;


    /**
     * 邀请书友
     * 109 新增 by p_bbinlliu
     */
    public static String URL_INVITATION_FRIENDS = null;

    /**
     * 兑换中心
     * 109 新增 by p_bbinlliu
     */
    public static String URL_EXCHANGE_CENTER = null;

    /**
     * 绑定手机号
     * 200 新增 by 刘健
     */
    public static String URL_BIND_PHONE = null;

    /**
     * 注销手机号
     * 202 新增 by 刘健
     */
    public static String URL_USER_CANCEL = null;

    public static void init(){
        AppConstant.isShowNetLog = AppDebug.isDebug();
    }

    static{

        AppConfig.C_PLATFORM = C_PLATFORM;
        AppConfig.URL_VERSION = URL_VERSION;
        AppConfig.APPID = APPID;
        AppConfig.AREAID = AREAID;
        AppConfig.WX_SHARE_APPID = WX_SHARE_APPID;
        AppConfig.URL_VERSION2 = URL_VERSION2;
        AppConfig.CUR_VERSION = CUR_VERSION;
        AppConfig.VERSION_NAME_NOW = VERSION_NAME_NOW;
        AppConfig.SERVER_URL_VERSION = SERVER_URL_VERSION;
        AppConfig.SKIN_PERMISSION = SKIN_PERMISSION;

        AppConstant.ROOT_PATH = BaseApplication.Companion.getINSTANCE().getExternalFilesDir(null)+"/";
        AppConstant.CACHE_ROOT_PATH = BaseApplication.Companion.getINSTANCE().getExternalCacheDir()+"/";
        AppConstant.INNER_ROOT_PATH = BaseApplication.Companion.getINSTANCE().getFilesDir()+ "/";
        AppConstant.DEFAULT_PATH = "/cofree";

        CommonConstant.BROADCAST_PERMISSION = BROADCAST_PERMISSION;
        CommonConstant.CONFIG_RECORD = AppConstant.ROOT_PATH + "record.db";
        CommonConstant.CONFIG_RECORD_GZIP = AppConstant.ROOT_PATH + "recordzip.db";
//
//        AdvConstants.ADV_DB = AppConstant.ROOT_PATH + "adv.db";
//        AdvConstants.ATM_ADV_DB = AppConstant.ROOT_PATH + "atmadv.db";

        //域名字典
        if (AppDebug.env == AppDebug.DEV_ENV) {
            DOMAINNAME_IYUEDU = "https://pt.reader.qq.com/";
            DOMAINNAME_WX = "http://wxdl.bookcs.3g.qq.com/";
            DOMAINNAME_ALLREADER = "https://cpptmainpage.reader.qq.com/";
//            DOMAINNAME_G_BOOK = "https://cs.g.book.qq.com/";
//            DOMAINNAME_SUPPORT = "https://support.qq.com/";
            DOMAINNAME_ZXAUTIOCMP = "https://ptcoopautocmp.reader.qq.com/";
            DOMAINNAME_ZXSEARCH = "https://ptcoopsearch.reader.qq.com/";
            DOMAINNAME_COMMON = "https://cpptmainpage.reader.qq.com/";
            DOMAINNAME_RECENT = "http://recentbook.bookcs.3g.qq.com/";
            DOMAINNAME_STATISTIC = "https://ptcooperatelog.reader.qq.com/";
            DOMAINNAME_CDKEY_ACTIVE_URL = "http://uat.pages.book.qq.com/";
            DOMAINNAME_OPENMONTH = "https://sbchargegateway.reader.qq.com/";
            DOMAINNAME_CLOUD = "https://ptcoopcloudnote.reader.qq.com/";
            READONLINE_MUSIC_URL = "https://ptmdwncoop.reader.qq.com";
            DOMAINNAME_ONLINE_CUSTOMER = "http://chat.95ib.com/";
            DOMAINNAME_INTERESTING_READ = "https://cpptmainpage.reader.qq.com";
            URL_BIND_PHONE = "https://oaaq.yuewen.com/mobile/bindphone";
            URL_USER_CANCEL = "https://oaaq.yuewen.com/mobile/usercancel";
        } else if (AppDebug.env == AppDebug.TEST_ENV) {
            DOMAINNAME_IYUEDU = "https://ptsolomo.reader.qq.com/";
            DOMAINNAME_WX = "http://wxdl.bookcs.3g.qq.com/";
            DOMAINNAME_ALLREADER = "https://ptcoopmainpage.reader.qq.com/";
//            DOMAINNAME_G_BOOK = "https://cs.g.book.qq.com/";
//            DOMAINNAME_SUPPORT = "https://support.qq.com/";
            DOMAINNAME_ZXAUTIOCMP = "https://ptcoopautocmp.reader.qq.com/";
            DOMAINNAME_ZXSEARCH = "https://ptcoopsearch.reader.qq.com/";
            DOMAINNAME_COMMON = "https://ptcoopmainpage.reader.qq.com/";
            DOMAINNAME_RECENT = "http://recentbook.bookcs.3g.qq.com/";
            DOMAINNAME_STATISTIC = "https://ptcooperatelog.reader.qq.com/";
            DOMAINNAME_CDKEY_ACTIVE_URL = "http://uat.pages.book.qq.com/";
            DOMAINNAME_OPENMONTH = "https://sbchargegateway.reader.qq.com/";
            DOMAINNAME_CLOUD = "https://ptcoopcloudnote.reader.qq.com/";
            READONLINE_MUSIC_URL = "https://ptmdwncoop.reader.qq.com";
            DOMAINNAME_ONLINE_CUSTOMER = "http://chat.95ib.com/";
            DOMAINNAME_INTERESTING_READ = "https://ptcoopmainpage.reader.qq.com";
            URL_BIND_PHONE = "https://oaaq.yuewen.com/mobile/bindphone";
            URL_USER_CANCEL = "https://oaaq.yuewen.com/mobile/usercancel";
        } else if (AppDebug.env == AppDebug.PRE_RELEASE_ENV) {
            DOMAINNAME_IYUEDU = "https://yfyuedu.reader.qq.com/";
            DOMAINNAME_WX = "http://wxdl.book.qq.com/";
            DOMAINNAME_ALLREADER = "https://ptyfcoopmainpage.reader.qq.com/";
//            DOMAINNAME_G_BOOK = "https://g.book.qq.com/";
//            DOMAINNAME_SUPPORT = "https://support.qq.com/";
            DOMAINNAME_ZXAUTIOCMP = "https://coopautocmp.reader.qq.com/";
            DOMAINNAME_ZXSEARCH = "https://coopsearch.reader.qq.com/";
            DOMAINNAME_COMMON = "https://ptyfcoopmainpage.reader.qq.com/";
            DOMAINNAME_RECENT = "http://recentbook.book.3g.qq.com/";
            DOMAINNAME_STATISTIC = "https://ptyfcooperatelog.reader.qq.com/";
            DOMAINNAME_CDKEY_ACTIVE_URL = "http://cdk.book.qq.com/";
            DOMAINNAME_OPENMONTH = "https://chargegateway.reader.qq.com/";
            DOMAINNAME_CLOUD = "https://coopcloudnote.reader.qq.com/";
            READONLINE_MUSIC_URL = "https://mdwncoop.reader.qq.com";
            DOMAINNAME_ONLINE_CUSTOMER = "http://yw.95ib.com/";
            DOMAINNAME_INTERESTING_READ = "https://ptyfcoopmainpage.reader.qq.com";
            URL_BIND_PHONE = "https://aq.yuewen.com/mobile/bindphone";
            URL_USER_CANCEL = "https://aq.yuewen.com/mobile/usercancel";
        } else if (AppDebug.env == AppDebug.RELEASE_ENV) {
            DOMAINNAME_IYUEDU = "https://yuedu.reader.qq.com/";
            DOMAINNAME_WX = "http://wxdl.book.qq.com/";
            DOMAINNAME_ALLREADER = "https://freeread.reader.qq.com/";
//            DOMAINNAME_G_BOOK = "https://g.book.qq.com/";
//            DOMAINNAME_SUPPORT = "https://support.qq.com/";
            DOMAINNAME_ZXAUTIOCMP = "https://coopautocmp.reader.qq.com/";
            DOMAINNAME_ZXSEARCH = "https://coopsearch.reader.qq.com/";
            DOMAINNAME_COMMON = "https://free.reader.qq.com/";
            DOMAINNAME_RECENT = "http://recentbook.book.3g.qq.com/";
            DOMAINNAME_STATISTIC = "https://cooperatelog.reader.qq.com/";
            DOMAINNAME_CDKEY_ACTIVE_URL = "http://cdk.book.qq.com/";
            DOMAINNAME_OPENMONTH = "https://chargegateway.reader.qq.com/";
            DOMAINNAME_CLOUD = "https://coopcloudnote.reader.qq.com/";
            READONLINE_MUSIC_URL = "mdwncoop.reader.qq.com";
            DOMAINNAME_ONLINE_CUSTOMER = "http://yw.95ib.com/";
            DOMAINNAME_INTERESTING_READ="https://free.reader.qq.com";
            URL_BIND_PHONE = "https://aq.yuewen.com/mobile/bindphone";
            URL_USER_CANCEL = "https://aq.yuewen.com/mobile/usercancel";
        }

        QQREADER_QUERY_OPEN_VIP_MONTH_PATH = "getOpenVipConfig?";
        QQREADER_OPEN_VIP_PATH = "openVip?";
        USER_DEGREE = DOMAINNAME_COMMON + "cofree-user/user-degree/get";
        URL_OBTAIN_RED_PACKET = DOMAINNAME_COMMON + "activity/cash";
        QUERY_READTIME = DOMAINNAME_COMMON + "free/readtime/init";
        PROFILE_EXCHANGE_URL = "http://pages.book.qq.com/phone?version=new&c_platform=" + FlavorConfig.C_PLATFORM;
        URL_BOOKSHELF_HEADER_ADV = DOMAINNAME_COMMON + "bookshelf/ad?sex=";

        URL_BOOKSHELF_INTERNAL_BOOK = DOMAINNAME_COMMON + "bookshelf/internalbook";
        URL_SIGN_OBTAIN_AD_REWARD = DOMAINNAME_COMMON + "sign/ad/award";
        GET_USER_INFO = DOMAINNAME_COMMON + "cofree-user/info";
        PROTOCOL_SERVER_URL_READER = DOMAINNAME_ALLREADER + "v" + URL_VERSION2 + "/";
        OBTAIN_TASK_VIDEO_REWARD = DOMAINNAME_COMMON + "task-center/auto/convert";
        OBTAIN_USER_TASK = DOMAINNAME_COMMON + "task-center/convert";
        URL_BOOKSHELF_WELFARE = DOMAINNAME_COMMON + "bookshelf/welfare";
        URL_BOOKSHELF_RECOMMEND = DOMAINNAME_COMMON + "bookshelf/cofree/recommend";


        ServerUrl.QULSCHEME = QULSCHEME;
        ServerUrl.OtherQULSCHEME = OtherQULSCHEME;
        ServerUrl.QURL_BOOK_DETAIL_URL = "book/";
        ServerUrl.DOMAINNAME_IYUEDU = DOMAINNAME_IYUEDU;
        ServerUrl.DOMAINNAME_ADR = DOMAINNAME_COMMON;
        ServerUrl.DOMAINNAME_WX = DOMAINNAME_WX;
        ServerUrl.DOMAINNAME_ANDROID_READER = DOMAINNAME_COMMON;
        ServerUrl.DOMAINNAME_ALLREADER = DOMAINNAME_ALLREADER;
//        ServerUrl.DOMAINNAME_ACTIVE = DOMAINNAME_ACTIVE;
//        ServerUrl.DOMAINNAME_QQREADER_3G = DOMAINNAME_QQREADER_3G;
//        ServerUrl.DOMAINNAME_EBOOKFU_3G = DOMAINNAME_EBOOKFU_3G;
//        ServerUrl.DOMAINNAME_G_BOOK = DOMAINNAME_G_BOOK;
//        ServerUrl.DOMAINNAME_SUPPORT = DOMAINNAME_SUPPORT;
        ServerUrl.DOMAINNAME_ZXAUTIOCMP = DOMAINNAME_ZXAUTIOCMP;
        ServerUrl.DOMAINNAME_ZXSEARCH = DOMAINNAME_ZXSEARCH;
        ServerUrl.DOMAINNAME_COMMON = DOMAINNAME_COMMON;
        ServerUrl.DOMAINNAME_RECENT = DOMAINNAME_RECENT;
        ServerUrl.DOMAINNAME_STATISTIC = DOMAINNAME_STATISTIC;
//        ServerUrl.DOMAINNAME_LOG = DOMAINNAME_LOG;
        ServerUrl.DOMAINNAME_COOPERATEEXT = DOMAINNAME_COMMON;
        ServerUrl.DOMAINNAME_CDKEY_ACTIVE_URL = DOMAINNAME_CDKEY_ACTIVE_URL;
        ServerUrl.QQREADER_QUERY_OPEN_VIP_MONTH_PATH = QQREADER_QUERY_OPEN_VIP_MONTH_PATH;
        ServerUrl.QQREADER_OPEN_VIP_PATH = QQREADER_OPEN_VIP_PATH;
//        ServerUrl.DOMAINNAME_H5 = DOMAINNAME_H5;
        ServerUrl.VIP_OPEN_BASE_URL = VIP_OPEN_BASE_URL;
        ServerUrl.SHARE_ICON_72 = SHARE_ICON_72;
        ServerUrl.CLOUD_NOTE_SERVER_URL = DOMAINNAME_CLOUD;
        ServerUrl.READONLINE_BOOK_URL = DOMAINNAME_ALLREADER+"ChapBatAuthWithPDV2?";
        ServerUrl.READONLINE_MUSIC_URL = READONLINE_MUSIC_URL+"ChapBatAuthWithPDV2";
        ServerUrl.QQREADER_UPDATE_SERVER_URL = DOMAINNAME_COMMON + "checkupdate";
        ServerUrl.DOMAINNAME_INTERESTING_READ = DOMAINNAME_INTERESTING_READ;
    }

    static {

        if (AppDebug.isDebug()) {
            URL_ONLINE_CUSTOMER = DOMAINNAME_ONLINE_CUSTOMER + "online?uid=101&userid=";
        } else {
            URL_ONLINE_CUSTOMER = DOMAINNAME_ONLINE_CUSTOMER + "online?uid=436&userid=";
        }

        if(AppDebug.env == AppDebug.TEST_ENV){
            ServerUrl.SERVER_URL = ServerUrl.DOMAINNAME_IYUEDU + "book_res/reader/" + AppConfig.C_PLATFORM + "/" + AppConfig.SERVER_URL_VERSION;
            ServerUrl.IYUEDU_COMMON_SERVER_URL = ServerUrl.DOMAINNAME_IYUEDU + "book_res/reader/" + AppConfig.C_PLATFORM +  "/"+ AppConfig.SERVER_URL_VERSION + "/";
            ServerUrl.PUBLICITY_SERVER_URL = ServerUrl.DOMAINNAME_IYUEDU + "book_res/reader/" + AppConfig.C_PLATFORM + "/publicity/";
        }else{
            ServerUrl.SERVER_URL = DOMAINNAME_IYUEDU + C_PLATFORM + "/" + SERVER_URL_VERSION;
            ServerUrl.IYUEDU_COMMON_SERVER_URL = DOMAINNAME_IYUEDU + C_PLATFORM + "/1_0_0/";
            ServerUrl.PUBLICITY_SERVER_URL = DOMAINNAME_IYUEDU + C_PLATFORM + "/publicity/";
        }

        DAILY_WELFARE = ServerUrl.SERVER_URL + "/dailyWelfare.html";
        CASH_OUT = ServerUrl.SERVER_URL + "/cashOut.html";
        MY_INTEGRAL = ServerUrl.SERVER_URL + "/myIntegral.html";
        MAIN_WEB_EARN_INTEGRAL_TAB = ServerUrl.SERVER_URL + "/earnIntegral.html";
        MAIN_WEB_EXCHANGE_GIFT_TAB = ServerUrl.SERVER_URL + "/exchangeGift.html";
        MALL_HOME = ServerUrl.SERVER_URL + "/mallHome.html";
        URL_MY_BOOK_FRIENDS = ServerUrl.SERVER_URL + "/myBookFriends.html";
        URL_EXCHANGE_CENTER = ServerUrl.SERVER_URL + "/cdkeyCenter.html";
        URL_INVITATION_FRIENDS = ServerUrl.SERVER_URL + "/inviteFriends.html";

        GETHOTWORDS = DOMAINNAME_COMMON + "search/hotword";
        ServerUrl.SERVER_DATA_RUL = DOMAINNAME_COMMON + URL_VERSION + "/nativepage";
        ServerUrl.SERVER_DATA_URL_DEPRECATED = DOMAINNAME_COMMON + URL_VERSION;
        ServerUrl.WX_SERVER_URL = DOMAINNAME_WX + "weixin/";
        ServerUrl.PROTOCOL_SERVER_URL = DOMAINNAME_COMMON + "v" + URL_VERSION2 + "/";
        ServerUrl.PROTOCOL_SERVER_URL_FOR_CLASSIFY = DOMAINNAME_COMMON;
        ServerUrl.URL_REWARD_COMMENT = DOMAINNAME_COMMON + "v" + URL_VERSION2 + "/";
        ServerUrl.STATICIS_SERVER_URL = DOMAINNAME_COMMON;
        ServerUrl.COMMON_SERVICE_URL = DOMAINNAME_COMMON;
        ServerUrl.QQREADER_SYN_QQ_URL = DOMAINNAME_COMMON;
//        ServerUrl.SERVER_OLD_URL = DOMAINNAME_QQREADER_3G;
//        CLOUD_NOTE_SERVER_URL = DOMAINNAME_EBOOKFU_3G;
//        ServerUrl.H5GAME_SERVER_URL = DOMAINNAME_G_BOOK;
        ServerUrl.ADR_COMMON_SERVICE_URL = DOMAINNAME_COMMON + "common";
//        ServerUrl.H5_SUPPORT_SERVER_URL = DOMAINNAME_SUPPORT + "touch/";
        ServerUrl.COMMON_PROTOCOL_URL = DOMAINNAME_COMMON + "v" + URL_VERSION + "/";
//        LOG_UPLOAD_URL = DOMAINNAME_LOG + "common/clientlog";
        ServerUrl.READ_DAY_URL = ServerUrl.DOMAINNAME_COMMON + "v" + URL_VERSION + "/";
        ServerUrl.ONLINE_WORKER_URL =  ServerUrl.DOMAINNAME_COMMON + "v" + URL_VERSION + "/";
        //新版本排行榜
        ServerUrl.RANK_NEW_SERVER_URL = DOMAINNAME_COMMON + "v" + URL_VERSION + "/";
        ServerUrl.CDKEY_ACTIVE_URL = DOMAINNAME_CDKEY_ACTIVE_URL + "phone?c_platform=" + FlavorConfig.C_PLATFORM;
        ServerUrl.FEED_NEW_SERVER_URL = DOMAINNAME_COMMON;

        ServerUrl.READ_PRIVATE_SETUP_URL = DOMAINNAME_RECENT + "mqq/upbookstatusbatch?";
        ServerUrl.UPLOAD_PROJECT_STATE＿URL = ServerUrl.SERVER_DATA_URL_DEPRECATED + "/interact/report?";
        ServerUrl.SKIN_DETAIL_PRE_URL = ServerUrl.SERVER_URL + "/themeDetail2.html?";
        ServerUrl.READ_ACHIEVEMENT_SHARE_URL = ServerUrl.IYUEDU_COMMON_SERVER_URL + "readAchievementShare.html?";
        ServerUrl.OBTAIN_PAYPAGE_ADV_URL = ServerUrl.PROTOCOL_SERVER_URL + "payingGuideV2";
        ServerUrl.BUY_BOOK_URL = ServerUrl.SERVER_URL + "/buybook.html?";
        ServerUrl.PAY_BOOK_URL = ServerUrl.COMMON_SERVICE_URL + "common/dobuybook?";
        ServerUrl.BUY_CHAPTER_URL = ServerUrl.SERVER_URL + "/buyChapter.html?";
        ServerUrl.BUY_PLUGIN_URL = ServerUrl.SERVER_URL + "/buyplugin.html?";
        ServerUrl.BUY_AUDIOBOOK_URL = ServerUrl.SERVER_URL  + "/buyaudiobook.html?";
        ServerUrl.BUY_CHAPTERS_URL = ServerUrl.COMMON_SERVICE_URL + "common/dobuychapters?";
        ServerUrl.BUY_TING_BOOK_RECORD = ServerUrl.COMMON_SERVICE_URL + "common/isbookbuy?";
        ServerUrl.BUY_CHAPTERS_RECORD = ServerUrl.PROTOCOL_SERVER_URL + "queryChapterLoad?";
        ServerUrl.VIP_BASEURL = ServerUrl.SERVER_URL + "/kt_vip.html";
        ServerUrl.BUY_RECORDS_URL = ServerUrl.SERVER_URL + "/buyhistory.html?";
        ServerUrl.OBTAIN_BOOK_DOWNLOAD_URL = ServerUrl.COMMON_SERVICE_URL + "bookalldown?";
        ServerUrl.MUSIC_BOOK_DOWNLOAD_URL = ServerUrl.SERVER_DATA_URL_DEPRECATED + "/downmedia?";
        ServerUrl.FEEDBACK_URL = ServerUrl.SERVER_DATA_URL_DEPRECATED + "/feedback/submit";
        ServerUrl.QQREADER_SERVER_URL = ServerUrl.COMMON_SERVICE_URL;
        ServerUrl.QQREADER_COMMON_ALL_URL = ServerUrl.DOMAINNAME_ADR + "app/sync";
        ServerUrl.QQREADER_PLUGIN_SERVER_URL = ServerUrl.QQREADER_SERVER_URL + "pluginupdate?id=";
        ServerUrl.QQREADER_PLUGIN_LIST_URL = ServerUrl.QQREADER_SERVER_URL + "getpluginlist?cid=";
        ServerUrl.QQREADER_SUGGEST_SERVER_URL = ServerUrl.QQREADER_SERVER_URL + "feedback";
        ServerUrl.QQREADER_IDENTIFY_SERVER_URL = ServerUrl.QQREADER_SERVER_URL + "auth?";
        ServerUrl.QQREADER_QUERYNEW_SERVER_URL = ServerUrl.QQREADER_SERVER_URL + "bookshelf/querylastchapter";
        ServerUrl.QQREADER_HOTWORDS = DOMAINNAME_ZXSEARCH + "hotkey?hotkeytype=" + C_PLATFORM + "&changenum=";
        ServerUrl. QQREADER__LISTEN_HOTWORDS = DOMAINNAME_ZXSEARCH + "audiohotkey?changenum=";
        ServerUrl.QQREADER_UPLOAD_EXCEPTION = ServerUrl.QQREADER_SERVER_URL + "exception";
        ServerUrl.QQREADER_GET_ADV_URL = ServerUrl.PROTOCOL_SERVER_URL + "adV2";
        ServerUrl.QQREADER_GET_CHARGE_LIST_URL = ServerUrl.SERVER_DATA_RUL + "/coin/recharge";
        ServerUrl.QQREADER_CLOUD_SHELF_UPDATE_BIG = ServerUrl.QQREADER_SERVER_URL + "cloud/updatepaged?clientversion=";
        ServerUrl.QQREADER_CLOUD_BOOK_SYN_COMMIT_BIG = ServerUrl.QQREADER_SERVER_URL + "cloud/commit?tid=";
        ServerUrl.QQREADER_CLOUD_SHELF_UPDATE1 = ServerUrl.QQREADER_SYN_QQ_URL + "cloud/update?clientversion=";
        ServerUrl.QQREADER_CLOUD_BOOK_SYN_UPDATE = ServerUrl.QQREADER_SYN_QQ_URL + "cloud/updatebook?bookid=";
        ServerUrl.QQREADER_CLOUD_BOOK_SYN_COMMIT = ServerUrl.QQREADER_SYN_QQ_URL + "cloud/commit?tid=";
        ServerUrl.QQREADER_CLOUD_BOOK_SYN_COMMIT_NEW = ServerUrl.QQREADER_SYN_QQ_URL + "bookshelf/commit?tid=";
        ServerUrl.QQREADER_CLOUD_BOOK_SYN_UPDATE_NEW = ServerUrl.QQREADER_SYN_QQ_URL + "bookshelf/updatebook?bookid=";
        ServerUrl.QQREADER_CLOUD_SHELF_UPDATE1_NEW = ServerUrl.QQREADER_SYN_QQ_URL + "bookshelf/update?clientversion=";
        ServerUrl.H5MAGE_CHARGE_URL = ServerUrl.H5GAME_SERVER_URL + "game/docharge?";
        ServerUrl.H5MAGE_GETOPENID_URL = ServerUrl.H5GAME_SERVER_URL + "game/queryopenid";
        ServerUrl.H5MAGE_GrantTicket_URL = ServerUrl.H5GAME_SERVER_URL + "game/sendCoin?";
        ServerUrl.ENDPAGE_GET_ICONS_URL = ServerUrl.STATICIS_SERVER_URL + "v6/endPage/getUserIcons?";
        ServerUrl.QQREADER_CLOUD_NOTE_SYN_SAVE = DOMAINNAME_CLOUD + "note/save";
        ServerUrl.QQREADER_ONLINE_CHAPTER_GET = ServerUrl.SERVER_DATA_URL_DEPRECATED
                + "/downloadChaptersBatch?bid=%s&pageSize=%d&pageNo=%d&oldVersion=%d&oldCount=%d";
        ServerUrl.QUERY_BOOKINFO_URL = DOMAINNAME_ALLREADER
                + "querybookinfo?bid=%s&lutime=%d&lucc=%d&luv=%d";
        ServerUrl.QQREADER_QUERY_OPEN_VIP_MONTH = ServerUrl.PROTOCOL_SERVER_URL + QQREADER_QUERY_OPEN_VIP_MONTH_PATH;
        ServerUrl.QQREADER_QUERY_OPEN_VIP_MONTH_QQ = ServerUrl.PROTOCOL_SERVER_URL + "getQOpenVipConfig?";
        ServerUrl.QQREAER_OPEN_VIP_QQ = ServerUrl.PROTOCOL_SERVER_URL + "openQQVip?";
        ServerUrl.QQREAER_OPEN_VIP = ServerUrl.PROTOCOL_SERVER_URL + QQREADER_OPEN_VIP_PATH;
        ServerUrl.QQREAER_OPEN_VIP_EXP = ServerUrl.PROTOCOL_SERVER_URL + "giveVipcard";
        ServerUrl.QQREAER_OPEN_VIP_GIFT = ServerUrl.PROTOCOL_SERVER_URL + "prize?month=";
        ServerUrl.QQREADER_SHARE_DETAIL_URL = ServerUrl.SERVER_URL + "/bookDetailShare.html?tf=1&bid=";
        ServerUrl.QQREADER_SHARE_LISTEN_DETAIL_URL = ServerUrl.IYUEDU_COMMON_SERVER_URL + "mediaBookShare.html?tf=1&bid=";
        ServerUrl.QQREADER_SKIN_LIST_SERVER_URL = ServerUrl.PROTOCOL_SERVER_URL + "nativepage/theme/list?list_version=";
        ServerUrl.QQREADER_SKIN_SERVER_URL = ServerUrl.PROTOCOL_SERVER_URL + "nativepage/theme/get?id=";
        ServerUrl.QQREADER_SKIN_ENABLE_SERVER_URL = ServerUrl.PROTOCOL_SERVER_URL + "nativepage/theme/enable?ids=";
        ServerUrl.BOOKCLUB_BEST_REPLY = ServerUrl.PROTOCOL_SERVER_URL + ServerUrl.QURL_COMMENT_URL + "setreplytop?";
        ServerUrl.BOOKCLUB_ADD_REPLY = ServerUrl.PROTOCOL_SERVER_URL + ServerUrl. QURL_COMMENT_URL + "addreply?";
        ServerUrl.BOOKCLUB_DEL_REPLY = ServerUrl.PROTOCOL_SERVER_URL + ServerUrl.QURL_COMMENT_URL + "delreply?";
        ServerUrl.BOOKCLUB_POST_TOPIC_URL = ServerUrl.PROTOCOL_SERVER_URL + ServerUrl.QURL_COMMENT_URL + "addcomment?bid=";
        ServerUrl.BOOKCLUB_DEL_COMMENT_URL = ServerUrl.PROTOCOL_SERVER_URL + ServerUrl.QURL_COMMENT_URL + "delcomment?";
        ServerUrl.REPORT_USER_TAG = ServerUrl.PROTOCOL_SERVER_URL + "reportusertag?";
        ServerUrl.QUERY_USER_TAG = ServerUrl.PROTOCOL_SERVER_URL + "queryselectedtag";
        ServerUrl.QUERY_READ_GENE = ServerUrl.PROTOCOL_SERVER_URL + "readgene";
        ServerUrl.QUERY_DAY8_GIFT = ServerUrl.PROTOCOL_SERVER_URL + "day8gift";
        ServerUrl.DISCOVERY_COMMENT_ZONE = ServerUrl.PROTOCOL_SERVER_URL + ServerUrl.QURL_COMMENT_URL + "zonelist?";
        ServerUrl.FEED_MESSAGE_URL = ServerUrl.PROTOCOL_SERVER_URL + "nativepage/infostream/list?";
        ServerUrl.FEED_MESSAGE_MORE_URL = ServerUrl.PROTOCOL_SERVER_URL + "nativepage/infostream/customizedRecList?id=";
        ServerUrl.FEED_HEAD_ENTRANCE = ServerUrl.PROTOCOL_SERVER_URL + "recommendNav";
        ServerUrl.SEARCH_KEYWORDS = ServerUrl.SEARCH_URL + ServerUrl.SEARCH_KEY;
        ServerUrl.TOPIC_COMMENT_ADDREPLY = ServerUrl.PROTOCOL_SERVER_URL  + "topic/addreply?";
        ServerUrl.AUTHOR_PAGE = ServerUrl.COMMON_PROTOCOL_URL + "getManitoHome?platform=1";
        ServerUrl.USERCENTER_PAGE = ServerUrl.COMMON_PROTOCOL_URL + "common/nativepage/user/profile?platform=android";
        ServerUrl.USERCENTER_MORE_BOOKS_PAGE = ServerUrl.COMMON_PROTOCOL_URL + "common/nativepage/user/shelf?";
        ServerUrl.USERCENTER_MORE_INTERACTIONS_PAGE = ServerUrl.COMMON_PROTOCOL_URL + "common/nativepage/user/interactive?";
        ServerUrl.USERCENTER_SIGN = ServerUrl.COMMON_PROTOCOL_URL + "common/nativepage/user/signature?";
        ServerUrl.REWARD_COMMENT = ServerUrl.PROTOCOL_SERVER_URL + ServerUrl.QURL_COMMENT_URL + "rewardcomment?";
        ServerUrl.BOOKLIST_SAME_CATEGORY_HOT_BOOKS = ServerUrl.PROTOCOL_SERVER_URL + "sameCategoryHotBooks?bid=";
        ServerUrl.BOOKLIST_SAME_CATEGORY_BOOKS = ServerUrl.PROTOCOL_SERVER_URL + "listDispatch?action=sameauthorcategorybooks&actionId=";
        ServerUrl.BRIGHT_POINT = ServerUrl.PROTOCOL_SERVER_URL + "book/brightPoint?bid=";
        ServerUrl.WELL_CHOSEN_BOOK_STORE = ServerUrl.PROTOCOL_SERVER_URL + "nativepage/infostream/boutiques?";
        ServerUrl.LUCKYMONEY_LOG_1 = ServerUrl.ADR_COMMON_SERVICE_URL + "/redbag/send";
        ServerUrl.LUCKYMONEY_LOG_2 = ServerUrl.COMMON_SERVICE_URL + "afterlogin";
        ServerUrl.UPLOAD_BOOKSCORE = ServerUrl.PROTOCOL_SERVER_URL + "nativepage/score/update";
        ServerUrl.GET_BOOKSCORE = ServerUrl.PROTOCOL_SERVER_URL + "nativepage/score/get";
        ServerUrl.QUERY_READTIME_NEW = DOMAINNAME_COMMON + "common/readtime/init";
        ServerUrl.REPORT_BOOK_CONTENT_EORRO_RUL = ServerUrl.STATICIS_SERVER_URL + "common/report/content";
        ServerUrl.READPAGE_CHAPTER_OVER_URL = PROTOCOL_SERVER_URL_READER + "chapterOver?";
        ServerUrl.READPAGE_CHAPTER_OVER_ADV_URL = PROTOCOL_SERVER_URL_READER + "chapterOverAd?";
        ServerUrl.CONFIG = ServerUrl.PROTOCOL_SERVER_URL + "config";
        ServerUrl.NEW_USER_FUN_GUIDE = ServerUrl.SERVER_URL + "/beginnerGuide.html";
        ServerUrl.READ_DAY_CHECK = ServerUrl.READ_DAY_URL + "task/readingDay";
        ServerUrl.READ_DAY_REPORT = ServerUrl.READ_DAY_URL + "task/readingDay/increase";
        ServerUrl.NATIVE_BOOKSTORE_COMMON_SEARCH_ASSOCIATE_WORD = DOMAINNAME_ZXAUTIOCMP + "autocmp?";
        ServerUrl.NATIVE_BOOKSTORE_LISTEN_SEARCH_ASSOCIATE_WORD = DOMAINNAME_ZXAUTIOCMP + "audio?";
        ServerUrl.NATIVE_BOOKSTORE_SEARCH_URL = DOMAINNAME_ZXSEARCH;
        ServerUrl.NATIVE_BOOKSTORE_COMMON_SEARCH_RESULT = ServerUrl.DOMAINNAME_ADR + "search/result?";
        ServerUrl.NATIVE_BOOKSTORE_LISTEN_SEARCH_RESULT = ServerUrl. NATIVE_BOOKSTORE_SEARCH_URL + "audiosearch?";
        ServerUrl.AUTHOR_PAGE_SHARE_URL = ServerUrl.SERVER_URL + "/manitoShare.html?";
        ServerUrl.NATIVE_BOOKSTORE_AUTHOR_FREE_MONTH_SEARCH_RESULT = ServerUrl.NATIVE_BOOKSTORE_AUTHOR_FREE_MONTH_SEARCH_URL + "authorfree?";
        ServerUrl.NATIVE_BOOKSTORE_FREE_SEARCH_ASSOCIATE_WORD = ServerUrl.NATIVE_BOOKSTORE_SEARCH_ASSOCIATE_PRE + "free?freetype=free&";
        ServerUrl.NATIVE_BOOKSTORE_MONTH_SEARCH_ASSOCIATE_WORD = ServerUrl.NATIVE_BOOKSTORE_SEARCH_ASSOCIATE_PRE + "free?freetype=monthfree&";
        ServerUrl.GET_BUILDIN_BOOK = ServerUrl.PROTOCOL_SERVER_URL + "shelf/embedBooksV2";
        ServerUrl.EXCHANGE_READTIME = DOMAINNAME_COMMON + "common/readtime/redeem";
        ServerUrl.READ_MONTH_STONE = DOMAINNAME_COMMON + "common/readtime/monthstone";
        ServerUrl.USERTASK = ServerUrl.COMMON_PROTOCOL_URL + "task/daily";
        ServerUrl.FEED_FIRST_PAGE_URL_ONLY_READ_OPPO = ServerUrl.COMMON_PROTOCOL_URL + "nativepage/infostream/mustread";
        ServerUrl.FEED_FIRST_PAGE_URL_ONLY_READ = DOMAINNAME_COMMON + "recommand/mustread";
        ServerUrl.FEED_FIRST_PAGE_URL = DOMAINNAME_COMMON + "recommand/firstPage";
        ServerUrl.FEED_FIRST_PAGE_URL_OPPO = ServerUrl.COMMON_PROTOCOL_URL + "nativepage/infostream/firstpage";
        ServerUrl.FEED_SECOND_PAGE_URL = DOMAINNAME_COMMON + "recommand/artificialColumn?sex=%d";
        ServerUrl.GET_PREMIUM_CONTENT_DETAIL_URL = DOMAINNAME_IYUEDU + "book_res/reader/coopactivity/1_0_0/article.html?id=%s";
        ServerUrl.LIMIT_TIME_TWO_LEVEL_URL = ServerUrl.COMMON_PROTOCOL_URL + "seckill";
        ServerUrl.END_BOOK_STORE_URL = ServerUrl.PROTOCOL_SERVER_URL + "queryOperation?finish=1&categoryFlag=";
        ServerUrl.NEW_BOOK_STORE_URL = ServerUrl.PROTOCOL_SERVER_URL + "queryOperation?categoryFlag=";
        ServerUrl.MONTH_BOOK_STORE_URL = ServerUrl.PROTOCOL_SERVER_URL + "queryOperation?month=1&categoryFlag=";
        ServerUrl.INTEREST_READER_LIST_URL = ServerUrl.PROTOCOL_SERVER_URL_FOR_CLASSIFY + "readflow/articles?";
        ServerUrl.SIGN_UP_NEW = ServerUrl.DOMAINNAME_COMMON + "sign/award";
        ServerUrl.GET_SIGN_INFO_NEW = ServerUrl.DOMAINNAME_COMMON + "sign/info";
        ServerUrl.GET_TREE_PROGRESS = ServerUrl.COMMON_PROTOCOL_URL+"task/bookTree/getTreeProgress";
        ServerUrl.ONLINE_PAGE_SIGN = ServerUrl.SERVER_URL + "/sign.html";
        ServerUrl.ONLINE_PAGE_REDETIME = ServerUrl.SERVER_URL + "/readTime.html";
        ServerUrl.URL_LOCAL_BOOK_MATCH =  ServerUrl.DOMAINNAME_COMMON + "v" + URL_VERSION + "/" + "localBook/match";
        ServerUrl.URL_LOCAL_BOOK_END_PAGE =  ServerUrl.DOMAINNAME_COMMON + "v" + URL_VERSION + "/" + "localBook/endPage";
        ServerUrl.URL_DISCOVERY_REDDOT = DOMAINNAME_COMMON + "readflow/red-point";
        ServerUrl.URL_DISCOVERY_FEED = DOMAINNAME_COMMON + "discover/index";
        ServerUrl.URL_CHECK_USER_PAY_TYPE = DOMAINNAME_COMMON + "freeAd/checkUserPay?adPosition=%s";
        ServerUrl.URL_OBTAIN_ADV_REWARD = DOMAINNAME_COMMON + "freeAd/getTickets?appid=%s";
        ServerUrl.URL_SAVE_APP_ADV_RECORD = DOMAINNAME_COMMON + "freeAd/saveRecord?appid=%s&downLoadTime=%d&downLoadStatus=%d";
        ServerUrl.URL_OBTAIN_APP_ADV_RECORD_NUM = DOMAINNAME_COMMON + "freeAd/getTicketNum";
        URL_OBTAIN_CATEGORY_CONFIG = DOMAINNAME_COMMON + "category/config";
        URL_OBTAIN_CATEGORY_ALL = DOMAINNAME_COMMON + "category/all-category?id=%d";
        URL_OBTAIN_CATEGORY_HOT_TAG = DOMAINNAME_COMMON + "category/hot-tag?id=%d";
        URL_OBTAIN_CATEGORY_RANK = DOMAINNAME_COMMON + "v6_0/rank/book?groupId=%d&columnId=%s&pageNo=%d&pageSize=%d";
        URL_OBTAIN_RANK_LEFT_TAB_INFO = DOMAINNAME_COMMON + "v6_0/rank/?rankPrefer=%d";
        URL_OBTAIN_RANK_RIGHT_INFO = DOMAINNAME_COMMON + "cofree/rank/rankList";
    }
}

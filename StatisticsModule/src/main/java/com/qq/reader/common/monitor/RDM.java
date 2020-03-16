package com.qq.reader.common.monitor;

import android.content.Context;

import com.qq.reader.common.utils.CommonConfig;
import com.qq.reader.core.utils.NetUtils;
import com.tencent.beacon.event.UserAction;
import com.tencent.mars.xlog.Log;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public final class RDM {

    public static final String EVENT_APP_START = "event_startup";//看到闪屏的次数
    public static final String EVENT_APP_START1 = "event_startup1";//进程启动的次数
    public static final String EVENT_APP_START2 = "event_startup2";//看到应用任何界面的次数

    public static final String EVENT_ONLINE_CONNECT_ALL = "online_conn_all";//经过重试以后在线阅读最终成功率

    public static final String EVENT_ONLINE_CONNECT = "online_conn";

    public static final String EVENT_ONLINE_CONNECT_FIRST_PACKAGE = "online_conn_first";

    public static final String EVENT_ONLINE_CONNECT_BACKGROUND = "online_conn_background_retry";

    public static final String EVENT_ONLINE_CONNECT_FOREGROUND = "online_conn_foreground";

    public static final String EVENT_ONLINE_BATDOWNLOAD = "online_batdownload";

    public static final String EVENT_GET_WRITABLE_DATABASE_ERROR = "get_writable_error";

    public static final String EVENT_OUT_MEMORY_EXCEPTION = "out_of_memory";

    public static final String EVENT_ONLINE_CHAPTERID_CHECK = "event_online_chapterid_check";

    public static final String EVENT_OFFLINE_PAGEDATA_LOAD_FROM_NET = "event_offline_pagedata_load_from_net";

    public static final String EVENT_LOCALSTORE_LOCALPAGE_LOAD_FROM_NET = "event_localstore_localpage_load_from_net";

    public static final String EVENT_LOCALSTORE_SERVERPAGE_LOAD_FROM_NET = "event_localstore_serverpage_load_from_net";

    public static final String EVENT_OFFLINE_PAGEDATA_LOAD_FROM_LOCAL = "event_offline_pagedata_load_from_local";

    public static final String EVENT_OFFLINE_PAGE_FIRSTSECTION_SHOW = "event_offline_page_firstsection_show";

    public static final String EVENT_STAT_EVENT_UPLOAD = "event_stat_event_upload";

    public static final String EVENT_IMAGELOADER_CONFIG_INIT_ERROR = "event_imageloader_config_init_error";

    public static final String EVENT_REPORT_STATUS = "event_report_status";

    public static final String EVENT_REPORT_READER_ALL_COUNT = "event_report_reader_all_count";
    public static final String EVENT_REPORT_READER_ONLINE_COUNT = "event_report_reader_online_count";

    public static final String EVENT_DETAILPAGE = "event_DetailPage";

    // VIVO drawer
    public static final String EVENT_A145 = "event_A145";

    public static final String IMPORT_NATIVE_BOOK = "IMPORT_NATIVE_BOOK";//导入书籍名称和作者上报

    /********************************/

    public static final String EVENT_Dir = "event_Dir";

    public static final String EVENT_READER = "event_reader"; // 使用用户数

    public static final String EVENT_READER_BOOKSTORE = "event_reader_bookstore";// 书城用户数

    public static final String EVENT_READBOOKONLINE = "event_readBookonline";// 在线阅读用户数

    public static final String EVENT_READBOOKONLINE_BOOKSTORE = "event_Bookonline";// 在线阅读用户数(书城进来的)

    public static final String EVENT_READBOOK = "event_readbook";// 阅读用户数

    public static final String EVENT_SIGNATURE = "event_signature";//apk签名

    public static final String EVENT_PFOFILE_OPEN_VIP = "profile_open_vip";  // 我的界面点击续费
    public static final String EVENT_PROFILE_PAY_SUCCESS = "profile_pay_success";  // 我的界面付费成功
    public static final String EVENT_PROFILE_PAY_CANCEL = "profile_pay_cancel";  // 我的界面付费取消
    public static final String EVENT_PROFILE_OPEN_VIP_USER_EXPIRED = "profile_open_vip_user_expired";  // 我的界面用户过期
    public static final String EVENT_PROFILE_OPEN_VIP_SUCCESS = "profile_open_vip_success";  // 我的界面开通包月成功
    public static final String EVENT_PROFILE_OPEN_VIP_FAIL = "profile_open_vip_fail";  // 我的界面开通包月失败

    public static final String EVENT_PROFILE_CHARGE = "profile_charge";  // 我的界面点击充值
    public static final String EVENT_PROFILE_CHARGE_SUCCESS = "profile_charge_success"; // 我的界面充值成功
    public static final String EVENT_PROFILE_CHARGE_CANCEL = "profile_charge_cancel";  // 我的界面充值取消
    public static final String EVENT_PROFILE_CHARGE_FAIL = "profile_charge_fail";  // 我的界面充值失败
    public static final String EVENT_PROFILE_CHARGE_USER_EXPIRED = "profile_charge_user_expired";  // 我的界面充值用户过期
    public static final String EVENT_DAY_FIRST_OPEN_PAGE = "event_first_open_page";
    public static final int RECORD_NUMBER_UPLOAD = 10000;
    public static final long RECORD_SIZE_UPLOAD_WIFI = 200 * 1024;
    public static final long RECORD_SIZE_UPLOAD_NO_WIFI = 30 * 1024;

    public static final String PARAM_FAILCODE = "param_FailCode";

    public static final int FAILCODE_HTTP_404 = 404;
    public static final int FAILCODE_HTTP_400 = 1000;
    public static final int FAILCODE_SOCKET_TIME_OUT = 1001;
    public static final int FAILCODE_SDCARD_FULL = 1002;
    public static final int FAILCODE_WAP_CHARGE = 1003;
    public static final int FAILCODE_REDIRECT = 1004;
    public static final int FAILCODE_WAP_CONNECT_REFUSED = 1005;
    public static final int FAILCODE_NO_ROUTE = 1006;
    public static final int FAILCODE_ILLEGALARGUMENT = 1007;
    public static final int FAILCODE_UNZIP_FORMAT_ERROR = 1008;
    public static final int FAILCODE_UNZIP_NO_FILE = 1009;
    public static final int FAILCODE_UNKNOWHOST_ERROR = 1010;
    public static final int FAILCODE_COMMON_IO = 1011;
    public static final int FAILCODE_COMMON_SOCKETERROR = 1012;
    public static final int FAILCODE_COMMON_SECURITYEXCEPTION = 1013;
    public static final int FAILCODE_SDCARD_NOT_AVAIABLE = 1014;
    public static final int FAILCODE_CONNECT_TIME_OUT = 1015;
    public static final int FAILCODE_HTTP_HOST_CONNECT_EX = 1016;

    public static final int FAILCODE_OTHER = 10000;

    public static final String EVENT_A239 = "event_A239";
    public static final String EVENT_A241 = "event_A241";


    // 信息流分割条
    public static final String EVENT_FEED_DIVIDER = "feed_divider";

    public static final String EVENT_CHAPTER_CONTENT_UPDATE = "event_chapter_content_update";
    public static final String EVENT_QUERY_BOOKINFO = "event_query_bookinfo";
    public static final String EVENT_CHAPTER_OVER = "event_chapter_over";
    public static final String EVENT_CHAPTER_OVER_SYNC = "event_chapter_over_sync";
    public static final String EVENT_CHAPTER_SYNC_CLEAN_CACHE = "event_chapter_sync_clean_cache";
    public static final String EVENT_CHAPTER_DIR_DOWN = "event_chapter_dir_down";
    public static final String EVENT_CHAPTER_USER_FEED_BACK = "event_chapter_user_feed_back";

    public static final String EVENT_EPUB_FILE_ERROR = "event_epub_file_error";

    //登录模块事件统计
    public static final String EVENT_TO_LOGIN_TIMES = "login_tologin";//调用登录接口次数
    public static final String EVENT_LOGIN_GETUSERINFO = "login_getuserinfo";//调用获取用户信息接口
    public static final String EVENT_LOGIN_REFRESHUSERINFO = "login_refreshuserinfo";//调用用户续期接口
    public static final String EVENT_LOGIN_SDK_PARAMETER = "login_sdk_parameter";//sdk获取信息不全
    public static final String EVENT_LOGIN_SDK_CONNERT = "login_sdk_connect";//sdk获取信息不全(三星特有)

    public static void stat(String eventName, Map<String, String> extraMap) {
        onUserAction(eventName, true, 0, 0, extraMap, false, false);
    }

    /**
     * 这个接口对于失败率的统计不考虑网络情况，没有网络也会记录失败日志,如果需要区分网络情况，
     * 请用{@link #onUserAction(String, boolean, long, long, Map}
     *
     * @param eventName 事件名称
     * @param isOk      事件是否成功
     * @param size      事件网络消耗
     * @param elapse    事件耗时
     * @param extraMap  失败信息扩展字段
     */

    public static void onUserAction(String eventName, boolean isOk,
                                    long elapse, long size, Map<String, String> extraMap) {

        onUserAction(eventName, isOk, elapse, size, extraMap, false, false);
    }

    /**
     * @param eventName    事件名称
     * @param isOk         事件是否成功
     * @param size         事件网络消耗
     * @param elapse       事件耗时
     * @param extraMap     失败信息扩展字段
     * @param needCheckNet 是否需要检测当前有网络
     *                     ，无网络的话不记录此次事件
     * @param isRealTime   是否需要实时上报
     */
    public static void onUserAction(String eventName, boolean isOk,
                                    long elapse, long size, Map<String, String> extraMap,
                                    boolean needCheckNet, boolean isRealTime) {

        try {
            if (extraMap == null) {
                extraMap = new HashMap<>();
            }
            extraMap.put("login_type", String.valueOf(CommonConfig.getLoginType()));
            Log.d("statRDM", "onUserAction " + eventName
                    + (extraMap == null ? "" : extraMap.toString()));
            boolean isNetWorkConnected = NetUtils.isNetworkConnected();
            if (!needCheckNet || isNetWorkConnected) {
                UserAction.onUserAction(eventName, isOk, elapse, size,
                        extraMap, isRealTime);
            }
        } catch (Throwable e) {
            Log.e("RDM", "onUserAction error : " + e);
        }
    }

    /**
     * 每天统计一次的需求可用这个方法判断本地要不要统计上报
     */
    public static boolean shoudStatisticsByDay(Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String value = sdf.format(System.currentTimeMillis());

        if (value.equals(StatisticsConfig.getTime(context))) {
            return false;
        } else {
            StatisticsConfig.setTiem(context, value);
            return true;
        }
    }


}

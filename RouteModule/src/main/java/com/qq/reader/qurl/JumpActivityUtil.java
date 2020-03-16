package com.qq.reader.qurl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.qq.reader.common.mark.Mark;
import com.qq.reader.common.monitor.Node;
import com.qq.reader.common.monitor.statparam.OriginStatParam;
import com.qq.reader.common.monitor.v1.ClickEvent;
import com.qq.reader.common.monitor.v1.PageNames;
import com.qq.reader.common.utils.CommonConfig;
import com.qq.reader.common.utils.FlavorUtils;
import com.qq.reader.common.utils.ServerUrl;
import com.qq.reader.core.BaseApplication;
import com.qq.reader.define.ActivityCodeConstant;
import com.qq.reader.define.JumpParameterConstants;
import com.qq.reader.define.SecondLevelConstant;
import com.qq.reader.dispatch.IEventListener;
import com.qq.reader.dispatch.NativeAction;
import com.qq.reader.dispatch.R;
import com.qq.reader.dispatch.RouteConstant;
import com.qq.reader.dispatch.RoutePath;
import com.qq.reader.service.app.AppRouterService;
import com.qq.reader.service.pay.PayService;
import com.qq.reader.service.push.IPushStatManagerService;
import com.qq.reader.utils.SearchModuleUtils;
import com.qq.reader.utils.Utility;
import com.tencent.mars.xlog.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JumpActivityUtil {

//    public static boolean GO_MAIN_AFTER_BACK = false;

//    /**
//     * 华为隐私政策协议页
//     *
//     * @param context    Context used to jump to specific activity
//     */
//    public static final String HUAWEI_PRIVACY_URL_PRE =
//            "file:///android_asset/bookstore/huawei_privacy_policy_";

//    public static void toTwoLevelActivity(boolean FLAG_ACTIVITY_CLEAR_TOP, Activity act, Bundle b) {
//        Intent intent = new Intent();
//        intent.putExtras(b);
//        intent.setClass(act, NativeBookStoreTwoLevelActivity.class);
//        if (FLAG_ACTIVITY_CLEAR_TOP)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        AnimationComm.overridePendingTransition(R.anim.slide_in_right,
//                R.anim.slide_out_left);
//        act.startActivityForResult(intent, ActivityCodeCostant.FORRESULT_DEFAULT_CODE);
//    }
//    public static final String READER_AGREEMENT_URL_PRE =
//            "file:///android_asset/bookstore/UserAgreement_";

    public static String getPageWithPreference(Context context, int pageType) {
        int mGendar = CommonConfig.getWebUserLike();
        if (mGendar == 0) {
            mGendar = 3;
        }
        String page = null;
        switch (mGendar) {
            case 1:
                // 男
                switch (pageType) {
                    case 0:
                        // 分类
                        page = RouteConstant.PAGE_NAME_CATEGORY_BOY;
                        break;
                    case 1:
                        // 排行
                        page = RouteConstant.PAGE_NAME_RANK_BOY;
                        break;
                    case 2:
                        // 包月
                        page = RouteConstant.PAGE_NAME_PAYMONTH_BOY;
                        break;
                    case 3:
                        // 免费
                        page = RouteConstant.PAGE_NAME_FREE_BOY;
                        break;
                    case 4:
                        // 完结
                        page = RouteConstant.PAGE_NAME_END_BOY;
                        break;
                    case 5:
                        //小编精选
                        page = RouteConstant.PAGE_NAME_EDITORCHOICE_BOY;
                        break;
                    case 6:
                        //经典
                        page = RouteConstant.PAGE_NAME_CLASSIC_BOY;
                        break;
                }
                break;
            case 2:
                // 女
                switch (pageType) {
                    case 0:
                        // 分类
                        page = RouteConstant.PAGE_NAME_CATEGORY_GIRL;
                        break;
                    case 1:
                        // 排行
                        page = RouteConstant.PAGE_NAME_RANK_GIRL;
                        break;
                    case 2:
                        // 包月
                        page = RouteConstant.PAGE_NAME_PAYMONTH_GIRL;
                        break;
                    case 3:
                        // 免费
                        page = RouteConstant.PAGE_NAME_FREE_GIRL;
                        break;
                    case 4:
                        // 完结
                        page = RouteConstant.PAGE_NAME_END_GIRL;
                        break;
                    case 5:
                        //小编精选
                        page = RouteConstant.PAGE_NAME_EDITORCHOICE_GIRL;
                        break;
                    case 6:
                        //经典
                        page = RouteConstant.PAGE_NAME_CLASSIC_GIRL;
                        break;
                }
                break;
            case 3:
                // 出版
                switch (pageType) {
                    case 0:
                        // 分类
                        page = RouteConstant.PAGE_NAME_CATEGORY_PUBLISH;
                        break;
                    case 1:
                        // 排行
                        page = RouteConstant.PAGE_NAME_RANK_PUBLISH;
                        break;
                    case 2:
                        // 包月
                        page = RouteConstant.PAGE_NAME_PAYMONTH_PUBLISH;
                        break;
                    case 3:
                        // 免费
                        page = RouteConstant.PAGE_NAME_FREE_PUBLISH;
                        break;
                    case 4:
                        // 完结
                        page = RouteConstant.PAGE_NAME_END_PUBLISH;
                        break;
                    case 5:
                        //小编精选
                        page = RouteConstant.PAGE_NAME_EDITORCHOICE_PUBLISH;
                        break;
                    case 6:
                        //经典
                        page = RouteConstant.PAGE_NAME_CLASSIC_PUBLISH;
                        break;
                }
                break;
        }
        return page;
    }

    /**
     * 跳转到登录页面
     */
    public static void goLogin(Activity context, final JumpActivityParameter jp) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.LOGIN_CHOOSE);
        postcard.withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        if (context != null) {
            doRouterJump(postcard, context, jp);
        }
    }

    /**
     * 跳转到登录页面
     * 支持传参
     */
    public static void goLogin(Activity context, Bundle bundle, final JumpActivityParameter jp) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.LOGIN_CHOOSE);
        postcard.with(bundle);
        postcard.withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        if (context != null) {
            doRouterJump(postcard, context, jp);
        }
    }

    /**
     * 跳转到书库（分类首页）
     */
    public static void goBook_Stacks(Activity activity, final JumpActivityParameter jp) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.MAIN_ACTIVITY)
                .withBoolean("fromjump", true)
                .withInt(RouteConstant.MAIN_TAB_TAG, 2)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 跳转到发现
     */
    public static void goFind(Activity activity, final JumpActivityParameter jp) {
        goFind(activity,jp,null);
    }

    /**
     * 跳转到发现
     */
    public static void goFind(Activity activity, final JumpActivityParameter jp, Bundle bundle) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.MAIN_ACTIVITY)
                .with(bundle)
                .withBoolean("fromjump", true)
                .withInt(RouteConstant.MAIN_TAB_TAG, 3)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 跳转到我的页面
     */
    public static void goMine(Activity activity, final JumpActivityParameter jp) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.MAIN_ACTIVITY)
                .withBoolean("fromjump", true)
                .withInt(RouteConstant.MAIN_TAB_TAG, 4)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }


    /**
     * 跳转到书评广场
     */
    public static void goCommentSquare(Activity context, String title, final JumpActivityParameter jp) {
        Bundle b = new Bundle();
        if (TextUtils.isEmpty(title))
            title = Utility.getStringById(R.string.book_review_square);
        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, title);
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_COMMENT_INDEX)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, context, jp);
    }

    public static void goVIPOpen(Activity activity) {
        if (activity != null) {
            PayService.openVip(activity);
        }
    }

    public static void goCoinrecharge(Activity activity) {
        if (activity != null) {
            PayService.charge(activity, null, 0);
        }
    }

    /**
     * 跳转到官方分类（书荒求助、原创交流、大神沙龙等的）书评区
     */
    public static void goOfficialClassifyComment(Activity context, long bid, String title, final JumpActivityParameter jp) {
        Bundle b = new Bundle();
        b.putString(NativeAction.KEY_JUMP_PAGENAME,
                RouteConstant.PAGE_NAME_DISCOVERY_COMMENT_DETAIL);
        b.putLong(NativeAction.URL_BUILD_PERE_BOOK_ID, bid);
        b.putString(NativeAction.PARA_TYPE_BOOK_NAME, title);
        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, title);
        b.putInt(RouteConstant.CTYPE, RouteConstant.CTYPE_COMMENT);
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, context, jp);
    }

    /**
     * 跳转到书评区/跳转到单本书书评区
     *
     * @param bookId
     * @param bookName 可以为null
     * @param cType    //ctype：0书籍相关的回复Constant.CTYPE_BOOK  4公共书评区相关评论Constant.CTYPE_COMMENT   5专题相关的回复Constant.CTYPE_TOPIC
     */
    public static void goBookComment(Activity activity, Long bookId, String bookName, int cType, final JumpActivityParameter jp) {
        if (activity == null) {
            return;
        }
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
                .withLong(NativeAction.URL_BUILD_PERE_BOOK_ID, bookId)
                .withString(NativeAction.PARA_TYPE_BOOK_NAME, bookName)
                .withString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_BOOKCLUB_MAIN)
                .withString(RouteConstant.LOCAL_STORE_IN_TITLE, BaseApplication
                        .getInstance().getResources().getString(R.string.bookclubindex))
                .withInt(RouteConstant.CTYPE, cType)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 打开发表评论页，并进入书友圈
     *
     * @param bookId
     */
    public static void goWriteComment_CommentList(Activity act, Long bookId, final JumpActivityParameter jp) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(RouteConstant.SHOWCOMMENTACTIVITY, true);
        bundle.putLong(NativeAction.URL_BUILD_PERE_BOOK_ID, bookId);
        bundle.putString(NativeAction.KEY_JUMP_PAGENAME,
                RouteConstant.PAGE_NAME_BOOKCLUB_MAIN);
        bundle.putString(RouteConstant.LOCAL_STORE_IN_TITLE, BaseApplication
                .getInstance().getResources().getString(R.string.bookclubindex));
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
                .with(bundle)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, act, jp);
    }

    public static void goSkinManage(Activity activity, final JumpActivityParameter jp) {
        if (activity == null) {
            return;
        }
        Postcard postcard = ARouter.getInstance().build(RoutePath.PROFILE_NATIVE_SKIN_MANAGER)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    public static void goSkinDetail(Activity activity, String pluginID, final JumpActivityParameter jp) {
        if (activity == null || pluginID == null || pluginID.length() == 0) {
            return;
        }
        Postcard postcard = ARouter.getInstance().build(RoutePath.PROFILE_NATIVE_SKIN_DETAIL)
                .withString(RouteConstant.WEBCONTENT, ServerUrl.SKIN_DETAIL_PRE_URL + "id=" + pluginID)
                .withString(RouteConstant.PLUGIN_ID, pluginID)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 跳转到精华书评
     *
     * @param topicId
     */
    public static void goSelectedComment(Activity activity, String topicId, String ctype, final JumpActivityParameter jp) {
        if (activity == null) {
            return;
        }
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_SELECTED_COMMENT)
                .withString(NativeAction.KEY_JUMP_PAGENAME,
                        RouteConstant.PAGE_NAME_SELECTED_COMMENT)
                .withString(RouteConstant.TOPIC_ID, topicId)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        try {
            //ctype 一般就是0和4
            postcard.withInt(RouteConstant.CTYPE, Integer.parseInt(ctype));
        } catch (Exception e) {
            Log.printErrStackTrace("JumpActivityUtil", e, null, null);
            Log.e("JumpActivity", e.getMessage());
        }
        String title = BaseApplication.getInstance().getResources()
                .getString(R.string.selected_comments);
        postcard.withString(RouteConstant.LOCAL_STORE_IN_TITLE, title);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 跳转到书评详情
     *
     * @param bookId
     * @param commentid
     * @param cType      //ctype：0书籍相关的回复Constant.CTYPE_BOOK  4公共书评区相关评论Constant.CTYPE_COMMENT   5专题相关的回复Constant.CTYPE_TOPIC
     * @param floorIndex 指定跳转到哪个楼层
     * @param floorNext  指定获取相对floorIndex楼层的楼层数（正数表示下面的楼层，负数表示上面的楼层）
     * @param isLocate   跳转后listview是否定位到回复楼层
     */
    public static void goBookCommentDetail(
            Activity activity,
            Long bookId,
            String commentid,
            int cType,
            String authorid,
            int floorIndex,
            int floorNext,
            boolean isLocate,
            int from,
            boolean isActiveReplyLayout,
            boolean isShowKeyboard,
            final JumpActivityParameter jp) {
        if (activity == null) {
            return;
        }
        Map<String, String> map = (HashMap<String, String>) jp.getJsonParamater();
        Bundle bundle = new Bundle();
        bundle.putLong(NativeAction.URL_BUILD_PERE_BOOK_ID, bookId);
        bundle.putString(RouteConstant.COMMENT_ID, commentid);
        bundle.putString(NativeAction.PARA_TYPE_COMMENT_UID, authorid);
        bundle.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_BOOKCLUB_REPLY);
        if (map != null) {
            bundle.putString(Node.KEY_S_ALG, map.get(Node.KEY_S_ALG));
            bundle.putString(Node.KEY_S_ITEMID, map.get(Node.KEY_S_ITEMID));
        }

        bundle.putInt(RouteConstant.FLOOR_INDEX, floorIndex);
        bundle.putInt(RouteConstant.FLOOR_NEXT, floorNext);
        bundle.putInt(RouteConstant.FROM_MESSAGE, from);
        bundle.putBoolean(RouteConstant.FLOOR_ISLOCATE, isLocate);
        bundle.putString(RouteConstant.LOCAL_STORE_IN_TITLE,
                BaseApplication.getInstance().getResources()
                        .getString(R.string.bookclubdetail));
        bundle.putInt(RouteConstant.CTYPE, cType);
        bundle.putBoolean(RouteConstant.ACTIVE_REPLY_LAYOUT, isActiveReplyLayout);
        bundle.putBoolean(RouteConstant.SHOW_KEYBOARD, isShowKeyboard);
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
                .with(bundle)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 跳转到影视专区书库
     **/
//    public static void goMovieBookStore(Activity activity, String actionTag
//            ,String fromTitle, final JumpActivityParameter jp) {
//        Bundle b = new Bundle();
//        //暂时写死，影视专区是h5页面无法从获取actionid。。。。
//        String actionId = RouteConstant.MOVIE_AREA_ACTION_ID;
//        b.putString(RouteConstant.CLASSIFY_FORM, RouteConstant.CLASSIFY_FORM_MOVIE_AREA);
//        if (TextUtils.isEmpty(actionTag)) {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_TAG,
//                    NativeAction.PARA_TYPE_CATEGORY_TAG_ALL_NEW);
//        } else {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_TAG, actionTag);
//        }
//        if (TextUtils.isEmpty(actionId)) {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, "0");
//        } else {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, actionId);
//        }
//        b.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_CLASSIFY);
//        b.putString(RouteConstant.FROM_TITLE, fromTitle);
//        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
//                .with(b)
//                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        doRouterJump(postcard, activity, jp);
//    }


    /**
     * 跳转到专区书库
     *
     * @param activity
     * @param actionTag
     * @param actionId
     * @param classFrom
     * @param jp
     */
//    public static void goClassify_Detail_From_Book_Store_Zone(Activity activity,
//                                                              String actionTag,
//                                                              String actionId,
//                                                              String classFrom,
//                                                              final JumpActivityParameter jp) {
//        Bundle b = new Bundle();
//        b.putString(RouteConstant.CLASSIFY_FORM, classFrom);
//        if (TextUtils.isEmpty(actionTag)) {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_TAG,
//                    NativeAction.PARA_TYPE_CATEGORY_TAG_ALL_NEW);
//        } else {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_TAG, actionTag);
//        }
//        if (TextUtils.isEmpty(actionId)) {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, "0");
//        } else {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, actionId);
//        }
//        b.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_CLASSIFY);
//        b.putString(RouteConstant.FROM_TITLE, BaseApplication.getInstance().getResources().getString(R.string.classify_back_title));
//        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
//                .with(b)
//                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        doRouterJump(postcard, activity, jp);
//    }

    /**
     * 从搜索页面跳转到分类页
     *
     * @param activity
     * @param actionTag
     * @param actionId
     * @param jp
     * @param searchFrom
     */
//    public static void goClassify_Detail_FROM_SEARCH(Activity activity, String actionTag, String actionId, final JumpActivityParameter jp, String searchFrom) {
//        Bundle b = new Bundle();
//        if (TextUtils.isEmpty(actionTag)) {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_TAG,
//                    NativeAction.PARA_TYPE_CATEGORY_TAG_ALL_NEW);
//        } else {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_TAG, actionTag);
//        }
//        if (TextUtils.isEmpty(actionId)) {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, "0");
//        } else {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, actionId);
//        }
//        b.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_CLASSIFY);
//        b.putString(NativeAction.PARA_TYPE_FROM_SEARCH, searchFrom);
//        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
//                .with(b)
//                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        doRouterJump(postcard, activity, jp);
//    }

    /**
     * 跳转到书库分类二级界面
     **/
//    public static void goClassify_Detail(Activity activity, String actionTag
//            , String actionId, String fromTitle, final JumpActivityParameter jp) {
//        Bundle b = new Bundle();
//        if (TextUtils.isEmpty(actionTag)) {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_TAG,
//                    NativeAction.PARA_TYPE_CATEGORY_TAG_ALL_NEW);
//        } else {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_TAG, actionTag);
//        }
//        if (TextUtils.isEmpty(actionId)) {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, "-1");
//        } else {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, actionId);
//        }
//        b.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_CLASSIFY);
//        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, fromTitle);
//        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
//                .with(b)
//                .withTransition(com.qq.reader.dispatch.R.anim.slide_in_right, com.qq.reader.dispatch.R.anim.slide_out_left);
//        analyzePushOrigin(jp, postcard);
//        doRouterJump(postcard, activity, jp);
//    }

    /**
     * 跳转到新的分类二级页
     *
     * @param activity
     * @param oldActionTag
     * @param actionId
     * @param fromTitle
     * @param jp
     */
    public static void goNewClassifyPage(Activity activity,
                                         String oldActionTag,
                                         String actionId,
                                         String fromTitle,
                                         final JumpActivityParameter jp) {
        Bundle b = new Bundle();
        List<String> paramList = SearchModuleUtils.getParamFromOldAction(oldActionTag);
        if(paramList!=null && paramList.size()>=6){
            b.putString(SearchModuleUtils.KEY_CATE_PAGE_TAG,paramList.get(0));
            b.putString(SearchModuleUtils.KEY_CATE_PAGE_STATUS,paramList.get(1));
            b.putString(SearchModuleUtils.KEY_CATE_PAGE_CHARGE_TYPE,paramList.get(2));
            b.putString(SearchModuleUtils.KEY_CATE_PAGE_UPDATETIME,paramList.get(3));
            b.putString(SearchModuleUtils.KEY_CATE_PAGE_WORDS,paramList.get(4));
            b.putString(SearchModuleUtils.KEY_CATE_PAGE_SEARCH_ORDER,paramList.get(5));
        }

        //分类id
        if (TextUtils.isEmpty(actionId)||"-1".equals(actionId)) {
            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, "");
        } else {
            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, actionId);
        }

        b.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_CLASSIFY);
        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, fromTitle);
        Postcard postcard = ARouter.getInstance().build(RoutePath.SEARCH_BOOK_CLASSIFY_PAGE)
                .with(b)
                .withTransition(com.qq.reader.dispatch.R.anim.slide_in_right, com.qq.reader.dispatch.R.anim.slide_out_left);
        analyzePushOrigin(jp, postcard);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 跳转到排行榜
     *
     * @param activity
     * @param title    可以为null
     */
    public static void goRank(Activity activity, String title, String rankFlag, final JumpActivityParameter jp, String fromTitle) {
        Bundle b = new Bundle();
        String pageName = getPageWithPreference(activity, 1);
        b.putString(NativeAction.KEY_JUMP_PAGENAME, pageName);
        //非cofree，不变；cofree只有男女
        //男女出tab列表
        ArrayList<String> pageList = new ArrayList<String>();
        pageList.add(RouteConstant.PAGE_NAME_RANK_BOY);
        pageList.add(RouteConstant.PAGE_NAME_RANK_GIRL);
        if (!FlavorUtils.isCoFree()){
            pageList.add(RouteConstant.PAGE_NAME_RANK_PUBLISH);
        }
        b.putStringArrayList(NativeAction.PARA_TYPE_PAGE_LIST, pageList);
        Bundle titleInfo = new Bundle();
        titleInfo.putString(RouteConstant.PAGE_NAME_RANK_BOY, Utility.getStringById(R.string.boy));
        titleInfo.putString(RouteConstant.PAGE_NAME_RANK_GIRL, Utility.getStringById(R.string.girl));
        if (!FlavorUtils.isCoFree()){
            titleInfo.putString(RouteConstant.PAGE_NAME_RANK_PUBLISH, Utility.getStringById(R.string.publisher));
        }
        b.putParcelable("titleInfo", titleInfo);
        //偏好
        if (FlavorUtils.isCoFree()){
            //只有男女
            if ("1".equals(rankFlag) || "2".equals(rankFlag)) {
                b.putString(NativeAction.URL_BUILD_PERE_RANK, rankFlag);
                b.putBoolean("withRankFlag", true);
            } else {
                int gendar = CommonConfig.getWebUserLike();
                if (gendar != 1 && gendar != 2) {
                    gendar = 1;
                }
                b.putString(NativeAction.URL_BUILD_PERE_RANK, String.valueOf(gendar));
            }
        } else {
            if ("1".equals(rankFlag) || "2".equals(rankFlag) || "3".equals(rankFlag)) {
                b.putString(NativeAction.URL_BUILD_PERE_RANK, rankFlag);
                b.putBoolean("withRankFlag", true);
            } else {
                int gendar = CommonConfig.getWebUserLike();
                if (gendar != 1 && gendar != 2 && gendar != 3) {
                    gendar = 3;
                }
                b.putString(NativeAction.URL_BUILD_PERE_RANK, String.valueOf(gendar));
            }
        }
        //title
        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, Utility.getStringById(R.string.rank_list));
        if (!TextUtils.isEmpty(fromTitle)) {
            b.putString(RouteConstant.FROM_TITLE, BaseApplication.getInstance().getResources().getString(R.string.bookrecommend));
        }
        try {
            JSONObject jo = new JSONObject();
            jo.put(NativeAction.URL_BUILD_PERE_ORIGIN, "102986");
            b.putString(NativeAction.PARA_TYPE_STAT_PARAMS, jo.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_NEW_RANK)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }



//    /**
//     * 跳转到排行榜二级页面
//     * @param activity
//     * @param title 标题
//     * @param mActionid
//     * @param mActiontag
//     * @param fromTitle 来源页面
//     * @param jp
//     */
//    public static void goRank_Detail(Activity activity, String title
//            , String mActionid, String mActiontag
//            , String fromTitle, final JumpActivityParameter jp) {
//        Bundle b = new Bundle();
//        if (TextUtils.isEmpty(title)) {
//            title = "排行榜";
//        }
//        b.putString(Constant.LOCAL_STORE_IN_TITLE, title);
//        b.putString(Constant.FROM_TITLE, fromTitle);
//        b.putString(NativeAction.KEY_ACTION, NativeAction.SERVER_ACTION_RANK);
//        if (TextUtils.isEmpty(mActionid)) {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, "0");
//        } else {
//            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, mActionid);
//        }
//        b.putString(NativeAction.URL_BUILD_PERE_ACTION_TAG, mActiontag);
//        b.putBoolean(NativeAction.PARA_TYPE_BOOLEAN, true);
//        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
//                .with(b)
//                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        doRouterJump(postcard, activity, jp);
//    }


    /**
     * 跳转到优惠券选择列表
     * isBookBuyOnly，仅仅按本购买，不可按章购买
     *
     * @param
     */
    public static void goCouponList(boolean FLAG_ACTIVITY_CLEAR_TOP, Activity mActivity, long couponId, boolean isBookBuyOnly) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.WEB_BROWER)
                .withString(RouteConstant.WEBCONTENT,
                        ServerUrl.COUPON_LIST + "?type=3" + "&isMult=0"
                                + "&couponId=" + String.valueOf(couponId) + "&isBookBuyOnly=" + (isBookBuyOnly ? 1 : 0))
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        if (FLAG_ACTIVITY_CLEAR_TOP){
            postcard.withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        JumpActivityParameter jp = new JumpActivityParameter();
        jp.setRequestCode(ActivityCodeConstant.FORRESULT_DEFAULT_CODE);
        doRouterJump(postcard, mActivity, jp);
    }

    /**
     * 跳转到我的包月特权
     *
     * @param context
     */
    public static void goMonthPrivilege(Activity context, final JumpActivityParameter jp, String parameters) {
        String url = ServerUrl.VIP_OPEN_BASE_URL;
        if(!TextUtils.isEmpty(parameters)){
            url += "?"+parameters;
        }
        Postcard postcard = ARouter.getInstance().build(RoutePath.WEB_BROWER)
                .withString(RouteConstant.WEBCONTENT, url)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, context, jp);
    }

    /**
     * 跳转到今日任务
     *
     * @param mActivity
     */
    public static void goTodayTask(Activity mActivity, final JumpActivityParameter jp, String jumpType) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.WEB_BROWER)
                .withString(RouteConstant.WEBCONTENT, ServerUrl.getProfileEverydayTask(mActivity, jumpType))
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, mActivity, jp);
    }

    /**
     * 跳转到书券页面
     *
     * @param mActivity
     */
    public static void goBookTicket(Activity mActivity, final JumpActivityParameter jp, String jumpType) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.WEB_BROWER)
                .withString(RouteConstant.WEBCONTENT, ServerUrl.BOOK_TICKET)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, mActivity, jp);
    }

    /**
     * 跳转到专题
     *
     * @param context
     * @param title
     * @param actionTag NativeAction.ACTION_TAG_NORMAL_TOPIC    NativeAction.ACTION_TAG_CLASSIC_TOPIC
     */
    public static void goSpecialTopic(Activity context, String title, String actionTag, final JumpActivityParameter jp) {

        Bundle b = new Bundle();
        if (TextUtils.isEmpty(title))
            title = Utility.getStringById(R.string.special_topic);
        if (TextUtils.isEmpty(actionTag)) {
            actionTag = "0";
        }
        b.putString(NativeAction.URL_BUILD_PERE_ACTION_TAG, actionTag);
        int mGendar = CommonConfig.getWebUserLike();
        if (mGendar == 1 || mGendar == 2) {
            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, String.valueOf(mGendar));
        } else {
            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, "0");
        }

        b.putString(NativeAction.KEY_JUMP_PAGENAME,
                RouteConstant.PAGE_NAME_DISCOVERY_TOPIC_INTERST);
        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, title);
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, context, jp);
    }

    /**
     * 跳转到经典
     */
    public static void goClassic(Activity activity, final JumpActivityParameter jp) {
        Bundle b = new Bundle();
        b.putString(NativeAction.KEY_JUMP_PAGENAME, getPageWithPreference(activity, 6));
        b.putString(NativeAction.URL_BUILD_USER_LIKE, String.valueOf(CommonConfig.getWebUserLike()));
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_CLASSIC)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 跳转到经典--名人堂
     */
    public static void goClassicAuthorIndex(Activity activity, final JumpActivityParameter jp) {
        Bundle b = new Bundle();
        b.putString(NativeAction.KEY_JUMP_PAGENAME, getPageWithPreference(activity, 6));
        b.putString(NativeAction.URL_BUILD_USER_LIKE, String.valueOf(CommonConfig.getWebUserLike()));
        b.putInt(RouteConstant.JUMP_PARAMETER_AUTHORS_INDEX, 1);
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_CLASSIC)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 跳转到专题详情
     *
     * @param context
     */
    public static void goSpecialTopicDetail(Activity context, String url
            , JumpActivityParameter jp) {
        goSpecialTopicDetail(context, url, null, jp);
    }

    /**
     * 跳转到专题详情
     *
     * @param context
     */
    public static void goSpecialTopicDetail(Activity context, String url
            ,String fromTitle , JumpActivityParameter jp) {
        String webUrl = ServerUrl.FEED_GOTOPIC + url;
        Postcard postcard = ARouter.getInstance().build(RoutePath.WEB_BROWER)
                .withString(RouteConstant.WEBCONTENT, webUrl)
                .withString(RouteConstant.FROM_TITLE, fromTitle)
                .withBoolean(RouteConstant.WEBCONTENT_COLLECT, true)
                .withBoolean(RouteConstant.WEBCONTENT_SHARE, true)
                .withBoolean(RouteConstant.WEBCONTENT_NEED_RECORD_HISTORY, false)//107版本专题不加入浏览历史
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, context, jp);
    }

    /**
     * 跳转到听书专区
     *
     * @param activity
     */
    public static void goListenZone(Activity activity, boolean needStartMain, final JumpActivityParameter jp) {
        Bundle b = new Bundle();
        b.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_LISTEN_ZONE);
        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE,
                BaseApplication
                        .getInstance().getString(R.string.listen_zone));
        b.putBoolean(NativeAction.KEY_NEED_START_MAIN, needStartMain);
        Postcard postcard = ARouter.getInstance().build(RoutePath.TING_AREA)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 跳转到发现包月专区
     *
     * @param activity
     */
    public static void goMonthArea(Activity activity, final JumpActivityParameter jp, String fromTitle) {

        Bundle b = new Bundle();
        String pageName = getPageWithPreference(activity, 2);
        if (!TextUtils.isEmpty(fromTitle)) {
            b.putString(RouteConstant.FROM_TITLE, BaseApplication.getInstance().getResources().getString(R.string.bookrecommend));
        }
        b.putString(NativeAction.KEY_JUMP_PAGENAME, pageName);
        try {
            JSONObject jo = new JSONObject();
            jo.put(NativeAction.URL_BUILD_PERE_ORIGIN, "102988");
            b.putString(NativeAction.PARA_TYPE_STAT_PARAMS, jo.toString());
        } catch (JSONException e) {
            Log.printErrStackTrace("JumpActivityUtil", e, null, null);
            e.printStackTrace();
        }

        ArrayList<String> pageList = new ArrayList<String>();
        pageList.add(RouteConstant.PAGE_NAME_PAYMONTH_BOY);
        pageList.add(RouteConstant.PAGE_NAME_PAYMONTH_GIRL);
        pageList.add(RouteConstant.PAGE_NAME_PAYMONTH_PUBLISH);
        b.putStringArrayList(NativeAction.PARA_TYPE_PAGE_LIST, pageList);

        Bundle titleInfo = new Bundle();
        titleInfo.putString(RouteConstant.PAGE_NAME_PAYMONTH_BOY, BaseApplication
                .getInstance().getString(R.string.boy));
        titleInfo.putString(RouteConstant.PAGE_NAME_PAYMONTH_GIRL, BaseApplication
                .getInstance().getString(R.string.girl));
        titleInfo.putString(
                RouteConstant.PAGE_NAME_PAYMONTH_PUBLISH,
                BaseApplication.getInstance().getString(
                        R.string.publisher));
        b.putParcelable("titleInfo", titleInfo);

        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, BaseApplication.getInstance().getString(R.string.local_zone_two_level_month));
        b.putString(RouteConstant.ACTIVITY_TYPE, RouteConstant.ACTIVITY_TYPE_MONTH_PAY);
        b.putString(RouteConstant.POP_RIGHT_ICON_FROM, RouteConstant.POP_RIGHT_ICON_FROM_MONTH);
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_ZONE_TWO_LEVEL)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 跳转到今日免费
     *
     * @param activity
     */
    public static void goTodayFree(Activity activity, final JumpActivityParameter jp, String fromTitle) {

        Bundle b = new Bundle();
        String pageName = getPageWithPreference(activity, 3);
        if (!TextUtils.isEmpty(fromTitle)) {
            b.putString(RouteConstant.FROM_TITLE, BaseApplication.getInstance().getResources().getString(R.string.bookrecommend));
        }
        b.putString(NativeAction.KEY_JUMP_PAGENAME, pageName);
        try {
            JSONObject jo = new JSONObject();
            jo.put(NativeAction.URL_BUILD_PERE_ORIGIN, "102988");
            b.putString(NativeAction.PARA_TYPE_STAT_PARAMS, jo.toString());
        } catch (JSONException e) {
            Log.printErrStackTrace("JumpActivityUtil", e, null, null);
            e.printStackTrace();
        }

        ArrayList<String> pageList = new ArrayList<String>();
        pageList.add(RouteConstant.PAGE_NAME_FREE_BOY);
        pageList.add(RouteConstant.PAGE_NAME_FREE_GIRL);
        pageList.add(RouteConstant.PAGE_NAME_FREE_PUBLISH);
        b.putStringArrayList(NativeAction.PARA_TYPE_PAGE_LIST, pageList);

        Bundle titleInfo = new Bundle();
        titleInfo.putString(RouteConstant.PAGE_NAME_FREE_BOY, BaseApplication
                .getInstance().getString(R.string.boy));
        titleInfo.putString(RouteConstant.PAGE_NAME_FREE_GIRL, BaseApplication
                .getInstance().getString(R.string.girl));
        titleInfo.putString(
                RouteConstant.PAGE_NAME_FREE_PUBLISH,
                BaseApplication.getInstance().getString(
                        R.string.publisher));
        b.putParcelable("titleInfo", titleInfo);
        b.putString(RouteConstant.ACTIVITY_TYPE, RouteConstant.ACTIVITY_TYPE_TODAY_FREE);
        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, BaseApplication.getInstance().getString(R.string.local_zone_two_level_free));
        b.putString(RouteConstant.POP_RIGHT_ICON_FROM, RouteConstant.POP_RIGHT_ICON_FROM_FREE);

        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_ZONE_TWO_LEVEL)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 跳转到完结二级页
     */
    public static void goEndBookTwoLevel(Activity activity, final JumpActivityParameter jp) {
        Bundle b = new Bundle();
        String pageName = getPageWithPreference(activity, 4);
        b.putString(NativeAction.KEY_JUMP_PAGENAME, pageName);

        ArrayList<String> pageList = new ArrayList<String>();
        pageList.add(RouteConstant.PAGE_NAME_END_BOY);
        pageList.add(RouteConstant.PAGE_NAME_END_GIRL);
        pageList.add(RouteConstant.PAGE_NAME_END_PUBLISH);
        b.putStringArrayList(NativeAction.PARA_TYPE_PAGE_LIST, pageList);

        Bundle titleInfo = new Bundle();
        titleInfo.putString(RouteConstant.PAGE_NAME_END_BOY, BaseApplication
                .getInstance().getString(R.string.boy));
        titleInfo.putString(RouteConstant.PAGE_NAME_END_GIRL, BaseApplication
                .getInstance().getString(R.string.girl));
        titleInfo.putString(RouteConstant.PAGE_NAME_END_PUBLISH, BaseApplication
                .getInstance().getString(R.string.publisher));
        b.putParcelable("titleInfo", titleInfo);
        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, BaseApplication.getInstance().getString(R.string.local_zone_two_level_end));
        b.putString(RouteConstant.FROM_TITLE, BaseApplication.getInstance().getString(R.string.bookrecommend));

        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_ZONE_TWO_LEVEL)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }



    /**
     * 跳转到趣读列表
     *
     * @param activity
     * @param jp
     */
    public static void goInterestReaderList(Activity activity, final JumpActivityParameter jp) {

        /*Bundle bundle = new Bundle();
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_INERENST_READER)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                .with(bundle);
        doRouterJump(postcard, activity, jp);*/
    }



    /**
     * 今日秒杀
     */
    public static void goLimitTimeDiscountBuy(Activity activity, JumpActivityParameter jp) {
        Bundle b = new Bundle();
        b.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_LIMIT_TIME_DISCOUNT_BUY);
        b.putBoolean(RouteConstant.LOCAL_STORE_USE_CACHE, false);
        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, Utility.getStringById(R.string.limit_time_discount_buy_title));
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_LIMIT_TIME_DISCOUNT_BUY)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 跳转到小编精选
     */
    public static void goEditorChoice(Activity activity, final JumpActivityParameter jp) {
        Bundle b = new Bundle();
        String pageName = getPageWithPreference(activity, 5);
        b.putString(NativeAction.KEY_JUMP_PAGENAME, pageName);

        ArrayList<String> pageList = new ArrayList<String>();
        pageList.add(RouteConstant.PAGE_NAME_EDITORCHOICE_BOY);
        pageList.add(RouteConstant.PAGE_NAME_EDITORCHOICE_GIRL);
        pageList.add(RouteConstant.PAGE_NAME_EDITORCHOICE_PUBLISH);
        b.putStringArrayList(NativeAction.PARA_TYPE_PAGE_LIST, pageList);

        Bundle titleInfo = new Bundle();
        titleInfo.putString(RouteConstant.PAGE_NAME_EDITORCHOICE_BOY, Utility.getStringById(R.string.boy));
        titleInfo.putString(RouteConstant.PAGE_NAME_EDITORCHOICE_GIRL, Utility.getStringById(R.string.girl));
        titleInfo.putString(RouteConstant.PAGE_NAME_EDITORCHOICE_PUBLISH, Utility.getStringById(R.string.publisher));
        b.putParcelable("titleInfo", titleInfo);

        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, titleInfo.getString(pageName));
        b.putString(RouteConstant.FROM_TITLE, BaseApplication.getInstance().getResources().getString(R.string.bookrecommend));
        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, BaseApplication.getInstance().getString(R.string.editor_choice));

        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_ZONE_TWO_LEVEL)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }


    // @TODO 这个得修改下

    /**
     * 跳转到专属推荐二级页
     **/
    public static void goExclusiveRecommend(Activity context, JumpActivityParameter jp) {
        try {
            Bundle b = new Bundle();
            b.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_EXCLUSIVE_RECOMMEND);
            b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, BaseApplication.getInstance().getResources().getString(R.string.exclusive_recommend));
            b.putString(RouteConstant.FROM_TITLE, BaseApplication.getInstance().getResources().getString(R.string.bookrecommend));

            Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_EXCLUSIVE_RECOMMEND)
                    .with(b)
                    .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            doRouterJump(postcard, context, jp);
        } catch (Exception e) {
            Log.printErrStackTrace("JumpActivityUtil", e, null, null);
            Log.e("JumpActivityUtil.goExclusiveRecommend", e.getMessage());
        }
    }

    /**
     * 跳转到名人堂
     *
     * @param context
     * @param title       可以为null
     * @param currentItem 默认指定分类标签是0
     */
    public static void goHallOfFame(Activity context, String title, int currentItem, final JumpActivityParameter jp) {
        if (TextUtils.isEmpty(title))
            title = Utility.getStringById(R.string.fame_man_hall);
        Bundle b = new Bundle();
        b.putString(NativeAction.KEY_JUMP_PAGENAME,
                RouteConstant.PAGE_NAME_HALLOFFAME);
        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, title);
        b.putInt(RouteConstant.CURRENT_ITEM, currentItem);
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_HALL_OF_FAME)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, context, jp);
    }

    /**
     * 跳转到精选书城
     *
     * @param activity
     */
    public static void goWellChosenBookStore(Activity activity, String title, final JumpActivityParameter jp) {
        Bundle b = new Bundle();
        b.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_WELLCHOSEN_BOOKSTORE);
        if (TextUtils.isEmpty(title)) {
            title = Utility.getStringById(R.string.selected_book_city);
        }
        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, title);
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 跳转到OPPO精选
     * 注1.url和其他的协议拼法不一样，这里直接拼好得了
     * 注2.参数id不知道干嘛用的，有重复，所以不能读缓存。
     *
     * @param activity
     */
    public static void goWellChosenForOppo(Activity activity, String infoId) {
        Bundle b = new Bundle();
        b.putString(NativeAction.URL_DATA_QURL, ServerUrl.FEED_MESSAGE_MORE_URL + infoId);
        b.putBoolean(RouteConstant.LOCAL_STORE_USE_CACHE, false);
        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, "OPPO精选");
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, null);
    }

    /**
     * 阅读基因
     *
     * @param activity
     * @param jp
     */
    public static void goGeneActivity(Activity activity, JumpActivityParameter jp) {
        if (activity == null) {
            return;
        }

        Postcard postcard = ARouter.getInstance().build(RoutePath.PROFILE_NATIVE_READING_GENE)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 跳转到基因设置Activity
     *
     * @param activity
     */
    public static void goGenSetActivity(Activity activity, JumpActivityParameter jp) {
        goGenSetActivity(activity, null, jp);
    }

    /**
     * 跳转到基因设置Activity
     *
     * @param activity
     */
    public static void goGenSetActivity(Activity activity,
                                        String fromTitle,
                                        JumpActivityParameter jp) {
        Bundle b = new Bundle();
        if (jp != null) {
            Object obj = jp.getJsonParamater();
            if (obj != null && (obj instanceof Map)) {
                Map map = (Map) obj;
                String type;
                if (map.containsKey("type")) {
                    type = map.get("type").toString();
                    b.putString("type", type);
                }
            }
        }
        Postcard postcard = ARouter.getInstance().build(RoutePath.FEED_MYPREFERENCE_GENSET)
                .with(b)
                .withString(RouteConstant.FROM_TITLE, fromTitle)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 我的帐户
     *
     * @param activity
     * @param jp
     */
    public static void goAccountActivity(Activity activity, JumpActivityParameter jp) {
        if (activity == null) {
            return;
        }

        Postcard postcard = ARouter.getInstance().build(RoutePath.PROFILE_ACCOUNT)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 跳转专题的全部回复列表
     *
     * @param context
     * @param tid
     * @param ctype
     * @param jp
     */
    public static void goTopicComment(Activity context, String tid, String ctype, String fromJump, JumpActivityParameter jp) {
        try {
            Bundle b = new Bundle();
            if (!TextUtils.isEmpty(fromJump)) {
                b.putString(RouteConstant.FROM_JUMP, fromJump);
            }
            b.putLong(NativeAction.URL_BUILD_PERE_PAGESTAMP, 0);
            b.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_BOOKCLUB_REPLYLIST);
            b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, Utility.getStringById(R.string.all_reply));
            b.putString(NativeAction.URL_BUILD_PERE_COMMENT_TID, tid);
            b.putInt(RouteConstant.CTYPE, Integer.parseInt(ctype));
            b.putInt(NativeAction.KEY_EXECUTE_TYPE, NativeAction.VALUE_EXECUTE_JUMP);
            Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_END_PAGE)
                    .with(b)
                    .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            doRouterJump(postcard, context, jp);
        } catch (Exception e) {
            Log.printErrStackTrace("JumpActivityUtil", e, null, null);
            Log.e("JumpActivityUtil.goTopicComment", e.getMessage());
        }
    }

    public static void goTopicComment(Activity context, String tid, String ctype, JumpActivityParameter jp) {
        goTopicComment(context, tid, ctype, null, null);
    }

    /**
     * 专题讨论列表页
     *
     * @param context
     * @param tid
     * @param ctype
     * @param jp
     */
    public static void goTopicDiscuss(Activity context, String tid, String ctype, JumpActivityParameter jp) {
        try {
            Bundle b = new Bundle();
            b.putLong(NativeAction.URL_BUILD_PERE_PAGESTAMP, 0);
            b.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_BOOKCLUB_DISCUSSLIST);
            b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, Utility.getStringById(R.string.discusslist_title));
            b.putString(NativeAction.URL_BUILD_PERE_COMMENT_TID, tid);
            b.putInt(RouteConstant.CTYPE, Integer.parseInt(ctype));
            b.putInt(NativeAction.KEY_EXECUTE_TYPE, NativeAction.VALUE_EXECUTE_JUMP);
            Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_END_PAGE)
                    .with(b)
                    .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            doRouterJump(postcard, context, jp);
        } catch (Exception e) {
            Log.printErrStackTrace("JumpActivityUtil", e, null, null);
            Log.e("JumpActivityUtil.goTopicDiscuss", e.getMessage());
        }
    }

    /**
     * 章评
     *
     * @param context
     * @param bid
     * @param chapterid
     * @param floorIndex
     * @param floorNext
     * @param jp
     */
    public static void goSingleChapterComment(Activity context, String bid, String chapterid, String chapterUuid, int floorIndex, int floorNext, JumpActivityParameter jp) {
        try {
            Bundle b = new Bundle();

            b.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_BOOKCLUB_CHAPTER);


            b.putString(RouteConstant.LOCAL_STORE_IN_TITLE,
                    Utility.getStringById(R.string.chapter_review_detail));
            b.putString(NativeAction.PARA_TYPE_BOOK_TITLE,
                    Utility.getStringById(R.string.chapter_review_detail));
            b.putLong(NativeAction.URL_BUILD_PERE_BOOK_ID, Long.valueOf(bid));

            b.putInt(NativeAction.URL_BUILD_PERE_CHAPTER_ID, Integer.valueOf(chapterid));
            b.putLong(NativeAction.URL_BUILD_PERE_CHAPTER_UUID, Long.valueOf(chapterUuid));

            b.putBoolean(RouteConstant.LOCAL_STORE_KEY_IS_FINISH, true);
            b.putInt(NativeAction.KEY_EXECUTE_TYPE, NativeAction.VALUE_EXECUTE_JUMP);

            b.putInt(RouteConstant.FLOOR_INDEX, floorIndex);
            b.putInt(RouteConstant.FLOOR_NEXT, floorNext);
            Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_END_PAGE)
                    .with(b)
                    .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            doRouterJump(postcard, context, jp);
        } catch (Exception e) {
            Log.printErrStackTrace("JumpActivityUtil", e, null, null);
            Log.e("JumpActivityUtil.goTopicComment", e.getMessage());
        }
    }

//    private static class ABTestOfSearchToolSearchToolMain implements IABTest {
//
//        @Override
//        public void doRDM() {
//            try {
//                String eventName = "";
//                if (ABTestHandle.getType() == ABTestHandle.TYPE_B) {
//                    eventName = RDM.ABTEST_B;
//                } else {
//                    eventName = RDM.ABTEST_A;
//                }
//                final Map<String, String> mMap = new HashMap<String, String>();
//                mMap.put("abcode", "0");
//                RDM.stat(eventName, mMap, ReaderApplication.getInstance().getApplicationContext());
//            } catch (Exception e) {
//
//            }
//        }
////    }

    /**
     * 跳转搜书神器 大家都在搜
     *
     * @param context Context
     */
    public static void toSearchToolMore(Activity context, JumpActivityParameter jp) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
                .withString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_SEARCH_TOOL_MORE)
                .withString(RouteConstant.LOCAL_STORE_IN_TITLE, BaseApplication.getInstance().getString(R.string.search_tool_more_title))
                .withBoolean(NativeAction.PARA_TYPE_SWIPE_REFRESH_ENABLE, true)
                .withInt(RouteConstant.NATIVE_LISTVIEW_DIVIDOR_HEIGHT, 1)
                .withInt(RouteConstant.NATIVE_LISTVIEW_DIVIDOR_RES, R.drawable.localstore_card_divider_line_drawable)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, context, jp);
    }

    /**
     * 跳转搜书神器主页
     *
     * @param context Context
     */
    public static void goSearchToolMain(Activity context, JumpActivityParameter jp) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_SEARCH_TOOL)
                .withString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_SEARCH_TOOL_MAIN)
                .withString(RouteConstant.LOCAL_STORE_IN_TITLE, BaseApplication.getInstance().getString(R.string.search_tool))
                .withBoolean(NativeAction.PARA_TYPE_SWIPE_REFRESH_ENABLE, false)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, context, jp);
    }

    /**
     * 跳转自定义组合搜索
     *
     * @param context Context
     */
    public static void toSearchOption(Activity context, JumpActivityParameter jp) {
        Bundle b = new Bundle();
        b.putString(NativeAction.KEY_JUMP_PAGENAME,
                RouteConstant.PAGE_NAME_HALLOFFAME);
        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, BaseApplication.getInstance().getString(R.string.search_tool_option_title));
        b.putBoolean(NativeAction.PARA_TYPE_SWIPE_REFRESH_ENABLE, true);
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_SEARCH_OPTION)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, context, jp);
    }

    /**
     * 跳转自定义组合搜索,带初始值
     *
     * @param context Context
     */
    public static void toSearchOption(Activity context, Bundle b, JumpActivityParameter jp) {
        b.putString(NativeAction.KEY_JUMP_PAGENAME,
                RouteConstant.PAGE_NAME_HALLOFFAME);
        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, BaseApplication.getInstance().getString(R.string.search_tool_option_title));
        b.putBoolean(NativeAction.PARA_TYPE_SWIPE_REFRESH_ENABLE, true);
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_SEARCH_OPTION)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, context, jp);
    }

    /**
     * 搜书神器跳转结果页
     *
     * @param context              Context used to jump to specific activity
     * @param optionText           选项文字描述
     * @param param                构成搜索条件的字段组合，用于后台URL拼写
     * @param isFromOptionActivity 是否是从自定义组合页跳转
     */
    public static void toSearchResult(Activity context, String optionText, String param, boolean isFromOptionActivity, JumpActivityParameter jp) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_SEARCH_RESULTS)
                .withString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_SEARCH_TOOL_RESULT)
                .withString(NativeAction.PARA_TYPE_SEARCH_OPTION, param)
                .withString(NativeAction.PARA_TYPE_SEARCH_OPTION_TEXT, optionText)
                .withString(RouteConstant.LOCAL_STORE_IN_TITLE, Utility.getStringById(R.string.search_result))
                .withBoolean(NativeAction.PARA_TYPE_SWIPE_REFRESH_ENABLE, true)
                .withBoolean(NativeAction.PARA_TYPE_BOOLEAN, isFromOptionActivity)
                .withInt(NativeAction.PARA_TYPE_SEARCH_RESULT_ORDER, 1)
                .withInt(RouteConstant.NATIVE_LISTVIEW_DIVIDOR_HEIGHT, context.getResources().getDimensionPixelOffset(R.dimen.search__tool_result_divider_div_height))
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        if(!FlavorUtils.isSamsung()){
            postcard.withInt(RouteConstant.NATIVE_LISTVIEW_DIVIDOR_RES, R.drawable.feed_listview_divider);
        }
        doRouterJump(postcard, context, jp);
    }

    /**
     * 听书
     *
     * @param context
     * @param bid
     * @param jp
     */
    public static void goPlayerActivity(Activity context, long bid, JumpActivityParameter jp) {
        try {
            Postcard postcard = ARouter.getInstance().build(RoutePath.TING_PLAYER)
                    .withLong(RouteConstant.MARK_BID, bid)
                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            doRouterJump(postcard, context, jp);
        } catch (Exception e) {
            Log.printErrStackTrace("JumpActivityUtil", e, null, null);
            Log.e("JumpActivityUtil.goPlayerActivity", e.getMessage());
        }
    }

    /**
     * 听书
     *
     * @param context
     * @param bid
     * @param chapterId
     * @param jp
     */
    public static void goPlayerActivity(Activity context, long bid, int chapterId, JumpActivityParameter jp) {
        try {
            Postcard postcard = ARouter.getInstance().build(RoutePath.TING_PLAYER)
                    .withLong(RouteConstant.MARK_BID, bid)
                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .withInt(RouteConstant.BOOK_CHAPTER_ID, chapterId);
            doRouterJump(postcard, context, jp);
        } catch (Exception e) {
            Log.printErrStackTrace("JumpActivityUtil", e, null, null);
            Log.e("JumpActivityUtil.goPlayerActivity", e.getMessage());
        }
    }

    /**
     * 跳转到推荐页（书友还读过的更多list）
     *
     * @param activity
     * @param bid
     */
    public static void goRecommendPage(Activity activity, String bid, JumpActivityParameter jp) {
        Bundle b = new Bundle();
        b.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_RECOMMENDPAGE);
        b.putString(RouteConstant.LOCAL_STORE_IN_TITLE, BaseApplication.getInstance().getString(R.string.title_recommend_page));
        b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, bid);
        b.putString(NativeAction.KEY_ACTION, "recommendbooks");
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }



    /**
     * 通用搜索跳转方法
     * @param searchRoutePath 根据RoutePath区别跳转类型，详见：<br/> {@link RoutePath}
     * @param fromActivity Activity
     * @param keyword 关键词
     * @param from 从哪里进入（统计）
     * @param hint 默认hint
     */
    public static void goSearch(String searchRoutePath, Activity fromActivity, String keyword, String from, String hint) {
        Bundle enterBundle = new Bundle();
        enterBundle.putString(RouteConstant.SEARCH_KEY, keyword);
        enterBundle.putString(RouteConstant.SEARCH_HINT, hint);
        enterBundle.putString("from", from);
        Postcard postcard = ARouter.getInstance().build(searchRoutePath)
                .with(enterBundle)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, fromActivity , null);
    }

    /**
     * 跳转到右上角放大镜对应的搜索页
     *兼容CommonSearch跳转方式
     *
     * @param context Context used to jump to specific activity
     * @param keyword 搜索关键词
     */
    public static void goCommonSearch(Activity context, String keyword) {
        goCommonSearch(context, keyword,"", "0");
    }

    /**
     * 跳转到右上角放大镜对应的搜索页
     * 兼容CommonSearch跳转方式
     *
     * @param context Context used to jump to specific activity
     * @param keyword 搜索关键词
     * @param from    从哪里进入 1书架，2推荐，3分类，4发现
     */
    public static void goCommonSearch(Activity context, String keyword, String hint, String from) {
        goSearch(RoutePath.BOOK_STORE_SEARCH_COMMON, context, keyword, from, hint);
    }

    /**
     * 跳转免费书搜索
     * @param activity
     * @param from
     */
    public static void goFreeSearch(Activity activity, String from) {
        goSearch(RoutePath.BOOK_STORE_SEARCH_FREE, activity, "",
                from, activity.getResources().getString(R.string.search_tip_top_free));
    }

    /**
     * 跳转包月书搜索
     * @param activity
     * @param from
     */
    public static void goMonthSearch(Activity activity, String from) {
        goSearch(RoutePath.BOOK_STORE_SEARCH_MONTH, activity, "",
                from, activity.getResources().getString(R.string.search_tip_top_month));
    }

    /**
     * 听书搜索
     * @param fromActivity
     */
    public static void goListenSearch(Activity fromActivity) {
        goSearch(RoutePath.BOOK_STORE_SEARCH_LISTEN, fromActivity, "", "", "");
    }

    /**
     * 从主页进入搜索页面，包含搜索Hint
     */
    public static void goSearchFromMain(Activity fromActivity, String from, String hint, String hintAdvId) {
        Bundle enterBundle = new Bundle();
        enterBundle.putString(RouteConstant.SEARCH_HINT, hint);
        enterBundle.putString("from", from);
        enterBundle.putString("hintAdvId", hintAdvId);
        statClickSearchFromMain(from);
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_SEARCH_COMMON)
                .with(enterBundle)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, fromActivity , null);
    }

    private static void statClickSearchFromMain(String from) {
        String pdid = "";
        if (TextUtils.equals("1", from)) {//书架
            pdid = "shelf";
        } else if (TextUtils.equals("2", from)) {//精选
            pdid = "jingxuan";
        } else if (TextUtils.equals("3", from)) {//分类
            pdid = "class";
        }
        if (!TextUtils.isEmpty(pdid)) {
            new ClickEvent
                    .Builder(PageNames.PAGE_SEARCH)
                    .setPageDataID(pdid)
                    .build()
                    .commit();
        }
    }


    /**
     * 根据JumpActivityParameter中配置的参数，跳转业务Actiivty的方式
     *
     * @param fromActivity
     * @param jp
     */
    private static void doRouterJump(Postcard postcard, Activity fromActivity, JumpActivityParameter jp) {
        if (jp == null) {
            jp = new JumpActivityParameter();
        }
        postcard.withFlags(jp.getFlag());
        if (!TextUtils.isEmpty(jp.getQurl())) {
            postcard.withString(NativeAction.URL_DATA_QURL, jp.getQurl());
        }
        if (jp.getRequestCode() != JumpActivityParameter.FORRESULT_CODE_NONE) {
            postcard.navigation(fromActivity, jp.getRequestCode());
        } else {
            postcard.navigation(fromActivity);
        }
    }

    /**
     * 根据JumpActivityParameter中配置的参数，跳转业务Actiivty的方式
     *
     * @param fromActivity
     * @param intent
     * @param jp
     */
    private static void doJump(Activity fromActivity, Intent intent, JumpActivityParameter jp) {
        if (fromActivity == null) {
            return;
        }
        if (jp == null) {
            jp = new JumpActivityParameter();
            jp.setFlag(intent.getFlags());
        }
//        intent.putExtra("JumpActivityParameter", jp);
        intent.setFlags(jp.getFlag());
        if (!TextUtils.isEmpty(jp.getQurl())) {
            intent.putExtra(NativeAction.URL_DATA_QURL, jp.getQurl());
        }
//        if (GO_MAIN_AFTER_BACK && !"com.qq.reader.activity.MainActivity".equals(intent.getComponent().getClassName())) {
//            intent.putExtra(Constant.INTENT_FROM_PUSH, true);
//        }
        if (jp.getRequestCode() != JumpActivityParameter.FORRESULT_CODE_NONE) {
            fromActivity.startActivityForResult(intent, jp.getRequestCode());
        } else {
            fromActivity.startActivity(intent);
        }

        fromActivity.overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);

//        if (GO_MAIN_AFTER_BACK) {
//            GO_MAIN_AFTER_BACK = false;
//        }

    }

    /**
     * 跳转到设置
     *
     * @param fromActivity
     */
    public static void goSetting(Activity fromActivity, JumpActivityParameter jp) {
        Uri packageURI = Uri.parse("package:" + fromActivity.getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        doJump(fromActivity, intent, jp);
    }

    //跳转到标签页面
    public static void goLabAct(Activity activity, String actionId, String actionTag, final JumpActivityParameter jp) {
        Bundle b = new Bundle();
        if (TextUtils.isEmpty(actionTag)) {
            b.putString(NativeAction.URL_BUILD_PERE_ACTION_TAG,
                    NativeAction.PARA_TYPE_CATEGORY_TAG_ALL_NEW);
        } else {
            b.putString(NativeAction.URL_BUILD_PERE_ACTION_TAG, actionTag);
        }
        if (TextUtils.isEmpty(actionId)) {
            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, "0");
        } else {
            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, actionId);
        }
        b.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_SEARCH_LABEL);
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity , jp);
    }

    //跳转到标签页面
    public static void goLabAct(Activity activity, String actionId, String actionTag, final JumpActivityParameter jp, String searchFrom) {
        Bundle b = new Bundle();
        if (TextUtils.isEmpty(actionTag)) {
            b.putString(NativeAction.URL_BUILD_PERE_ACTION_TAG,
                    NativeAction.PARA_TYPE_CATEGORY_TAG_ALL_NEW);
        } else {
            b.putString(NativeAction.URL_BUILD_PERE_ACTION_TAG, actionTag);
        }
        if (TextUtils.isEmpty(actionId)) {
            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, "0");
        } else {
            b.putString(NativeAction.URL_BUILD_PERE_ACTION_ID, actionId);
        }
        b.putString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_SEARCH_LABEL);
        b.putString(NativeAction.PARA_TYPE_FROM_SEARCH, searchFrom);
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
                .with(b)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity , jp);
    }

//    /**
//     * 跳转到用户主页
//     *
//     * @param activity
//     * @param userId   请求用户的userid
//     * @param jp
//     */
//    public static void goUserCenter(Activity activity, String userId, final JumpActivityParameter jp) {
//        Intent intent = new Intent();
//        intent.putExtra(NativeAction.KEY_JUMP_PAGENAME,
//                Constant.PAGE_NAME_USER_CENTER_MAIN);
//        intent.putExtra(Constant.USER_ID, userId);
//        intent.setClass(activity, UserCenterNewActivity.class);
//        AnimationComm.overridePendingTransition(R.anim.slide_in_right,
//                R.anim.slide_out_left);
//        doJump(activity, intent, jp);
//    }

    /**
     * 跳转到普通作者的所有产品页面
     *
     * @param activity
     * @param authorName
     */
    public static void goNormalAuthProduct(Activity activity, String authorName, int searchType, final JumpActivityParameter jp) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
                .withString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_PUBLISHER_AND_AUTHOR)
                .withString(RouteConstant.SEARCH_KEY, authorName)
                .withString(RouteConstant.LOCAL_STORE_IN_TITLE, authorName)
                .withInt(RouteConstant.SEARCH_NEED_DIRECT, 0)
                .withInt(RouteConstant.SEARCH_TYPE, searchType)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity , jp);
    }



    /**
     * @param activity
     * @param userId
     * @param userNickName
     * @param userIconUrl
     * @param jp
     */
    public static void goUserCenter(Activity activity, String userId, String userNickName, String userIconUrl, final JumpActivityParameter jp) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_USER_CENTER)
                .withString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_USER_CENTER_MAIN)
                .withString(RouteConstant.USER_ID, userId)
                .withString(RouteConstant.USER_NICKNAME, userNickName)
                .withString(RouteConstant.USER_ICONURL, userIconUrl)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity , jp);
    }

    /**
     * 根据书籍bid，跳转至作家全部作品页,包含全部作品
     */
    public static void goAuthorAllBooksInCategory(Activity activity,String authorId, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(JumpParameterConstants.CHANNEL_TAB_BUNDLE_TITLE, title);
        bundle.putString(JumpParameterConstants.CHANNEL_TAB_BUNDLE_TYPE, String.valueOf(SecondLevelConstant.SECOND_LEVEL_ALL_WORKS));
        bundle.putString(JumpParameterConstants.CHANNEL_TAB_BUNDLE_ID, authorId);
        Postcard postcard  = ARouter.getInstance().build(RoutePath.COMMON_BOOK_LIST_TWO_PAGE);
        postcard.with(bundle).withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        if (activity != null) {
            doRouterJump(postcard, activity, null);
        }
    }

    public static void goSignDetailActivity(Activity context) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.PROFILE_SIGN_DETAILS)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        postcard.navigation();

    }

    /**
     * 跳转到阅读日详情页面
     *
     * @param context
     */
    public static void goReadDayDetail(Activity context, String url) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.WEB_BROWER)
                .withString(RouteConstant.WEBCONTENT, url);
        postcard.navigation();
    }

    /**
     * 今日精选二级页面
     *
     * @param activity
     * @param jp
     */
    public static void goTodayRecommond(Activity activity, Bundle bundle
            , final JumpActivityParameter jp) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
                .with(bundle)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 主编推荐二级页
     */
    public static void goVirtualRecommendTabPage(Activity activity
            , final JumpActivityParameter jp) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
                .withString(RouteConstant.LOCAL_STORE_IN_TITLE, activity.getString(R.string.editor_recommond_title))
                .withString(RouteConstant.FROM_TITLE, activity.getString(R.string.bookrecommend))
                .withString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_VIRTUAL_RECOMMEND_TAB)
                .withString(NativeAction.URL_BUILD_PERE_ACTION_TAG, RouteConstant.ACTION_TAG_VIRTUAL_RECOMMEND)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, jp);
    }

    /**
     * 主编推荐三级页
     */
    public static void goVirtualRecommendThreeLevelPage(Activity activity
            , String title, long editorId) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
                .withString(RouteConstant.FROM_TITLE, activity.getString(R.string.editor_recommond_title))
                .withString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_VIRTUAL_RECOMMEND_THREE_LEVEL)
                .withString(RouteConstant.LOCAL_STORE_IN_TITLE, title)
                .withLong(RouteConstant.ID, editorId)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, null);
    }

    public static void goVirtualRecommendThreeLevelPage(Activity activity
            , String title, long editorId,String fromTitle) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_TWO_LEVEL)
                .withString(RouteConstant.FROM_TITLE, fromTitle)
                .withString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_VIRTUAL_RECOMMEND_THREE_LEVEL)
                .withString(RouteConstant.LOCAL_STORE_IN_TITLE, title)
                .withLong(RouteConstant.ID, editorId)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, null);
    }

//    public static void gotoLocalImportActivity(Activity activity, int requestCode) {
//        Postcard postcard = ARouter.getInstance().build(RoutePath.READER_LOCAL_IMPORT)
//                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        if (requestCode < 0) {
//            postcard.navigation(activity);
//        } else {
//            postcard.navigation(activity,requestCode);
//        }
//    }



    public static void gotoCloudActivity(Activity activity, int requestCode) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_CLOUD_BOOK_LIST)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        if (requestCode < 0) {
            postcard.navigation(activity);
        } else {
            postcard.navigation(activity,requestCode);
        }
    }

    public static void backRootActivity(Activity activity) {
//        Intent intent;
//        intent = new Intent();
//        intent.setClass(activity, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        activity.startActivity(intent);
        Postcard postcard = ARouter.getInstance().build(RoutePath.MAIN_ACTIVITY)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, null);
    }

    public static void goToHelp(Activity context, String url){
        Postcard postcard = ARouter.getInstance().build(RoutePath.WEB_BROWER)
                .withString(RouteConstant.WEBCONTENT, url)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, context, null);
    }

    //跳转到隐私界面
    public static void goPrivacy(Activity context, String url){
        Postcard postcard = ARouter.getInstance().build(RoutePath.WEB_BROWER)
                .withString(RouteConstant.WEBCONTENT, url)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, context, null);
    }

    //跳转网页
    public static void goOnlinePage(Activity context, Bundle bundle){
        Postcard postcard = ARouter.getInstance().build(RoutePath.WEB_BROWER)
                .with(bundle);

        postcard.withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, context, null);
    }

    public static void goOnlinePage(Activity context, String url){
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(RouteConstant.WEBCONTENT, url);
        Postcard postcard = ARouter.getInstance().build(RoutePath.WEB_BROWER)
                .with(bundle);

        postcard.withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, context, null);
    }




//    /**
//     * 跳转到登录界面
//     * @param activity
//     * @param bundle 跳转来源
//     */
//    public static void jumpToThirdPartyLogin(Activity activity, Bundle bundle) {
//        Postcard postcard = ARouter.getInstance().build(RoutePath.LOGIN_CHOOSE);
//        postcard.with(bundle);
//        postcard.withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//
//        if (activity != null) {
//            JumpActivityParameter jumpActivityParameter = new JumpActivityParameter();
//            jumpActivityParameter.setRequestCode(Constant.NEW_USER_LOGIN_REQUESTCODE);
//            doRouterJump(postcard, activity, jumpActivityParameter);
//        }
//    }

    /**
     * 跳转到浏览历史
     * @param activity
     */
    public static void goOnlineHistory(Activity activity) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.PROFILE_HISTORY);
        postcard.withTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        if (activity != null) {
            doRouterJump(postcard, activity, null);
        }
    }

//    public static void jumpToThirdPartyLogin(Activity activity) {
//        jumpToThirdPartyLogin(activity, null);
//    }

    /**
     * 跳转频道独立页面
     * @param activity
     */
    public static void goChannelTwoPageActivity(Activity activity, Bundle bundle) {
        Postcard postcard  = ARouter.getInstance().build(RoutePath.CHANNEL_TWO_PAGE);
        postcard.with(bundle);
        if (activity != null) {
            doRouterJump(postcard, activity, null);
        }
    }

    /**
     * 跳转书籍列表独立页面
     * @param activity
     */
    public static void goCommonBookListPageActivity(Activity activity, Bundle bundle) {
        Postcard postcard  = ARouter.getInstance().build(RoutePath.COMMON_BOOK_LIST_TWO_PAGE);
        postcard.with(bundle);
        if (activity != null) {
            doRouterJump(postcard, activity, null);
        }
    }

    /**
     * 跳转书籍详情目录页
     */
    public static void goBookDetailChapterList(Activity activity, Mark mark) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_CHAPTER);
        postcard.withParcelable(RouteConstant.MARK, mark);
        if (activity != null) {
            doRouterJump(postcard, activity, null);
        }
    }



    /**
     * 跳转到插件列表页面
     * @param activity
     */
    public static void goPluginList(Activity activity) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.PLUGIN_LIST)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, null);
    }

    /**
     * 跳转到福利页面
     */
    public static void goWelfareActivity(Activity activity,Bundle bundle) {

        Postcard postcard = ARouter.getInstance().build(RoutePath.PROFILE_WELFARE)
                .with(bundle)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        postcard.navigation(activity);
    }


    /**
     * 跳转批量管理页面
     * @param activity
     */
    public static void goBookShelfBatchManager(Activity activity) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOKSHELF_BATCH_MANAGER)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, null);
    }

    public static void goAuthorMainPage(Activity activity, String authorId, String authorName, String iconUrl, final JumpActivityParameter jp) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.BOOK_STORE_AUTHOR_PAGE)
                .withString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_AUTHOR_MAIN)
                .withString(RouteConstant.AUTHORPAGE_KEY_AUTHORID, authorId)
                .withTransition(com.qq.reader.dispatch.R.anim.slide_in_right, com.qq.reader.dispatch.R.anim.slide_out_left);
        if (!TextUtils.isEmpty(authorName)) {
            postcard.withString(RouteConstant.AUTHORPAGE_KEY_AUTHOR_NAME, authorName);
        }
        if (!TextUtils.isEmpty(iconUrl)) {
            postcard.withString(RouteConstant.AUTHORPAGE_KEY_AVATAR_URL, iconUrl);
        }
        doRouterJump(postcard, activity , jp);
    }

    /**
     * 解析PUSH_ORIGIN字段
     * @param jp
     * @param postcard
     */
    private static void analyzePushOrigin(JumpActivityParameter jp, Postcard postcard) {
        //解析PUSH_ORIGIN字段
        if (jp != null) {
            if (jp.getJsonParamater() != null && jp.getJsonParamater() instanceof Map) {
                Map<String, String> map = (Map) jp.getJsonParamater();
                String pushOriginJson = map.get(IPushStatManagerService.KEY_STAT_PUSH_ORIGIN_JSON);
                if (!TextUtils.isEmpty(pushOriginJson)) {
                    postcard.withString(IPushStatManagerService.KEY_STAT_PUSH_ORIGIN_JSON, pushOriginJson);
                }
            }
        }
    }


    /**
     * 跳转到新版本榜单独立二级页  110版本添加
     */
    public static void goRank_Detail_old(Activity activity, String title
            , String mActionid, String mActiontag, final JumpActivityParameter jp){
        if (TextUtils.isEmpty(title)) {
            title = com.qq.reader.utils.Utility.getStringById(com.qq.reader.dispatch.R.string.rank_list);
        }
        //目前只有男1、女2，
        String sex = "1";
        if (!TextUtils.equals(mActiontag, "boy")) {
            sex = "2";
        }

        Bundle bundle = new Bundle();
        bundle.putString(JumpParameterConstants.CHANNEL_TAB_BUNDLE_ID, mActionid);
        bundle.putString(JumpParameterConstants.CHANNEL_TAB_BUNDLE_RANK_SEX, sex);
        bundle.putString(JumpParameterConstants.CHANNEL_TAB_BUNDLE_TITLE, title);
        bundle.putString(JumpParameterConstants.CHANNEL_TAB_BUNDLE_TYPE, String.valueOf(SecondLevelConstant.SECOND_LEVEL_RANK));  //独立榜单二级页
//        bundle.putString(ReaderCommonBookListActivity.CHANNEL_TAB_BUNDLE_EXTRA, extra);
        Postcard postcard  = ARouter.getInstance().build(RoutePath.COMMON_BOOK_LIST_TWO_PAGE);
        postcard.with(bundle)
                .withTransition(com.qq.reader.dispatch.R.anim.slide_in_right, com.qq.reader.dispatch.R.anim.slide_out_left);
        analyzePushOrigin(jp, postcard);
        if (activity != null) {
            doRouterJump(postcard, activity, jp);
        }
    }

    /**
     * 跳转到书架
     */
    public static void goBookshelf(Activity activity, Bundle bundle, final JumpActivityParameter jp) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.MAIN_ACTIVITY)
                .with(bundle)
                .withBoolean("fromjump", true)
                .withInt(RouteConstant.MAIN_TAB_TAG, 0)
                .withTransition(com.qq.reader.dispatch.R.anim.slide_in_right, com.qq.reader.dispatch.R.anim.slide_out_left);
        analyzePushOrigin(jp, postcard);
        doRouterJump(postcard, activity, jp);
    }

    public static void goBookshelf(Activity activity, final JumpActivityParameter jp) {
        goBookshelf(activity,null, jp);
    }

    /**
     * 跳转到精选
     */
    public static void goFeed(Activity activity, Bundle bundle, final JumpActivityParameter jp) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.MAIN_ACTIVITY)
                .with(bundle)
                .withBoolean("fromjump", true)
                .withInt(RouteConstant.MAIN_TAB_TAG, 1)
                .withTransition(com.qq.reader.dispatch.R.anim.slide_in_right, com.qq.reader.dispatch.R.anim.slide_out_left);
        if (jp != null && jp.getJsonParamater() != null && jp.getJsonParamater() instanceof Map) {
            Map<String, String> map = (Map) jp.getJsonParamater();
            String channelId = map.get(RouteConstant.RECOMEND_CHANNELID);
            if (!TextUtils.isEmpty(channelId)) {
                postcard.withString(RouteConstant.RECOMEND_CHANNELID, channelId);
            }
        }
        analyzePushOrigin(jp,postcard);
        doRouterJump(postcard, activity, jp);
    }

    public static void goFeed(Activity activity, final JumpActivityParameter jp) {
        goFeed(activity, null,jp);
    }

    /**
     * 跳转到排行榜二级页面
     * @param activity
     * @param title 标题
     * @param mActionid
     * @param mActiontag
     * @param jp
     */
    public static void goRank_Detail(Activity activity, String title
            , String mActionid, String mActiontag
            , final JumpActivityParameter jp) {
        goRank_Detail_old(activity, title, mActionid, mActiontag, jp);
    }

    /**
     * 跳转到网页
     *
     * @param context
     * @param url
     */
    public static void goWebBrowserForContents(Activity context, String url, Bundle bundle,JumpActivityParameter jp) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Postcard postcard = ARouter.getInstance().build(RoutePath.WEB_BROWER)
                .withTransition(com.qq.reader.dispatch.R.anim.slide_in_right, com.qq.reader.dispatch.R.anim.slide_out_left);
        postcard.with(bundle);
        //新手付费教育礼包参数
        if (url.contains("?coupons")) {
            String[] splitsubURL = url.split("\\?coupons");
            url = splitsubURL[0];
            try {
                String coupons = splitsubURL[1];
                String[] temp = coupons.split("\\?");
                postcard.withString(RouteConstant.NEW_USER_REWARD_NUM, temp[0])
                        .withString(RouteConstant.NEW_USER_REWARD_INTRO, temp[1])
                        .withBoolean(RouteConstant.GET_NEW_USER_REWARD_FROM_BOOKSHELF, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //107版本专题不加入浏览历史
//        if (url.contains("topicV2.html")) {
//            postcard.withBoolean(Constant.WEBCONTENT_NEED_RECORD_HISTORY, true);
//        }
        postcard.withString(RouteConstant.WEBCONTENT, url);
        analyzePushOrigin(jp, postcard);
        doRouterJump(postcard, context, jp);
    }

    public static void goWebBrowserForContents(Activity context, String url, JumpActivityParameter jp) {
        goWebBrowserForContents(context, url, null ,jp);
    }


    /**
     * 跳转到书籍详情
     */
    public static void goBookDetail(final Activity activity, String bid, OriginStatParam statParam, Bundle extraBundle, JumpActivityParameter jp) {
        if (activity == null) {
            return;
        }
        long id = 0;
        try {
            id = Long.valueOf(bid);
        } catch (Exception e) {
            Log.printErrStackTrace("JumpActivityUtil", e, null, null);
            // no need to handle
        }
        if (id <= 0) {
            return;
        }
        Postcard postcard = ARouter.getInstance().build(RoutePath.READER_BOOK_DETAIL)
                .with(extraBundle)
                .withLong(RouteConstant.BOOK_DETAIL_BID, id)
                .withString(NativeAction.KEY_JUMP_PAGENAME, RouteConstant.PAGE_NAME_DETAIL)
                .withTransition(com.qq.reader.dispatch.R.anim.slide_in_right, com.qq.reader.dispatch.R.anim.slide_out_left);
        if (statParam != null) {
            postcard.withParcelable(NativeAction.PARA_TYPE_STAT_PARAMS, statParam);
        }
        analyzePushOrigin(jp, postcard);
        doRouterJump(postcard, activity, jp);
    }

    public static void goReaderPage(final Activity activity, String bid, int cid, int offset, JumpActivityParameter jp) {
        AppRouterService.goReaderPage(activity,bid,cid,offset,jp);
    }

    public static void goFeedBack(Activity activity, final JumpActivityParameter jp) {
        AppRouterService.goFeedBack(activity,jp);
    }

    public static void goActivityArea(boolean FLAG_ACTIVITY_CLEAR_TOP, Activity activity
            , int tabIndex){
        AppRouterService.goActivityArea(FLAG_ACTIVITY_CLEAR_TOP,activity,tabIndex);
    }

    public static void goNewBookStore(Activity activity, final JumpActivityParameter jp){
        AppRouterService.goNewBookStore(activity, jp);
    }

    public static void goMyTicketConvert(Activity activity, int tabIndex, String title) {
        AppRouterService.goMyTicketConvert(activity, tabIndex, title);
    }

    public static void goH5Game(Activity context, String CmdParas, boolean urlIsEncode, JumpActivityParameter jp) {
        AppRouterService.goH5Game(context, CmdParas, urlIsEncode, jp);
    }

    public static void goLocalBookMatchActivity(Activity from, Bundle bundle) {
        AppRouterService.goLocalBookMatchActivity(from, bundle);
    }

    public static void gotoNetImportActivity(Activity activity) {
        AppRouterService.gotoNetImportActivity(activity);
    }

    public static void jumpToUserCenter(Activity activity) {
        //  华为用的、目前没发现问题
    }

    public static void goMessageActivity(boolean FLAG_ACTIVITY_CLEAR_TOP, Activity activity) {
        AppRouterService.goMessageActivity(FLAG_ACTIVITY_CLEAR_TOP, activity);
    }

    public static void goMain(Activity activity, final JumpActivityParameter jp) {
        AppRouterService.goMain(activity, jp);
    }

    public static void goMain(Activity activity, Intent intent, final JumpActivityParameter jp) {
        AppRouterService.goMain(activity, intent, jp);
    }

    public static void goProfileLevelActivity(Activity context, int vipLevel, final JumpActivityParameter jp) {
        AppRouterService.goProfileLevelActivity(context, vipLevel, jp);
    }

    public static void goCheckNet(Activity from, JumpActivityParameter pa) {
        AppRouterService.goCheckNet(from, pa);
    }

    public static void goHallOfFameDetail(IEventListener eventListener, String name) {
        AppRouterService.goHallOfFameDetail(eventListener, name);
    }

    public static void goAccountCoupon(boolean FLAG_ACTIVITY_CLEAR_TOP, Activity context, int tabIndex) {
        AppRouterService.goAccountCoupon(FLAG_ACTIVITY_CLEAR_TOP, context, tabIndex);
    }

    public static void goEndBookStore(Activity activity, int sex, final JumpActivityParameter jp) {
        AppRouterService.goEndBookStore(activity, sex, jp);
    }

    public static void goMonthBookStore(Activity activity, int sex, final JumpActivityParameter jp) {
        AppRouterService.goMonthBookStore(activity, sex, jp);
    }

    public static void goProfileLevelActivity(Activity context, int vipLevel, int autoReceive, final JumpActivityParameter jp) {
        AppRouterService.goProfileLevelActivity(context, vipLevel, autoReceive, jp);
    }

    public static Bundle getJumpMessageBundle(Context context) {
        return AppRouterService.getJumpMessageBundle(context);
    }

    public static boolean showGameView(Activity activity, String actionId) {
        return AppRouterService.showGameView(activity, actionId);
    }

    public static void goBackFinish(Activity activity) {
        AppRouterService.goBackFinish(activity);
    }

    /**
     * 跳转隐私设置页面
     * @param activity
     */
    public static void goProfilePrivacySetting(Activity activity) {
        Postcard postcard = ARouter.getInstance().build(RoutePath.PROFILE_PRIVACY_SETTING)
                .withTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        doRouterJump(postcard, activity, null);
    }
}

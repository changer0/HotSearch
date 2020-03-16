package com.qq.reader.service.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qq.reader.adv.AdvLogicCallBack;
import com.qq.reader.common.download.DownloadBookTask;
import com.qq.reader.common.login.ILoginNextTask;
import com.qq.reader.common.mark.Mark;
import com.qq.reader.dispatch.IEventListener;
import com.qq.reader.dispatch.RoutePath;
import com.qq.reader.entity.AdLinkBean;
import com.qq.reader.pay.BuyPackageListner;
import com.qq.reader.qurl.JumpActivityParameter;

import java.util.List;

public class AppRouterService {

    public static void openBook(Activity activity, Intent i) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation(activity);
        if (appRouterService != null) {
            appRouterService.openBook(activity, i);
        }
    }

    public static boolean isReadDay(Context context) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation(context);
        if (appRouterService != null) {
            return appRouterService.isReadDay(context);
        } else {
            return false;
        }
    }


    /*****************urlcenter部分下沉不下去*****************/

    public static void goFeedBack(Activity activity, final JumpActivityParameter jp){
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation(activity);
        if (appRouterService != null) {
            appRouterService.goFeedBack(activity,jp);
        }
    }
    public static void goReaderPage(final Activity activity, String bid, int cid, int offset, JumpActivityParameter jp){
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation(activity);
        if (appRouterService != null) {
            appRouterService.goReaderPage(activity,bid,cid,offset,jp);
        }
    }
    public static void goActivityArea(boolean FLAG_ACTIVITY_CLEAR_TOP, Activity activity, int tabIndex){
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation(activity);
        if (appRouterService != null) {
            appRouterService.goActivityArea(FLAG_ACTIVITY_CLEAR_TOP,activity,tabIndex);
        }
    }

    public static void goProfileLevelActivity(Activity context, int vipLevel, final JumpActivityParameter jp) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation(context);
        if (appRouterService != null) {
            appRouterService.goProfileLevelActivity(context,vipLevel,jp);
        }
    }

    /*****************urlcenter部分下沉不下去*****************/

    public static boolean downLoadBook(DownloadBookTask task, Activity activity, boolean isshowmsg) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation(activity);
        if (appRouterService != null) {
            return appRouterService.downLoadBook(task,activity,isshowmsg);
        } else {
            return false;
        }
    }

    public static ILoginNextTask openBuyPackageDialog(int packageid, Activity act, BuyPackageListner listner) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation(act);
        if (appRouterService != null) {
            return appRouterService.openBuyPackageDialog(packageid,act,listner);
        } else {
            return null;
        }
    }


    public static void showNotification(Context context, byte eventType, int count, DownloadBookTask dt) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation(context);
        if (appRouterService != null) {
            appRouterService.showNotification(context,eventType,count,dt);
        }
    }

//    public static void showNotification(Context context, byte eventType, Mark onlineTag, List<Integer> chapterList) {
//        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation(context);
//        if (appRouterService != null) {
//            appRouterService.showNotification(context, eventType, onlineTag, chapterList);
//        }
//    }

    public static void showNotification(Context context,
                                        byte eventType,
                                        Mark onlineTag,
                                        String formatCountTime,
                                        boolean isNetConnect,
                                        List<Integer> chapterList){
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation(context);
        if (appRouterService != null) {
            appRouterService.showNotification(context, eventType, onlineTag,formatCountTime,isNetConnect, chapterList);
        }
    }

    public static void copyInternalBooks(int sex) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.copyInternalBooks(sex);
        }
    }

    public static void goNewBookStore(Activity activity, final JumpActivityParameter jp) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.goNewBookStore(activity, jp);
        }
    }

    public static void goMyTicketConvert(Activity activity, int tabIndex, String title) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.goMyTicketConvert(activity, tabIndex, title);
        }
    }

    public static void goH5Game(Activity context, String CmdParas, boolean urlIsEncode, JumpActivityParameter jp) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.goH5Game(context, CmdParas, urlIsEncode, jp);
        }
    }

    public static void goLocalBookMatchActivity(Activity from, Bundle bundle) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.goLocalBookMatchActivity(from, bundle);
        }
    }

    public static void gotoNetImportActivity(Activity activity) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.gotoNetImportActivity(activity);
        }
    }

    public static void goMessageActivity(boolean FLAG_ACTIVITY_CLEAR_TOP, Activity activity) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.goMessageActivity(FLAG_ACTIVITY_CLEAR_TOP, activity);
        }
    }

    public static void goMain(Activity activity, final JumpActivityParameter jp) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.goMain(activity, jp);
        }
    }

    public static void goMain(Activity activity, Intent intent, final JumpActivityParameter jp) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.goMain(activity, intent, jp);
        }
    }

    public static void goCheckNet(Activity from, JumpActivityParameter pa) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.goCheckNet(from, pa);
        }
    }

    public static void goHallOfFameDetail(IEventListener eventListener, String name) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.goHallOfFameDetail(eventListener, name);
        }
    }

    public static void goAccountCoupon(boolean FLAG_ACTIVITY_CLEAR_TOP, Activity context, int tabIndex) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.goAccountCoupon(FLAG_ACTIVITY_CLEAR_TOP, context, tabIndex);
        }
    }

    public static void goEndBookStore(Activity activity, int sex, final JumpActivityParameter jp) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.goEndBookStore(activity, sex, jp);
        }
    }

    public static void goMonthBookStore(Activity activity, int sex, final JumpActivityParameter jp) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.goMonthBookStore(activity, sex, jp);
        }
    }

    public static void goProfileLevelActivity(Activity context, int vipLevel, int autoReceive, final JumpActivityParameter jp) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.goProfileLevelActivity(context, vipLevel, autoReceive, jp);
        }
    }

    public static Bundle getJumpMessageBundle(Context context) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation(context);
        if (appRouterService != null) {
            return appRouterService.getJumpMessageBundle();
        } else {
            return null;
        }
    }

    public static void checkUpgradeManual(Activity activity) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.checkUpgradeManual(activity);
        }
    }

    public static boolean showGameView(Activity activity, String actionId) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation(activity);
        if (appRouterService != null) {
            return appRouterService.showGameView(activity, actionId);
        } else {
            return false;
        }
    }

    public static void goBackFinish(Activity activity) {
        IAppRouterService appRouterService = (IAppRouterService) ARouter.getInstance().build(RoutePath.APP_MODULE_SERVICE).navigation();
        if (appRouterService != null) {
            appRouterService.goBackFinish(activity);
        }
    }
}

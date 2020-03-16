package com.qq.reader.service.share;

import android.app.Activity;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qq.reader.dispatch.RoutePath;

public class ShareRouterService {

    public static boolean onQQShareRespon(Activity context,int requestCode, int resultCode, Intent data) {
        IShareRouterService shareRouterService = (IShareRouterService) ARouter.getInstance().build(RoutePath.SHARE_MODULE_SERVICE).navigation(context);
        if (shareRouterService != null) {
            return shareRouterService.onQQShareRespon(requestCode,resultCode,data);
        }
        return false;
    }

    public static void showShareDialogForBook(Activity context, String bid, String bookName) {
        IShareRouterService shareRouterService = (IShareRouterService) ARouter.getInstance().build(RoutePath.SHARE_MODULE_SERVICE).navigation(context);
        if (shareRouterService != null) {
            shareRouterService.showShareDialogForBook(context, bid, bookName);
        }
    }

    public static void showShareDialogForPage(Activity context, String title, String pageUrl, String coverUrl, String summery) {
        IShareRouterService shareRouterService = (IShareRouterService) ARouter.getInstance().build(RoutePath.SHARE_MODULE_SERVICE).navigation(context);
        if (shareRouterService != null) {
            shareRouterService.showShareDialogForPage(context, title, pageUrl, coverUrl, summery);
        }
    }

    public static void showShareDialogForNote(Activity context, String bid, String title, String summery) {
        IShareRouterService shareRouterService = (IShareRouterService) ARouter.getInstance().build(RoutePath.SHARE_MODULE_SERVICE).navigation(context);
        if (shareRouterService != null) {
            shareRouterService.showShareDialogForNote(context, bid, title, summery);
        }
    }

    public static void shareBookForSys(Activity context, String bookName) {
        IShareRouterService shareRouterService = (IShareRouterService) ARouter.getInstance().build(RoutePath.SHARE_MODULE_SERVICE).navigation(context);
        if (shareRouterService != null) {
            shareRouterService.shareBookForSys(context, bookName);
        }
    }
}

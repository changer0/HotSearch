package com.qq.reader.service.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qq.reader.common.login.model.LoginUser;
import com.qq.reader.dispatch.RoutePath;

public class LoginRouterService {

    public static String getLoginUIN(Context context) {
        ILoginRouterService loginRouterService = (ILoginRouterService) ARouter
                .getInstance()
                .build(RoutePath.LOGIN_MOUDLE_SERVICE)
                .navigation(context);
        if (loginRouterService != null) {
            return loginRouterService.getLoginUIN();
        } else {
            return "";
        }
    }

    public static LoginUser getLoginUser(Context context) {
        ILoginRouterService loginRouterService = (ILoginRouterService) ARouter
                .getInstance()
                .build(RoutePath.LOGIN_MOUDLE_SERVICE)
                .navigation(context);
        if (loginRouterService != null) {
            return loginRouterService.getLoginUser();
        } else {
            return null;
        }
    }

    public static boolean isLogin(Context context) {
        ILoginRouterService loginRouterService = (ILoginRouterService) ARouter
                .getInstance()
                .build(RoutePath.LOGIN_MOUDLE_SERVICE)
                .navigation(context);
        if (loginRouterService != null) {
            return loginRouterService.isLogin();
        } else {
            return false;
        }
    }

    public static void startLogin(Activity act) {
        ILoginRouterService loginRouterService = (ILoginRouterService) ARouter
                .getInstance()
                .build(RoutePath.LOGIN_MOUDLE_SERVICE)
                .navigation(act);
        if (loginRouterService != null) {
            loginRouterService.startLogin(act);
        }
    }

    public static void startLogin(Activity act, Bundle bundle) {
        ILoginRouterService loginRouterService = (ILoginRouterService) ARouter
                .getInstance()
                .build(RoutePath.LOGIN_MOUDLE_SERVICE)
                .with(bundle)
                .navigation(act);
        if (loginRouterService != null) {
            loginRouterService.startLogin(act);
        }
    }

    public static void logout(Context context) {
        ILoginRouterService loginRouterService = (ILoginRouterService) ARouter
                .getInstance()
                .build(RoutePath.LOGIN_MOUDLE_SERVICE)
                .navigation(context);
        if (loginRouterService != null) {
            loginRouterService.logout();
        }
    }

    public static boolean tryLogin(Activity context, Boolean isShowUI) {
        ILoginRouterService loginRouterService = (ILoginRouterService) ARouter
                .getInstance()
                .build(RoutePath.LOGIN_MOUDLE_SERVICE)
                .navigation(context);
        if (loginRouterService != null) {
            return loginRouterService.tryLogin(context, isShowUI);
        } else {
            return false;
        }
    }

}

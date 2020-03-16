package com.qq.reader.service.login;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.qq.reader.common.login.model.LoginUser;

/**
 * 此类存在意义：未提供对外service,并且未从app中拆分出来
 */
public interface ILoginRouterService extends IProvider {

    String getLoginUIN();

    LoginUser getLoginUser();

    boolean isLogin();

    void startLogin(Activity act);

    void startLogin(Activity act, Bundle bundle);

    void logout();

    boolean tryLogin(Activity context, Boolean isShowUI);

}

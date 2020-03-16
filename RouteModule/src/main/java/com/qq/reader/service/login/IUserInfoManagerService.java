package com.qq.reader.service.login;

import android.app.Activity;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.qq.reader.core.utils.WeakReferenceHandler;
import com.qq.reader.module.login.UserInfoBean;
import com.qq.reader.ARouterUtils;
import com.tencent.mars.xlog.Log;


/**
 * @author zhanglulu on 2020-02-11.
 * for
 */
public abstract class IUserInfoManagerService implements IProvider {


    private static final String TAG = "IUserInfoManagerService";
    private static IUserInfoManagerService instance;

    public static IUserInfoManagerService getInstance() {
        if (instance == null) {
            synchronized (IUserInfoManagerService.class) {
                if (instance == null) {
                    instance = ARouterUtils.getUserInfoManagerService();
                }
            }
        }
        if (instance == null) {
            Log.e(TAG, "getInstance: IUserInfoManagerService 获取为空");
        }
        return instance;
    }

    public abstract void addGetUserInfoListener(UserInfoListener listener);

    public abstract void removeGetUserInfoListener(UserInfoListener listener);

    @Nullable
    public abstract UserInfoBean getLocalUserInfo();

    public abstract void getNetUserInfo(boolean loginFirst);

    public abstract void handleBaseActivityOnGetUserInfo(Activity activity, WeakReferenceHandler handler, boolean loginFirst);

    public interface UserInfoListener{
        void onGetUserInfo(UserInfoBean userInfoBean, boolean loginFirst);
    }

}
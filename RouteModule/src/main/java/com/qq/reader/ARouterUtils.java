package com.qq.reader;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qq.reader.dispatch.RoutePath;
import com.qq.reader.service.ILoginInfoService;
import com.qq.reader.service.IPropsService;
import com.qq.reader.service.audio.IAudioPlayManager;
import com.qq.reader.service.login.IUserInfoManagerService;
import com.qq.reader.service.push.IPushStatManagerService;

/**
 *
 */
public class ARouterUtils {

    @Nullable
    public static Object getService(String path){
        return ARouter.getInstance().build(path).navigation();
    }

    public static Object goNext(Activity activity,String path){
        return ARouter.getInstance().build(path).navigation(activity);
    }

    public static Object goNext(Activity activity,String path, Bundle b){
        return ARouter.getInstance().build(path).with(b).navigation(activity);
    }

    public static void goNextForResult(Activity activity,int requestCode,String path){
        ARouter.getInstance().build(path).navigation(activity,requestCode);
    }

    public static void goNextForResult(Activity activity,int requestCode,String path, Bundle b){
        ARouter.getInstance().build(path).with(b).navigation(activity,requestCode);
    }

    /**
     * 道具Manager
     * @return
     */
    public static IPropsService getPropsManagerService(){
        return (IPropsService)getService(RoutePath.PROPS_MANAGER);
    }

    /**
     * 获取登录信息
     * @return
     */
    public static ILoginInfoService getLoginInfoService(){
        return (ILoginInfoService)getService(RoutePath.LOGIN_INFO_SERVICE);
    }

    /**
     * 获取 UserInfoManagerService
     * @return
     */
    public static IUserInfoManagerService getUserInfoManagerService() {
        return (IUserInfoManagerService)getService(RoutePath.USER_INFO_MANAGER_SERVICE);
    }

    /**
     * 获取 PushStatManagerService
     * @return
     */
    public static IPushStatManagerService getPushStatManagerService() {
        return (IPushStatManagerService)getService(RoutePath.PUSH_STAT_MANAGER);
    }

    /**
     * 听书播放服务
     * @return
     */
    public static IAudioPlayManager getAudioPlayService(){
        return (IAudioPlayManager) getService (RoutePath.AUDIO_BOOK_PLAYER_SERVICE);
    }
}

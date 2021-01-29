package com.yuewen.component.router;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yuewen.component.router.builder.YWRouterParamBuilder;
import com.yuewen.component.router.builder.YWRouterParamSimpleBuilder;

/**
 * Created by ronaldo on 2019/4/12.
 * 路由管理类
 */

public class YWRouter {
    private static final String TAG = "ComponentRouterManager";

    private static Application mApplication;


//        return Holder.instance;
//    }

    /**
     * 初始化，并保存application
     * @param application
     * @param isDebug 开发模式下，需要打开此开关，否则新增的 Api 会无法生效
     */
    public static void init(Application application , boolean isDebug) {
        if(isDebug) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();
        }
        ARouter.init(application);
        mApplication = application;

//        LiveEventBus.get()
//                .config()
//                .supportBroadcast(application)
//                .lifecycleObserverAlwaysActive(true)
//                .autoClear(false);
    }

    public static Application getApplication() {
        return mApplication;
    }

    /**
     * 通过builder构建跳转参数，提供统一跳转接口
     * @param builder
     */
    public static void navigation(YWRouterParamBuilder.Builder builder) {
        if (builder != null) {
            navigation(builder.getPath(), builder.getUri(), builder.getBundle(), builder.getFlags(), builder.getAction(), builder.getTag(),
                    builder.getTimeout(), builder.getIsGreenChannel(), builder.getEnterAnim(), builder.getExitAnim(), builder.getContext(),
                    builder.getRequestCode(), builder.getNavigationCallback());
        }
    }

    /**
     * 通过intent传递参数，action和flag，提供统一跳转接口
     * ComponetRouterParamSimpleBuilder不包含bundle，action和flag信息
     * @param intent
     * @param builder
     */
    public static void navigation(Intent intent, YWRouterParamSimpleBuilder.Builder builder) {
        if (builder != null && intent != null) {
            navigation(builder.getPath(), builder.getUri(), intent.getExtras(), intent.getFlags(), intent.getAction(), builder.getTag(),
                    builder.getTimeout(), builder.getIsGreenChannel(), builder.getEnterAnim(), builder.getExitAnim(), builder.getContext(),
                    builder.getRequestCode(), builder.getNavigationCallback());
        }
    }

    private static void navigation(String path, Uri uri, Bundle bundle, int flags, String action, Object tag, int timeOut, boolean isGreenChannel,
                            int enterAnim, int exitAnim, Context context, int requestCode, NavigationCallback navigationCallback) {
        Postcard postcard;
        if (!TextUtils.isEmpty(path)) {
            postcard = ARouter.getInstance().build(path);
        } else if (uri != null) {
            postcard = ARouter.getInstance().build(uri);
        } else {
            Log.e("ronaldo", "path和uri不能同时为空");
            return;
        }

        if (bundle != null) {
            postcard.with(bundle);
        }

        if (flags != 0) {
            if ((!(context instanceof Activity)) && ((flags & Intent.FLAG_ACTIVITY_NEW_TASK) == 0)){
                flags = flags | Intent.FLAG_ACTIVITY_NEW_TASK;
            }
            postcard.withFlags(flags);
        }

        if (!TextUtils.isEmpty(action)) {
            postcard.withAction(action);
        }

        if (tag != null) {
            postcard.setTag(tag);
        }

        if (timeOut > 0) {
            postcard.setTimeout(timeOut);
        }

        if (isGreenChannel) {
            postcard.greenChannel();
        }

        if (enterAnim != -1 || exitAnim != -1) {
            postcard.withTransition(enterAnim, exitAnim);
        }

        if (context != null) {
            if (context instanceof Activity) {
                Activity activity = (Activity)context;
                postcard.navigation(activity, requestCode, navigationCallback);
            } else {
                postcard.navigation(context, navigationCallback);
            }
        } else {
            postcard.navigation();
        }

    }

    /**
     * 根据Class获取服务接口
     * @param clazz  服务接口Class
     * @param <T>
     * @return
     */
    public static <T extends IProvider> T navigation(Class<? extends T> clazz) {
        T service = ARouter.getInstance().navigation(clazz);
        return service;
    }

    /**
     * 根据路径获取服务接口
     * @param path  路由路径
     * @param clazz 服务接口Class
     * @param <T>
     * @return
     */
    public static <T extends IProvider> T navigation(String path, Class<? extends T> clazz) {
        Object object = ARouter.getInstance().build(path).navigation();
        return clazz.cast(object);
    }

//    /**
//     * 注册静态消息监听
//     * @param eventName
//     * @param clazz
//     * @param observer
//     * @param <T>
//     */
//    public <T> void registerStaticEvent(String eventName, Class<T> clazz, Observer<T> observer) {
//        LiveEventBus.get().with(eventName, clazz).observeForever(observer);
//    }
//
//    /**
//     * 反注册静态消息监听
//     * @param eventName
//     * @param clazz
//     * @param observer
//     * @param <T>
//     */
//    public <T> void unregisterStaticEvent(String eventName, Class<T> clazz, Observer<T> observer) {
//        LiveEventBus.get().with(eventName, clazz).removeObserver(observer);
//    }
//
//    /**
//     * 注册动态消息监听，不需要反注册
//     * @param eventName
//     * @param clazz
//     * @param lifecycleOwner
//     * @param observer
//     * @param <T>
//     */
//    public <T> void registerDynamicEvent(String eventName, Class<T> clazz, LifecycleOwner lifecycleOwner, Observer<T> observer) {
//        LiveEventBus.get().with(eventName, clazz).observe(lifecycleOwner, observer);
//    }

//    /**
//     * 实时发送消息
//     * @param eventName
//     * @param clazz
//     * @param object
//     * @param <T>
//     */
//    public <T> void postEvent(String eventName, Class<T> clazz, T object) {
//        postDelayEvent(eventName, clazz, object, 0);
//    }

//    /**
//     * 延迟发送消息
//     * @param eventName
//     * @param clazz
//     * @param object
//     * @param delayTime
//     * @param <T>
//     */
//    public <T> void postDelayEvent(String eventName, Class<T> clazz, T object, long delayTime) {
//        LiveEventBus.get().with(eventName, clazz).postDelay(object, delayTime);
//    }

}

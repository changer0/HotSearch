package com.qq.reader.core.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.qq.reader.baseui.R;
import com.qq.reader.core.BaseApplication;

/**
 * Created by qiaoshouqing on 2018/11/2.
 */

public class NotificationCoreUtil {

    public static String NOTIFICATION_CHANNEL_ID = "0";
    public static String NOTIFICATION_LOW_IMPORTANCE_CHANNEL_ID;
    public static final String NOTIFICATION_CHANNEL_NAME = BaseApplication.Companion.getINSTANCE().getString(R.string.app_name);

    static {
        NOTIFICATION_LOW_IMPORTANCE_CHANNEL_ID = BaseApplication.Companion.getINSTANCE().getPackageName() + "book";
    }
    /**
     * 通用的Notification Builder.
     * 为了兼容不同版本的notification，生成的时候必须使用Notification.Builder.build()方法
     * @param context
     * @return
     */
    public static Notification.Builder makeCommonNotificationBuilder(Context context, NotificationManager nm){

        //只在Android O及其之上需要设置通知渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，
            //通知才能正常弹出
            nm.createNotificationChannel(notificationChannel);
        }

        Notification.Builder builder= null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            builder = new Notification.Builder(context, NOTIFICATION_CHANNEL_ID);
        } else {
            builder = new Notification.Builder(context);
        }

        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.icon_notify_small);
        builder.setContentTitle(context.getResources().getString(R.string.app_name));
        return builder;
    }

    /**
     * 构建低优先级的notification builder，没有声音
     * @return
     */
    public static Notification.Builder buildLowImportanceNotificationBuilder(Context context, NotificationManager nm) {
        //只在Android O及其之上需要设置通知渠道
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_LOW_IMPORTANCE_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，
            //通知才能正常弹出
            notificationChannel.setSound(null, null);
            nm.createNotificationChannel(notificationChannel);

            builder = new Notification.Builder(context, NOTIFICATION_LOW_IMPORTANCE_CHANNEL_ID);
            builder.setSound(null);
        } else {
            builder = new Notification.Builder(context);
        }

        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(BaseApplication.Companion.getINSTANCE().getResources(), R.drawable.icon_notify_large));
        builder.setSmallIcon(R.drawable.icon_notify_small);
        builder.setContentTitle(context.getResources().getString(R.string.app_name));
        return builder;
    }


}

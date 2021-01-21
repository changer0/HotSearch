package com.yuewen.component.router.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.yuewen.component.router.YWRouter;
import com.yuewen.component.router.builder.YWRouterParamSimpleBuilder;
import com.yuewen.component.router.constant.YWRouterConstant;

/**
 * 路由广播，目前用于notification的界面跳转，需要外部intent传界面path
 * 点击notification首先中转到ComponentRouterReceiver，由ComponentRouterReceiver进行界面的分发
 *
 *
 * 举例使用
 * NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
 * Intent noticeIntent = new Intent();
 * builder.setContentTitle("this is title");
 * builder.setContentText("this is content");
 * builder.setSmallIcon(R.mipmap.ic_launcher);
 * noticeIntent.setAction(ComponentRouterConstant.PARAM_ROUTER_ACTION)
 * noticeIntent.putExtra(ComponentRouterConstant.PARAM_ROUTER_PATH, AppRoutePathConstant.PATH_MAINACTIVITY);
 * noticeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 * PendingIntent contentIntent = PendingIntent.getBroadcast(getApplicationContext(),requestCode, noticeIntent,flags);
 * builder.setContentIntent(contentIntent);
 */


public class YWRouterReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String path = bundle.getString(YWRouterConstant.PARAM_ROUTER_PATH);
                if (!TextUtils.isEmpty(path)) {
                    YWRouterParamSimpleBuilder.Builder builder = new YWRouterParamSimpleBuilder.Builder();
                    builder.setPath(path);
                    YWRouter.navigation(intent, builder);
                }
            }
        }
    }
}

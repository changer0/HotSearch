package com.qq.reader.delegate;//
//package com.qq.reader.delegate;
//
//import android.app.Activity;
//import android.os.Build;
//import android.view.View;
//import android.view.WindowManager;
//
//import com.color.support.view.ColorStatusbarTintUtil;
//
//public class SystemUiDelegate {
//
//    public static void setStatusBarTint(Activity activity, int statusType) {
//        if (Build.VERSION.SDK_INT <= 19) {
//            return;
//        }
//        if (statusType == ActivityConfig.STATUS_IMMERSE) {
//            View decorView = activity.getWindow().getDecorView();
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility()
//                    | ColorStatusbarTintUtil.SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT);
//            activity.getWindow().setStatusBarColor(0xffffffff);
//        } else if (statusType == ActivityConfig.STATUS_TRANSLUCENT) {
//            // unsupported
//        }
//    }
//}

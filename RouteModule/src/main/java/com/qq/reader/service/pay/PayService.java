package com.qq.reader.service.pay;

import android.app.Activity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qq.reader.dispatch.RoutePath;

/**
 * @author jlc
 * @date 2019/5/15
 */

////todo jlchang 这个类要优化掉
public class PayService {

    public static void initialize(Activity activity) {
        IPayService payService = (IPayService) ARouter.getInstance().build(RoutePath.PAY_SERVICE).navigation(activity);
        if (payService != null) {
            payService.initialize(activity);
        }
    }

    public static void charge(Activity activity, String jsonInfo, int value) {
        IPayService payService = (IPayService) ARouter.getInstance().build(RoutePath.PAY_SERVICE).navigation(activity);
        if (payService != null) {
            payService.charge(jsonInfo, value);
        }
    }

    public static void openVip(Activity activity) {
        IPayService payService = (IPayService) ARouter.getInstance().build(RoutePath.PAY_SERVICE).navigation(activity);
        if (payService != null) {
            payService.openVip();
        }
    }
}

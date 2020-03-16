package com.qq.reader.adv;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qq.reader.dispatch.RoutePath;
import com.tencent.mars.xlog.Log;

/**
 * Author: liujian.a
 * Date: 2019/8/28 10:32
 * Description: ArouterUtil service单例管理类
 */
public class IAdvServiceManager {

    private static volatile IAdvService instance;

    private IAdvServiceManager() {
    }

    public static IAdvService getIAdvService() {
        if (instance == null) {
            synchronized (IAdvServiceManager.class) {
                if (instance == null) {
                    instance = (IAdvService) ARouter.getInstance().build(RoutePath.ADV_MANAGER).navigation();
                }
            }
        }
        if (instance == null) {
            Log.e("IAdvServiceManager", "IAdvService为空");
        }
        return instance;
    }
}

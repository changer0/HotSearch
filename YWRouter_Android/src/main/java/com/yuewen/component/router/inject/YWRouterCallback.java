package com.yuewen.component.router.inject;

import com.yuewen.component.router.bean.YWRouterResult;
import com.yuewen.component.router.exception.YWRouterException;

/**
 * Created by ronaldo on 2019/4/12.
 */

public interface YWRouterCallback {
    /**
     * 成功回调
     * @param YWRouterResult
     */
    public void onSuccess(YWRouterResult YWRouterResult);

    /**
     * 失败回调
     * @param YWRouterException
     */
    public void onFail(YWRouterException YWRouterException);
}

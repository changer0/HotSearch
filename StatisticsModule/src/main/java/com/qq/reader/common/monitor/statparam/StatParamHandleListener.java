package com.qq.reader.common.monitor.statparam;

import androidx.annotation.Nullable;

/**
 * @author zhanglulu on 2019/9/20.
 * for 上报参数持久化 回调
 */
public interface StatParamHandleListener<T> {
    void onResult(@Nullable  T result);
}

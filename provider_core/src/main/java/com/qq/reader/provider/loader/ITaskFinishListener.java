package com.qq.reader.provider.loader;

import com.qq.reader.provider.DataProvider;

/**
 * @author zhanglulu on 2020/11/20.
 * for
 */
interface ITaskFinishListener<R, P> {
    void onSuccess(DataProvider<R, P> provider);
    void onFailure(Throwable throwable);
}

package com.qq.reader.zebra.loader;

import com.qq.reader.zebra.Zebra;

/**
 * @author zhanglulu on 2020/11/20.
 * for
 */
interface ITaskFinishListener<T> {
    void onSuccess(Zebra<T> provider);
    void onFailure(Throwable throwable);
}

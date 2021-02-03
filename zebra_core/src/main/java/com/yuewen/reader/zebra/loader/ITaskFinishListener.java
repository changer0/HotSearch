package com.yuewen.reader.zebra.loader;

import com.yuewen.reader.zebra.Zebra;

/**
 * @author zhanglulu on 2020/11/20.
 * for
 */
interface ITaskFinishListener<T> {
    void onSuccess(Zebra<T> provider);
    void onFailure(Throwable throwable);
}

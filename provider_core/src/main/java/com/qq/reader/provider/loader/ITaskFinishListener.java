package com.qq.reader.provider.loader;

import com.qq.reader.provider.DataProvider;
import com.qq.reader.provider.R;

/**
 * @author zhanglulu on 2020/11/20.
 * for
 */
interface ITaskFinishListener<T> {
    void onSuccess(DataProvider<T> provider);
    void onFailure(Throwable throwable);
}

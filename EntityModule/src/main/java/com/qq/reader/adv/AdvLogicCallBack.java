package com.qq.reader.adv;

/**
 * Created by hexiaole on 2019/7/3.
 */
public interface AdvLogicCallBack<T>{
    void onAdLogicSuccess(T data);

    void onAdLogicError(int code, Exception e);
}

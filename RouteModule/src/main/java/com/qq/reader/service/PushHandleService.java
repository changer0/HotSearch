package com.qq.reader.service;

import android.app.Activity;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * File description.
 *
 * @author p_bbinliu
 * @date 2019/1/10
 */
public interface PushHandleService extends IProvider {

    //初始化push服务
    void register();

    //上报pushToken
    void uploadToken();

    //重置相关标志位
    void reset();
}

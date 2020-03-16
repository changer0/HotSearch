package com.qq.reader.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Created by liubin on 2018/4/13.
 */

public interface PushService extends IProvider {

    void doAction(String message, int pushType, Context context);
}

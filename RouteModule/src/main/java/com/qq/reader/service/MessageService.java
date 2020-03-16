package com.qq.reader.service;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * File description.
 *
 * @author liubin
 * @date 2018/5/8
 */

public interface MessageService extends IProvider {

    void clear(int type);
}

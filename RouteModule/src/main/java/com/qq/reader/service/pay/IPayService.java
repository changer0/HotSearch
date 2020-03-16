package com.qq.reader.service.pay;


import android.app.Activity;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author jlc
 * @date 2019/5/14
 */

public interface IPayService extends IProvider {

    void initialize(Activity activity);

    void charge(final String jsonInfo, final int value);

    void openVip();
}

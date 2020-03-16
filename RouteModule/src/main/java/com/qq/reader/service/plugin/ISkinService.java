package com.qq.reader.service.plugin;


import android.app.Activity;
import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author jlchang
 * @date 2019/10/27
 *
 * skin 相关接口调用
 */

public interface ISkinService extends IProvider {
    boolean isExistSkin(String id);
    void querySkinEnableByNet(Context context);
    void registerSkinActivity(Activity targetActivity);
    void release(Activity targetActivity);
}

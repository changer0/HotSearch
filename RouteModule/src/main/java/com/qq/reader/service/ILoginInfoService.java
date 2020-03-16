package com.qq.reader.service;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Created by zhanglulu on 2019/4/26.
 * for 用于给其他Module 提供登录信息的Service
 */
public interface ILoginInfoService extends IProvider {
    //与 LoginConstant 强绑定
    int NOT_LOGIN = -1;//未登录
    int LOGIN_QQ = 1;//QQ登录
    int LOGIN_WX = 2;//WX登录
    int LOGIN_REFRESHTOKEN=3;//刷新微信token
    int LOGIN_OTHER = 10;//暂定,待后台确认.以后任何的第三方登录接入,都用这个值
    int LOGIN_QIDIAN = 50;//起点登录

    /**
     * 获取登录类型
     * @return
     */
    int getLoginType();
}

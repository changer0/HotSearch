package com.example.providermoduledemo

import com.qq.reader.appconfig.HeadInterceptor
import com.qq.reader.common.login.LoginManager.Companion.initLogin
import com.qq.reader.core.BaseApplication
import com.qq.reader.core.http.HeadInterceptorManager

/**
 * Created by zhanglulu on 2020/3/16.
 * for
 */
class MyApp: BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        FlavorConfig.init()
        initLogin()
        //添加统一header到拦截器管理类中，此初始化要在多进程之前，保证多进程正常能通过拦截器获取登录态
        HeadInterceptorManager.getInstance().add(HeadInterceptor())

    }
}
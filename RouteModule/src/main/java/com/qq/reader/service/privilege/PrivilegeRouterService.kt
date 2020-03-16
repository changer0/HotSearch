package com.qq.reader.service.privilege

import android.app.Activity
import com.alibaba.android.arouter.launcher.ARouter
import com.qq.reader.adv.Advertisement
import com.qq.reader.core.utils.WeakReferenceHandler
import com.qq.reader.dispatch.RoutePath

class PrivilegeRouterService {

    companion object {

        fun initPrivilegeManger(activity: Activity, handler: WeakReferenceHandler){
            val privilegeRouterService = ARouter.getInstance().build(RoutePath.PRIVILEGE_MODULE_SERVICE).navigation(activity) as IPrivilegeRouterService
            privilegeRouterService?.initPrivilegeManger(activity, handler)
        }

        fun loadATMAdv(context: Activity, needToast: Boolean) {
            val privilegeRouterService = ARouter.getInstance().build(RoutePath.PRIVILEGE_MODULE_SERVICE).navigation(context) as IPrivilegeRouterService
            privilegeRouterService?.loadATMAdv(needToast)
        }

        fun showChannelAdv(context: Activity, onlyNewUser: Boolean, pageName: String) {
            val privilegeRouterService = ARouter.getInstance().build(RoutePath.PRIVILEGE_MODULE_SERVICE).navigation(context) as IPrivilegeRouterService
            privilegeRouterService?.showChannelAdv(onlyNewUser, pageName)
        }

        fun showAdvDialog(context: Activity, adv: Advertisement) {
            val privilegeRouterService = ARouter.getInstance().build(RoutePath.PRIVILEGE_MODULE_SERVICE).navigation(context) as IPrivilegeRouterService
            privilegeRouterService?.showAdvDialog(adv)
        }

        fun release(context: Activity){
            val privilegeRouterService = ARouter.getInstance().build(RoutePath.PRIVILEGE_MODULE_SERVICE).navigation(context) as IPrivilegeRouterService
            privilegeRouterService?.release()
        }
    }
}
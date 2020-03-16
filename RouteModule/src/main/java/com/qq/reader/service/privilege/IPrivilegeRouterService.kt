package com.qq.reader.service.privilege

import android.app.Activity
import com.alibaba.android.arouter.facade.template.IProvider
import com.qq.reader.adv.Advertisement
import com.qq.reader.core.utils.WeakReferenceHandler

interface IPrivilegeRouterService : IProvider{
    fun initPrivilegeManger(activity: Activity, handler:WeakReferenceHandler)
    fun loadATMAdv(needToast: Boolean)
    fun showChannelAdv(onlyNewUser: Boolean, pageName: String)
    fun showAdvDialog(adv: Advertisement)
    fun release()
}
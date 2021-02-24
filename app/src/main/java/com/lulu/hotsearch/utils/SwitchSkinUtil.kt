package com.lulu.hotsearch.utils

import androidx.lifecycle.LifecycleOwner
import com.lulu.basic.net.CoroutineScopeManager
import com.lulu.basic.net.HttpCoroutineUtils
import com.lulu.hotsearch.bean.SkinPackageBean
import com.lulu.hotsearch.define.ServerUrl
import com.yuewen.reader.zebra.utils.GSONUtil
import kotlinx.coroutines.launch

object SwitchSkinUtil {
    const val SWITCH_SKIN_URL = ServerUrl.CONFIG_DOMAIN + "skin_config.json"
    @JvmStatic
    fun requestSkinConfig(owner: LifecycleOwner, finished: (beanList: List<SkinPackageBean>?) -> Unit) {
        CoroutineScopeManager.getScope(owner).launch {
            val entity = HttpCoroutineUtils.doRequestGet(SWITCH_SKIN_URL)
            try {
                if (entity.isSuccess) {
                    val beanList =
                            GSONUtil.parseJsonToList(entity.jsonStr, SkinPackageBean::class.java)
                    finished.invoke(beanList)
                } else {
                    finished.invoke(null)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                finished.invoke(null)
            }
        }
    }
}
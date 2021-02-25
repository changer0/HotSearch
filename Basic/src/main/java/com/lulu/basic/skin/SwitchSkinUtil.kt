package com.lulu.basic.skin

import android.text.TextUtils
import androidx.lifecycle.LifecycleOwner
import com.lulu.basic.net.CoroutineScopeManager
import com.lulu.basic.net.HttpCoroutineUtils
import com.lulu.basic.db.DBManager
import com.lulu.basic.define.ServerUrl
import com.yuewen.reader.zebra.utils.GSONUtil
import kotlinx.coroutines.*

object SwitchSkinUtil {
    private val SWITCH_SKIN_URL = ServerUrl.CONFIG_DOMAIN + "skin_config.json"
    @JvmStatic
    fun requestSkinConfig(owner: LifecycleOwner, requestListener: RequestListener?) {
        CoroutineScopeManager.getScope(owner).launch {

            val netQ = async { requestNetData() }
            val localQ = async { requestLocalData() }

            val localData = localQ.await()
            val netData = netQ.await()

            localData?.apply {
                //本地数据有
                netData?.apply{
                    mergeLocalNetData(
                        netData,
                        localData
                    )
                    requestListener?.onSuccess(netData)
                }?: apply{
                    requestListener?.onSuccess(localData)
                }

            }?:apply{
                //本地数据无,直接返回网络数据
                netData?.apply {
                    //保存数据
                    saveLocalData(netData)
                    requestListener?.onSuccess(netData)
                }?:apply {
                    requestListener?.onFailure(java.lang.Exception("网络数据出错"))
                }
            }


        }
    }

    private suspend fun requestNetData(): List<SkinPackageBean>? {
        val entity = HttpCoroutineUtils.doRequestGet(SWITCH_SKIN_URL)
        try {
            if (entity.isSuccess) {
                return GSONUtil.parseJsonToList(entity.jsonStr, SkinPackageBean::class.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private suspend fun requestLocalData(): List<SkinPackageBean>? = withContext(Dispatchers.IO) {
        DBManager.get().skinPackageDao().getAllSuspend()
    }

    private suspend fun saveLocalData(netList: List<SkinPackageBean>) = withContext(Dispatchers.IO) {
        DBManager.get().skinPackageDao().insertSkinPackageListSuspend(netList)
    }

    private suspend fun mergeLocalNetData(netList: List<SkinPackageBean>, localList: List<SkinPackageBean>) = withContext(Dispatchers.IO) {

        for (netBean in netList) {
            val localBean = findSkinPackById(
                netBean.id,
                localList
            )
            localBean?.apply {
                if (netBean.version > localBean.version) {
                    netBean.isUpdate = true//需要升级
                }
            }
        }
        //保存本地数据
        DBManager.get().skinPackageDao().insertSkinPackageListSuspend(netList)
    }

    private fun findSkinPackById(id: String, list: List<SkinPackageBean>): SkinPackageBean? {
        for (skinPackageBean in list) {
            if (TextUtils.equals(id, skinPackageBean.id)) {
                return skinPackageBean
            }
        }
        return null
    }

    fun updateSkinPackageBean(skinPackageBean: SkinPackageBean) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                DBManager.get().skinPackageDao().insertSkinPackage(skinPackageBean)
            }
        }
    }


    interface RequestListener {
        fun onSuccess(beanList: List<SkinPackageBean>)
        fun onFailure(e: Throwable)
    }
}
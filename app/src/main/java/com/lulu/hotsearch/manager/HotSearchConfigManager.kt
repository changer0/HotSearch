package com.lulu.hotsearch.manager

import android.text.TextUtils
import android.util.Log
import com.lulu.baseutil.FileUtil
import com.lulu.baseutil.Init
import com.lulu.basic.utils.AssetsUtil
import com.lulu.hotsearch.HotSearchKVStorage
import com.lulu.hotsearch.bean.HotSearchConfigBean
import com.lulu.hotsearch.define.Constant
import com.yuewen.reader.zebra.utils.GSONUtil
import java.io.File

/**
 * 热搜配置管理类
 */
object HotSearchConfigManager {
    private const val TAG = "HotSearchConfigManager"
    private val CONFIG_INFO_PATH = Init.ROOT_PATH + "hot_search_config.json"
    private const val LOCAL_CONFIG_PATH = "local_config_info.json"
    private var hotSearchList = mutableListOf<HotSearchConfigBean.HotSearchListBean>()
    private var configInfo = HotSearchConfigBean()

    fun saveConfigInfo(jsonStr: String) {
        try {
            val configInfo = GSONUtil.parseJsonWithGSON<HotSearchConfigBean>(jsonStr, HotSearchConfigBean::class.java)
            //解析失败、为空都忽略数据
            if (configInfo == null || configInfo.hotSearchList.isEmpty()) {
                return
            }
            val isSuccess = FileUtil.writeFile(File(CONFIG_INFO_PATH), jsonStr, false)
            Log.d(TAG, "saveFilterRule: isSuccess: $isSuccess")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 检查是否有数据
     */
    fun hasData(): Boolean {
        if (hotSearchList.isNotEmpty()) {
            return true
        }
        return File(CONFIG_INFO_PATH).exists()
    }

    fun getConfigList(): List<HotSearchConfigBean.HotSearchListBean> {
        if (hotSearchList.isNotEmpty()) {
            return hotSearchList
        }
        val jsonStr = FileUtil.readFile(File(CONFIG_INFO_PATH))
        try {
            if (!TextUtils.isEmpty(jsonStr)) {
                configInfo = GSONUtil.parseJsonWithGSON<HotSearchConfigBean>(jsonStr, HotSearchConfigBean::class.java)
                hotSearchList.clear()
                hotSearchList.addAll(configInfo.hotSearchList)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (hotSearchList.isEmpty()) {
            configInfo = GSONUtil.parseJsonWithGSON<HotSearchConfigBean>(
                AssetsUtil.getAssetsFileToString(LOCAL_CONFIG_PATH), HotSearchConfigBean::class.java)
            hotSearchList.addAll(configInfo.hotSearchList)
        }
        hotSearchList.reverse()
        return hotSearchList
    }

    @JvmStatic
    public fun saveCurType(type: String) {
        HotSearchKVStorage.setLastType(type)
    }

    @JvmStatic
    public fun getCurType(): String{
        return getCurConfigBean().type
    }

    @JvmStatic
    public fun getCurConfigBean(): HotSearchConfigBean.HotSearchListBean {
        var curBean = checkHasConfigBeanByType(HotSearchKVStorage.getLastType())
        if (curBean == null) {
            saveCurType(Constant.HOT_SEARCH_WB)
            curBean =
                HotSearchConfigBean.HotSearchListBean()
            curBean.name = "微博"
            curBean.title = "微博热搜榜"
            curBean.icon = "https://gitee.com/luluzhang/HotSearchConfigProject/raw/master/icon/sina_wb.png"
            curBean.type = "weibo"
        }
        return curBean
    }

    private fun checkHasConfigBeanByType(type: String): HotSearchConfigBean.HotSearchListBean? {
        for (bean in getConfigList()) {
            if (TextUtils.equals(type, bean.type)) {
                return bean
            }
        }
        return null
    }
}
package com.lulu.hotsearch

import android.text.TextUtils
import android.util.Log
import com.lulu.baseutil.FileUtil
import com.lulu.baseutil.Init
import com.lulu.baseutil.ParcelUtil
import com.lulu.basic.utils.AssetsUtil
import com.lulu.hotsearch.bean.FilterRule
import com.yuewen.reader.zebra.utils.GSONUtil
import java.io.File

private const val TAG = "LaunchInfoManager"

object LaunchInfoManager {
    private const val AD_RULES = "auto_invoke_rules.json"
    private val FILTER_RULE_PATH = Init.ROOT_PATH + "filterRule.json"
    private val CONFIG_INFO_PATH = Init.ROOT_PATH + "continueReadMark.json"

    public fun saveFilterRule(jsonStr: String) {
        try {
            val filterRules = GSONUtil.parseJsonToList(jsonStr, FilterRule::class.java)
            //解析失败、为空都忽略数据
            if (filterRules.isEmpty()) {
                return
            }
            val isSuccess = FileUtil.writeFile(File(FILTER_RULE_PATH), jsonStr, false)
            Log.d(TAG, "saveFilterRule: isSuccess: $isSuccess")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    public fun getFilterRule(): List<FilterRule> {
        val jsonStr = FileUtil.readFile(File(FILTER_RULE_PATH))
        var filterRules = mutableListOf<FilterRule>()
        try {
            if (!TextUtils.isEmpty(jsonStr)) {
                filterRules = GSONUtil.parseJsonToList<FilterRule>(jsonStr, FilterRule::class.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (filterRules.isEmpty()) {
            filterRules = GSONUtil.parseJsonToList<FilterRule>(AssetsUtil.getAssetsFileToString(AD_RULES), FilterRule::class.java)
        }
        return filterRules
    }

}
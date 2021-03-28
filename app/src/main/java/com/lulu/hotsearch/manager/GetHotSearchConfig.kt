package com.lulu.hotsearch.manager

import android.os.Bundle
import com.lulu.plugin.lib.IGetHotSearchConfig

/**
 * 用于获取热搜配置信息
 */
class GetHotSearchConfig: IGetHotSearchConfig {
    override fun getCurHotSearchInfo(): Bundle {
        return Bundle().apply {
            putString(IGetHotSearchConfig.CONFIG_INFO_NAME, HotSearchConfigManager.getCurConfigBean().name)
        }
    }
}
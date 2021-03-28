package com.lulu.plugin.lib

import android.os.Bundle

interface IGetHotSearchConfig {
    companion object {

        //获取配置信息得类
        const val HOT_SEARCH_INFO_CLASS = "com.lulu.hotsearch.manager.GetHotSearchConfig"

        const val CONFIG_INFO_NAME = "CONFIG_INFO_NAME"
    }
    fun getCurHotSearchInfo(): Bundle
}
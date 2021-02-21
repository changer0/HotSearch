package com.lulu.skin.support

import android.content.Context
import android.text.TextUtils
import android.view.View
import java.util.*

/**
 * 支持的换肤的属性管理类
 */
public object SupportSkinManager {



    private val supportSkinAttrMap = mutableMapOf(
            pair(BackgroundAttr())
            ,pair(TextColorAttr())

    )

    private fun pair(attr: SupportSkinAttr): Pair<String , SupportSkinAttr> {
        return Pair(attr.attrName(), attr)
    }

    @JvmStatic
    public fun isSupportedAttr(attributeName: String): Boolean {
        return supportSkinAttrMap.containsKey(attributeName)
    }

    @JvmStatic
    public fun getSupportSkinAttr(attributeName: String): SupportSkinAttr? {
        return supportSkinAttrMap[attributeName]
    }

    abstract class SupportSkinAttr {
        abstract fun attrName(): String
        abstract fun doAction(attrType: String?, view: View, context: Context, resName: String?, resId: Int)
    }

}
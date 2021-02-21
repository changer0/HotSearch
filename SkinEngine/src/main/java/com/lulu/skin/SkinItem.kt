package com.lulu.skin

import android.content.Context
import android.view.View
import android.widget.TextView
import com.lulu.skin.support.SupportSkinManager

/**
 * 皮肤收集类 Item
 */
class SkinItem(private val view: View?, private val attrs: List<SkinAttr>?) {
    fun apply(context: Context) {
        if (view == null || attrs == null) return
        for (attr in attrs) {
            val attrName = attr.attrName
            val attrType = attr.attrType
            val resName = attr.resName
            val resId = attr.resId
            SupportSkinManager.getSupportSkinAttr(attrName)?.doAction(attrType, view, context, resName, resId)
        }
    }

}
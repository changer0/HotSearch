package com.lulu.skin

import android.content.Context
import android.view.View
import android.widget.TextView
import com.lulu.skin.support.SupportSkinManager
import java.lang.ref.WeakReference

/**
 * 皮肤收集类 Item
 */
class SkinItem(view: View?, private val attrs: List<SkinAttr>?) {

    /**
     * 使用弱引用 View, 防止内存泄漏
     */
    private val viewRef = WeakReference<View>(view)

    fun apply(context: Context) {
        val view = viewRef.get()
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
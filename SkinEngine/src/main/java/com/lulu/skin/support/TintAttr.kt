package com.lulu.skin.support

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.lulu.skin.SkinEngine

class TintAttr : SupportSkinManager.SupportSkinAttr() {
    override fun attrName(): String {
        return "tint"
    }

    override fun doAction(attrType: String?, view: View, context: Context, resName: String?, resId: Int) {
        if (view !is ImageView) {
            return
        }
        when (attrType) {
            "color" -> view.imageTintList = SkinEngine.get().getColorStateList(context, resName, resId)
        }
    }
}
package com.lulu.skin.support

import android.content.Context
import android.view.View
import com.lulu.skin.SkinEngine

class BackgroundTintAttr : SupportSkinManager.SupportSkinAttr() {
    override fun attrName(): String {
        return "backgroundTint"
    }

    override fun doAction(attrType: String?, view: View, context: Context, resName: String?, resId: Int) {
        when (attrType) {
            "color" -> view.backgroundTintList = SkinEngine.get().getColorStateList(context, resName, resId)
        }
    }
}
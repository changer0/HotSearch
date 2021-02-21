package com.lulu.skin.support

import android.content.Context
import android.view.View
import com.lulu.skin.SkinEngine

class BackgroundAttr : SupportSkinManager.SupportSkinAttr() {
    override fun attrName(): String {
        return "background"
    }

    override fun doAction(attrType: String?, view: View, context: Context, resName: String?, resId: Int) {
        when (attrType) {
            "color" -> view.setBackgroundColor(SkinEngine.get().getColor(context, resName, resId))
            "drawable" -> view.background = SkinEngine.get().getDrawable(context, resName, resId)
        }
    }
}
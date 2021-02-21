package com.lulu.skin.support

import android.content.Context
import android.view.View
import androidx.core.view.TintableBackgroundView
import com.lulu.skin.SkinEngine

class BackgroundTintAttr : SupportSkinManager.SupportSkinAttr() {
    override fun attrName(): String {
        return "backgroundTint"
    }

    override fun doAction(attrType: String?, view: View, context: Context, resName: String?, resId: Int) {
        if (view !is TintableBackgroundView) {
            return
        }
        when (attrType) {
            "color" -> view.supportBackgroundTintList = SkinEngine.get().getColorStateList(context, resName, resId)
        }
    }
}
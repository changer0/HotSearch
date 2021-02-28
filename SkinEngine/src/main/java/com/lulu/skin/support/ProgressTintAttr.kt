package com.lulu.skin.support

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.lulu.skin.SkinEngine

class ProgressTintAttr : SupportSkinManager.SupportSkinAttr() {
    override fun attrName(): String {
        return "progressTint"
    }

    override fun doAction(attrType: String?, view: View, context: Context, resName: String?, resId: Int) {
        if (view !is ProgressBar) {
            return
        }
        when (attrType) {
            "color" -> view.progressTintList = SkinEngine.get().getColorStateList(context, resName, resId)
        }
    }
}
package com.lulu.skin.support

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import com.lulu.skin.SkinEngine

class IndeterminateTintAttr : SupportSkinManager.SupportSkinAttr() {
    override fun attrName(): String {
        return "indeterminateTint"
    }

    override fun doAction(attrType: String?, view: View, context: Context, resName: String?, resId: Int) {
        if (view !is ProgressBar) return
        when (attrType) {
            "color" -> view.indeterminateTintList = SkinEngine.get().getColorStateList(context, resName, resId)
        }
    }
}
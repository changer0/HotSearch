package com.lulu.skin.support

import android.content.Context
import android.view.View
import android.widget.TextView
import com.lulu.skin.SkinEngine

class TextColorAttr : SupportSkinManager.SupportSkinAttr() {
    override fun attrName(): String {
        return "textColor"
    }

    override fun doAction(attrType: String?, view: View, context: Context, resName: String?, resId: Int) {
        if (view is TextView && "color" == attrType) {
            view.setTextColor(SkinEngine.get().getColor(context, resName, resId))
        }
    }
}
package com.lulu.skin.support

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.lulu.skin.SkinEngine

class SrcAttr : SupportSkinManager.SupportSkinAttr() {
    override fun attrName(): String {
        return "src"
    }

    override fun doAction(attrType: String?, view: View, context: Context, resName: String?, resId: Int) {
        if (view !is ImageView) {
            return
        }
        view.setImageDrawable(SkinEngine.get().getDrawable(context, resName, resId))
    }
}
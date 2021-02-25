package com.lulu.skin.support

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.lulu.skin.R
import com.lulu.skin.SkinEngine

private const val TAG = "SrcAttr"
class SrcAttr : SupportSkinManager.SupportSkinAttr() {
    override fun attrName(): String {
        return "src"
    }

    override fun doAction(attrType: String?, view: View, context: Context, resName: String?, resId: Int) {
        if (view !is ImageView) {
            return
        }

        var originConstantState: Drawable.ConstantState? = null
        if (view.drawable != null) {
            originConstantState = view.drawable.constantState
        }

        val tag = view.getTag(R.string.skin_tag)
        if (context.resources.getDrawable(resId).constantState != originConstantState && tag == null) {
            //当前 View 中的 Drawable 与原资源 Drawable 不一致, 且无换肤 Tag, 说明是外部自行设置的资源
            Log.d(TAG, "doAction: 与原资源 Drawable 不一致, 且无换肤 Tag: ${context.resources.getResourceName(view.id)}")
            return
        }
        if (tag is String && TextUtils.equals(tag, SkinEngine.get().skinPath)) {
            Log.d(TAG, "doAction: 与上次换肤 Tag 一致无需换肤: ${context.resources.getResourceName(view.id)}")
            return
        }

        view.setImageDrawable(SkinEngine.get().getDrawable(context, resName, resId))
        if (SkinEngine.get().isExternalSkin) {
            view.setTag(R.string.skin_tag, SkinEngine.get().skinPath)
        } else {
            view.setTag(R.string.skin_tag, null)
        }

        view.setImageDrawable(SkinEngine.get().getDrawable(context, resName, resId))
    }

    //下面代码暂时保留吧

    //        var originConstantState: Drawable.ConstantState? = null
    //        if (view.drawable != null) {
    //            originConstantState = view.drawable.constantState
    //        }
    //
    //        val tag = view.getTag(R.string.skin_tag)
    //        if (context.resources.getDrawable(resId).constantState != originConstantState
    //            && tag == null) {
    //            //当前 View 中的 Drawable 与原资源 Drawable 不一致, 且无换肤 Tag, 说明是外部自行设置的资源
    //            Log.d(TAG, "doAction: 与原资源 Drawable 不一致, 且无换肤 Tag: ${context.resources.getResourceName(view.id)}")
    //            return
    //        }
    //        if (tag is String && TextUtils.equals(tag, SkinEngine.get().skinPath)) {
    //            Log.d(TAG, "doAction: 与上次换肤 Tag 一致无需换肤: ${context.resources.getResourceName(view.id)}")
    //            return
    //        }
    //
    //        view.setImageDrawable(SkinEngine.get().getDrawable(context, resName, resId))
    //        if (SkinEngine.get().isExternalSkin) {
    //            view.setTag(R.string.skin_tag, SkinEngine.get().skinPath)
    //        } else {
    //            view.setTag(R.string.skin_tag, null)
    //        }
}
package com.lulu.hotsearch

import android.widget.ImageView
import com.lulu.basic.skin.SkinManager

object HotSearchTitle {
    fun adapter(rightImg: ImageView) {
        rightImg.imageTintList = SkinManager.get().getColorStateList("primaryTextColor")
    }
}
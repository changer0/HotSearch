package com.lulu.basic.skin

import android.text.TextUtils
import com.lulu.baseutil.Init
import com.lulu.skin.ISkinUpdateListener
import com.lulu.skin.SkinEngine


/**
 * 换肤管理类
 */
public class SkinManager {
    companion object {
        @JvmStatic
        public fun get(): SkinManager {
            return Holder.instance
        }

        @JvmStatic
        public val SKIN_PATH = Init.ROOT_PATH + "skin/"
    }
    object Holder {
        val instance = SkinManager()
    }

    public fun addSkinUpdateListener(listener: ISkinUpdateListener) {
        SkinEngine.get().addSkinUpdateListener(listener)
    }

    public fun removeSkinUpdateListener(listener: ISkinUpdateListener) {
        SkinEngine.get().removeSkinUpdateListener(listener)
    }
    /**
     * 皮肤初始化
     */
    public fun init() {
        val skinPath = SkinKVStorage.getSkinPath()
        if (!TextUtils.isEmpty(skinPath)) {
            SkinEngine.get().loadSkin(Init.context, skinPath!!)
        }
    }

    /**
     * 切换皮肤
     */
    public fun switchSkin(skinPath: String) {
        if (TextUtils.isEmpty(skinPath)) {
            return
        }
        SkinEngine.get().loadSkin(Init.context, skinPath) {
            SkinKVStorage.setSkinPath(skinPath)
        }
    }

    /**
     * 恢复默认主题
     */
    public fun restoreDefaultTheme() {
        SkinKVStorage.setSkinPath(null)
        SkinEngine.get().restoreDefaultTheme()
    }

    public fun isExternalSkin(): Boolean {
        return SkinEngine.get().isExternalSkin
    }

    public fun getColor() {
        //return SkinEngine.get().getColor(Init.context, )
    }
}
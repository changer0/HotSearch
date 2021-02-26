package com.lulu.basic.skin

import com.lulu.basic.kvstorage.KVStorage

/**
 * 皮肤 Storage
 */
object SkinKVStorage : KVStorage() {
    private const val SP_NAME = "skin_setting"

    private const val SKIN_PATH = "skin_path"

    @JvmStatic
    fun setSkinPath(path: String?) {
        doCommit(obtainEditor(SP_NAME).putString(SKIN_PATH, path))
    }
    @JvmStatic
    fun getSkinPath(): String? {
        return obtainSP(SP_NAME).getString(SKIN_PATH, null)
    }

    private const val SKIN_ID = "skin_id"

    @JvmStatic
    fun setSkinId(id: String?) {
        doCommit(obtainEditor(SP_NAME).putString(SKIN_ID, id))
    }
    @JvmStatic
    fun getSkinId(): String? {
        return obtainSP(SP_NAME).getString(SKIN_ID, null)
    }
}
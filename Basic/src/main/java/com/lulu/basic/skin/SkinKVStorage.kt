package com.lulu.basic.skin

import com.lulu.basic.kvstorage.KVStorage

/**
 * 皮肤 Storage
 */
public object SkinKVStorage : KVStorage() {
    private const val SP_NAME = "skin_setting"

    private const val SKIN_PATH = "skin_path"

    @JvmStatic
    public fun setSkinPath(path: String?) {
        doCommit(obtainEditor(SP_NAME).putString(SKIN_PATH, path))
    }
    @JvmStatic
    public fun getSkinPath(): String? {
        return obtainSP(SP_NAME).getString(SKIN_PATH, null)
    }
}
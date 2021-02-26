package com.lulu.basic.skin

/**
 * 皮肤切换监听
 */
interface ISkinSwitchListener {
    fun onSuccess()
    fun onFailure(e: Throwable)
}
package com.lulu.basic.net

import android.util.Log
import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

/**
 * @author zhanglulu on 2020/7/1.
 * for 协程 Scope 管理类，防止内存泄漏 Lifecycle ON_DESTROY 态自动销毁
 */
private const val TAG = "CoroutineScopeManager"
object CoroutineScopeManager {
    private val scopeMap = mutableMapOf<Lifecycle, CoroutineScope>()

    /**
     * 获取 CoroutineScope
     */
    public fun getScope(lifecycle: Lifecycle): CoroutineScope {
        var scope = scopeMap[lifecycle]
        if (scope != null) {
            return scope
        }
        scope = CoroutineScope(Dispatchers.Main)
        val observer: (source: LifecycleOwner, event: Lifecycle.Event) -> Unit = { source, event ->
            Log.d(TAG, "lifecycle called with: source = $source, event = $event")
            if (event == Lifecycle.Event.ON_DESTROY) {
                //当前 Activity 销毁了
                scope.cancel()
                scopeMap.remove(lifecycle)
            }
        }
        lifecycle.addObserver(GenericLifecycleObserver(observer))
        scopeMap[lifecycle] = scope
        return scope
    }

    /**
     * 获取 CoroutineScope
     */
    public fun getScope(owner: LifecycleOwner): CoroutineScope {
        return getScope(owner.lifecycle)
    }
}
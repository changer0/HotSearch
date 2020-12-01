package com.qq.reader.provider.simple

import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @author zhanglulu on 2020/11/20.
 * for
 */
open class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * Views indexed with their IDs
     */
    private var views: SparseArray<View?> = SparseArray()

    fun <T : View?> getView(@IdRes viewId: Int): T {
        var view = views[viewId]
        if (view == null) {
            view = itemView.findViewById(viewId)
            views.put(viewId, view)
        }
        @Suppress("UNCHECKED_CAST")
        return view as T
    }
}
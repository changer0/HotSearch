package com.example.providermoduledemo

import android.util.Log

/**
 * @author zhanglulu on 2020/10/23.
 * for
 */
object Utils {
    fun getResIdByString(idStr: String?, c: Class<*>): Int {
        return try {
            val idField = c.getDeclaredField(idStr)
            idField.getInt(idField)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("FreeCard_3NBooks", "strId 转换出错")
            -1
        }
    }
}
package com.lulu.hotsearch.db

import androidx.room.Room
import com.lulu.baseutil.Init

/**
 * @author zhanglulu
 */
class DBManager {
    companion object {
        fun get(): AppDatabase {
            return Holder.instance.db
        }
    }
    object Holder {
        val instance = DBManager()
    }

    public var db = Room.databaseBuilder(Init.context, AppDatabase::class.java, "hot_search_db").build()
}
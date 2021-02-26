package com.lulu.basic.db

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lulu.baseutil.Init

/**
 * @author zhanglulu
 */
class DBManager {
    companion object {
        fun get(): AppDatabase {
            return Holder.instance.db
        }

        val migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("")
            }

        }
    }
    object Holder {
        val instance = DBManager()
    }

    public var db = Room.databaseBuilder(Init.context, AppDatabase::class.java, Init.dbName)
            //.addMigrations(migration) 预留升级
            .build()
}
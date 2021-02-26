package com.lulu.basic.db

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.lulu.basic.db.SkinPackageDao
import com.lulu.basic.skin.SkinPackageBean

/**
 * @author zhanglulu
 */
@Database(entities = [SkinPackageBean::class], version = 1)
abstract class BasicDatabase : RoomDatabase() {



    abstract fun skinPackageDao(): SkinPackageDao


    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }

}
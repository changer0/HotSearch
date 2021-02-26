package com.lulu.basic.db

import android.util.Log
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lulu.baseutil.Init

/**
 * @author zhanglulu
 */
private const val TAG = "DBManager"
class BasicDBManager {
    companion object {
        fun get(): BasicDatabase {
            return Holder.instance.db
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //https://zhuanlan.zhihu.com/p/77408877?from_voters_page=true
                //1.创建一张符合我们要求的临时表temp_Student
                //2.将数据从旧表Student拷贝至临时表temp_Student
                //3.删除旧表Student
                //4.将临时表temp_Student重命名为Student

//                //创建一个临时新表
//                database.execSQL("CREATE TABLE IF NOT EXISTS `temp_skin_package_entity` (`id` TEXT NOT NULL, `name` TEXT, `downloadUrl` TEXT, `version` INTEGER NOT NULL, `isUpdate` INTEGER NOT NULL, `hasLocalFile` INTEGER NOT NULL, PRIMARY KEY(`id`))")
//                //将数据从旧表拷贝至临时表
//                database.execSQL("INSERT INTO `temp_skin_package_entity` (id, name, downloadUrl, version, isUpdate, hasLocalFile) SELECT id, name, downloadUrl, version, isUpdate FROM `skin_package_entity`");
//                //删除旧表
//                database.execSQL("DROP TABLE `skin_package_entity`")
//                //将临时表重命名为
//                database.execSQL("ALTER TABLE `temp_skin_package_entity` RENAME TO `skin_package_entity`")


                //https://blog.csdn.net/u011609120/article/details/104053491
                //添加一列
                //database.execSQL("ALTER TABLE `skin_package_entity` ADD COLUMN `hasLocalFile` INTEGER NOT NULL DEFAULT 0")
                Log.d(TAG, "migrate: 数据已迁移!!")
            }

        }
    }
    object Holder {
        val instance = BasicDBManager()
    }

    public var db = Room.databaseBuilder(Init.context, BasicDatabase::class.java, Init.dbName)
            //.addMigrations(MIGRATION_1_2)
            .build()
}
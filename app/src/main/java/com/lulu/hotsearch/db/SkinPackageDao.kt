package com.lulu.hotsearch.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lulu.hotsearch.bean.SkinPackageBean

/**
 * @author zhanglulu
 */
@Dao
interface SkinPackageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSkinPackage(vararg skinPackage: SkinPackageBean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkinPackageSuspend(vararg skinPackage: SkinPackageBean)

    @Update
    fun updateSkinPackage(vararg skinPackage: SkinPackageBean)

    @Update
    suspend fun updateSkinPackageSuspend(vararg skinPackage: SkinPackageBean)

    @Delete
    fun deleteSkinPackage(vararg skinPackage: SkinPackageBean)

    @Delete
    suspend fun deleteSkinPackageSuspend(vararg skinPackage: SkinPackageBean)

    @Query("SELECT * FROM skin_package_entity")
    fun getAll() : LiveData<Array<SkinPackageBean>>

    @Query("SELECT * FROM skin_package_entity where id = :id LIMIT 1")
    fun getById(id: String): LiveData<SkinPackageBean>
}
package com.lulu.basic.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lulu.basic.skin.SkinPackageBean

/**
 * @author zhanglulu
 */
@Dao
interface SkinPackageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSkinPackage(vararg skinPackage: SkinPackageBean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkinPackageSuspend(vararg skinPackage: SkinPackageBean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkinPackageListSuspend(skinPackageList: List<SkinPackageBean>)

    @Update
    fun updateSkinPackage(vararg skinPackage: SkinPackageBean)

    @Update
    suspend fun updateSkinPackageSuspend(vararg skinPackage: SkinPackageBean)

    @Delete
    fun deleteSkinPackage(vararg skinPackage: SkinPackageBean)

    @Delete
    suspend fun deleteSkinPackageSuspend(vararg skinPackage: SkinPackageBean)

    @Query("DELETE FROM skin_package_entity")
    suspend fun deleteAllSuspend()

    @Query("SELECT * FROM skin_package_entity")
    fun getAll() : LiveData<List<SkinPackageBean>>

    @Query("SELECT * FROM skin_package_entity")
    suspend fun getAllSuspend() : List<SkinPackageBean>?

    @Query("SELECT * FROM skin_package_entity where id = :name LIMIT 1")
    fun getByName(name: String): LiveData<SkinPackageBean>
}
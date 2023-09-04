/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.model.repository.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.myone.mynewsapp.common.sourceRemoteKeyTable
import id.myone.mynewsapp.model.repository.datasource.local.entity.SourceRemoteKeyEntity

@Dao
interface SourceRemoteKeyDao {
    @Query("select * from $sourceRemoteKeyTable where id=:id")
    suspend fun getRemoteKeyById(id: Int): SourceRemoteKeyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys(data: List<SourceRemoteKeyEntity>)

    @Query("delete from $sourceRemoteKeyTable")
    suspend fun deleteRemoteKeys()

    @Query("select * from $sourceRemoteKeyTable order by id limit 1")
    suspend fun getLatestKeys(): SourceRemoteKeyEntity?
}
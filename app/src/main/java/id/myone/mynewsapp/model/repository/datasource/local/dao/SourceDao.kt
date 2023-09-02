package id.myone.mynewsapp.model.repository.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.myone.mynewsapp.common.sourceTable
import id.myone.mynewsapp.model.repository.datasource.local.entity.SourceEntity

@Dao
interface SourceDao {

    @Query("select * from $sourceTable")
    fun getAllSources(): PagingSource<Int, SourceEntity>

    @Query("select * from $sourceTable where id=:id")
    suspend fun getSourceById(id: String): SourceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSources(source: List<SourceEntity>)

    @Delete
    suspend fun deleteAllSource()
}

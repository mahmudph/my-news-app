/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.model.repository.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.myone.mynewsapp.common.articleRemoteKeyTable
import id.myone.mynewsapp.model.repository.datasource.local.entity.ArticleRemoteKeyEntity


@Dao
interface ArticleRemoteKeyDao {
    @Query("select * from $articleRemoteKeyTable where id=:id")
    suspend fun getRemoteKeyById(id: Int): ArticleRemoteKeyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys(data: List<ArticleRemoteKeyEntity>)

    @Query("delete from $articleRemoteKeyTable")
    suspend fun deleteRemoteKeys()

    @Query("select * from $articleRemoteKeyTable order by id limit 1")
    suspend fun getLatestKeys(): ArticleRemoteKeyEntity?
}
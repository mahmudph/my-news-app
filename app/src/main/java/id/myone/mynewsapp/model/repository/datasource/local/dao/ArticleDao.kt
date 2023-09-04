/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.model.repository.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.myone.mynewsapp.common.articleTable
import id.myone.mynewsapp.model.repository.datasource.local.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Query("select * from $articleTable where arc_id=:id")
    fun getAllArticles(id: String): PagingSource<Int, ArticleEntity>

    @Query("select * from $articleTable where id=:id")
    suspend fun getArticlesById(id: Int): ArticleEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(article: List<ArticleEntity>)

    @Query("delete from $articleTable")
    suspend fun deleteAllArticles()
}
/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.model.repository

import androidx.paging.PagingData
import id.myone.mynewsapp.model.repository.datasource.local.entity.ArticleEntity
import id.myone.mynewsapp.model.repository.datasource.local.entity.SourceEntity
import kotlinx.coroutines.flow.Flow

interface AppRepositoryContract {
    suspend fun getArticleById(id: Int): ArticleEntity
    suspend fun getSourceById(id: String): SourceEntity

    fun getSources(category: String): Flow<PagingData<SourceEntity>>
    fun getArticles(sources: String, q: String): Flow<PagingData<ArticleEntity>>
}
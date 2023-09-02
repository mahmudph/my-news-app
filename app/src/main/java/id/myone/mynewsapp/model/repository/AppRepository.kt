/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.model.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import id.myone.mynewsapp.common.pageSize
import id.myone.mynewsapp.extensions.mapToEntity
import id.myone.mynewsapp.model.repository.datasource.local.AppDatabase
import id.myone.mynewsapp.model.repository.datasource.local.dao.ArticleDao
import id.myone.mynewsapp.model.repository.datasource.local.dao.SourceDao
import id.myone.mynewsapp.model.repository.datasource.local.entity.ArticleEntity
import id.myone.mynewsapp.model.repository.datasource.local.entity.SourceEntity
import id.myone.mynewsapp.model.repository.datasource.remote.AppService
import id.myone.mynewsapp.model.repository.paging.ArticlesDataSources
import id.myone.mynewsapp.model.repository.paging.ArticlesRemoteMediator
import id.myone.mynewsapp.model.repository.paging.SourceArticleDataSource
import id.myone.mynewsapp.model.repository.paging.SourceArticleRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class AppRepository(
    private val appService: AppService,
    private val appDatabase: AppDatabase,
    private val articleDao: ArticleDao,
    private val sourceDao: SourceDao,
) : AppRepositoryContract {

    override suspend fun getArticleById(id: Int): ArticleEntity {
        val response = articleDao.getArticlesById(id)
        return response!!
    }

    override fun getSources(category: String): Flow<PagingData<SourceEntity>> {
        val pagingSourceFactory = { sourceDao.getAllSources() }
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            remoteMediator = SourceArticleRemoteMediator(
                remoteService = appService,
                category = category,
                localDatabase = appDatabase,
                sourceDao = sourceDao,
                sourceRemoteDao = appDatabase.sourceRemoteKeyDao(),
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    override suspend fun getSourceById(id: String): SourceEntity {
        val response = sourceDao.getSourceById(id)
        return response!!
    }

    override fun getArticles(sources: String): Flow<PagingData<ArticleEntity>> {
        val pagingSourceFactory = { articleDao.getAllArticles() }
        return Pager(
            config = PagingConfig(pageSize = pageSize),
            remoteMediator = ArticlesRemoteMediator(
                remoteService = appService,
                sources = sources,
                localDatabase = appDatabase,
                articleDao = articleDao,
                articleRemoteKeyDao = appDatabase.articleRemoteKeyDao(),
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    override fun searchArticles(searchQuery: String): Flow<PagingData<ArticleEntity>> {
        val response = Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { ArticlesDataSources(appService, searchQuery) }
        ).flow

        return response.map { result ->
            result.map { data -> data.mapToEntity() }
        }
    }

    override fun searchSources(searchQuery: String): Flow<PagingData<SourceEntity>> {
        val response = Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { SourceArticleDataSource(appService, searchQuery) }
        ).flow

        return response.map { result ->
            result.map { data -> data.mapToEntity() }
        }
    }
}
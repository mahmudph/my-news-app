/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.model.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.myone.mynewsapp.common.pageSize
import id.myone.mynewsapp.model.repository.datasource.local.AppDatabase
import id.myone.mynewsapp.model.repository.datasource.local.dao.ArticleDao
import id.myone.mynewsapp.model.repository.datasource.local.dao.SourceDao
import id.myone.mynewsapp.model.repository.datasource.local.entity.ArticleEntity
import id.myone.mynewsapp.model.repository.datasource.local.entity.SourceEntity
import id.myone.mynewsapp.model.repository.datasource.remote.AppService
import id.myone.mynewsapp.model.repository.paging.ArticlesRemoteMediator
import id.myone.mynewsapp.model.repository.paging.SourceArticleRemoteMediator
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class AppRepository(
    private val appService: AppService,
    private val appDatabase: AppDatabase,
    private val articleDao: ArticleDao,
    private val sourceDao: SourceDao,
) : AppRepositoryContract {

    override suspend fun getArticleById(id: Int): ArticleEntity {
        return articleDao.getArticlesById(id)
    }

    override fun getSources(category: String): Flow<PagingData<SourceEntity>> {
        val pagingSourceFactory = { sourceDao.getAllSources(category) }
        return Pager(
            config = PagingConfig(pageSize = pageSize, enablePlaceholders = false),
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

    override fun getArticles(sources: String, q: String): Flow<PagingData<ArticleEntity>> {
        val pagingSourceFactory = { articleDao.getAllArticles(sources) }
        return Pager(
            config = PagingConfig(pageSize = pageSize, enablePlaceholders = false),
            remoteMediator = ArticlesRemoteMediator(
                search = q,
                remoteService = appService,
                sources = sources,
                localDatabase = appDatabase,
                articleDao = articleDao,
                articleRemoteKeyDao = appDatabase.articleRemoteKeyDao(),
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }
}
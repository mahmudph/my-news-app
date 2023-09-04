/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.model.repository.paging

import id.myone.mynewsapp.base.paging.BaseRemoteMediator
import id.myone.mynewsapp.common.language
import id.myone.mynewsapp.extensions.mapToEntity
import id.myone.mynewsapp.model.repository.datasource.local.AppDatabase
import id.myone.mynewsapp.model.repository.datasource.local.dao.ArticleDao
import id.myone.mynewsapp.model.repository.datasource.local.dao.ArticleRemoteKeyDao
import id.myone.mynewsapp.model.repository.datasource.local.entity.ArticleEntity
import id.myone.mynewsapp.model.repository.datasource.local.entity.ArticleRemoteKeyEntity
import id.myone.mynewsapp.model.repository.datasource.remote.AppService


class ArticlesRemoteMediator(
    localDatabase: AppDatabase,
    private val search: String,
    private val sources: String,
    private val remoteService: AppService,
    private val articleDao: ArticleDao,
    private val articleRemoteKeyDao: ArticleRemoteKeyDao,
) : BaseRemoteMediator<ArticleEntity, ArticleRemoteKeyEntity>(localDatabase) {


    override suspend fun getRemoteKeyById(id: Int): ArticleRemoteKeyEntity {
        return articleRemoteKeyDao.getRemoteKeyById(id)
    }

    override suspend fun truncateLocalData() {
        articleDao.deleteAllArticles()
        articleRemoteKeyDao.deleteRemoteKeys()
    }

    override suspend fun getDataFromService(
        page: Int,
        pageSize: Int,
    ): List<ArticleEntity> {
        val response = remoteService.getArticles(search, sources, page, pageSize, language)
        return response.articles.map { it.mapToEntity() }
    }

    override suspend fun refreshLocalData(
        prePage: Int?,
        nextPage: Int?,
        data: List<ArticleEntity>,
    ) {
        val keys = data.map {
            ArticleRemoteKeyEntity(prePage = prePage, nexPage = nextPage)
        }

        articleDao.insertArticles(data)
        articleRemoteKeyDao.addRemoteKeys(keys)
    }
}


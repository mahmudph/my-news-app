/**
 * Created by Mahmud on 04/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.model.repository.paging

import id.myone.mynewsapp.base.paging.BaseRemoteMediator
import id.myone.mynewsapp.common.language
import id.myone.mynewsapp.common.sourceRemoteKeyTable
import id.myone.mynewsapp.extensions.mapToEntity
import id.myone.mynewsapp.model.repository.datasource.local.AppDatabase
import id.myone.mynewsapp.model.repository.datasource.local.dao.SourceDao
import id.myone.mynewsapp.model.repository.datasource.local.dao.SourceRemoteKeyDao
import id.myone.mynewsapp.model.repository.datasource.local.entity.ArticleEntity
import id.myone.mynewsapp.model.repository.datasource.local.entity.ArticleRemoteKeyEntity
import id.myone.mynewsapp.model.repository.datasource.local.entity.SourceEntity
import id.myone.mynewsapp.model.repository.datasource.local.entity.SourceRemoteKeyEntity
import id.myone.mynewsapp.model.repository.datasource.remote.AppService


class SourceArticleRemoteMediator(
    localDatabase: AppDatabase,
    private val category: String,
    private val remoteService: AppService,
    private val sourceDao: SourceDao,
    private val sourceRemoteDao: SourceRemoteKeyDao,
) : BaseRemoteMediator<SourceEntity, SourceRemoteKeyEntity>(localDatabase) {


    override suspend fun getRemoteKeyById(id: Any): SourceRemoteKeyEntity {
        return sourceRemoteDao.getRemoteKeyById(id as Int)
    }

    override suspend fun truncateLocalData() {
        sourceDao.deleteAllSource()
        sourceRemoteDao.deleteRemoteKeys()
    }

    override suspend fun getDataFromService(
        page: Int,
        pageSize: Int,
    ): List<SourceEntity> {
        val response =  remoteService.getSources(page, pageSize,category, language)
        return response.sources.map { it.mapToEntity() }
    }

    override suspend  fun refreshLocalData(
        prePage: Int?,
        nextPage: Int?,
        data: List<SourceEntity>,
    ) {
        val keys = data.map {
            SourceRemoteKeyEntity(prePage = prePage, nexPage = nextPage)
        }

        sourceDao.insertSources(data)
        sourceRemoteDao.addRemoteKeys(keys)
    }
}


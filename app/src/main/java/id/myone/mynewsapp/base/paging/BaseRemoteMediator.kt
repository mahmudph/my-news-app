/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.base.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import id.myone.mynewsapp.base.paging.interfaces.BaseRemoteMediatorContract
import id.myone.mynewsapp.model.repository.datasource.local.AppDatabase
import id.myone.mynewsapp.model.repository.datasource.local.entity.interfaces.BaseEntity
import id.myone.mynewsapp.model.repository.datasource.local.entity.interfaces.BaseRemoteKeyEntity

@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator<T : BaseEntity, Z : BaseRemoteKeyEntity>(
    private val appDatabase: AppDatabase,
) : RemoteMediator<Int, T>(), BaseRemoteMediatorContract<T, Z> {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, T>,
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nexPage?.minus(1) ?: 1
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    remoteKeys?.nexPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    remoteKeys?.prePage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                }
            }

            val response = getDataFromService(page = currentPage, pageSize = state.config.pageSize)
            val isEndOfPaginationReached = response.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (isEndOfPaginationReached) null else currentPage + 1

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    truncateLocalData()
                }

                refreshLocalData(prevPage, nextPage, response)
            }

            MediatorResult.Success(endOfPaginationReached = isEndOfPaginationReached)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, T>,
    ): Z? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                getRemoteKeyById(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, T>,
    ): Z? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { article ->
                getRemoteKeyById(article.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, T>,
    ): Z? {

        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { article ->
                getRemoteKeyById(article.id)
            }
    }
}
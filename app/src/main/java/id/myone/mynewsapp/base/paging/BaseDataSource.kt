/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.base.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.myone.mynewsapp.base.paging.interfaces.BaseDataSourceContract

abstract class BaseDataSource<T : Any>: PagingSource<Int, T>(), BaseDataSourceContract<T> {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {

            val position = params.key ?: INITIAL_PAGE
            val responseData = loadDataFromService(position, params.loadSize)

            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val INITIAL_PAGE = 1
    }
}
/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.model.repository.paging

import id.myone.mynewsapp.base.paging.BaseDataSource
import id.myone.mynewsapp.common.language
import id.myone.mynewsapp.model.repository.datasource.remote.AppService
import id.myone.mynewsapp.model.repository.datasource.remote.model.SourceData

class SourceArticleDataSource(
    private val apiSource: AppService,
    private val querySearch: String,
) : BaseDataSource<SourceData>() {
    override suspend fun loadDataFromService(position: Int, loadSize: Int): List<SourceData> {
        val response = apiSource.getSources(position, loadSize, querySearch, language)
        return response.sources
    }
}
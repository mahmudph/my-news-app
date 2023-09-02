/**
 * Created by Mahmud on 05/05/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.model.repository.paging

import id.myone.mynewsapp.base.paging.BaseDataSource
import id.myone.mynewsapp.common.language
import id.myone.mynewsapp.model.repository.datasource.remote.model.Article
import id.myone.mynewsapp.model.repository.datasource.remote.AppService


class ArticlesDataSources(
    private val apiSource: AppService,
    private val querySearch: String,
) : BaseDataSource<Article>() {

    override suspend fun loadDataFromService(position: Int, loadSize: Int): List<Article> {
        val result = apiSource.searchNews(position, loadSize, querySearch, language = language)
        return result.articles
    }
}
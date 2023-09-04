/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.myone.mynewsapp.model.repository.AppRepositoryContract
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ListArticleViewModel(
    private val appRepositoryContract: AppRepositoryContract,
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val sourceName = MutableStateFlow("")

    val result = combine(searchQuery.debounce(500), sourceName) { searchQuery, sourceName ->
        searchQuery to sourceName
    }.flatMapLatest { data -> appRepositoryContract.getArticles(data.second, data.first) }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty()
    ).cachedIn(viewModelScope)

    val isShowLoading = searchQuery.map { it.isEmpty() }

    fun getArticles(source: String) {
        sourceName.value = source.lowercase()
    }

    fun searchArticle(q: String) {
        searchQuery.value = q
    }
}
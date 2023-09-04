/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
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

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SourceViewModel(
    private val appRepositoryContract: AppRepositoryContract,
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val categoryName = MutableStateFlow("")

    val result = combine(searchQuery.debounce(500), categoryName) { searchQuery, category ->
        searchQuery to category
    }.flatMapLatest { data -> appRepositoryContract.getSources(data.second) }.map {
        it.filter { sourceEntity -> sourceEntity.name.lowercase().contains(searchQuery.value) }
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty()
    ).cachedIn(viewModelScope)


    val isShowLoading = searchQuery.map { it.isEmpty() }
    fun getSources(category: String) {
        categoryName.value = category.lowercase()
    }

    fun searchSource(query: String) {
        searchQuery.value = query.lowercase()
    }
}
/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.mynewsapp.model.repository.AppRepositoryContract
import id.myone.mynewsapp.model.repository.ResultData
import id.myone.mynewsapp.model.repository.datasource.local.entity.SourceEntity
import id.myone.mynewsapp.utils.Event
import id.myone.mynewsapp.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SourceViewModel(
    private val appRepositoryContract: AppRepositoryContract,
) : ViewModel() {

    private val backupSourceList = MutableStateFlow<List<SourceEntity>>(emptyList())
    private val sourceList =
        MutableStateFlow<Event<UIState<List<SourceEntity>>>>(Event(UIState.Initial))
    val sources = sourceList.asStateFlow()

    fun getSourceByCategory(category: String, page: Int = 1) {
        viewModelScope.launch {
            sourceList.value = Event(UIState.Loading)
            when (val result = appRepositoryContract.getSources(category, page)) {
                is ResultData.Success -> {
                    backupSourceList.value = result.data
                    sourceList.value = Event(UIState.Success(result.data))
                }

                is ResultData.Error -> {
                    sourceList.value = Event(UIState.Error(result.exception.message.toString()))
                }
            }
        }
    }

    fun searchSource(query: String) {
        viewModelScope.launch {
            if (query.isNotEmpty()) {
                val result = backupSourceList.value.filter { it.name.contains(query, true) }
                sourceList.value = Event(UIState.Success(result))
            }
        }
    }
}
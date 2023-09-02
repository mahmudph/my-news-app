/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.myone.mynewsapp.model.repository.AppRepositoryContract
import id.myone.mynewsapp.model.repository.datasource.local.entity.ArticleEntity
import id.myone.mynewsapp.utils.Event
import id.myone.mynewsapp.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailArticleViewModel(
    private val appRepository: AppRepositoryContract,
): ViewModel() {

    private val _result = MutableStateFlow<Event<UIState<ArticleEntity>>>(Event(UIState.Loading))
    val result = _result.asStateFlow()

    fun getArticleById(id: Int) {
        viewModelScope.launch {
            try {

                val response = appRepository.getArticleById(id)
                _result.value = Event(UIState.Success(response))

            } catch (e: Exception) {
                _result.value = Event(UIState.Error(e.localizedMessage ?: "Unknown Error"))
                e.printStackTrace()
            }
        }
    }
}
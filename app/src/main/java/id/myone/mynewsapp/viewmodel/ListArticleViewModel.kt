/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import id.myone.mynewsapp.model.repository.AppRepositoryContract

class ListArticleViewModel(
    private val appRepository: AppRepositoryContract,
) : ViewModel() {

    fun getArticles(source: String) = appRepository.getArticles(source).cachedIn(viewModelScope)
}
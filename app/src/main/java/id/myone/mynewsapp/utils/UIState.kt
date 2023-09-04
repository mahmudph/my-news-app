/**
 * Created by Mahmud on 04/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.utils

sealed class UIState<out T>{
    data object Loading: UIState<Nothing>()
    data class Success<T>(val data: T): UIState<T>()
    data class Error(val message: String): UIState<Nothing>()
}
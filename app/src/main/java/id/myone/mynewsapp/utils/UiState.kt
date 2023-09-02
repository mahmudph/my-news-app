package id.myone.mynewsapp.utils

sealed class UIState<out T>{
    object Initial: UIState<Nothing>()
    object Loading: UIState<Nothing>()
    data class Success<T>(val data: T): UIState<T>()
    data class Error(val message: String): UIState<Nothing>()
    data class FormValidationError(val errors: Map<String,List<String>>): UIState<Nothing>()
}
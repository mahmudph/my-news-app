/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.base.ui.interfaces

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import id.myone.mynewsapp.utils.Event
import id.myone.mynewsapp.utils.UIState
import kotlinx.coroutines.flow.Flow

interface BaseFragmentContract<T>: BaseContract<T> {
    fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?,): T
    fun <T> observerViewModel(flow: Flow<Event<UIState<T>>>, action: (isError: Boolean, T?) -> Unit)

    fun showLoading()
    fun hideLoading()

    fun showSnackBarMessage(message: String): Snackbar
    fun showSnackBarMessage(message: String, action: String, actionListener: () -> Unit): Snackbar
}
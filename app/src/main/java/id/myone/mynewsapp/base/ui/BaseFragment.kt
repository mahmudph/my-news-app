/**
 * Created by Mahmud on 02/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.base.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import id.myone.mynewsapp.base.ui.interfaces.BaseFragmentContract
import id.myone.mynewsapp.utils.Event
import id.myone.mynewsapp.utils.UIState
import id.myone.mynewsapp.view.widget.LoadingDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

abstract class BaseFragment<T : ViewBinding> : Fragment(), BaseFragmentContract<T> {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    private val loadingDialog: LoadingDialog by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = setupViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
    }

    override fun showLoading() {
        if (!loadingDialog.isAdded && !loadingDialog.isVisible) {
            val fragment = parentFragmentManager.beginTransaction().remove(loadingDialog)
            loadingDialog.show(fragment, loadingDialogName)
        }
    }

    override fun hideLoading() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (loadingDialog.isVisible || loadingDialog.isAdded) loadingDialog.dismiss()
        }, 1000)
    }

    override fun <T> observerViewModel(
        flow: Flow<Event<UIState<T>>>,
        action: (isError: Boolean, T?) -> Unit,
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { event ->
                    event.getContentIfNotHandled()?.let { data ->
                        when (data) {
                            is UIState.Loading -> showLoading()
                            is UIState.Success -> {
                                hideLoading()
                                action.invoke(false, data.data)
                            }

                            is UIState.Error -> {
                                hideLoading()
                                showSnackBarMessage(data.message)
                                action.invoke(true, null)
                            }

                            else -> throw NotImplementedError("Not implemented")
                        }
                    }
                }
            }
        }
    }

    override fun showSnackBarMessage(message: String): Snackbar {
        return Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT);
    }

    override fun showSnackBarMessage(
        message: String,
        action: String,
        actionListener: () -> Unit,
    ): Snackbar {
        return showSnackBarMessage(message).apply {
            setAction(action) {
                actionListener.invoke()
            }
        }
    }

    companion object {
        private val loadingDialogName = LoadingDialog.Companion::class.java.name
    }
}
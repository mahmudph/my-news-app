package id.myone.mynewsapp.view.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import id.myone.mynewsapp.R
import id.myone.mynewsapp.databinding.FragmentLoadingDialogBinding

class LoadingDialog : DialogFragment() {
    private lateinit var binding: FragmentLoadingDialogBinding
    private val cancelable = arguments?.getBoolean(CANCELABLE) ?: false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =  FragmentLoadingDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.apply {
            isCancelable = cancelable
            this.setCanceledOnTouchOutside(cancelable)
            window?.setLayout((resources.displayMetrics.widthPixels * 0.7).toInt(), 350)
            window?.setBackgroundDrawableResource(R.drawable.medium_shape)
        }
    }

    companion object {
        private const val CANCELABLE = "cancelable"

        @JvmStatic
        fun newInstance(cancelable: Boolean = false) =
            LoadingDialog().apply {
                arguments = bundleOf(CANCELABLE to cancelable)
            }
    }
}
package id.myone.mynewsapp.view.ui.article_screen.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.myone.mynewsapp.databinding.FragmentListOptionDialogListDialogBinding

class ListOptionDialogFragment(
    private val onclick: (type: String) -> Unit,
) : BottomSheetDialogFragment() {

    private var _binding: FragmentListOptionDialogListDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentListOptionDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            goDetail.setOnClickListener { onclick(DETAIL) }
            goWebview.setOnClickListener { onclick(WEB_VIEW) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun newInstance(onclick: (type: String) -> Unit) = ListOptionDialogFragment(onclick)

        const val DETAIL = "detail"
        const val WEB_VIEW = "web_view"
    }
}
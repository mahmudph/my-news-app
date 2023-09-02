package id.myone.mynewsapp.view.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import id.myone.mynewsapp.R
import id.myone.mynewsapp.base.ui.BaseFragment
import id.myone.mynewsapp.databinding.FragmentCategoryArticleBinding


class CategoryArticleFragment : BaseFragment<FragmentCategoryArticleBinding>() {

    private lateinit var adapter: ArrayAdapter<String>

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCategoryArticleBinding {
        return FragmentCategoryArticleBinding.inflate(inflater, container, false)
    }

    override fun setupView() {
        val categories = resources.getStringArray(R.array.article_categories)
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categories)

        binding.categoryArticleListView.apply { adapter = adapter }
    }

    override fun setupObserver() {

    }
}
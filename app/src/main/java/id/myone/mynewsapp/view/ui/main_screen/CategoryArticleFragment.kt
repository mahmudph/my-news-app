package id.myone.mynewsapp.view.ui.main_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.myone.mynewsapp.base.ui.BaseFragment
import id.myone.mynewsapp.databinding.FragmentCategoryArticleBinding
import id.myone.mynewsapp.view.ui.main_screen.adapter.CategoryAdapter
import id.myone.mynewsapp.view.ui.main_screen.data.categories

class CategoryArticleFragment : BaseFragment<FragmentCategoryArticleBinding>() {

    private lateinit var listAdapter: CategoryAdapter

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCategoryArticleBinding {
        return FragmentCategoryArticleBinding.inflate(inflater, container, false)
    }

    override fun setupView() {

        listAdapter = CategoryAdapter(requireContext(), categories) { category ->
            val navController = view?.findNavController()
            navController?.navigate(
                CategoryArticleFragmentDirections.actionCategoryScreenToSourceScreen(
                    categoryName = getString(category.name),
                    sourceId = null,
                    sourcename = null,
                )
            )
        }

        binding.categoryArticle.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }
    }

    override fun setupObserver() {

    }
}
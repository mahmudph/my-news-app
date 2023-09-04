package id.myone.mynewsapp.view.ui.article_screen

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import id.myone.mynewsapp.R
import id.myone.mynewsapp.base.ui.BaseFragment
import id.myone.mynewsapp.databinding.FragmentArticleListBinding
import id.myone.mynewsapp.view.ui.article_screen.adapter.ArticleAdapter
import id.myone.mynewsapp.view.ui.article_screen.bottomsheet.ListOptionDialogFragment
import id.myone.mynewsapp.view.widget.LoadDataAdapterLoading
import id.myone.mynewsapp.viewmodel.ListArticleViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleListFragment : BaseFragment<FragmentArticleListBinding>() {

    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var bottomSheet: ListOptionDialogFragment

    private val viewModel by viewModel<ListArticleViewModel>()
    private val args by navArgs<ArticleListFragmentArgs>()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentArticleListBinding {
        return FragmentArticleListBinding.inflate(inflater, container, false)
    }

    override fun setupView() {
        setupSearchSource()

        articleAdapter = ArticleAdapter(requireContext()) { article ->
            bottomSheet = ListOptionDialogFragment.newInstance {

                bottomSheet.dismiss()

                view?.findNavController()?.apply {
                    when (it) {

                        ListOptionDialogFragment.DETAIL -> this.navigate(
                            ArticleListFragmentDirections.actionSourceScreenToArticleDetail(
                                article.id
                            )
                        )

                        ListOptionDialogFragment.WEB_VIEW -> article.url?.let { url ->
                            this.navigate(
                                ArticleListFragmentDirections.actionSourceScreenToArticleDetailWebView(
                                    url
                                )
                            )
                        }
                    }
                }
            }

            bottomSheet.show(parentFragmentManager, ListOptionDialogFragment::class.java.name)
        }

        binding.listArticle.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articleAdapter.withLoadStateFooter(
                footer = LoadDataAdapterLoading {
                    articleAdapter.retry()
                }
            )
        }

        binding.refreshArticle.setOnRefreshListener {
            articleAdapter.refresh()
        }
    }

    private fun setupSearchSource() {
        val toolbar = requireActivity().findViewById(R.id.toolbar) as MaterialToolbar
        val searchMenuItem = toolbar.menu.findItem(R.id.search)
        val searchView = searchMenuItem.actionView as androidx.appcompat.widget.SearchView

        searchView.apply {
            queryHint = "Search Sources"
            setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.i(this.javaClass.name, "onQueryTextSubmit: $query")
                    viewModel.searchArticle(query ?: "")
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.searchArticle(newText ?: "")
                    return false
                }
            })
        }
    }

    override fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.result.collect { result ->
                    Log.i(this.javaClass.name, result.toString())
                    articleAdapter.submitData(result)
                }
            }
        }

        viewModel.getArticles(args.sourceId)

        lifecycleScope.launch {
            binding.apply {
                articleAdapter.loadStateFlow.collect { loadState ->
                    when (loadState.refresh) {
                        is LoadState.Loading -> {
                            if (viewModel.isShowLoading.first()) showLoading()
                        }

                        is LoadState.Error -> {
                            refreshArticle.isRefreshing = false
                            errorArticle.root.visibility = View.GONE
                            listArticle.visibility = View.GONE

                            hideLoading()
                            showSnackBarMessage((loadState.refresh as LoadState.Error).error.message.toString()).apply {
                                show()
                            }
                        }

                        is LoadState.NotLoading -> {
                            hideLoading()
                            refreshArticle.isRefreshing = false
                            errorArticle.root.visibility = View.GONE
                            listArticle.visibility = View.VISIBLE
                        }
                    }

                    if (loadState.append is LoadState.NotLoading) {
                        if (articleAdapter.itemCount < 1 && loadState.append.endOfPaginationReached) {
                            errorArticle.root.visibility = View.GONE
                            emptyArticle.root.visibility = View.VISIBLE
                            listArticle.visibility = View.GONE
                        } else {
                            listArticle.visibility = View.VISIBLE
                            errorArticle.root.visibility = View.GONE
                            emptyArticle.root.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}
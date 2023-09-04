package id.myone.mynewsapp.view.ui.source_screen


import android.util.Log
import android.view.Gravity
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
import id.myone.mynewsapp.databinding.FragmentArticleSourceBinding
import id.myone.mynewsapp.view.ui.source_screen.adapter.ArticleSourceAdapter
import id.myone.mynewsapp.view.widget.LoadDataAdapterLoading
import id.myone.mynewsapp.viewmodel.SourceViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleSourceFragment : BaseFragment<FragmentArticleSourceBinding>() {
    private lateinit var sourceAdapter: ArticleSourceAdapter

    private val viewModel by viewModel<SourceViewModel>()
    private val args by navArgs<ArticleSourceFragmentArgs>()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentArticleSourceBinding {
        return FragmentArticleSourceBinding.inflate(inflater, container, false)
    }

    private fun setupSearchSource() {
        val toolbar = requireActivity().findViewById(R.id.toolbar) as MaterialToolbar
        val searchMenuItem = toolbar.menu.findItem(R.id.search)
        val searchView = searchMenuItem.actionView as androidx.appcompat.widget.SearchView

        searchView.apply {
            queryHint = "Search Sources"
            gravity = Gravity.START
            setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.i(this.javaClass.name, "onQueryTextSubmit: $query")
                    viewModel.searchSource(query ?: "")
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.searchSource(newText ?: "")
                    return true
                }
            })
        }
    }

    override fun setupView() {
        setupSearchSource()
        sourceAdapter = ArticleSourceAdapter {
            val action = ArticleSourceFragmentDirections.actionSourceScreenToArticleList(
                sourceId = it.sourceId,
                sourcename = it.name
            )
            view?.findNavController()?.navigate(action)
        }

        binding.listSource.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = sourceAdapter.withLoadStateFooter(
                footer = LoadDataAdapterLoading {
                    sourceAdapter.retry()
                }
            )
        }

        binding.refreshSource.setOnRefreshListener {
            sourceAdapter.refresh()
        }
    }

    override fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.result.collect { sourceAdapter.submitData(it) }
            }
        }

        viewModel.getSources(args.categoryName)

        lifecycleScope.launch {
            binding.apply {
                sourceAdapter.loadStateFlow.collect { loadState ->
                    when (loadState.refresh) {
                        is LoadState.Loading -> {
                            if (viewModel.isShowLoading.first()) showLoading()
                        }

                        is LoadState.Error -> {
                            refreshSource.isRefreshing = false
                            errorSource.root.visibility = View.GONE
                            emptySource.root.visibility = View.GONE
                            listSource.visibility = View.GONE

                            hideLoading()
                            showSnackBarMessage((loadState.refresh as LoadState.Error).error.message.toString()).apply {
                                show()
                            }
                        }

                        is LoadState.NotLoading -> {
                            hideLoading()
                            refreshSource.isRefreshing = false
                            errorSource.root.visibility = View.GONE
                            listSource.visibility = View.VISIBLE
                        }
                    }

                    if (loadState.append is LoadState.NotLoading) {
                        if (sourceAdapter.itemCount < 1 && loadState.append.endOfPaginationReached) {
                            errorSource.root.visibility = View.GONE
                            emptySource.root.visibility = View.VISIBLE
                            listSource.visibility = View.GONE
                        } else {
                            listSource.visibility = View.VISIBLE
                            errorSource.root.visibility = View.GONE
                            emptySource.root.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}
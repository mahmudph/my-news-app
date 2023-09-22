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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import id.myone.mynewsapp.R
import id.myone.mynewsapp.base.ui.BaseFragment
import id.myone.mynewsapp.databinding.FragmentArticleSourceBinding
import id.myone.mynewsapp.utils.Event
import id.myone.mynewsapp.utils.UIState
import id.myone.mynewsapp.view.ui.source_screen.adapter.ArticleSourceListAdapter
import id.myone.mynewsapp.view.ui.source_screen.adapter.SourceListScrollListener
import id.myone.mynewsapp.viewmodel.SourceViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleSourceFragment : BaseFragment<FragmentArticleSourceBinding>() {
    private lateinit var sourceAdapter: ArticleSourceListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

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

        linearLayoutManager = LinearLayoutManager(requireContext())
        sourceAdapter = ArticleSourceListAdapter {
            val action = ArticleSourceFragmentDirections.actionSourceScreenToArticleList(
                sourceId = it.sourceId,
                sourcename = it.name
            )
            view?.findNavController()?.navigate(action)
        }

        binding.listSource.apply {
            adapter = sourceAdapter
            layoutManager = linearLayoutManager
            addOnScrollListener(getArticleOnScrollListener())
        }
    }

    override fun <T> observerViewModel(
        flow: Flow<Event<UIState<T>>>,
        action: (isError: Boolean, T?) -> Unit,
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sources.collect {
                    it.getContent().let { state ->
                        binding.apply {
                            when (state) {

                                is UIState.Success -> action(false, state.data as T)
                                is UIState.Loading -> {
                                    if (!sourceAdapter.getIsLoading()) {
                                        showLoading()
                                        emptySource.root.visibility = View.GONE
                                        errorSource.root.visibility = View.GONE
                                    }
                                }

                                is UIState.Error -> {
                                    showSnackBarMessage(state.toString()).apply { show() }
                                    if (sourceAdapter.getIsLoading()) {
                                        sourceAdapter.removeFooter()
                                    }
                                    action(true, null)
                                }

                                is UIState.Initial -> {
                                    viewModel.getSourceByCategory(args.categoryName, sourceAdapter.getCurrentPage())
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun setupObserver() {
        observerViewModel(viewModel.sources) { isError, sources ->
            binding.apply {

                // if success
                if (!isError) {
                    // when the progressbar is visible and the data is not empty
                    // this mean it being trigger by the load more items
                    // then increment the current page
                    if (sourceAdapter.getIsLoading()) {
                        sourceAdapter.removeFooter()

                        if (sources!!.isNotEmpty()) {
                            sourceAdapter.incrementCurrentPage()
                            sourceAdapter.setSourceList(sources, false)
                        } else {
                            showSnackBarMessage(getString(R.string.end_page_message)).apply {
                                show()
                            }
                            sourceAdapter.setIsLastPage(true)
                        }

                        return@apply
                    }

                    // when the progressbar is not visible
                    // this mean it being trigger by the initial page opened

                    if (sources!!.isEmpty()) {
                        emptySource.root.visibility = View.VISIBLE
                        listSource.visibility = View.GONE
                        errorSource.root.visibility = View.GONE
                    }

                    hideLoading()
                    sourceAdapter.setSourceList(sources, true)

                } else {
                    // if errors
                    hideLoading()
                    emptySource.root.visibility = View.GONE
                    listSource.visibility = View.GONE
                    errorSource.root.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getArticleOnScrollListener(): SourceListScrollListener {
        val listener = object : SourceListScrollListener(linearLayoutManager) {
            override fun isLoading(): Boolean {
                return sourceAdapter.getIsLoading()
            }

            override fun isLastPage(): Boolean {
                return sourceAdapter.getIsLastPage()
            }

            override fun loadMoreItems() {
                // get the source by category with the given category name and
                // current page plus one
                sourceAdapter.addLoadingFooter()
                viewModel.getSourceByCategory(args.categoryName, sourceAdapter.getCurrentPage() + 1)
            }
        }
        return listener
    }
}
package id.myone.mynewsapp.view.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import id.myone.mynewsapp.base.ui.BaseFragment
import id.myone.mynewsapp.databinding.FragmentArticleDetailBinding
import id.myone.mynewsapp.viewmodel.DetailArticleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleDetailFragment : BaseFragment<FragmentArticleDetailBinding>() {

    private val viewModel by viewModel<DetailArticleViewModel>()
    private val articleListFragmentArgs by navArgs<ArticleDetailFragmentArgs>()


    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentArticleDetailBinding {
        return FragmentArticleDetailBinding.inflate(inflater, container, false)
    }


    override fun setupView() {

    }

    override fun setupObserver() {
        observerViewModel(viewModel.result) { isError, res ->
            if(isError) {
                return@observerViewModel
            }
        }

        viewModel.getArticleById(articleListFragmentArgs.articleId)
    }
}
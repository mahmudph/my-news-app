package id.myone.mynewsapp.view.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import id.myone.mynewsapp.R
import id.myone.mynewsapp.base.ui.BaseFragment
import id.myone.mynewsapp.databinding.FragmentArticleDetailBinding
import id.myone.mynewsapp.utils.DateFormat
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
        binding.backButton.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
    }

    override fun setupObserver() {
        observerViewModel(viewModel.result) { isError, result ->
            if (isError) return@observerViewModel
            result?.let { article ->

                article.publishedAt?.let { date ->
                    binding.articleAuthorDate.text = DateFormat.formatDate(
                        date,
                    )
                }

                binding.title.text = article.title
                binding.articleAuthorName.text = article.author
                binding.articleAuthorSource.text = article.source?.name ?: "-"
                binding.content.text = article.content ?: "-"

                Glide.with(requireContext()).load(article.urlToImage ?: "")
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .into(binding.image)
            }

        }

        viewModel.getArticleById(articleListFragmentArgs.articleId)
    }
}
package id.myone.mynewsapp.view.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import id.myone.mynewsapp.base.ui.BaseFragment
import id.myone.mynewsapp.databinding.FragmentArticleWebViewDetailBinding

class ArticleWebViewDetailFragment : BaseFragment<FragmentArticleWebViewDetailBinding>() {

    private val args by navArgs<ArticleWebViewDetailFragmentArgs>()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentArticleWebViewDetailBinding {
        return FragmentArticleWebViewDetailBinding.inflate(inflater, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun setupView() {
        binding.apply {
            webview.webViewClient = WebViewClient()
            webview.loadUrl(args.url)
            webview.settings.javaScriptEnabled = true
            webview.settings.setSupportZoom(false)
        }
    }

    override fun setupObserver() {

    }
}
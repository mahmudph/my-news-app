package id.myone.mynewsapp.view.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import id.myone.mynewsapp.base.ui.BaseFragment
import id.myone.mynewsapp.databinding.FragmentArticleSourceBinding


class ArticleSourceFragment : BaseFragment<FragmentArticleSourceBinding>() {

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentArticleSourceBinding {
        return FragmentArticleSourceBinding.inflate(inflater, container, false)
    }

    override fun setupView() {

    }

    override fun setupObserver() {

    }
}
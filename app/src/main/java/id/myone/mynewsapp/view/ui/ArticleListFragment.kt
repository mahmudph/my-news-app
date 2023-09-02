package id.myone.mynewsapp.view.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import id.myone.mynewsapp.base.ui.BaseFragment
import id.myone.mynewsapp.databinding.FragmentArticleListBinding

class ArticleListFragment : BaseFragment<FragmentArticleListBinding>() {


    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentArticleListBinding {
        return FragmentArticleListBinding.inflate(inflater, container, false)
    }


    override fun setupView() {

    }

    override fun setupObserver() {

    }
}
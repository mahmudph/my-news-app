/**
 * Created by Mahmud on 03/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.view.ui.article_screen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.myone.mynewsapp.R
import id.myone.mynewsapp.databinding.ArticleListItemBinding
import id.myone.mynewsapp.model.repository.datasource.local.entity.ArticleEntity
import id.myone.mynewsapp.utils.DateFormat

class ArticleAdapter(
    private val context: Context,
    private val onClick: (ArticleEntity) -> Unit,
) : PagingDataAdapter<ArticleEntity, ArticleAdapter.ArticleSourceViewHolder>(DIFF_CALLBACK) {

    private lateinit var binding: ArticleListItemBinding
    override fun onBindViewHolder(holder: ArticleSourceViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ArticleSourceViewHolder {
        binding = ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleSourceViewHolder(context, binding, onClick)
    }

    class ArticleSourceViewHolder(
        private val context: Context,
        private val binding: ArticleListItemBinding,
        private val onClick: (ArticleEntity) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleEntity) {
            binding.apply {

                articleTitle.text = article.title ?: "-"
                articleAuthor.text = article.author ?: "Unknown";
                articleDescription.text = article.description ?: "-"

                article.publishedAt?.let { date ->
                    articleDate.text = DateFormat.formatDate(date)
                }

                Glide.with(context).load(article.urlToImage ?: "")
                    .centerCrop().placeholder(R.drawable.placeholder)
                    .into(articleImage)

                root.setOnClickListener { onClick(article) }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleEntity>() {
            override fun areItemsTheSame(
                oldItem: ArticleEntity,
                newItem: ArticleEntity,
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: ArticleEntity,
                newItem: ArticleEntity,
            ): Boolean {
                return oldItem.content == newItem.content
            }
        }
    }
}
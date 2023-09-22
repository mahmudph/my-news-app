/**
 * Created by Mahmud on 03/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.view.ui.source_screen.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.myone.mynewsapp.databinding.SourceListItemBinding
import id.myone.mynewsapp.model.repository.datasource.local.entity.SourceEntity

class ArticleSourceAdapter(
    private val onClick: (SourceEntity) -> Unit,
): PagingDataAdapter<SourceEntity, ArticleSourceAdapter.ArticleSourceViewHolder>(DIFF_CALLBACK) {

    private lateinit var binding: SourceListItemBinding

    override fun onBindViewHolder(holder: ArticleSourceViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ArticleSourceViewHolder {
        binding = SourceListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleSourceViewHolder(binding, onClick)
    }

    class ArticleSourceViewHolder(
        private val binding: SourceListItemBinding,
        private val onClick: (SourceEntity) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(source: SourceEntity) {
            binding.sourceName.text = source.name
            binding.sourceDescription.text = source.description
            binding.sourceCountry.text = "(${source.country.uppercase()})"

            binding.root.setOnClickListener {
                onClick(source)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SourceEntity>() {
            override fun areItemsTheSame(
                oldItem: SourceEntity,
                newItem: SourceEntity,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: SourceEntity,
                newItem: SourceEntity,
            ): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}
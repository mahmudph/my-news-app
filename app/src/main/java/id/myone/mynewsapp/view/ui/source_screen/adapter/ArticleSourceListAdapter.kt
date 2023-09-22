/**
 * Created by Mahmud on 20/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.view.ui.source_screen.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.myone.mynewsapp.databinding.LoadingItemBinding
import id.myone.mynewsapp.databinding.SourceListItemBinding
import id.myone.mynewsapp.model.repository.datasource.local.entity.SourceEntity

class ArticleSourceListAdapter(
    private val onClick: (SourceEntity) -> Unit,
) : ListAdapter<SourceEntity, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private val dataList = mutableListOf<SourceEntity>()
    private var currentPage = 0

    private var isLastPage = false
    private var isLoading = false

    inner class ViewHolders(
        private val binding: SourceListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: SourceEntity) {
            binding.apply {
                sourceName.text = data.name
                sourceDescription.text = data.description
                sourceCountry.text = "(${data.country.uppercase()})"
                root.setOnClickListener { onClick(data) }
            }
        }
    }


    inner class LoadingViewHolder(binding: LoadingItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == LOADING) {
            val binding =
                LoadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(binding)
        } else {
            val binding =
                SourceListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolders(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> (holder as ViewHolders).bind(getItem(position))
            LOADING -> {}
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSourceList(sources: List<SourceEntity>, isFilteredData: Boolean) {
        if (isFilteredData) {
            isLastPage = false
            notifyItemRangeRemoved(0, dataList.size)
            dataList.clear()
        }

        dataList.addAll(sources)
        submitList(dataList)
        notifyItemRangeInserted(dataList.size, sources.size)
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position == dataList.size - 1) && isLoading) LOADING
        else ITEM
    }

    fun incrementCurrentPage() {
        currentPage++
    }

    fun setIsLastPage(value: Boolean) {
        isLastPage = value
    }

    fun getCurrentPage() = currentPage
    fun getIsLastPage() = isLastPage
    fun getIsLoading() = isLoading

    fun addLoadingFooter() {
        isLoading = true
        dataList.add(
            SourceEntity(
                sourceId = "",
                category = "",
                country = "",
                description = "",
                language = "",
                name = "",
                url = ""
            )
        )
        notifyItemInserted(dataList.size - 1)
    }

    fun removeFooter() {
        isLoading = false
        val position = dataList.size - 1

        if (getItem(position) != null) {
            dataList.removeAt(position)
            notifyItemRemoved(position)
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
                return oldItem.sourceId == newItem.sourceId
            }
        }
        private const val LOADING = 0
        private const val ITEM = 1
    }
}
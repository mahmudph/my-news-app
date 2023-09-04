/**
 * Created by Mahmud on 04/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.view.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.myone.mynewsapp.databinding.LoadDataAdapterLoadingBinding

class LoadDataAdapterLoading(
    private val retry: () -> Unit,
) : LoadStateAdapter<LoadDataAdapterLoading.LoadingStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): LoadDataAdapterLoading.LoadingStateViewHolder {
        val binding = LoadDataAdapterLoadingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadingStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(
        holder: LoadDataAdapterLoading.LoadingStateViewHolder,
        loadState: LoadState,
    ) {
        holder.bind(loadState)
    }

    inner class LoadingStateViewHolder(
        private val binding: LoadDataAdapterLoadingBinding,
        private val retry: () -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }
    }
}
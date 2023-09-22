/**
 * Created by Mahmud on 20/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.view.ui.source_screen.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class SourceListScrollListener(
    private val layoutManager: LinearLayoutManager,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount
        val visibleItemCount = layoutManager.childCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                && firstVisibleItemPosition >= 0
            ) {
                loadMoreItems()
            }
        }
    }

    abstract fun isLoading(): Boolean

    abstract fun isLastPage(): Boolean

    abstract fun loadMoreItems()
}
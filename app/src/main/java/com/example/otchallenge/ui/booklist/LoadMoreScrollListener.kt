package com.example.otchallenge.ui.booklist

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LoadMoreScrollListener(
    private val bottomThreshold: Int,
    private val isLoading: () -> Boolean,
    private val hasMoreData: () -> Boolean,
    private val loadMoreData: () -> Unit,
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int,
    ) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) { // Only check when scrolling down
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val lastVisibleItemPosition = visibleItemCount + firstVisibleItemPosition

            if (!isLoading() && hasMoreData()) {
                if (lastVisibleItemPosition >= totalItemCount - bottomThreshold &&
                    firstVisibleItemPosition >= 0
                ) {
                    loadMoreData()
                }
            }
        }
    }
}

package com.example.otchallenge.utils.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Spacing item decoration for RecyclerView. Ensures uniform spacing between items.
 */
class SpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val columnCount = (parent.layoutManager as? GridLayoutManager)?.spanCount ?: 1

        val left = if (position % columnCount == 0) spacing else spacing / 2
        val right = if (position % columnCount == columnCount - 1) spacing else spacing / 2
        val top = if (position < columnCount) spacing else 0
        val bottom = spacing

        outRect.set(left, top, right, bottom)
    }
}
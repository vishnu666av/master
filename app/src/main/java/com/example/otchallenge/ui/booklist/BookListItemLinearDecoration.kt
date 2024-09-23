package com.example.otchallenge.ui.booklist

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.otchallenge.R
import kotlin.math.roundToInt

class BookListItemLinearDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val verticalPadding =
            view.resources.getDimension(R.dimen.vertical_padding_book_item).roundToInt()
        with(outRect) {
            // If it's first position then we don't want to add top padding as it would be covered
            // by parent's top padding already
            top = if (position == 0) {
                0
            } else {
                verticalPadding
            }
            bottom = verticalPadding
        }
    }
}

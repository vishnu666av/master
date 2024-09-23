package com.example.otchallenge.ui.booklist

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.otchallenge.R
import kotlin.math.roundToInt

/**
 * [RecyclerView.ItemDecoration] to use with screens larger than 600 dp wide. So definitely tablets,
 * and also some phones when in landscape mode
 */
class BookListItemGridDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position = parent.getChildAdapterPosition(view)
        val columnCount = view.resources.getInteger(R.integer.column_count)
        val verticalPadding =
            view.resources.getDimension(R.dimen.vertical_padding_book_item).roundToInt()
        val horizontalPadding =
            view.resources.getDimension(R.dimen.horizontal_padding_book_item).roundToInt()
        with(outRect) {
            // Don't add vertical padding for the first item
            top =
                if (position == 0 || position == 1) {
                    0
                } else {
                    verticalPadding
                }
            left =
                if (position % columnCount == 0) {
                    0
                } else {
                    horizontalPadding
                }
            right =
                if (position % columnCount == columnCount - 1) {
                    0
                } else {
                    horizontalPadding
                }
            bottom = verticalPadding
        }
    }
}

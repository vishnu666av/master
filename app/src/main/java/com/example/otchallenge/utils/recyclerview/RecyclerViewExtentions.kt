package com.example.otchallenge.utils.recyclerview

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
* Breaks the recycler view layout, on bigger screens. And uses linear layout on smaller screens.
* @param columnWidth - The minimum size, for column width in pixels.
* */
fun RecyclerView.autoFitLayout(columnWidth: Double) {
    (context as? AppCompatActivity)?.let {
        // ** Obtaining the screen width, in DP **
        val widthPixels= it.resources.displayMetrics.widthPixels
        val density = resources.displayMetrics.density
        val widthDp = widthPixels / density

        // ** Setting layout manager, according to screen width **
        if (widthDp > columnWidth){
            val columnCount = kotlin.math.floor((widthDp / columnWidth)).toInt()
            layoutManager = GridLayoutManager(it, columnCount).apply {

                // ** Excluding header view, from shrinking into columns
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int) =
                        if (position == 0) columnCount else 1
                }
            }
        } else {
            layoutManager = LinearLayoutManager(it)
        }
    }
}
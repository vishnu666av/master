package com.example.otchallenge.utils.recyclerview

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.round

/**
* Breaks the recycler view layout, on bigger screens. And uses linear layout on smaller screens.
* @param columnWidth - The minimum size, for column width in pixels.
* */
fun RecyclerView.autoFitLayout(columnWidth: Int) {
    (context as? AppCompatActivity)?.let {
        // ** Obtaining the screen width, in Pixels **
        val widthPixels= it.resources.displayMetrics.widthPixels

        // ** Setting layout manager, according to screen width **
        if (widthPixels > columnWidth){
            val columnCount = round(widthPixels.toDouble() / columnWidth.toDouble()).toInt()
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
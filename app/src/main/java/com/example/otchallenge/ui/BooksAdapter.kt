package com.example.otchallenge.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.otchallenge.R
import com.example.otchallenge.model.BookModel
import com.example.otchallenge.modules.ImageProvider

class BooksAdapter(
    private val imageProvider: ImageProvider,
) : RecyclerView.Adapter<BookVieHolder>() {

    private val books = mutableListOf<BookModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookVieHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BookVieHolder(layoutInflater.inflate(R.layout.book_item_view, parent, false))
    }

    override fun getItemCount() = books.count()

    override fun onBindViewHolder(holder: BookVieHolder, position: Int) {
        val bookToDisplay = books[position]
        holder.title?.text = bookToDisplay.title
        holder.description?.text = bookToDisplay.description
        imageProvider.displayImage(holder.image, bookToDisplay.image)
    }

    fun updateData(newBooks: List<BookModel>) {
        books.clear()
        books.addAll(newBooks)
        // we replaced all data so we have to call this even with warning
        notifyDataSetChanged()
    }
}

class BookVieHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image: ImageView? = view.findViewById(R.id.imageView)
    val title: TextView? = view.findViewById(R.id.title)
    val description: TextView? = view.findViewById(R.id.description)
}

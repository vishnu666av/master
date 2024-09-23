package com.example.otchallenge.ui.booklist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.otchallenge.R
import com.example.otchallenge.data.Book
import com.example.otchallenge.databinding.ViewBookListItemBinding

/**
 * Change this adapter to [ListAdapter] if data is not static anymore
 */
class BookListRecyclerViewAdapter(
    private val values: List<Book>
) : RecyclerView.Adapter<BookListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewBookListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size

    class ViewHolder(binding: ViewBookListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val title: TextView = binding.bookTitle
        private val description: TextView = binding.bookDescription
        private val image: ImageView = binding.bookImage
        private val author: TextView = binding.bookAuthor

        fun bind(book: Book) {
            title.text = book.title
            description.text = book.description
            author.text = itemView.context.getString(R.string.by_author, book.author)
            // Glide can handle this but we're manually handling `null` for better snapshot test
            // support, otherwise snapshot test would go through Glide code unnecessarily
            if (book.imageUrl == null) {
                image.setImageDrawable(
                    AppCompatResources.getDrawable(
                        itemView.context,
                        R.drawable.book_image_placeholder
                    )
                )
            } else {
                Glide.with(itemView)
                    .load(book.imageUrl)
                    .placeholder(R.drawable.book_image_placeholder)
                    .into(image)
            }
        }
    }
}
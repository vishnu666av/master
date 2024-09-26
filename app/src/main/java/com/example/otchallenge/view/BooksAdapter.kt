package com.example.otchallenge.view

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.otchallenge.R
import com.example.otchallenge.databinding.ItemBookBinding
import com.example.otchallenge.model.Book

class BooksAdapter(private var books: List<Book>) : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

    fun updateBooks(newBooks: List<Book>){
        books = newBooks
        notifyDataSetChanged()
    }
    class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(book: Book){
            binding.titleTextView.text = book.title
            binding.authorTextView.text = book.author
            binding.descriptionTextView.text = book.description

            //Use Glide to load the book image
            Glide.with(itemView.context)
                .load(book.book_image)
                .placeholder(R.drawable.placeholder)
                .into(binding.bookImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBookBinding.inflate(layoutInflater, parent, false)
        return BookViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }

}
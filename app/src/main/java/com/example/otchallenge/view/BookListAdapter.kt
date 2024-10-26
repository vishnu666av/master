package com.example.otchallenge.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.otchallenge.R
import com.example.otchallenge.databinding.ItemBookBinding
import com.example.otchallenge.model.Book

class BookListAdapter(private var books: List<Book>,
    private val clickListener: (Book) -> Unit): RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemBookBinding = DataBindingUtil.inflate(inflater, R.layout.item_book, parent, false)
        return BookViewHolder(binding)
    }

    override fun getItemCount() = books.size


    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position], clickListener)
    }

    class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book, clickListener: (Book) -> Unit){
            binding.book = book
            binding.root.setOnClickListener{
               // Log.d("BookListAdapter", "root clicked")
                clickListener(book)
            }
            binding.executePendingBindings()
        }
    }
}
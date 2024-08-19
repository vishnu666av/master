package com.example.otchallenge.ui.book.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.example.otchallenge.databinding.BookItemBinding
import com.example.otchallenge.domain.BookListContent
import com.example.otchallenge.domain.BookUIModel

/**
 * Adapter for the book list.
 */
class BookListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = mutableListOf<BookListContent>()

    companion object {
        private const val TYPE_BOOK: Int = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = BookItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BookListViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_BOOK
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position] as BookListContent.BookContent
        (holder as BookListViewHolder).bind(item.bookUIModel)
    }

    class BookListViewHolder(private val binding: BookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookUIModel) {
            binding.bookUIModel = book
        }
    }

    /**
     * Updates the list of books displayed in the RecyclerView.
     */
    fun updateItemList(newList: List<BookListContent>) {
        if (items != newList) {
            items.clear()
            items.addAll(newList)
            notifyDataSetChanged()
        }
    }
}
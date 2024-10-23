package com.example.otchallenge.booklist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.example.otchallenge.R
import com.example.otchallenge.booklist.uistate.BookUIState
import com.example.otchallenge.databinding.BookListItemBinding

class BookListAdapter(private val books: List<BookUIState>) :
    RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            BookListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(books[position])
    }

    class ViewHolder(private val binding: BookListItemBinding, private val ctx: Context) : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: BookUIState) {
            binding.titleTextView.text =
                ctx.getString(R.string.rank_and_title, book.rank, book.title)
            binding.titleTextView.isSelected = true

            binding.authorTextView.text = book.author
            binding.descriptionTextView.text = book.description

            Glide.with(binding.root)
                .load(book.imageUrl)
                .centerCrop()
                .transition(withCrossFade())
                .into(binding.imageView)
        }

    }

}
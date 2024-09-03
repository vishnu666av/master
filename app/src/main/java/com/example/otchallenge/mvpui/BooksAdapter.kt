package com.example.otchallenge.mvpui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.otchallenge.R
import com.example.otchallenge.databinding.BookItemBinding
import com.example.otchallenge.domain.models.Book

class BooksAdapter(): ListAdapter<Book, BooksAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = getItem(position)
        holder.title.text = book.title
        holder.description.text = book.description
        book.image?.let {
            Glide.with(holder.image)
                .load(book.image)
                .placeholder(R.drawable.im_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image)
        }

    }

    class ViewHolder(binding: BookItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.image
        val title = binding.title
        val description = binding.description
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.primaryIsbn10 == newItem.primaryIsbn10
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
        }
    }
}

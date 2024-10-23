package com.example.otchallenge.booklist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.otchallenge.databinding.BookListHeaderBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HeaderAdapter(var listName: String = "", var modifiedDate: LocalDateTime = LocalDateTime.now()) :
    RecyclerView.Adapter<HeaderAdapter.ViewHolder>() {

    class ViewHolder(val binding: BookListHeaderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(listName: String, modifiedDate: LocalDateTime) {
            binding.bookListTitle.text = listName
            binding.bookListLastUpdate.text = modifiedDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = BookListHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listName, modifiedDate)
    }

    fun update(listName: String, modifiedDate: LocalDateTime) {
        this.listName = listName
        this.modifiedDate = modifiedDate
        notifyItemChanged(0)
    }
}
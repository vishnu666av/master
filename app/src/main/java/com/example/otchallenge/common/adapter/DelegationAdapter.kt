package com.example.otchallenge.common.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DelegationAdapter(private val delegates: List<DelegateAdapter<out RecyclerView.ViewHolder, out ItemModel>>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<ItemModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegates[viewType].onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        delegates[getItemViewType(position)].onBindViewHolder(holder, item)
    }

    override fun getItemViewType(position: Int): Int {
        return delegates.indexOfFirst { it.isForViewType(items[position]) }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<ItemModel>) {
        this.items = items
        notifyDataSetChanged()
    }
}
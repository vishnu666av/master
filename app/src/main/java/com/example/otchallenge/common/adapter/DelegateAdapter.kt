package com.example.otchallenge.common.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface DelegateAdapter<VH : RecyclerView.ViewHolder, T : ItemModel> {

    fun onCreateViewHolder(parent: ViewGroup): VH

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ItemModel)

    fun isForViewType(item: ItemModel): Boolean
}
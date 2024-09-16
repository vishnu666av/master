package com.example.otchallenge.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.otchallenge.R

class OtAdapter(private var dataSet: List<Item>) :
    RecyclerView.Adapter<OtAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val description: TextView = view.findViewById(R.id.description)
        val imageview: ImageView = view.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_view, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.title.text = dataSet[position].title
        viewHolder.description.text = dataSet[position].description

        Glide.with(viewHolder.imageview)
            .load(dataSet[position].imageUrl)
            .into(viewHolder.imageview)

    }

    override fun getItemCount() = dataSet.size

    fun setData(dataSet: List<Item>){
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

}

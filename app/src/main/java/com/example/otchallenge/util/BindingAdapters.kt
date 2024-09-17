package com.example.otchallenge.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * Binding adapters for setting image url to ImageView
 */
@BindingAdapter("android:imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    if (imageUrl.isNullOrEmpty()) {
        imageView.setImageResource(android.R.drawable.ic_menu_gallery)
    } else {
        val glideRequest = Glide.with(imageView.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(android.R.drawable.ic_menu_gallery)
        glideRequest.into(imageView)
    }
}

/**
 * Binding adapter for setting visibility of a view
 */
@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}

/**
 * Binding adapter for setting image resource to ImageView
 */
@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}
package com.example.otchallenge.modules

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.otchallenge.R

/**
 *  this is the image provider implementation created to decouple the image
 *  library from the logical code, receives teh context to share the cache and speed up load
 *
 *  during load will display launcher as place holder, this is useful in low speed connections
 *
 *  Note: open class for mock testing purposes
 */
open class ImageProvider(private val context: Context) {

    open fun displayImage(view: ImageView?, url: String? = null) {
        if (url.isNullOrEmpty() || view == null) {
            return
        }
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(view)
    }
}

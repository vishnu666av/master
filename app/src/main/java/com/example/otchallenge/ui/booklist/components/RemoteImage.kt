package com.example.otchallenge.ui.booklist.components

import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.Placeholder
import com.bumptech.glide.integration.compose.RequestBuilderTransform
import com.bumptech.glide.integration.compose.Transition
import com.bumptech.glide.load.engine.DiskCacheStrategy

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RemoteImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    // TODO(judds): Consider using separate GlideImage* methods instead of sealed classes.
    // See http://shortn/_x79pjkMZIH for an internal discussion.
    loading: Placeholder? = null,
    failure: Placeholder? = null,
    transition: Transition.Factory? = null,
    // TODO(judds): Consider defaulting to load the model here instead of always doing so below.
    requestBuilderTransform: RequestBuilderTransform<Drawable> = { it },
) {
    GlideImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter =colorFilter,
        loading = loading,
        failure = failure,
        transition = transition,
    ) {
        it.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    }
}

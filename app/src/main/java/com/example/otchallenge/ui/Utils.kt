package com.example.otchallenge.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.otchallenge.data.BookDto
import com.example.otchallenge.model.Book

/**
 * a modifier extension to add shimmering effect to a component.
 */
fun Modifier.loadingShimmer(showShimmer: Boolean): Modifier = composed {
    return@composed if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.1f),
            Color.DarkGray.copy(alpha = 0.3f),
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(1250), repeatMode = RepeatMode.Reverse
            ), label = ""
        )

        this.drawWithContent {
            drawRect(
                Brush.sweepGradient(
                    colors = shimmerColors,
                    center = Offset(translateAnimation.value, 0f)
                )
            )
        }
    } else {
        this.background(
            Brush.linearGradient(
                colors = listOf(Color.Transparent, Color.Transparent),
                start = Offset.Zero,
                end = Offset.Zero
            )
        )
    }
}

/**
 * used in ui and unit testing.
 */
fun Book.Companion.prototype(): Book = Book(
    rank = 1,
    title = "Romeo and Juliet",
    author = "William Shakespeare",
    description = "A tragic love story that follows two young lovers, Romeo Montague and Juliet Capulet, whose families are embroiled in a bitter feud.",
    imageUrl = "",
    buyLinks = listOf()
)

/**
 * maps a [BookDto] object to a [Book]
 */
fun Book.Companion.fromBookDto(bookDto: BookDto): Book =
    Book(
        rank = bookDto.rank,
        title = bookDto.title,
        author = bookDto.author,
        description = bookDto.description,
        imageUrl = bookDto.imageUrl,
        buyLinks = bookDto.buyLinks
    )
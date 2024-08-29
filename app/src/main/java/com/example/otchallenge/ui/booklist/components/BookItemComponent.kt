package com.example.otchallenge.ui.booklist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.placeholder
import com.example.otchallenge.R
import com.example.otchallenge.domain.models.Book
import com.example.otchallenge.ui.corecomponents.RemoteImage
import com.example.otchallenge.ui.theme.AppTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BookItemComponent(
    book: Book,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.extraSmall,
        tonalElevation = 4.dp,
        modifier = modifier.then(Modifier.fillMaxWidth())
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            book.image?.let {
                RemoteImage(
                    url = book.image,
                    contentDescription = book.title,
                    modifier = Modifier
                        .width(50.dp)
                        .align(Alignment.CenterVertically),
                    loading = placeholder(R.drawable.im_placeholder),
                    failure = placeholder(R.drawable.im_placeholder)
                )
            } ?: Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.im_placeholder),
                contentDescription = "placeholder",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                ProvideTextStyle(value = MaterialTheme.typography.titleMedium) {
                    Text(text = book.title)
                }
                ProvideTextStyle(value = MaterialTheme.typography.bodySmall) {
                    Text(text = book.description)
                }
            }

        }
    }
}

@Preview
@Composable
private fun PreviewBookItemComponent() {
    AppTheme {
        BookItemComponent(
            book = Book(
                primaryIsbn10 = "1",
                rank = 1,
                title = "Test title",
                description = "This is a book description",
                image = null
            )
        )
    }
}

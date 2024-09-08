package com.example.otchallenge.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.otchallenge.model.Book

@Composable
fun BookRowItem(
    book: Book,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            modifier = modifier.loadingShimmer(isLoading),
            text = book.title,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(Modifier.size(4.dp))

        Text(
            modifier = modifier.loadingShimmer(isLoading),
            text = "by ${book.author}",
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(Modifier.size(8.dp))

        Text(
            modifier = modifier.loadingShimmer(isLoading),
            text = book.description,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFictionRowItem() {
    MaterialTheme {
        BookRowItem(
            modifier = Modifier.padding(10.dp),
            book = Book.prototype()
        )
    }
}
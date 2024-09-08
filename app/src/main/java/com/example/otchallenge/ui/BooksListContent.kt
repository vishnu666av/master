package com.example.otchallenge.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.otchallenge.R
import com.example.otchallenge.model.Book

@Composable
fun BooksListContent(
    uiState: BooksListUiState,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        BooksListUiState.Idle -> {}
        BooksListUiState.Loading -> LoadingState(modifier = modifier)
        BooksListUiState.Error -> ErrorState()
        is BooksListUiState.Empty -> EmptyState()
        is BooksListUiState.OfflineList -> DataState(items = uiState.items, isOfflineList = true)
        is BooksListUiState.OnlineList -> DataState(items = uiState.items, isOfflineList = false)
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    LazyColumn(modifier.padding(horizontal = 16.dp)) {
        items(3) {
            BookRowItem(
                book = Book.prototype(),
                isLoading = true
            )

            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@Composable
private fun ErrorState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.error_failed_to_fetch_list),
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(16.dp))

        Button(onClick = {}) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                text = stringResource(id = R.string.label_retry),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.error_no_items_found),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(16.dp))

        Button(onClick = {}) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                text = stringResource(id = R.string.label_look_again),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun DataState(
    modifier: Modifier = Modifier,
    items: List<Book>,
    isOfflineList: Boolean
) {
    LazyColumn(modifier.padding(horizontal = 16.dp)) {
        if (isOfflineList) {
            item {

                Text(
                    text = stringResource(id = R.string.label_offline_list),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.size(4.dp))
            }
        }

        items(items) {
            BookRowItem(book = it)

            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoadingState() {
    MaterialTheme {
        LoadingState()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewErrorState() {
    MaterialTheme {
        ErrorState()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEmptyState() {
    MaterialTheme {
        EmptyState()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewOfflineDataState() {
    MaterialTheme {
        DataState(
            items = listOf(
                Book.prototype(),
                Book.prototype(),
                Book.prototype(),
                Book.prototype()
            ),
            isOfflineList = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewOnlineDataState() {
    MaterialTheme {
        DataState(
            items = listOf(
                Book.prototype(),
                Book.prototype(),
                Book.prototype(),
                Book.prototype()
            ),
            isOfflineList = false
        )
    }
}
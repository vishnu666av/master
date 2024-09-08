package com.example.otchallenge.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.otchallenge.R
import com.example.otchallenge.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun BooksListContent(
    uiState: BooksListUiState,
    onRetryCta: () -> Unit,
    modifier: Modifier = Modifier,
    imageLoader: RequestManager? = null
) {
    when (uiState) {
        BooksListUiState.Idle -> {}

        BooksListUiState.Loading -> LoadingState(modifier, imageLoader)

        BooksListUiState.Error -> ErrorState(onRetryCta = onRetryCta, modifier)

        is BooksListUiState.Empty -> EmptyState(onRetryCta = onRetryCta, modifier)

        is BooksListUiState.OfflineList -> DataState(
            items = uiState.items,
            imageLoader = imageLoader,
            isOfflineList = true,
            modifier = modifier
        )

        is BooksListUiState.OnlineList -> DataState(
            items = uiState.items,
            imageLoader = imageLoader,
            isOfflineList = false,
            modifier = modifier
        )
    }
}

@Composable
private fun LoadingState(
    modifier: Modifier = Modifier,
    imageLoader: RequestManager? = null
) {
    LazyColumn(modifier.padding(horizontal = 16.dp)) {
        items(5) {
            BookRowItem(
                book = Book.prototype(),
                imageLoader = imageLoader,
                isLoading = true
            )

            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@Composable
private fun ErrorState(onRetryCta: () -> Unit, modifier: Modifier = Modifier) {
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

        Button(onClick = onRetryCta) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                text = stringResource(id = R.string.label_retry),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun EmptyState(onRetryCta: () -> Unit, modifier: Modifier = Modifier) {
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

        Button(onClick = onRetryCta) {
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
    items: List<Book>,
    modifier: Modifier = Modifier,
    imageLoader: RequestManager? = null,
    isOfflineList: Boolean
) {
    val dimmingAlpha = if (isOfflineList) {
        0.5f
    } else {
        1.0f
    }

    LazyColumn(modifier.padding(horizontal = 16.dp)) {
        item {
            ListTitle(isOfflineList = isOfflineList)

            Spacer(modifier = Modifier.size(16.dp))
        }

        items(items) {
            BookRowItem(book = it, imageLoader = imageLoader, dimmingAlpha = dimmingAlpha)

            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .height(1.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
            )
        }
    }
}

@Composable
private fun ListTitle(isOfflineList: Boolean, modifier: Modifier = Modifier) {
    val title = buildAnnotatedString {
        append(stringResource(id = R.string.label_hard_cover_fictions))
        if (isOfflineList) {
            withStyle(
                style = MaterialTheme.typography.labelLarge.toSpanStyle().copy(
                    color = MaterialTheme.colorScheme.tertiary
                )
            ) {
                append(" (${stringResource(id = R.string.label_offline_list)})")
            }
        }
    }

    Text(
        text = title,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier.padding(horizontal = 16.dp)
    )
}

@Composable
fun BookRowItem(
    book: Book,
    modifier: Modifier = Modifier,
    dimmingAlpha: Float = 1.0f,
    imageLoader: RequestManager? = null,
    isLoading: Boolean = false
) {
    Row(
        modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp)
    ) {

        BookImage(
            imageLoader = imageLoader,
            modifier = Modifier
                .padding(top = 4.dp)
                .loadingShimmer(isLoading)
                .alpha(dimmingAlpha),
            url = book.imageUrl
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {

            Spacer(Modifier.size(4.dp))

            Text(
                modifier = Modifier.loadingShimmer(isLoading),
                text = book.title,
                color = MaterialTheme.colorScheme.primary.copy(dimmingAlpha),
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(Modifier.size(4.dp))

            Text(
                modifier = Modifier.loadingShimmer(isLoading),
                text = "by ${book.author}",
                color = MaterialTheme.colorScheme.tertiary.copy(dimmingAlpha),
                style = MaterialTheme.typography.labelSmall
            )

            Spacer(Modifier.size(8.dp))

            Text(
                modifier = Modifier.loadingShimmer(isLoading),
                text = book.description,
                color = MaterialTheme.colorScheme.secondary.copy(dimmingAlpha),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun BookImage(
    url: String,
    modifier: Modifier = Modifier,
    imageLoader: RequestManager? = null,
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(url) {
        withContext(Dispatchers.IO) {
            imageLoader?.asBitmap()?.load(url)?.addListener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>,
                    isFirstResource: Boolean
                ): Boolean {
                    bitmap = null
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any,
                    target: Target<Bitmap>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    bitmap = resource
                    return false
                }

            })?.submit()
        }
    }

    bitmap?.let {
        BookImageFromBitmap(bitmap = it, modifier = modifier.size(64.dp, 96.dp))
    } ?: BookImagePlaceHolder(modifier = modifier.size(64.dp, 96.dp))
}

@Composable
private fun BookImageFromBitmap(
    bitmap: Bitmap,
    modifier: Modifier = Modifier
) {
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = null,
        modifier = modifier.size(64.dp, 96.dp),
        contentScale = ContentScale.Fit
    )
}

@Composable
private fun BookImagePlaceHolder(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.book_image_placeholder),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
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
        ErrorState(onRetryCta = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEmptyState() {
    MaterialTheme {
        EmptyState(onRetryCta = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBookRowItem() {
    MaterialTheme {
        BookRowItem(
            modifier = Modifier.padding(10.dp),
            book = Book.prototype()
        )
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
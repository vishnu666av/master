package com.example.otchallenge.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.example.otchallenge.R
import com.example.otchallenge.data.models.Book
import com.example.otchallenge.ui.theme.Typography

@Composable
fun BookListScreen(modifier: Modifier) {
    val viewModel: BookListViewModel = hiltViewModel()
    val resultState = viewModel.resultState.collectAsState().value
    BookListContent(resultState, modifier)
}

@Composable
internal fun BookListContent(
    resultState: ListResultUiState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (resultState) {
            ListResultUiState.ErrorOccurred ->
                item {
                    ErrorState()
                }

            ListResultUiState.Loading -> item { LoadingState() }
            is ListResultUiState.Success -> {
                if (resultState.isEmpty()) {
                    item { EmptyListState() }
                } else {
                    items(resultState.items) {
                        BookListItem(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingState() {
    CircularProgressIndicator()
}

@Composable
private fun ErrorState() {
    Text(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
        text = stringResource(id = R.string.text_empty_loading_failed_state),
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun EmptyListState() {
    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = stringResource(id = R.string.text_empty_list_state),
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun BookListItem(book: Book) {
    val thumbnailWidth = 100.dp
    val thumbnailHeight = 120.dp
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BookThumbnail(
            modifier =
                Modifier
                    .align(Alignment.CenterVertically)
                    .width(thumbnailWidth)
                    .height(thumbnailHeight),
            imageUrl = book.bookImageUrl,
        )

        Spacer(modifier = 12.dp.width)

        Column {
            Text(text = book.title.uppercase(), style = Typography.bodyLarge)
            Spacer(modifier = 8.dp.height)

            Text(
                text = stringResource(R.string.label_author, book.author),
                style = Typography.labelSmall,
            )
            Spacer(modifier = 12.dp.height)
            Text(
                text = book.description,
                style = Typography.bodyMedium,
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun BookThumbnail(
    imageUrl: String?,
    modifier: Modifier = Modifier,
) {
    GlideSubcomposition(modifier = modifier, model = imageUrl) {
        when (state) {
            RequestState.Failure -> {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(color = Color.White),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.label_image_placeholder),
                        color = Color.Black,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            RequestState.Loading -> LoadingState()
            is RequestState.Success ->
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painter,
                    contentDescription = stringResource(R.string.label_image_thumbnail_description),
                    contentScale = ContentScale.FillWidth,
                )
        }
    }
}

inline val Dp.width: Modifier get() = Modifier.width(this)
inline val Dp.height: Modifier get() = Modifier.height(this)

@Preview
@Composable
fun ListItemPreview() {
    BookListItem(
        Book(
            "A fancy title",
            "A young woman’s play about her ancestor Emilia Bassano, who wrote Shakespeare’s works, is submitted to a festival under a male pseudonym.",
            "Best Author",
            null,
        ),
    )
}

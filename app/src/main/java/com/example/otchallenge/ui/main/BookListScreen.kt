package com.example.otchallenge.ui.main

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.example.otchallenge.R
import com.example.otchallenge.data.database.BookEntity
import com.example.otchallenge.data.database.toModel
import com.example.otchallenge.data.models.Book
import com.example.otchallenge.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BookListScreen(
    presenter: BookListContract.BookListPresenter,
    view: BookListContract.BookListView,
    modifier: Modifier,
) {
    val viewModel: BookListViewModel = hiltViewModel()
    val (page, setPage) =
        remember {
            mutableIntStateOf(0)
        }

    var isRefreshing by remember { mutableStateOf(false) }

    LifecycleStartEffect(Unit) {
        presenter.attachView(view)

        onStopOrDispose {
            presenter.detachView()
        }
    }

    LaunchedEffect(Unit, page) {
        presenter.fetchBooks(page)
    }

    val loading = view.loading.collectAsState().value
    val error = view.error.collectAsState().value
    val data = view.paging.collectAsLazyPagingItems()

    val pullToRefreshState =
        remember {
            object : PullToRefreshState {
                private val anim = Animatable(0f, Float.VectorConverter)

                override val distanceFraction
                    get() = anim.value

                override suspend fun animateToThreshold() {
                    anim.animateTo(1f, spring(dampingRatio = Spring.DampingRatioHighBouncy))
                }

                override suspend fun animateToHidden() {
                    anim.animateTo(0f)
                }

                override suspend fun snapTo(targetValue: Float) {
                    anim.snapTo(targetValue)
                }
            }
        }
    BookListContent(
        loading = loading,
        error = error,
        data = data,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            data.refresh()
            isRefreshing = false
        },
        modifier = modifier,
        pullToRefreshState = pullToRefreshState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BookListContent(
    loading: Boolean,
    error: Boolean,
    data: LazyPagingItems<BookEntity>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    gridState: LazyGridState = rememberLazyGridState(),
    pullToRefreshState: PullToRefreshState = rememberPullToRefreshState(),
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (loading) {
            LoadingState()
        } else {
            run {}
        }
        if (error) {
            ErrorState()
        } else {
            run {}
        }
        AdaptiveGrid(
            gridState = gridState,
            pullToRefreshState = pullToRefreshState,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
        ) {
            items(data.itemCount, key = data.itemKey { it.isbn }) { index ->
                val bookItem = data[index]
                if (bookItem != null) {
                    BookListItem(bookItem.toModel())
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveGrid(
    gridState: LazyGridState,
    pullToRefreshState: PullToRefreshState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    scope: LazyGridScope.() -> Unit,
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = pullToRefreshState,
    ) {
        LazyVerticalGrid(columns = GridCells.FixedSize(400.dp), state = gridState) {
            scope()
        }
    }
}

@Composable
private fun LoadingState() {
    CircularProgressIndicator()
}

@Composable
private fun ErrorState(message: String = stringResource(id = R.string.text_empty_loading_failed_state)) {
    Text(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
        text = message,
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
            "ISBN",
            1,
            "A fancy title",
            "A young woman’s play about her ancestor Emilia Bassano, who wrote Shakespeare’s works, is submitted to a festival under a male pseudonym.",
            "Best Author",
            null,
        ),
    )
}

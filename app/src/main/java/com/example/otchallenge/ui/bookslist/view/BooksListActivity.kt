package com.example.otchallenge.ui.bookslist.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.example.otchallenge.BooksApp
import com.example.otchallenge.ui.bookslist.presenter.BooksListUiState
import com.example.otchallenge.ui.bookslist.presenter.IBooksListPresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

class BooksListActivity : ComponentActivity(), BookListView {

    @Inject
    lateinit var imageLoader: RequestManager

    @Inject
    lateinit var presenter: IBooksListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as BooksApp).appComponent.inject(this)

        setContent {
            enableEdgeToEdge()
            MaterialTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(WindowInsets.systemBars.asPaddingValues())
                ) {
                    val uiState by presenter.uiState.observeAsState()

                    Crossfade(
                        targetState = uiState,
                        label = this.javaClass.simpleName
                    ) {
                        BooksListContent(
                            uiState = it ?: BooksListUiState.Error,
                            imageLoader = imageLoader,
                            onRetryCta = {
                                lifecycleScope.launch { presenter.getBooks() }
                            },
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                }
            }
        }

        if (savedInstanceState == null) {
            presenter.onViewAttached(this@BooksListActivity)
            lifecycleScope.launch { presenter.getBooks() }
        }
    }

    override fun getLifecycleOwner(): LifecycleOwner = this
}

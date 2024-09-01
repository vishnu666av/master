package com.example.otchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.otchallenge.ui.main.BookListContract
import com.example.otchallenge.ui.main.BookListScreen
import com.example.otchallenge.ui.theme.OTCBookTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var bookListPresenter: BookListContract.BookListPresenter

    @Inject
    lateinit var bookListView: BookListContract.BookListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OTCBookTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BookListScreen(
                        bookListPresenter,
                        bookListView,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

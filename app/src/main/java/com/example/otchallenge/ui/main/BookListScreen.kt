package com.example.otchallenge.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.otchallenge.R

@Composable
fun BookListScreen(modifier: Modifier) {
    BookListContent(modifier)
}

@Composable
internal fun BookListContent(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        EmptyListState()
    }
}

@Composable
internal fun EmptyListState() {
    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = stringResource(id = R.string.text_empty_list_state),
        textAlign = TextAlign.Center,
    )
}

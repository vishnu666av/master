package com.example.otchallenge.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.otchallenge.model.Fiction

@Composable
fun FictionsListContent(
    fictions: List<Fiction>,
    modifier: Modifier = Modifier
) {

    LazyColumn(modifier) {
        items(fictions) {
            FictionRowItem(fiction = it)
        }
    }
}
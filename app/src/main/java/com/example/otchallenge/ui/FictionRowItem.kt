package com.example.otchallenge.ui

import android.content.res.Configuration
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
import com.example.otchallenge.model.Fiction

@Composable
fun FictionRowItem(fiction: Fiction, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = modifier,
            text = fiction.title,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(Modifier.size(4.dp))

        Text(
            modifier = modifier,
            text = "by ${fiction.author}",
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(Modifier.size(8.dp))

        Text(
            modifier = modifier,
            text = fiction.description,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun PreviewFictionRowItem() {
    MaterialTheme {
        FictionRowItem(
            fiction = Fiction(
                rank = 1,
                title = "Romeo and Juliet",
                author = "William Shakespeare",
                description = "A tragic love story that follows two young lovers, Romeo Montague and Juliet Capulet, whose families are embroiled in a bitter feud. Despite their families’ hatred for each other, Romeo and Juliet secretly marry. However, a series of misunderstandings and a tragic chain of events lead to the deaths of both lovers. Their deaths ultimately reconcile their feuding families.",
                imageUrl = ""
            )
        )
    }
}
package com.example.otchallenge.ui.corecomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.otchallenge.R

@Composable
fun RecoverableError(
    message: @Composable () -> Unit,
    title: @Composable () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.then(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProvideTextStyle(value = MaterialTheme.typography.titleMedium) {
            title()
        }
        ProvideTextStyle(value = MaterialTheme.typography.bodySmall) {
            message()
        }
        TextButton(onClick = onRetry) {
            Text(text = stringResource(R.string.recoverable_error_retry))
        }
    }

}

@Preview
@Composable
private fun PreviewRecoverableError() {
    AppTheme {
        Surface {
            RecoverableError(
                title = { Text(text = "Error fetching something") },
                message = { Text(text = "Check your connection and try again") },
                onRetry = { /*TODO*/ }
            )
        }
    }
}

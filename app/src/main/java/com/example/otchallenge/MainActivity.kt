package com.example.otchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.otchallenge.ui.FictionsListContent
import com.example.otchallenge.ui.FictionsListUiState
import com.example.otchallenge.ui.FictionsListViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: FictionsListViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as MyApplication).appComponent.inject(this)

        setContent {
            enableEdgeToEdge()
            MaterialTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(WindowInsets.systemBars.asPaddingValues())
                ) {
                    // todo: remember to use CrossFade for animations
                    when (val uiState = viewModel.uiState.collectAsState().value) {
                        FictionsListUiState.Idle -> {}
                        FictionsListUiState.Loading -> {}
                        FictionsListUiState.Error -> {}
                        is FictionsListUiState.Empty -> {}
                        is FictionsListUiState.OfflineData -> FictionsListContent(fictions = uiState.items)
                        is FictionsListUiState.OnlineData -> FictionsListContent(fictions = uiState.items)
                    }
                }
            }
        }
    }
}

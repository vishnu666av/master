package com.example.otchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.otchallenge.ui.main.BookListScreen
import com.example.otchallenge.ui.theme.OTCBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OTCBookTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BookListScreen(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

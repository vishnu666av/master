package com.example.otchallenge.booklist

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.otchallenge.MyApplication
import com.example.otchallenge.R
import com.example.otchallenge.booklist.uistate.BooksContentUIState
import javax.inject.Inject

class BookListActivity : AppCompatActivity(), BookListView {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    @Inject
    lateinit var presenter: BookListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ** Inject dependencies **
        (application as MyApplication).appComponent
			.bookListComponentBuilder()
			.withContext(this)
            .withView(this)
            .build()
			.inject(this)

        // ** Setup UI **
        enableEdgeToEdge()
        setContentView(R.layout.activity_book_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun updateLoadingState(loadingState: LoadingState<BooksContentUIState>) {
        TODO("Not yet implemented")
    }

    override val ownerLifecycleScope: LifecycleCoroutineScope
        get() = lifecycleScope
}

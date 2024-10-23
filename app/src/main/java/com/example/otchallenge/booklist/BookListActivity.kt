package com.example.otchallenge.booklist

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.otchallenge.MyApplication
import com.example.otchallenge.R
import com.example.otchallenge.booklist.adapter.BookListAdapter
import com.example.otchallenge.booklist.uistate.BooksContentUIState
import com.example.otchallenge.databinding.ActivityBookListBinding
import com.example.otchallenge.utils.recyclerview.SpacingItemDecoration
import com.example.otchallenge.utils.recyclerview.autoFitLayout
import java.net.UnknownHostException
import javax.inject.Inject

class BookListActivity : AppCompatActivity(), BookListView {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    @Inject
    lateinit var presenter: BookListPresenter

    private lateinit var binding: ActivityBookListBinding

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

        binding = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ** Setting up the recycler view **
        binding.recyclerView.autoFitLayout(
            resources.getDimension(R.dimen.recycler_view_column_width).toDouble()
        )
        binding.recyclerView.addItemDecoration(
            SpacingItemDecoration(resources.getDimension(R.dimen.recycler_view_padding).toInt())
        )

        // ** Load books
        presenter.loadBooks()
    }

    override fun updateLoadingState(loadingState: LoadingState<BooksContentUIState>) {
        when (loadingState) {
            is LoadingState.Error -> {
                binding.errorContainer.visibility = View.VISIBLE
                binding.progressBarContainer.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE

                if (loadingState.throwable is UnknownHostException) {
                    binding.errorImageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.twotone_cell_tower_80, null))
                    binding.errorTextView.text = getString(R.string.no_internet_connection_error)
                } else {
                    binding.errorImageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.twotone_error_80, null))
                    binding.errorTextView.text = getString(R.string.fetch_error_display)
                }
            }

            LoadingState.Loading -> {
                binding.progressBarContainer.visibility = View.VISIBLE
                binding.errorContainer.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE
            }

            is LoadingState.Ready -> {
                binding.recyclerView.visibility = View.VISIBLE
                binding.progressBarContainer.visibility = View.GONE
                binding.errorContainer.visibility = View.GONE

                loadingState.content?.let { content ->
                    binding.recyclerView.adapter = BookListAdapter(content.books)
                }
            }
        }
    }

    override val ownerLifecycleScope: LifecycleCoroutineScope
        get() = lifecycleScope
}

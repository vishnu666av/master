package com.example.otchallenge.booklist

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import com.example.otchallenge.MyApplication
import com.example.otchallenge.R
import com.example.otchallenge.booklist.adapter.BookListAdapter
import com.example.otchallenge.booklist.adapter.HeaderAdapter
import com.example.otchallenge.booklist.uistate.BooksContentUIState
import com.example.otchallenge.booklist.uistate.LoadingState
import com.example.otchallenge.databinding.ActivityBookListBinding
import com.example.otchallenge.utils.recyclerview.SpacingItemDecoration
import com.example.otchallenge.utils.recyclerview.autoFitLayout
import com.google.android.material.snackbar.Snackbar
import java.net.UnknownHostException
import javax.inject.Inject

class BookListActivity : AppCompatActivity(), BookListView {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    @Inject
    lateinit var presenter: BookListPresenter

    private lateinit var binding: ActivityBookListBinding

    private lateinit var bookListAdapter : BookListAdapter
    private lateinit var headerAdapter : HeaderAdapter

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

        // ** Setting up the recycler view and its adapters**
        binding.recyclerView.autoFitLayout(
            resources.getDimensionPixelSize(R.dimen.recycler_view_column_width)
        )
        binding.recyclerView.addItemDecoration(
            SpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.recycler_view_padding))
        )

        // ** Concatenating adapters to the recycler view **
        bookListAdapter = BookListAdapter()
        headerAdapter = HeaderAdapter()
        binding.recyclerView.adapter = ConcatAdapter(headerAdapter, bookListAdapter)

        // ** Retry button
        binding.retryButton.setOnClickListener {
            presenter.reloadIfNeeded()
        }

        // ** Load books **
        presenter.loadBooks()
    }

    override fun onResume() {
        super.onResume()
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), connectivityManagerCallback)
    }

    override fun onPause() {
        super.onPause()

        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
    }

    override fun deliverState(state: BooksContentUIState) {
        when (state.loadingState) {
            is LoadingState.Error -> {
                binding.errorContainer.visibility = View.VISIBLE
                binding.progressBarContainer.visibility = View.GONE
                binding.recyclerView.visibility = View.GONE

                // ** Display the correct error message **
                if (state.loadingState.throwable is UnknownHostException) {
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

                headerAdapter.update(state.listName, state.lastModified)
                bookListAdapter.updateBooks(state.books)
            }
        }
    }

    override val ownerLifecycleScope: LifecycleCoroutineScope
        get() = lifecycleScope

    private val connectivityManagerCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            runOnUiThread {
                presenter.reloadIfNeeded()
            }
        }

        override fun onLost(network: Network) {
            Snackbar.make(binding.root, getString(R.string.internet_lost), Snackbar.LENGTH_SHORT).show()
        }
    }
}

package com.example.otchallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.otchallenge.databinding.ActivityMainBinding
import com.example.otchallenge.modules.DataProvider
import com.example.otchallenge.model.BookModel
import com.example.otchallenge.modules.ImageProvider
import com.example.otchallenge.presenter.BooksPresenter
import com.example.otchallenge.presenter.BooksPresenterInterface
import com.example.otchallenge.ui.BooksAdapter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), BooksPresenterInterface {

    @Inject
    lateinit var imageProvider: ImageProvider

    @Inject
    lateinit var dataProvider: DataProvider

    private lateinit var binding: ActivityMainBinding

    private lateinit var presenter: BooksPresenter

    private lateinit var adapter: BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initUi()
    }

    private fun initUi() {
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        adapter = BooksAdapter(imageProvider)
        binding.recyclerView.adapter = adapter
        binding.retryButton.setOnClickListener {
            // retry to get the information
            dataProvider.getBooks()
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            // refresh the information
            dataProvider.getBooks()
        }
        // due the strong reference between ui and presenter, presenter should live
        // only in the lifecycle scope with the view
        presenter = BooksPresenter(this@MainActivity)
        dataProvider.booksData.observe(this@MainActivity) {
            // display the information with the presenter
            presenter.presentUi(it)
        }
        // as view has been created we can request the initial data
        dataProvider.getBooks()
    }

    override fun displayBooks(books: List<BookModel>) {
        adapter.updateData(books)
        binding.retryButton.isVisible = false
    }

    override fun displayLoader(showLoader: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = showLoader
    }

    override fun displayError(message: String?) {
        binding.swipeRefreshLayout.isRefreshing = false
        binding.retryButton.isVisible = true
        message?.let {
            Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
        }
    }
}

package com.example.otchallenge.mvpui

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.otchallenge.R
import com.example.otchallenge.databinding.ActivityMainBinding
import com.example.otchallenge.domain.errors.AppError
import com.example.otchallenge.domain.models.Book
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BookListContract.View {

    private lateinit var binding: ActivityMainBinding
    private val adapter = BooksAdapter()

    @Inject
    lateinit var presenter: BookListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViews()
        presenter.init(this, lifecycleScope)
        presenter.loadBooks()
    }

    override fun setBooks(books: List<Book>) {
        adapter.submitList(books)
        binding.bookList.isVisible = true
        binding.loading.isVisible = false
        binding.recoverableError.isVisible = false
    }

    override fun setLoading() {
        binding.loading.isVisible = true
        binding.recoverableError.isVisible = false
        binding.bookList.isVisible = false
    }

    override fun setError(throwable: Throwable) {
        if (throwable is AppError.Http) {
            binding.errorMessage.setText(R.string.http_error_message)
        } else {
            binding.errorMessage.setText(R.string.network_error_message)
        }
        binding.recoverableError.isVisible = true
        binding.bookList.isVisible = false
        binding.loading.isVisible = false

    }

    private fun setupViews() {
        binding.bookList.adapter = adapter
        binding.bookList.layoutManager = LinearLayoutManager(this)
        (binding.retryButton as Button).setOnClickListener {
            presenter.loadBooks()
        }
    }
}

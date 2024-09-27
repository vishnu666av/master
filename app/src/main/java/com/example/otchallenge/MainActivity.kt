package com.example.otchallenge

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.otchallenge.databinding.ActivityMainBinding
import com.example.otchallenge.model.Book
import com.example.otchallenge.presenter.BooksContract
import com.example.otchallenge.presenter.BooksPresenter
import com.example.otchallenge.repository.BookRepository
import com.example.otchallenge.view.BooksAdapter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), BooksContract.View {

	@Inject
	lateinit var repository: BookRepository

	private lateinit var presenter: BooksPresenter
	private lateinit var adapter: BooksAdapter

	//viewBinding
	private lateinit var mainBinding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		(application as MyApplication).appComponent.inject(this)
		super.onCreate(savedInstanceState)

		mainBinding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(mainBinding.root)

		presenter = BooksPresenter(repository)
		presenter.attach(this)

		setupRecyclerView()

		// Fetch the books when the activity is created
		presenter.fetchBooks()
	}

	// RecyclerView, Adapter setting
	private fun setupRecyclerView(){
		adapter = BooksAdapter(emptyList())
		mainBinding.recyclerView.apply {
			layoutManager = LinearLayoutManager(this@MainActivity)
			adapter = this@MainActivity.adapter
		}
	}

	override fun showProgress() {
		mainBinding.progressBar.visibility = View.VISIBLE
	}

	override fun hideProgress() {
		println("hideProgress()")
		mainBinding.progressBar.visibility = View.GONE
	}

	override fun displayBooks(books: List<Book>) {
		adapter.updateBooks(books)
		mainBinding.recyclerView.visibility = View.VISIBLE
	}

	override fun showError(message: String) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show()
	}

	override fun onDestroy() {
		presenter.detach()
		super.onDestroy()
	}
}

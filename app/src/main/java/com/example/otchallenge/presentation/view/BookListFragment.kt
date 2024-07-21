package com.example.otchallenge.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.otchallenge.MyApplication
import com.example.otchallenge.R
import com.example.otchallenge.presentation.model.BookDetailPresentation
import com.example.otchallenge.presentation.model.BookPresentation
import com.example.otchallenge.presentation.presenter.BookPresenter
import javax.inject.Inject

class BookListFragment : Fragment(), BookView {

    @Inject
    lateinit var presenter: BookPresenter

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookAdapter
    private lateinit var emptyView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_list, container, false)
        recyclerView = view.findViewById(R.id.recycler_book_list)
        emptyView = view.findViewById(R.id.empty_view)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)


        adapter = BookAdapter { book ->
            val action =
                BookListFragmentDirections.actionBookListFragmentToBookDetailFragment(book.id)
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        swipeRefreshLayout.setOnRefreshListener {
            presenter.loadBooks()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        presenter.loadBooks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
        presenter.clearDisposables()
    }

    override fun showBooks(books: List<BookPresentation>) {
        swipeRefreshLayout.isRefreshing = false
        if (books.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
            emptyView.text = "No books available at the moment. Please try again later."
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
            adapter.submitList(books)
        }
    }

    override fun showBookDetails(book: BookDetailPresentation) {
        TODO("Not yet implemented")
    }

    override fun showError(error: String) {
        TODO("Not yet implemented")
    }
}
package com.example.otchallenge.ui.book.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import com.example.otchallenge.R
import com.example.otchallenge.databinding.FragmentBookListBinding
import com.example.otchallenge.ui.book.viewmodel.BookListViewModel
import com.example.otchallenge.ui.book.adapter.BookListAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Displays a list of books in a RecyclerView.
 * Utilizes a ViewModel to observe UI state changes and manage data.
 */
@AndroidEntryPoint
class BookListFragment : Fragment() {

    // Adapter for the RecyclerView to display book list.
    private lateinit var bookListAdapter: BookListAdapter

    // Binding object to access the layout's views.
    private lateinit var binding: FragmentBookListBinding

    // ViewModel that holds the UI state and business logic.
    private val viewModel: BookListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchBooks()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookListBinding.inflate(inflater, container, false)
        bookListAdapter = BookListAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initTryAgain()
        initSwipeRefresh()
        initObservables()
        initFabMenu()
    }

    private fun initRecyclerView() {
        binding.bookListRv.apply {
            adapter = bookListAdapter
        }
    }

    private fun initObservables() {
        // Observes the list of book and updates the RecyclerView.
        viewModel.uiState.observe(viewLifecycleOwner) {
            if (it.items.isEmpty()) {
                binding.pageStateContainer.pageState = viewModel.determinePageState()
            } else {
                bookListAdapter.updateItemList(it.items)
                binding.swipeContainer.isRefreshing = false
                binding.pageStateContainer.pageState = null
            }
        }
    }

    private fun initFabMenu() {
        binding.fab.setOnClickListener { view ->
            PopupMenu(requireContext(), view).apply {
                menuInflater.inflate(R.menu.fab_menu, menu)
                setOnMenuItemClickListener { item ->
                    viewModel.setPageStateOption(item.itemId)
                    dismiss() // Dismiss the popup to reflect the changes immediately
                    true
                }
                show()
            }
        }
    }

    private fun initTryAgain() {
        binding.pageStateContainer.tryAgainButton.setOnClickListener {
            viewModel.fetchBooks(forceRefresh = true)
        }
    }

    private fun initSwipeRefresh() {
        binding.swipeContainer.setOnRefreshListener {
            viewModel.refreshBookList()
        }
    }
}
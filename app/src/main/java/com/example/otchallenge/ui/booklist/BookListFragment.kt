package com.example.otchallenge.ui.booklist

import android.app.ProgressDialog.show
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.BundleCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.otchallenge.MyApplication
import com.example.otchallenge.R
import com.example.otchallenge.data.Book
import com.example.otchallenge.databinding.FragmentBookListBinding
import com.example.otchallenge.util.logDebug
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope

class BookListFragment : Fragment(), BookListView {

    companion object {
        private const val RECYCLER_VIEW_STATE = "recycler_view_state"
        private const val TAG = "BookListFragment"

        /**
         * When lastVisibleItemPosition is these many items away from the total count,
         * load more data. This is for imitating pre-load behavior aggressively rather than waiting
         * for user's scroll reaching up to the end. When user fast scrolls, this would help
         */
        private const val THRESHOLD_FOR_LOAD_MORE = 5
    }

    @Inject
    lateinit var presenter: BookListPresenter

    /**
     * Not a fan of this naming style but it's what officially documented
     * https://developer.android.com/topic/libraries/view-binding#fragments so using it here
     */
    private var _binding: FragmentBookListBinding? = null

    /**
     * This property is only valid between onCreateView and onDestroyView.
     */
    private val binding get() = _binding!!

    private lateinit var bookListRecyclerViewAdapter: BookListRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        logDebug("BookListFragment - onCreateView")
        _binding = FragmentBookListBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Handle saving state of the LayoutManager so when device is rotated, we don't lose the
     * last scroll position
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.list.layoutManager?.onSaveInstanceState()
        binding.list.layoutManager?.let {
            outState.putParcelable(RECYCLER_VIEW_STATE, it.onSaveInstanceState())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logDebug("$TAG - onViewCreated")
        (requireActivity().application as MyApplication).appComponent.inject(this)
        presenter.attachView(this)
        val columnCount = resources.getInteger(R.integer.column_count)
        bookListRecyclerViewAdapter = BookListRecyclerViewAdapter()
        val recyclerViewLayoutManager = when {
            columnCount == 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }
        binding.list.setup(recyclerViewLayoutManager, columnCount)
        if (savedInstanceState == null) {
            presenter.loadInitialData()
        } else {
            presenter.loadMoreData()
        }
    }

    private fun RecyclerView.setup(linearLayoutManager: LinearLayoutManager, columnCount: Int) {
        layoutManager = linearLayoutManager
        adapter = bookListRecyclerViewAdapter
        val itemDecoration = if (columnCount == 1) {
            BookListItemLinearDecoration()
        } else {
            BookListItemGridDecoration()
        }
        addItemDecoration(itemDecoration)
        addOnScrollListener(
            LoadMoreScrollListener(
                bottomThreshold = THRESHOLD_FOR_LOAD_MORE,
                isLoading = presenter::isLoading,
                hasMoreData = presenter::hasMoreData,
                loadMoreData = presenter::loadMoreData
            )
        )
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val state = savedInstanceState?.let {
            BundleCompat.getParcelable(
                savedInstanceState,
                RECYCLER_VIEW_STATE,
                Parcelable::class.java
            )
        }
        binding.list.layoutManager?.onRestoreInstanceState(state)
    }

    override fun onDestroyView() {
        logDebug("$TAG - onDestroyView")
        presenter.detachView()
        super.onDestroyView()
        _binding = null
    }

    override fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

    override fun addBooks(books: List<Book>) {
        bookListRecyclerViewAdapter.addBooks(books)
    }

    override fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).run {
            addCallback(object : Snackbar.Callback() {
                override fun onShown(sb: Snackbar?) {
                    CountingIdlingResourceProvider.increment()
                }

                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    CountingIdlingResourceProvider.decrement()
                }
            })
            show()
        }
    }

    override fun getLifecycleScope(): CoroutineScope = viewLifecycleOwner.lifecycleScope
}

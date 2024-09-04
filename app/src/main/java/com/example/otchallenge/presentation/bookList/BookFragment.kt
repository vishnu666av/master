package com.example.otchallenge.presentation.bookList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.otchallenge.MyApplication
import com.example.otchallenge.R
import com.example.otchallenge.common.adapter.DelegationAdapter
import com.example.otchallenge.common.util.ConnectivityProvider
import com.example.otchallenge.databinding.FragmentBookBinding
import com.example.otchallenge.presentation.bookList.model.BookItemModel
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class BookFragment : Fragment(), BookListContract.View {

    @Inject
    lateinit var presenter: BookListContract.Presenter

    @Inject
    lateinit var networkProvider: ConnectivityProvider

    private lateinit var adapter: DelegationAdapter

    private var _binding: FragmentBookBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)
        presenter.loadBooks()
        setupRecyclerView()

        binding.bookViewState.btnTryAgain.setOnClickListener {
            binding.bookViewState.rootView.visibility = View.GONE
            presenter.loadBooks()
        }
    }

    private fun setupRecyclerView() {
        val delegates = listOf(
            BookDelegateAdapter(),
        )
        adapter = DelegationAdapter(delegates)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
        _binding = null
    }

    override fun showBooks(books: List<BookItemModel>) {
        if(books.isEmpty() && !networkProvider.isNetworkAvailable()) {
            showError(getString(R.string.network_error))
        } else {
            adapter.setItems(books)
        }
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun showError(message: String) {
        binding.bookViewState.rootView.visibility = View.VISIBLE
        binding.bookViewState.tvErrorMsg.text = message
    }
}
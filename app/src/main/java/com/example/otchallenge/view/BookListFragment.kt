package com.example.otchallenge.view

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.otchallenge.MyApplication
import com.example.otchallenge.databinding.FragmentBookListBinding
import com.example.otchallenge.model.Book
import com.example.otchallenge.presenter.BookListPresenter
import com.example.otchallenge.presenter.BookListView
import com.example.otchallenge.utils.CacheUtils
import com.example.otchallenge.utils.NetworkUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class BookListFragment: Fragment(), BookListView {

    @Inject
    lateinit var presenter: BookListPresenter
    private var _binding: FragmentBookListBinding? = null
    private val binding get() = _binding!!
    private val disposables = CompositeDisposable()
    private val foldStateSubject = BehaviorSubject.create<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        foldStateSubject.onNext(getSpanCount())
        observeFoldStateChanges()
        presenter.attachListView(this)
        loadBooks()
        //Log.d("BookListFragment", "Is button clickable: ${binding.btnTryAgain.isClickable}")

        binding.btnTryAgain.setOnClickListener{
           // Log.d("BookListFragment", "try again clicked")
            loadBooks()
        }
    }

    private fun setupRecyclerView() {
        binding.booksRecyclerView.layoutManager = createLayoutManager(getSpanCount())
    }

    private fun createLayoutManager(spanCount: Int) =
        if (spanCount == 1) LinearLayoutManager(context) else GridLayoutManager(context, spanCount)

    private fun observeFoldStateChanges() {
        val disposable = foldStateSubject
            .distinctUntilChanged()
            .subscribe { spanCount ->
                binding.booksRecyclerView.layoutManager = createLayoutManager(spanCount)
            }
        disposables.add(disposable)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        foldStateSubject.onNext(getSpanCount())
    }
    private fun loadBooks(){
        context?.let {
            if (NetworkUtils.isNetworkAvailable(it)) {
           // Log.d("BookListFragment", "Network Available")
            showLoading(true, false)
            presenter.fetchBooks("KoRB4K5LRHygfjCL2AH6iQ7NeUqDAGAB",0, requireContext())
        }else {
            //Log.d("BookListFragment", "Network not Available")
            showCachedBooks()
        }
    }}

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
        _binding = null
        disposables.clear()
    }

    private fun cacheBooks(books: List<Book>){
        val booksJson = Gson().toJson(books)
        CacheUtils.saveBooks(requireContext(), booksJson)
    }

    override fun onBooksFetched(books: List<Book>) {
        //Log.d("BookListFragment", "Books fetched: ${books.size}")

        binding.booksRecyclerView.adapter = BookListAdapter(books){ book ->
           //Log.d("BookListFragment", "Navigating with book rank: ${book.primary_isbn10}")
            val action = BookListFragmentDirections.actionBookListFragmentToBookDetailFragment(book.rank)
            findNavController().navigate(action)
        }
        cacheBooks(books)
        showLoading(isLoading = false, errorMessage = false)
    }

    override fun showError(message: String) {
        showLoading(isLoading = false, errorMessage = true)
    }

    private fun showCachedBooks(){
        val booksJson = CacheUtils.getBooks(requireContext())
        booksJson?.let {
            val booksType = object: TypeToken<List<Book>>() {}.type
            val books: List<Book> = Gson().fromJson(booksJson, booksType)
            onBooksFetched(books)
        } ?: showError("No Cached data available")
    }

    private fun showLoading(isLoading: Boolean, errorMessage: Boolean){
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
        binding.tvErrorMsg.visibility = if(!isLoading && errorMessage) View.VISIBLE else View.GONE
        binding.btnTryAgain.visibility = if(!isLoading && errorMessage) View.VISIBLE else View.GONE
        binding.booksRecyclerView.visibility = if(isLoading) View.GONE else View.VISIBLE
    }

    private fun getSpanCount(): Int {
        return if (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE) 2 else 1
    }

}
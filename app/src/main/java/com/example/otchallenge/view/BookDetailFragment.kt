package com.example.otchallenge.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.otchallenge.R
import com.example.otchallenge.databinding.FragmentBookDetailBinding
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.otchallenge.MyApplication
import com.example.otchallenge.model.Book
import com.example.otchallenge.presenter.BookDetailView
import com.example.otchallenge.presenter.BookListPresenter
import javax.inject.Inject


class BookDetailFragment: Fragment(), BookDetailView {

    @Inject
    lateinit var presenter: BookListPresenter
    private lateinit var binding: FragmentBookDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_book_detail,container,false)
        (activity?.application as MyApplication).appComponent.inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: BookDetailFragmentArgs by navArgs()
        val bookId = args.bookId
        presenter.attachDetailView(this)
        presenter.getBookDetails(bookId, requireContext())
    }

    override fun onBookDetailsFetched(book: Book) {
        binding.book = book
    }

    override fun showError(message: String) {
        Log.e("BookDetailFragment", "Error $message")
    }
}
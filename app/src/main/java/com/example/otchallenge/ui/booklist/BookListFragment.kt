package com.example.otchallenge.ui.booklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.otchallenge.R
import com.example.otchallenge.data.FakeData
import com.example.otchallenge.databinding.FragmentBookListBinding

class BookListFragment : Fragment() {

    // Not a fan of this naming style but it's what officially documented
    // https://developer.android.com/topic/libraries/view-binding#fragments so using it here
    private var _binding: FragmentBookListBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val columnCount = resources.getInteger(R.integer.column_count)
        _binding = FragmentBookListBinding.inflate(inflater, container, false)
        val view = binding.root
        with(view) {
            layoutManager = when {
                columnCount == 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = BookListRecyclerViewAdapter(FakeData.books)
            addItemDecoration(BookListItemLinearDecoration().takeIf { columnCount == 1 }
                ?: BookListItemGridDecoration())
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
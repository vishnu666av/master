package com.example.otchallenge.utils

import android.content.Context
import android.content.SharedPreferences

object CacheUtils {

    private const val PREFS_NAME = "BookCache"
    private const val BOOK_LIST_KEY = "book_list"

    fun saveBooks(context: Context, booksJson: String){
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(BOOK_LIST_KEY, booksJson).apply()
    }

    fun getBooks(context: Context): String?{
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(BOOK_LIST_KEY, null)
    }
}
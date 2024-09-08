package com.example.otchallenge.data.local

import androidx.room.TypeConverter
import com.example.otchallenge.model.BuyLink
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class BuyLinkListTypeConverter {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val buyLinkListType = Types.newParameterizedType(List::class.java, BuyLink::class.java)
    private val buyLinkListAdapter = moshi.adapter<List<BuyLink>>(buyLinkListType)

    @TypeConverter
    fun fromBuyLinkList(buyLinks: List<BuyLink>?): String? {
        return buyLinks?.let { buyLinkListAdapter.toJson(it) }
    }

    @TypeConverter
    fun toBuyLinkList(buyLinksString: String?): List<BuyLink>? {
        return buyLinksString?.let { buyLinkListAdapter.fromJson(it) }
    }
}
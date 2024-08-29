package com.example.otchallenge.data.di

import com.example.otchallenge.BuildConfig
import com.example.otchallenge.data.network.BooksApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesJsonDeserializer(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    fun providesRetrofitInstance(json: Json): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(
            json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType()
            )
        )
        .build()


    @Provides
    fun providesBooksApi(retrofit: Retrofit): BooksApi = retrofit
        .create(BooksApi::class.java)

}

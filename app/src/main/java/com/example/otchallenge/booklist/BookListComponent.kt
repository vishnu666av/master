package com.example.otchallenge.booklist

import android.content.Context
import com.example.otchallenge.di.ActivityScope
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface BookListComponent {

    @Subcomponent.Builder
    interface Builder {
        fun withContext(@BindsInstance context: Context): Builder
        fun withView(@BindsInstance view: BookListView): Builder
        fun build(): BookListComponent
    }

    fun inject(activity: BookListActivity)
}
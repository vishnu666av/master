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
        /*
        * Provides activity context to the subcomponent graph */
        fun withContext(@BindsInstance context: Context): Builder

        /*
        * Provides class that implements the view (activity) to the subcomponent graph */
        fun withView(@BindsInstance view: BookListView): Builder
        fun build(): BookListComponent
    }

    fun inject(activity: BookListActivity)
}
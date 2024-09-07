package com.example.otchallenge.model

import com.example.otchallenge.data.Repository
import com.example.otchallenge.di.LocalRepository
import com.example.otchallenge.di.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

/**
 * the presenter is where we embed our business logic.
 * in this case, the logic is to try displaying fresh online data and fall back
 * on local database if the call fails or internet connection is not available.
 */
class FictionsListPresenter @Inject constructor(
    @LocalRepository val localRepository: Repository<Fiction>,
    @RemoteRepository val remoteRepository: Repository<Fiction>
) {

    suspend fun getFictions(): FictionsDataState = withContext(Dispatchers.IO) {
        /**
         * todo: change the logic here to save response to database.
         * I can probably forgo the Stale and Fresh data list and just mark it with a
         * boolean flag to indicate this is a stale list.
         */
        when (val fetchRemoteResult = getRemoteData()) {
            is FictionsDataState.FreshListData -> fetchRemoteResult
            else -> getLocalData()
        }
    }

    private fun getRemoteData(): FictionsDataState = try {
        val items = remoteRepository.all()
        if (items.isEmpty()) {
            FictionsDataState.Empty(timestamp = Date())
        } else {
            FictionsDataState.StaleListData(items = items, timestamp = Date())
        }
    } catch (e: Exception) {
        FictionsDataState.Error(message = e.localizedMessage, timestamp = Date())
    }

    private fun getLocalData(): FictionsDataState = try {
        val items = localRepository.all()
        if (items.isEmpty()) {
            FictionsDataState.Empty(timestamp = Date())
        } else {
            FictionsDataState.StaleListData(items = items, timestamp = Date())
        }
    } catch (e: Exception) {
        FictionsDataState.Error(message = e.localizedMessage, timestamp = Date())
    }
}
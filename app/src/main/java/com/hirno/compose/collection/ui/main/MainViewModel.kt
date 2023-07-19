package com.hirno.compose.collection.ui.main

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.*
import com.hirno.compose.collection.R
import com.hirno.compose.collection.data.source.CollectionsRepository
import com.hirno.compose.collection.model.collection.CollectionResponseModel
import com.hirno.compose.collection.network.response.NetworkResponse
import kotlinx.coroutines.launch

/**
 * The [MainActivity] view model. Stores and manipulates state of the fragment
 *
 * @property collectionsRepository The repository instance used to manage collections
 */
class MainViewModel(
    private val collectionsRepository: CollectionsRepository,
) : ViewModel() {
    companion object {
        /**
         * Minimum amount of time required to update cache in milliseconds
         */
        const val CACHE_UPDATE_INTERVAL = 5 * 60 * 1000
    }

    private val _collections = MutableLiveData<CollectionResponseModel>()
    val collections: LiveData<CollectionResponseModel> = _collections

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _isDataLoadingError = MutableLiveData<Boolean>()
    val isDataLoadingError: LiveData<Boolean> = _isDataLoadingError

    private val _errorText = MutableLiveData<Any?>()
    val errorText: LiveData<Any?> = _errorText

    /**
     * Stores the state of our Retrofit request so that no duplicate request is performed
     */
    private var isRequestingCollections = false

    /**
     * A pending collections request waiting for the network state to perform
     */
    private var pendingCollectionsRequest: Runnable? = null

    /**
     * A handler object responsible to run cache update intervals
     */
    private val cacheUpdateHandler = Handler(Looper.getMainLooper())
    /**
     * Runs cache updates
     */
    private val cacheUpdateRunnable = Runnable {
        loadCollections(silentRefresh = true)
    }

    /**
     * Requests collections from repository
     *
     * @param silentRefresh Pass `true` to perform a silent update of the cache without user noticing
     */
    fun loadCollections(silentRefresh: Boolean = false) {
        if (isRequestingCollections) return
        val showProgressBar = !silentRefresh || collections.value == null
        viewModelScope.launch {
            isRequestingCollections = true
            _dataLoading.value = showProgressBar
            when (val result = collectionsRepository.getCollections()) {
                is NetworkResponse.Success -> onCollectionsLoadingSuccessful(result.body)
                is NetworkResponse.ApiError -> onCollectionsLoadingFailed(result.body.title, silentRefresh)
                is NetworkResponse.NetworkError -> onCollectionsLoadingFailed(R.string.failed_to_connect_to_remote_server, silentRefresh, shouldRetry = true)
                is NetworkResponse.UnknownError -> onCollectionsLoadingFailed(R.string.an_error_occurred, silentRefresh)
            }
            _dataLoading.value = false
            isRequestingCollections = false
        }
    }

    private fun onCollectionsLoadingSuccessful(collections: CollectionResponseModel) {
        _collections.value = collections
        _errorText.value = null
        _isDataLoadingError.value = false
        pendingCollectionsRequest = null
        setupCacheUpdateInterval()
    }

    private fun onCollectionsLoadingFailed(error: Any?, silentRefresh: Boolean, shouldRetry: Boolean = false) {
        _errorText.value = error
        val showError = !silentRefresh || collections.value == null
        _isDataLoadingError.value = showError
        pendingCollectionsRequest = if (shouldRetry) Runnable {
            loadCollections(silentRefresh)
        } else null
    }

    /**
     * Performs the pending requests if there are any
     */
    fun onNetworkConnectivityChanged() {
        pendingCollectionsRequest?.run()
    }

    private fun setupCacheUpdateInterval() {
        cancelCacheUpdateInterval()
        cacheUpdateHandler.postDelayed(cacheUpdateRunnable, CACHE_UPDATE_INTERVAL.toLong())
    }
    private fun cancelCacheUpdateInterval() {
        cacheUpdateHandler.removeCallbacks(cacheUpdateRunnable)
    }
}

/**
 * The factory of [MainViewModel]
 *
 * @property collectionsRepository The collections repository
 * @constructor Creates a [MainViewModel] instance
 */
@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val collectionsRepository: CollectionsRepository,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (MainViewModel(collectionsRepository) as T)
}
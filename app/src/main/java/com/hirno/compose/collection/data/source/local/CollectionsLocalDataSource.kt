package com.hirno.compose.collection.data.source.local

import com.hirno.compose.collection.data.source.CollectionsDataSource
import com.hirno.compose.collection.model.collection.CollectionResponseModel
import com.hirno.compose.collection.data.GenericResponse
import com.hirno.compose.collection.network.response.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Collections local data source implementation
 *
 * @property collectionsDao The DAO reference used to perform database related actions
 * @property ioDispatcher The coroutines dispatcher used to perform DAO operations on
 */
class CollectionsLocalDataSource(
    private val collectionsDao: CollectionsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CollectionsDataSource {
    /**
     * Get all collections from local storage
     *
     * @return A [NetworkResponse.Success] when all collections are retrieved successfully or a [NetworkResponse.UnknownError] with the exception that occurred
     */
    override suspend fun getCollections(): GenericResponse<CollectionResponseModel> = withContext(ioDispatcher) {
        return@withContext try {
            NetworkResponse.Success(CollectionResponseModel(ArrayList(collectionsDao.getAll())))
        } catch (e: Exception) {
            NetworkResponse.UnknownError(e)
        }
    }

    /**
     * Deletes all existing collections from local storage and inserts the new ones
     *
     * @param collections The new collections
     * @return `true` if the insertion was successful, `false` otherwise
     */
    override suspend fun cacheCollections(collections: CollectionResponseModel): Boolean {
        return try {
            collectionsDao.deleteAll()
            collectionsDao.insertAll(collections.objects)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Deletes all collections from local storage
     *
     */
    override suspend fun deleteAllCollections() {
        collectionsDao.deleteAll()
    }
}
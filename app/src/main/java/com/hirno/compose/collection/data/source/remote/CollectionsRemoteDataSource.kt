package com.hirno.compose.collection.data.source.remote

import com.hirno.compose.collection.data.source.CollectionsDataSource
import com.hirno.compose.collection.model.collection.CollectionResponseModel
import com.hirno.compose.collection.data.GenericResponse
import com.hirno.compose.collection.network.ApiClient

/**
 * Collections remote data source implementation
 */
object CollectionsRemoteDataSource : CollectionsDataSource {
    /**
     * Retrieves collections from server
     *
     * @return Server response of the network call
     */
    override suspend fun getCollections(): GenericResponse<CollectionResponseModel> {
        return ApiClient.retrofit.getCollections("Rembrandt")
    }

    override suspend fun cacheCollections(collections: CollectionResponseModel): Boolean {
        throw UnsupportedOperationException("Cannot insert anything on remote data source!")
    }

    override suspend fun deleteAllCollections() {
        throw UnsupportedOperationException("Cannot delete anything on remote data source!")
    }
}
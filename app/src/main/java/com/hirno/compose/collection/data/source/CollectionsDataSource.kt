package com.hirno.compose.collection.data.source

import com.hirno.compose.collection.model.collection.CollectionResponseModel
import com.hirno.compose.collection.data.GenericResponse

/**
 * Main entry point for accessing collections data.
 */
interface CollectionsDataSource {
    suspend fun getCollections(): GenericResponse<CollectionResponseModel>

    suspend fun cacheCollections(collections: CollectionResponseModel): Boolean

    suspend fun deleteAllCollections()
}
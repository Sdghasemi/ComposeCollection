package com.hirno.compose.collection.data.source

import com.hirno.compose.collection.data.GenericResponse
import com.hirno.compose.collection.model.collection.CollectionResponseModel

/**
 * Interface to the data layer.
 */
interface CollectionsRepository {
    suspend fun getCollections(): GenericResponse<CollectionResponseModel>
}
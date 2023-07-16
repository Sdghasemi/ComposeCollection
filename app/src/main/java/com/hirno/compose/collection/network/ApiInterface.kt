package com.hirno.compose.collection.network

import com.hirno.compose.collection.model.collection.CollectionResponseModel
import com.hirno.compose.collection.data.GenericResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API interface of the app endpoints
 */
interface ApiInterface {
    @GET("collection")
    suspend fun getCollections(@Query("q") query: String, @Query("ps") pageSize: Int = 100): GenericResponse<CollectionResponseModel>
}
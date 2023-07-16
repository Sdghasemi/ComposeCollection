package com.hirno.compose.collection.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hirno.compose.collection.model.collection.CollectionItemModel

/**
 * Data Access Object for Collections table
 */
@Dao
interface CollectionsDao {
    /**
     * Select all collections from the Collections table.
     *
     * @return all loaded collections.
     */
    @Query("SELECT * FROM Collections")
    suspend fun getAll(): List<CollectionItemModel>

    /**
     * Insert collections in the database.
     *
     * @param collections the collections to be inserted.
     */
    @Insert
    suspend fun insertAll(collections: List<CollectionItemModel>)

    /**
     * Delete all collections.
     */
    @Query("DELETE FROM Collections")
    suspend fun deleteAll()
}
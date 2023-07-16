package com.hirno.compose.collection.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hirno.compose.collection.model.collection.CollectionItemModel

/**
 * The Room Database that contains the Collections table.
 */
@Database(entities = [CollectionItemModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun collectionsDao(): CollectionsDao
}
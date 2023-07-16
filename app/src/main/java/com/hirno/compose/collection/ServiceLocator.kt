package com.hirno.compose.collection

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.hirno.compose.collection.data.source.CollectionsDataSource
import com.hirno.compose.collection.data.source.CollectionsRepository
import com.hirno.compose.collection.data.source.DefaultCollectionsRepository
import com.hirno.compose.collection.data.source.local.AppDatabase
import com.hirno.compose.collection.data.source.local.CollectionsLocalDataSource
import com.hirno.compose.collection.data.source.remote.CollectionsRemoteDataSource

/**
 * A Service Locator for the [CollectionsRepository]. This is the prod version, with a
 * the "real" [CollectionsRemoteDataSource].
 */
object ServiceLocator {

    private val lock = Any()
    private var database: AppDatabase? = null
    @Volatile
    var collectionsRepository: CollectionsRepository? = null
        @VisibleForTesting set

    /**
     * Returns the prod version of [CollectionsRepository]
     *
     * @param context The application context
     * @return The prod version of [CollectionsRepository]
     */
    fun provideCollectionsRepository(context: Context): CollectionsRepository {
        synchronized(this) {
            return collectionsRepository ?: createCollectionsRepository(context)
        }
    }

    private fun createCollectionsRepository(context: Context): CollectionsRepository {
        val newRepo = DefaultCollectionsRepository(context, CollectionsRemoteDataSource, createCollectionsLocalDataSource(context))
        collectionsRepository = newRepo
        return newRepo
    }

    private fun createCollectionsLocalDataSource(context: Context): CollectionsDataSource {
        val database = database ?: createDatabase(context)
        return CollectionsLocalDataSource(database.collectionsDao())
    }

    private fun createDatabase(context: Context): AppDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "App.db"
        ).build()
        database = result
        return result
    }

    /**
     * Resets repository data sources. Used for integration testing
     */
    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            // Clear all data to avoid test pollution.
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            collectionsRepository = null
        }
    }
}
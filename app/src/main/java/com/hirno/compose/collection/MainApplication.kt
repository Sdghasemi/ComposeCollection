package com.hirno.compose.collection

import android.app.Application
import com.hirno.compose.collection.data.source.CollectionsRepository

class MainApplication : Application() {
    /**
     * Collections repository used for accessing collections inside the app itself
     */
    val collectionsRepository: CollectionsRepository
        get() = ServiceLocator.provideCollectionsRepository(this)
}
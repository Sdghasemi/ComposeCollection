package com.hirno.compose.collection.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.hirno.compose.collection.MainApplication
import com.hirno.compose.collection.model.collection.CollectionResponseModel
import com.hirno.compose.collection.model.ui.AppUiState
import com.hirno.compose.collection.model.ui.AppUiStateModel
import com.hirno.compose.collection.ui.MainScreen
import com.hirno.compose.collection.ui.theme.AppTheme

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = MainViewModelFactory((applicationContext as MainApplication).collectionsRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.loadCollections()

        setContent {
            AppTheme {
                val collections by viewModel.collections.observeAsState(CollectionResponseModel())
                val isLoading by viewModel.dataLoading.observeAsState(false)
                val uiState = when {
                    isLoading -> AppUiState(AppUiStateModel.Loading)
                    collections.objects.isNotEmpty() -> AppUiState(AppUiStateModel.Success(collections))
                    else -> AppUiState(AppUiStateModel.Error)
                }
                MainScreen(
                    modifier = Modifier.fillMaxSize(),
                    mainState = uiState,
                )
            }
        }
    }
}
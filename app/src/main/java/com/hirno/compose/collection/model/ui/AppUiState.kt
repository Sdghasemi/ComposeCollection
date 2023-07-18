package com.hirno.compose.collection.model.ui

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.hirno.compose.collection.model.collection.CollectionResponseModel

@Stable
class AppUiState(
    uiState: AppUiStateModel = AppUiStateModel.Loading
) {
    val uiState by mutableStateOf(uiState)
}

sealed class AppUiStateModel {
    object Loading : AppUiStateModel()
    object Error : AppUiStateModel()
    class Success(
        val model: CollectionResponseModel
    ) : AppUiStateModel()
}
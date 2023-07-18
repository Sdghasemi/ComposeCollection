package com.hirno.compose.collection.ui

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hirno.compose.collection.R
import com.hirno.compose.collection.model.collection.CollectionItemModel
import com.hirno.compose.collection.model.collection.CollectionResponseModel
import com.hirno.compose.collection.model.ui.AppUiState
import com.hirno.compose.collection.model.ui.AppUiStateModel
import com.hirno.compose.collection.ui.theme.AppTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainState: AppUiState = AppUiState()
) {
    Box(
        modifier = modifier
    ) {
        when (val state = mainState.uiState) {
            is AppUiStateModel.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
            is AppUiStateModel.Error -> Text(text = stringResource(id = R.string.failed_to_connect_to_remote_server))
            is AppUiStateModel.Success -> MainList(
                model = state.model,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun MainList(
    model: CollectionResponseModel,
    modifier: Modifier = Modifier,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(all = 6.dp),
    ) {
        items(model.objects) { item ->
            MainItem(
                item = item,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun MainItem(
    item: CollectionItemModel,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.padding(all = 6.dp),
    ) {
        Column {
            val image = item.webImage
            val ratio = rememberImageRatio(image)
            val placeholderDrawable = rememberPlaceholder()
            GlideImage(
                model = image.url,
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio),
            ) {
                it
                    .placeholder(placeholderDrawable)
            }
            Text(
                text = item.title ?: "",
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun rememberImageRatio(image: CollectionItemModel.WebImage) = remember(image) {
    image.width.toFloat() / image.height
}

@Composable
private fun rememberPlaceholder(
    placeholderColor: Color = MaterialTheme.colorScheme.surfaceVariant
) = remember(placeholderColor) {
    ColorDrawable(placeholderColor.toArgb())
}

@Preview(device = Devices.NEXUS_6)
@Composable
fun MainPreview() {
    AppTheme {
        MainScreen(
            modifier = Modifier.fillMaxSize(),
            mainState = AppUiState(
                AppUiStateModel.Success(
                CollectionResponseModel(
                objects = arrayListOf(
                    CollectionItemModel(
                        id = "nl-SK-A-4691",
                        objectNumber = "SK-A-4691",
                        title = "Zelfportret",
                        webImage = CollectionItemModel.WebImage(
                            width = 2118,
                            height = 2598,
                            url = "https://lh3.googleusercontent.com/7qzT0pbclLB7y3fdS1GxzMnV7m3gD3gWnhlquhFaJSn6gNOvMmTUAX3wVlTzhMXIs8kM9IH8AsjHNVTs8em3XQI6uMY=s0",
                        ),
                    ),
                    CollectionItemModel(
                        id = "nl-SK-A-4050",
                        objectNumber = "SK-A-4050",
                        title = "Zelfportret als de apostel Paulus",
                        webImage = CollectionItemModel.WebImage(
                            width = 2287,
                            height = 2724,
                            url = "https://lh3.googleusercontent.com/NrCcfeY0r2F3M2hIQe5SLDRofR2tVzeOH18VjflOYGj88v4clb4v2H_VgCZR4nJhYsxxH9ATzfkL2tRqOWEK5-gPVEE=s0",
                        ),
                    ),
                    CollectionItemModel(
                        id = "nl-SK-C-5",
                        objectNumber = "SK-C-5",
                        title = "De Nachtwacht",
                        webImage = CollectionItemModel.WebImage(
                            width = 5656,
                            height = 4704,
                            url = "https://lh3.googleusercontent.com/SsEIJWka3_cYRXXSE8VD3XNOgtOxoZhqW1uB6UFj78eg8gq3G4jAqL4Z_5KwA12aD7Leqp27F653aBkYkRBkEQyeKxfaZPyDx0O8CzWg=s0",
                        ),
                    ),
                )
            )
            ))
        )
    }
}
package com.hirno.compose.collection.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(device = Devices.NEXUS_6)
@Composable
fun MainActivityScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "Hello!!!",
        )
    }
}
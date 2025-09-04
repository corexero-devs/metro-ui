package com.codeancy.metroui.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SnackBar(
    show: Boolean,
    message: String,
    onDismiss: () -> Unit,
    durationInMs: Long = 2000,
    backgroundColor: Color = SnackbarDefaults.color
) {
    val snackBarHostState = remember { SnackbarHostState() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .navigationBarsPadding()
            .imePadding()
    ) {
        if (show) {
            LaunchedEffect(show) {
                snackBarHostState.showSnackbar(message)
                delay(durationInMs)
                onDismiss()
            }
        }
        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

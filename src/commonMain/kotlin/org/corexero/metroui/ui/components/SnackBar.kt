package org.corexero.metroui.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
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
    backgroundColor: Color = SnackbarDefaults.backgroundColor
) {
    val snackBarHostState = remember { SnackbarHostState() }
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp)
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
                .padding(16.dp),
            snackbar = { data ->
                Snackbar(
                    modifier = Modifier.padding(8.dp),
                    backgroundColor = backgroundColor,
                    content = {
                        Text(text = data.message, color = Color.White)
                    }
                )
            }
        )
    }
}

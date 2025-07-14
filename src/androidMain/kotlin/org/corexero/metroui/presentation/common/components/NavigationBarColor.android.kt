package org.corexero.metroui.presentation.common.components

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun NavigationBarColor(navigationBarColor: Color) {
    val activity = LocalContext.current as? Activity
    LaunchedEffect(key1 = Unit) {
        if (activity != null) {
            activity.window.navigationBarColor = navigationBarColor.toArgb()
        }
    }
}
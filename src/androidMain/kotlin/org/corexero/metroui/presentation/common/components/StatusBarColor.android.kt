package org.corexero.metroui.presentation.common.components

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat

@Composable
actual fun StatusBarColor(
    isLightStatusBar: Boolean,
    statusBarColor: Color,
    navigationBarColor: Color
) {
    val activity = LocalContext.current as? Activity
    LaunchedEffect(key1 = Unit) {
        if (activity != null) {
            WindowCompat.getInsetsController(
                activity.window,
                activity.window.decorView
            ).isAppearanceLightStatusBars = isLightStatusBar
            activity.window.statusBarColor = statusBarColor.toArgb()
            activity.window.navigationBarColor = navigationBarColor.toArgb()
        }
    }
}
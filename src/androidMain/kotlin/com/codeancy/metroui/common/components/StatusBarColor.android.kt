package com.codeancy.metroui.common.components

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.WindowCompat

@Composable
actual fun StatusBarColor(
    isLightStatusBar: Boolean,
) {
    val activity = LocalActivity.current
    LaunchedEffect(key1 = Unit) {
        if (activity != null) {
            WindowCompat.getInsetsController(
                activity.window,
                activity.window.decorView
            ).isAppearanceLightStatusBars = isLightStatusBar
        }
    }
}
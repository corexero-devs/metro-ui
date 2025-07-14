package org.corexero.metroui.presentation.common.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
expect fun StatusBarColor(
    isLightStatusBar: Boolean = false,
    statusBarColor: Color = MaterialTheme.colors.primary,
    navigationBarColor: Color = MaterialTheme.colors.primary
)
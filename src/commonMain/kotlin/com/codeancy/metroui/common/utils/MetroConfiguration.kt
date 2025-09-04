package com.codeancy.metroui.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import org.jetbrains.compose.resources.DrawableResource

data class MetroConfiguration(
    val appTitle: String,
    val allowLocationButtonText: String,
    val mapDrawableResource: DrawableResource,
    val appName: String,
    val appVersion: String
)

val LocalMetroConfiguration =
    compositionLocalOf<MetroConfiguration> { error("No MetroConfig provided") }

val MetroConfig
    @Composable
    get() = LocalMetroConfiguration.current
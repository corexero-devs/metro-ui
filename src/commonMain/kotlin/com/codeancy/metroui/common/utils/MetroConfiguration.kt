package com.codeancy.metroui.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import com.codeancy.metroui.ads.InterstitialAdController

expect class MapDrawableResource

data class MetroConfiguration(
    val appTitle: String,
    val allowLocationButtonText: String,
    val mapDrawableResource: MapDrawableResource,
    val appName: String,
    val appVersion: String,
    val interstitialAdController: InterstitialAdController?
)

val LocalMetroConfiguration =
    compositionLocalOf<MetroConfiguration> { error("No MetroConfig provided") }

val MetroConfig
    @Composable
    get() = LocalMetroConfiguration.current
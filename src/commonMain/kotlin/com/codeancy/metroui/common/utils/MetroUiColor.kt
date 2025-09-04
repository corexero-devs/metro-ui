package com.codeancy.metroui.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

private val greenDotColor = Color(0xFF22C55E)
private val redDotColor = Color(0xFFEF4444)
private val blueDark = Color(0xFF0052A5)
private val subHeadingTitle = Color(0xFF6B7280)
private val lastMetroPrimary = Color(0xFFf59e0b)
private val borderColor = Color(0xFFfef3c7)
private val lastMetroBackgroundColor = Color(0xFFfffbeb)
private val nearestMetroColor = Color(0xFF3B82F6)
private val Green500 = Color(0xFF22C55E)      // full opacity
private val Green50050 = Color(0x8022C55E)

data class MetroUiColors(
    val stationCard: StationSelectionCard = StationSelectionCard(),
    val componentCard: ComponentCard = ComponentCard(),
    val onBackground: Color = Color.Black,
    val onSurface: Color = Color.Black,
    val subHeading: Color = subHeadingTitle,
    val fareTextColor: Color = blueDark,
    val recentSearchArrowColor: Color = Color(0xFF9CA3AF),
    val dialogBorderColor: Color = Color(0xFFE5E7EB),
    val lastMetroTiming: LastMetroTiming = LastMetroTiming(),
    val onPrimaryColor: Color = Color.White,
    val surfaceColor: Color = Color(0xFFDBEAFE),
    val liveLocation: LiveLocation = LiveLocation(),
    val background: Color = Color(0xFFF5F7FA),
) {

    data class StationSelectionCard(
        val sourceDotColor: Color = greenDotColor,
        val destinationDotColor: Color = redDotColor,
        val indicatorLineColor: Color = Color(0xFFD1D5DB),
        val unfocusedBorderColor: Color = Color(0xFFD0D7DE),
        val inputBackgroundColor: Color = Color(0xFFF0F4F8),
        val cardContainerColor: Color = Color.White,
    )

    data class ComponentCard(
        val cardContainerColor: Color = Color.White,
    )

    data class LiveLocation(
        val liveColor: Color = Green500,
        val liveColor50: Color = Green50050,
    )

    data class LastMetroTiming(
        val primary: Color = lastMetroPrimary,
        val border: Color = borderColor,
        val background: Color = lastMetroBackgroundColor,
        val nearestMetro: Color = nearestMetroColor,
    )

}

val LocalMetroUiColor: CompositionLocal<MetroUiColors> =
    compositionLocalOf { MetroUiColors() }

val MetroUiColor
    @Composable
    get() = LocalMetroUiColor.current

val LastMetroTiming
    @Composable
    get() = MetroUiColor.lastMetroTiming
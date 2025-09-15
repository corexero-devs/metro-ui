package com.codeancy.metroui.route.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.codeancy.metroui.common.components.ComponentCard
import com.codeancy.metroui.domain.models.LiveLocationUi
import com.codeancy.metroui.domain.models.StationUi

@Composable
fun InterchangeLayout(
    allStations: List<StationUi>,
    visibleStations: List<StationUi>,
    stationInfo: @Composable (StationUi, Int) -> Unit,
    indicator: @Composable (Int) -> Unit,
    indicatorBackground: @Composable () -> Unit,
    liveLocationStatusIndicator: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    spacingBetweenStations: Dp = 4.dp,
    indictorBetweenSpacing: Dp = 8.dp,
    indicatorWidth: Dp = 12.dp,
    liveLocationWidth: Dp = 16.dp,
    liveLocationUi: LiveLocationUi.Location? = null
) {

    val density = LocalDensity.current

    val spacing = remember(density) {
        with(density) {
            spacingBetweenStations.roundToPx()
        }
    }

    val indicatorFixedWidth = remember {
        with(density) {
            indicatorWidth.roundToPx()
        }
    }

    val spacingBetweenIndictorAndStationInfo = remember {
        with(density) {
            indictorBetweenSpacing.roundToPx()
        }
    }

    val liveLocationFixedWidth = remember {
        with(density) {
            liveLocationWidth.roundToPx()
        }
    }

    val stationsCount = visibleStations.size

    ComponentCard(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Layout(
            content = {
                indicatorBackground()
                visibleStations.forEachIndexed { index, ui ->
                    stationInfo(ui, index)
                }
                repeat(visibleStations.size) {
                    indicator(it)
                }
                liveLocationStatusIndicator()
            },
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth()
                .padding(
                    top = 12.dp,
                    bottom = 12.dp,
                    end = 12.dp,
                    start = 16.dp
                ),
        ) { measurables, constraints ->

            val minWidth =
                constraints.minWidth - indicatorFixedWidth - spacingBetweenIndictorAndStationInfo
            val maxWidth =
                constraints.maxWidth - indicatorFixedWidth - spacingBetweenIndictorAndStationInfo
            val stationConstraint = constraints.copy(
                maxWidth = maxWidth.coerceAtLeast(0),
                minWidth = minWidth.coerceAtLeast(0)
            )
            val stationsPlaceables = measurables.subList(1, stationsCount + 1)
                .map { it.measure(stationConstraint) }

            val indicatorPlaceables = measurables.subList(
                stationsCount + 1,
                stationsCount + stationsCount + 1
            ).map {
                it.measure(
                    constraints.copy(
                        minWidth = indicatorFixedWidth,
                        maxWidth = indicatorFixedWidth,
                        maxHeight = indicatorFixedWidth,
                        minHeight = indicatorFixedWidth
                    )
                )
            }

            val liveLocationPlaceable = measurables.last().measure(
                constraints.copy(
                    minWidth = liveLocationFixedWidth,
                    maxWidth = liveLocationFixedWidth,
                    maxHeight = liveLocationFixedWidth,
                    minHeight = liveLocationFixedWidth
                )
            )

            val totalHeight =
                stationsPlaceables.fold(0) { acc, placeable ->
                    acc + placeable.height
                } + (stationsCount - 1) * spacing

            val indicatorBackgroundPlaceable = measurables.first().measure(
                Constraints(
                    minHeight = totalHeight,
                    maxHeight = totalHeight,
                    maxWidth = indicatorFixedWidth,
                    minWidth = indicatorFixedWidth
                )
            )

            layout(constraints.maxWidth, totalHeight) {

                var nextStationPosition = 0
                indicatorBackgroundPlaceable.placeRelative(0, 0)
                val indictorX =
                    indicatorBackgroundPlaceable.width / 2 - (indicatorPlaceables.first().width / 2)

                stationsPlaceables.forEachIndexed { index, placeable ->
                    placeable.placeRelative(
                        indicatorBackgroundPlaceable.width + spacingBetweenIndictorAndStationInfo,
                        nextStationPosition
                    )
                    val currentStationIndicator = indicatorPlaceables[index]
                    val indicatorY =
                        when (index) {
                            0 -> nextStationPosition
                            stationsPlaceables.lastIndex ->
                                nextStationPosition + placeable.height - currentStationIndicator.height

                            else ->
                                nextStationPosition + (placeable.height - currentStationIndicator.height) / 2

                        }
                    currentStationIndicator.placeRelative(indictorX, indicatorY)
                    nextStationPosition += placeable.height + spacing
                }

                // live location ui
                if (liveLocationUi != null) {
                    val liveLocationX =
                        indicatorBackgroundPlaceable.width / 2 - liveLocationPlaceable.width / 2
                    if (visibleStations.size == 2) {
                        val fromStationIndex =
                            allStations.indexOfFirst { it.id == liveLocationUi.startStation.entity }
                        if (fromStationIndex < 0 || fromStationIndex >= allStations.lastIndex) {
                            return@layout
                        }

                        val endY = totalHeight - liveLocationPlaceable.height
                        val segmentSize = endY / (allStations.size - 1)
                        val startY = segmentSize * fromStationIndex
                        val actualEndY = startY + segmentSize
                        val placement =
                            startY + ((actualEndY - startY) * liveLocationUi.fraction).toInt()
                        liveLocationPlaceable.placeRelative(liveLocationX, placement)
                    } else {
                        val fromStationIndex =
                            visibleStations.indexOfFirst { it.id == liveLocationUi.startStation.entity }
                        if (fromStationIndex < 0 || fromStationIndex >= visibleStations.lastIndex) {
                            return@layout
                        }
                        val startY = fromStationIndex * spacing + (fromStationIndex - 1).let {
                            if (it < 0) {
                                0
                            } else {
                                stationsPlaceables.subList(0, it + 1).fold(0) { acc, placeable ->
                                    acc + placeable.height
                                }
                            }
                        }
                        val endY =
                            startY + stationsPlaceables[fromStationIndex].height + spacing + stationsPlaceables[fromStationIndex + 1].height - liveLocationPlaceable.height
                        val placement = startY + ((endY - startY) * liveLocationUi.fraction).toInt()
                        liveLocationPlaceable.placeRelative(liveLocationX, placement)
                    }
                }
            }
        }
    }
}
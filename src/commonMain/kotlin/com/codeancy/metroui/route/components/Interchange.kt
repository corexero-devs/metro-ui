package com.codeancy.metroui.route.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeancy.metroui.common.utils.MetroUiColor
import com.codeancy.metroui.common.utils.hexToColor
import com.codeancy.metroui.domain.models.LiveLocationUi
import com.codeancy.metroui.domain.models.RouteResultUi
import com.codeancy.metroui.domain.models.StationUi
import indianmetro.metroui.generated.resources.Res
import indianmetro.metroui.generated.resources.hide_all_station
import indianmetro.metroui.generated.resources.platform_no
import indianmetro.metroui.generated.resources.show_all_station
import indianmetro.metroui.generated.resources.towards
import org.jetbrains.compose.resources.stringResource

@Composable
fun InterchangeRoute(
    routeResultUi: RouteResultUi,
    liveLocationUis: List<LiveLocationUi.Location?>,
    modifier: Modifier = Modifier
) {

    var selectedInterchangeIndex: Int? by remember {
        mutableStateOf(null)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        routeResultUi.interchange.forEachIndexed { index, interchange ->
            if (index != 0) {
                ChangeLine(
                    lineName = interchange.lineName,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            InterchangeV2(
                interchange = interchange,
                isExpanded = selectedInterchangeIndex == index,
                modifier = Modifier
                    .fillMaxWidth(),
                liveLocationUi = liveLocationUis[index],
                onToggle = {
                    selectedInterchangeIndex = if (selectedInterchangeIndex == index) {
                        null
                    } else {
                        index
                    }
                },
            )
        }
    }
}

@Composable
private fun InterchangeV2(
    interchange: RouteResultUi.Interchange,
    liveLocationUi: LiveLocationUi.Location?,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {

    val stations = remember(isExpanded, interchange) {
        if (!isExpanded) {
            listOf(
                interchange.sourceStation,
                interchange.destinationStation
            )
        } else {
            listOf(interchange.sourceStation)
                .plus(interchange.inBetweenStations)
                .plus(listOf(interchange.destinationStation))
        }
    }

    val allStations = remember(interchange) {
        listOf(interchange.sourceStation)
            .plus(interchange.inBetweenStations)
            .plus(listOf(interchange.destinationStation))
    }

    InterchangeLayout(
        allStations = allStations,
        visibleStations = stations,
        spacingBetweenStations = 6.dp,
        stationInfo = { stationUi, index ->
            when (index) {
                0 -> {
                    StartStationInfo(
                        stationUi = stationUi,
                        lineColor = interchange.lineColor.hexToColor(),
                        isExpanded = isExpanded,
                        onToggle = onToggle,
                        hasInBetweenStations = interchange.inBetweenStations.isNotEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                stations.lastIndex -> {
                    DestinationStationText(stationUi = stationUi)
                }

                else -> {
                    InBetweenStations(stationUi = stationUi)
                }
            }
        },
        indicatorBackground = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(1000.dp))
                    .background(interchange.lineColor.hexToColor())
            )
        },
        indicator = {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(
                        1.dp,
                        MetroUiColor.subHeading,
                        CircleShape
                    )
                    .background(Color.White)
            )
        },
        liveLocationStatusIndicator = {
            LiveLocation(
                isLiveUpdate = liveLocationUi?.isLiveUpdated ?: false,
                hasLocation = true,
                modifier = Modifier
            )
        },
        liveLocationUi = liveLocationUi,
        modifier = modifier
    )

}


@Composable
private fun StartStationInfo(
    stationUi: StationUi,
    lineColor: Color,
    isExpanded: Boolean,
    hasInBetweenStations: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
    ) {
        StartStationWithTowards(
            startStationName = stationUi.name.asString(),
            isTowardsVisible = stationUi.towards != null,
            modifier = Modifier
                .fillMaxWidth()
        )

        PlatformNoAndTowardsStationInfo(
            platformNo = stationUi.platformNo,
            towardsStationName = stationUi.towards,
            lineColor = lineColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp)
        )

        if (hasInBetweenStations) {
            HideAndShowStationsButton(
                isExpanded = isExpanded,
                onToggle = onToggle
            )
        }
    }
}

@Composable
private fun DestinationStationText(
    stationUi: StationUi,
) {
    Text(
        text = stationUi.name.asString(),
        style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 14.sp,
            color = MetroUiColor.onSurface,
            fontWeight = FontWeight.Medium,
        ),
    )
}

@Composable
private fun InBetweenStations(
    stationUi: StationUi,
) {
    Text(
        text = stationUi.name.asString(),
        style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MetroUiColor.subHeading
        ),
    )
}

@Composable
private fun HideAndShowStationsButton(
    isExpanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onToggle() }
    ) {

        Icon(
            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
        )

        Text(
            text = if (isExpanded) stringResource(Res.string.hide_all_station) else stringResource(
                Res.string.show_all_station
            ),
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = MetroUiColor.subHeading
            )
        )
    }
}

@Composable
private fun PlatformNoAndTowardsStationInfo(
    platformNo: String?,
    towardsStationName: String?,
    lineColor: Color,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(platformNo != null && towardsStationName != null) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            if (platformNo != null) {
                Text(
                    text = stringResource(Res.string.platform_no, platformNo),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp,
                        color = lineColor,
                        fontWeight = FontWeight.Medium,
                    )
                )
            }
            if (towardsStationName != null) {
                Text(
                    text = towardsStationName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MetroUiColor.subHeading
                    )
                )
            }
        }
    }
}

@Composable
private fun StartStationWithTowards(
    startStationName: String,
    isTowardsVisible: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = startStationName,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                color = MetroUiColor.onSurface,
                fontWeight = FontWeight.Medium,
            )
        )

        AnimatedVisibility(isTowardsVisible) {
            Text(
                text = stringResource(Res.string.towards),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MetroUiColor.subHeading.copy(alpha = 0.7f)
                )
            )
        }
    }
}
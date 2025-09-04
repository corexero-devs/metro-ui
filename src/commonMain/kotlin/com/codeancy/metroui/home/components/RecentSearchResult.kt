package com.codeancy.metroui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeancy.metroui.common.components.ComponentCard
import com.codeancy.metroui.common.components.HomeScreenComponentHeader
import com.codeancy.metroui.common.utils.MetroUiColor
import com.codeancy.metroui.common.utils.hexToColor
import com.codeancy.metroui.domain.models.RouteResultUi
import indianmetro.metroui.generated.resources.Res
import indianmetro.metroui.generated.resources.arrow
import indianmetro.metroui.generated.resources.recent_searches
import indianmetro.metroui.generated.resources.stations_count
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource


@Composable
fun RecentRouteResults(
    routeResultsUi: List<RouteResultUi>,
    onClick: (RouteResultUi) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {

        HomeScreenComponentHeader(
            title = stringResource(Res.string.recent_searches),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        ComponentCard(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                routeResultsUi.forEachIndexed { index, routeResultUi ->
                    val lineColors = remember(routeResultUi) {
                        routeResultUi.interchange.map {
                            it.lineColor.hexToColor()
                        }
                    }
                    val subTitle = remember(routeResultUi) {
                        routeResultUi.interchange.takeIf {
                            it.size == 1
                        }?.firstOrNull()?.lineName
                    }
                    RecentSearchResult(
                        sourceStationName = routeResultUi.sourceStation.name.asString(),
                        destinationStationName = routeResultUi.destinationStation.name.asString(),
                        stationCounts = routeResultUi.stations,
                        subTitle = subTitle ?: "Multiple Lines",
                        fare = routeResultUi.formattedFare,
                        lineColors = lineColors,
                        onClick = {
                            onClick(routeResultUi)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    if (index < routeResultsUi.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = MetroUiColor.subHeading,
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecentSearchResult(
    sourceStationName: String,
    destinationStationName: String,
    fare: String,
    stationCounts: Int,
    subTitle: String,
    lineColors: List<Color>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 12.dp,
                vertical = 12.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = sourceStationName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MetroUiColor.onSurface,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    )
                )

                Icon(
                    imageVector = vectorResource(Res.drawable.arrow),
                    contentDescription = null,
                    tint = MetroUiColor.recentSearchArrowColor,
                    modifier = Modifier
                        .size(20.dp)
                )

                Text(
                    text = destinationStationName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MetroUiColor.onSurface,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    )
                )
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    lineColors.forEach {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(it)
                        )
                    }
                }

                Text(
                    text = subTitle,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MetroUiColor.subHeading,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                    )
                )

                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(MetroUiColor.subHeading)
                )

                Text(
                    text = stringResource(Res.string.stations_count, stationCounts),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MetroUiColor.subHeading,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                    )
                )
            }

        }

        if (fare.isNotEmpty()) {
            Text(
                text = fare,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MetroUiColor.fareTextColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFDBEAFE))
                    .padding(
                        vertical = 4.dp,
                        horizontal = 6.dp
                    )
            )
        }

    }

}
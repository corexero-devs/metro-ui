package org.corexero.metroui.presentation.home.components

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
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jaipurmetro.metroui.generated.resources.Res
import jaipurmetro.metroui.generated.resources.arrow
import jaipurmetro.metroui.generated.resources.recent_searches
import jaipurmetro.metroui.generated.resources.stations_count
import org.corexero.metroui.ui.theme.blueDark
import org.corexero.metroui.ui.theme.interFont
import org.corexero.metroui.ui.theme.subHeadingTitle
import org.corexero.metroui.utils.hexToColor
import org.corexero.metroui.domain.model.RouteResultUi
import org.corexero.metroui.presentation.common.components.ComponentCard
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
                        sourceStationName = routeResultUi.sourceStation.name,
                        destinationStationName = routeResultUi.destinationStation.name,
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
                        Divider(
                            modifier = Modifier.fillMaxWidth(),
                            color = subHeadingTitle,
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
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        fontFamily = interFont
                    )
                )

                Icon(
                    imageVector = vectorResource(Res.drawable.arrow),
                    contentDescription = null,
                    tint = Color(0xFF9CA3AF),
                    modifier = Modifier
                        .size(20.dp)
                )

                Text(
                    text = destinationStationName,
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        fontFamily = interFont
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
                    style = TextStyle(
                        color = subHeadingTitle,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        fontFamily = interFont
                    )
                )

                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(subHeadingTitle)
                )

                Text(
                    text = stringResource(Res.string.stations_count, stationCounts),
                    style = TextStyle(
                        color = subHeadingTitle,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        fontFamily = interFont
                    )
                )
            }

        }

        if (fare.isNotEmpty()) {
            Text(
                text = fare,
                style = TextStyle(
                    color = blueDark,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    fontFamily = interFont
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
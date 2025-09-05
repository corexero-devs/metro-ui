package com.codeancy.metroui.route.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codeancy.metroui.common.components.ComponentCard
import com.codeancy.metroui.domain.models.RouteResultUi
import com.codeancy.metroui.domain.models.StationUi

@Composable
fun MetroRouteStations(
    routeResultUi: RouteResultUi,
    modifier: Modifier = Modifier
) {
    ComponentCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            routeResultUi.interchange.forEachIndexed { index, interchange ->
                MetroRouteStation(
                    stationUi = interchange.sourceStation,
                    icon = if (index == 0) {
                        StationUi.StationIcon.In
                    } else {
                        StationUi.StationIcon.Out
                    },
                    type = if (index == 0) {
                        StationUi.StationType.Start
                    } else {
                        StationUi.StationType.Interchange
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                interchange.inBetweenStations.forEach { stationUi ->
                    MetroRouteStation(
                        stationUi = stationUi,
                        icon = StationUi.StationIcon.Train,
                        type = StationUi.StationType.Regular,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                if (index == routeResultUi.interchange.lastIndex) {
                    MetroRouteStation(
                        stationUi = interchange.destinationStation,
                        icon = StationUi.StationIcon.Out,
                        type = StationUi.StationType.End,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
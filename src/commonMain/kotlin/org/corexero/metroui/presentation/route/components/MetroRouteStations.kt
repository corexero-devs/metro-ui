package org.corexero.metroui.presentation.route.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.corexero.metroui.presentation.common.components.ComponentCard
import org.corexero.metroui.domain.model.RouteResultUi

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
                    modifier = Modifier
                        .fillMaxWidth()
                )
                interchange.inBetweenStations.forEach { stationUi ->
                    MetroRouteStation(
                        stationUi = stationUi,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                if (index == routeResultUi.interchange.lastIndex) {
                    MetroRouteStation(
                        stationUi = interchange.destinationStation,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
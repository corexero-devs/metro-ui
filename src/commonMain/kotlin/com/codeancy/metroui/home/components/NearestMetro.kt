package com.codeancy.metroui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeancy.metroui.common.components.MetroDialog
import com.codeancy.metroui.common.components.rememberLocationPermission
import com.codeancy.metroui.common.utils.LastMetroTiming
import com.codeancy.metroui.common.utils.MetroConfig
import com.codeancy.metroui.common.utils.MetroUiColor
import com.codeancy.metroui.common.utils.hexToColor
import com.codeancy.metroui.domain.models.NearestMetroStationUi
import com.codeancy.metroui.home.utils.HomeScreenState
import com.codeancy.metroui.home.utils.HomeScreenUiAction
import indianmetro.metroui.generated.resources.Res
import indianmetro.metroui.generated.resources.allow_location_access_in_android
import indianmetro.metroui.generated.resources.allow_location_access_in_ios
import indianmetro.metroui.generated.resources.find_nearby_stations
import indianmetro.metroui.generated.resources.finding_stations
import indianmetro.metroui.generated.resources.nearest_metro_location
import indianmetro.metroui.generated.resources.nearest_metro_stations
import indianmetro.metroui.generated.resources.searching_for_metro_stations
import indianmetro.metroui.generated.resources.time_to_nearest_metro
import org.corexero.sutradhar.utils.Platform
import org.corexero.sutradhar.utils.platform
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource


private val backgroundColor = Color(0xFFDBEAFE)

@Composable
fun NearestMetro(
    state: HomeScreenState.NearestMetroState?,
    onAction: (HomeScreenUiAction) -> Unit,
    modifier: Modifier = Modifier
) {

    if (state == null) return

    MetroDialog(
        title = stringResource(Res.string.nearest_metro_stations),
        onClose = {
            onAction(HomeScreenUiAction.OnDismissNeatestMetro)
        },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        when (state) {
            HomeScreenState.NearestMetroState.ShowRationale -> {

                val permission = rememberLocationPermission(
                    onPermissionGranted = {
                        onAction(HomeScreenUiAction.OnLocationPermissionGranted)
                    },
                    onPermissionDenied = {
                        onAction(HomeScreenUiAction.OnLocationPermissionDenied)
                    }
                )

                NearestMetroRationale(
                    onRequestLocation = {
                        permission.request()
                    },
                    modifier = modifier
                )
            }

            HomeScreenState.NearestMetroState.Loading -> {
                NearestMetroLoading(
                    modifier = modifier
                )
            }

            is HomeScreenState.NearestMetroState.NearestMetroStations -> {
                NearestMetroStations(
                    stations = state.stations,
                    onClick = {
                        onAction(HomeScreenUiAction.OnSelectNearestMetroStationUi(it))
                    },
                    modifier = modifier
                )
            }
        }
    }

}

@Composable
private fun NearestMetroRationale(
    onRequestLocation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = vectorResource(Res.drawable.nearest_metro_location),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
                tint = LastMetroTiming.nearestMetro
            )
        }

        Text(
            text = stringResource(Res.string.find_nearby_stations),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        )

        Text(
            text = when (platform) {
                Platform.Android -> stringResource(Res.string.allow_location_access_in_android)
                Platform.Ios -> stringResource(Res.string.allow_location_access_in_ios)
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 12.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight.Normal,
                color = MetroUiColor.subHeading,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Button(
            onClick = onRequestLocation,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = MetroConfig.allowLocationButtonText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                )
            )
        }
    }
}

@Composable
private fun NearestMetroLoading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(20.dp),
                strokeWidth = 2.dp,
                color = LastMetroTiming.nearestMetro,
            )
        }

        Text(
            text = stringResource(Res.string.finding_stations),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        )

        Text(
            text = stringResource(Res.string.searching_for_metro_stations),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 12.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight.Normal,
                color = MetroUiColor.subHeading,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

    }
}

@Composable
fun NearestMetroStations(
    stations: List<NearestMetroStationUi>,
    onClick: (NearestMetroStationUi) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        stations.forEach { stationUi ->
            NearestMetroStation(
                stationName = stationUi.stationName,
                colors = stationUi.lineColors,
                distance = stationUi.formatDistance(),
                time = stationUi.toTravelTime(),
                nextTrainTime = "Next: 5 min",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                onClick = {
                    onClick(stationUi)
                }
            )
        }

    }
}

@Composable
@Stable
private fun NearestMetroStation(
    stationName: String,
    colors: List<String>,
    distance: String,
    time: String,
    nextTrainTime: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(
                1.dp,
                MetroUiColor.dialogBorderColor,
                RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stationName,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    colors.forEach {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(it.hexToColor())
                        )
                    }
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = vectorResource(Res.drawable.time_to_nearest_metro),
                    contentDescription = null,
                    modifier = Modifier
                        .size(10.dp),
                    tint = MetroUiColor.subHeading
                )

                Text(
                    text = time,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 10.sp,
                        lineHeight = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = MetroUiColor.subHeading
                    )
                )

                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(MetroUiColor.subHeading)
                )

                Text(
                    text = distance,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 10.sp,
                        lineHeight = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = MetroUiColor.subHeading
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = nextTrainTime,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 10.sp,
                        lineHeight = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = MetroUiColor.subHeading
                    )
                )
            }
        }
    }
}
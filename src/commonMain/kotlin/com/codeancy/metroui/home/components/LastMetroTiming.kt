package com.codeancy.metroui.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeancy.metroui.common.components.MetroDialog
import com.codeancy.metroui.common.utils.LastMetroTiming
import com.codeancy.metroui.common.utils.MetroUiColor
import com.codeancy.metroui.home.utils.HomeScreenState
import com.codeancy.metroui.home.utils.HomeScreenUiAction
import indianmetro.metroui.generated.resources.Res
import indianmetro.metroui.generated.resources.check_another_route
import indianmetro.metroui.generated.resources.check_last_train
import indianmetro.metroui.generated.resources.checking_timings
import indianmetro.metroui.generated.resources.daily_services
import indianmetro.metroui.generated.resources.disclaimer
import indianmetro.metroui.generated.resources.finding_metro_schedule
import indianmetro.metroui.generated.resources.first_metro_time
import indianmetro.metroui.generated.resources.last_metro_time
import indianmetro.metroui.generated.resources.last_train_timings
import indianmetro.metroui.generated.resources.train_timings_disclaimer
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun LastMetroTiming(
    lastMetroTimingState: HomeScreenState.LastMetroTimingState?,
    onAction: (HomeScreenUiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (lastMetroTimingState == null) return
    MetroDialog(
        title = stringResource(Res.string.last_train_timings),
        onClose = {
            onAction(HomeScreenUiAction.OnDismissLastMetroTiming)
        },
        modifier = modifier
    ) {
        when (lastMetroTimingState) {
            is HomeScreenState.LastMetroTimingState.StationSelect -> {
                StationSelect(
                    stationSelect = lastMetroTimingState,
                    onAction = onAction,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            is HomeScreenState.LastMetroTimingState.Loading -> {
                LastMetroTimeLoading(
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            is HomeScreenState.LastMetroTimingState.LastMetroTime -> {
                LastMetroTime(
                    lastMetroTime = lastMetroTimingState,
                    onCheckAnotherRoute = {
                        onAction(HomeScreenUiAction.OnLastMetroClick)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun StationSelect(
    stationSelect: HomeScreenState.LastMetroTimingState.StationSelect,
    onAction: (HomeScreenUiAction) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        StationSelectContent(
            source = stationSelect.sourceString,
            destination = stationSelect.destinationString,
            allStations = stationSelect.stations,
            onSourceChanged = {
                onAction(HomeScreenUiAction.OnLastMetroChangeSource(it))
            },
            onDestinationChanged = {
                onAction(HomeScreenUiAction.OnLastMetroChangeDestination(it))
            },
            onSwap = {
                onAction(HomeScreenUiAction.OnLastMetroStationSwap)
            },
            onSourceStationSelected = {
                onAction(HomeScreenUiAction.OnLastMetroSelectSource(it))
            },
            onDestinationStationSelected = {
                onAction(HomeScreenUiAction.OnLastMetroSelectDestination(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                onAction(HomeScreenUiAction.OnGetLastMetroTiming)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LastMetroTiming.primary
            ),
            contentPadding = PaddingValues(
                vertical = 12.dp
            )
        ) {
            Text(
                text = stringResource(Res.string.check_last_train),
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
fun LastMetroTimeLoading(
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
                color = LastMetroTiming.primary,
            )
        }

        Text(
            text = stringResource(Res.string.checking_timings),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        )

        Text(
            text = stringResource(Res.string.finding_metro_schedule),
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
private fun LastMetroTime(
    lastMetroTime: HomeScreenState.LastMetroTimingState.LastMetroTime,
    onCheckAnotherRoute: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = lastMetroTime.source.name.asString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
                tint = Color.Black
            )

            Text(
                text = lastMetroTime.destination.name.asString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MetroTime(
                imageVector = vectorResource(Res.drawable.first_metro_time),
                title = "First Metro",
                value = lastMetroTime.firstMetroTime,
                color = LastMetroTiming.nearestMetro,
                modifier = Modifier
                    .weight(1f)
            )

            MetroTime(
                imageVector = vectorResource(Res.drawable.last_metro_time),
                title = "Last Metro",
                value = lastMetroTime.lastMetroTime,
                color = LastMetroTiming.primary,
                modifier = Modifier
                    .weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    1.dp,
                    LastMetroTiming.border,
                    RoundedCornerShape(8.dp)
                )
                .background(LastMetroTiming.background)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Icon(
                imageVector = vectorResource(Res.drawable.disclaimer),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp),
                tint = LastMetroTiming.primary
            )

            Text(
                text = stringResource(Res.string.train_timings_disclaimer),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 10.sp,
                    lineHeight = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = LastMetroTiming.primary
                )
            )

        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onCheckAnotherRoute,
            border = BorderStroke(
                width = 0.4.dp,
                color = MetroUiColor.subHeading
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp),
                    tint = Color.Black
                )

                Text(
                    text = stringResource(Res.string.check_another_route),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                )

            }
        }

    }
}

@Composable
private fun MetroTime(
    imageVector: ImageVector,
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(
                0.4.dp,
                MetroUiColor.subHeading,
                RoundedCornerShape(10.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp),
                tint = color
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            )

        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(color)
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            )

        }

        Text(
            text = stringResource(Res.string.daily_services),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 12.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight.Normal,
                color = MetroUiColor.subHeading,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

    }

}
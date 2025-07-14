package org.corexero.metroui.presentation.home.components

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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jaipurmetro.metroui.generated.resources.Res
import jaipurmetro.metroui.generated.resources.check_another_route
import jaipurmetro.metroui.generated.resources.check_last_train
import jaipurmetro.metroui.generated.resources.checking_timings
import jaipurmetro.metroui.generated.resources.daily_services
import jaipurmetro.metroui.generated.resources.disclaimer
import jaipurmetro.metroui.generated.resources.finding_metro_schedule
import jaipurmetro.metroui.generated.resources.first_metro_time
import jaipurmetro.metroui.generated.resources.last_metro_time
import jaipurmetro.metroui.generated.resources.last_train_timings
import jaipurmetro.metroui.generated.resources.train_timings_disclaimer
import org.corexero.metroui.presentation.home.utils.HomeScreenState
import org.corexero.jaipurmetro.presentation.home.utils.HomeScreenUiAction
import org.corexero.metroui.ui.theme.interFont
import org.corexero.metroui.ui.theme.subHeadingTitle
import org.corexero.metroui.presentation.common.components.MetroDialog
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

val lastMetroPrimary = Color(0xFFf59e0b)
val borderColor = Color(0xFFfef3c7)
val lastMetroBackgroundColor = Color(0xFFfffbeb)

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
            allStations = remember(stationSelect.stations) { stationSelect.stations.map { it.name } },
            onSourceChanged = {
                onAction(HomeScreenUiAction.OnLastMetroChangeSource(stationSelect, it))
            },
            onDestinationChanged = {
                onAction(HomeScreenUiAction.OnLastMetroChangeDestination(stationSelect, it))
            },
            onSwap = {
                onAction(HomeScreenUiAction.OnLastMetroStationSwap)
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
                backgroundColor = lastMetroPrimary
            ),
            contentPadding = PaddingValues(
                vertical = 12.dp
            )
        ) {
            Text(
                text = stringResource(Res.string.check_last_train),
                style = MaterialTheme.typography.h5.copy(
                    fontFamily = interFont,
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
                color = lastMetroPrimary,
            )
        }

        Text(
            text = stringResource(Res.string.checking_timings),
            style = MaterialTheme.typography.h5.copy(
                fontFamily = interFont,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        )

        Text(
            text = stringResource(Res.string.finding_metro_schedule),
            style = MaterialTheme.typography.h5.copy(
                fontFamily = interFont,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight.Normal,
                color = subHeadingTitle,
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
                text = lastMetroTime.source.name,
                style = MaterialTheme.typography.h5.copy(
                    fontFamily = interFont,
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
                text = lastMetroTime.destination.name,
                style = MaterialTheme.typography.h5.copy(
                    fontFamily = interFont,
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
                color = nearestMetroColor,
                modifier = Modifier
                    .weight(1f)
            )

            MetroTime(
                imageVector = vectorResource(Res.drawable.last_metro_time),
                title = "Last Metro",
                value = lastMetroTime.lastMetroTime,
                color = lastMetroPrimary,
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
                    borderColor,
                    RoundedCornerShape(8.dp)
                )
                .background(lastMetroBackgroundColor)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Icon(
                imageVector = vectorResource(Res.drawable.disclaimer),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp),
                tint = lastMetroPrimary
            )

            Text(
                text = stringResource(Res.string.train_timings_disclaimer),
                style = MaterialTheme.typography.h5.copy(
                    fontFamily = interFont,
                    fontSize = 10.sp,
                    lineHeight = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = lastMetroPrimary
                )
            )

        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onCheckAnotherRoute,
            border = BorderStroke(
                width = 0.4.dp,
                color = subHeadingTitle
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
                    style = MaterialTheme.typography.h5.copy(
                        fontFamily = interFont,
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
                subHeadingTitle,
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
                style = MaterialTheme.typography.h5.copy(
                    fontFamily = interFont,
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
                style = MaterialTheme.typography.h5.copy(
                    fontFamily = interFont,
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            )

        }

        Text(
            text = stringResource(Res.string.daily_services),
            style = MaterialTheme.typography.h5.copy(
                fontFamily = interFont,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight.Normal,
                color = subHeadingTitle,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

    }

}
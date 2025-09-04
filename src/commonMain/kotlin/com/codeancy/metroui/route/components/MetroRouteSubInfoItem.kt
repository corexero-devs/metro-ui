package com.codeancy.metroui.route.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeancy.metroui.common.components.ComponentCard
import com.codeancy.metroui.common.utils.MetroUiColor
import indianmetro.metroui.generated.resources.Res
import indianmetro.metroui.generated.resources.fare
import indianmetro.metroui.generated.resources.interchanges
import indianmetro.metroui.generated.resources.metro
import indianmetro.metroui.generated.resources.runtime
import indianmetro.metroui.generated.resources.stations
import indianmetro.metroui.generated.resources.swap
import indianmetro.metroui.generated.resources.time
import indianmetro.metroui.generated.resources.to
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource


@Composable
fun MetroRouteSubInfo(
    time: Int,
    fare: Int,
    stations: Int,
    interchanges: Int,
    startStationName: String,
    destinationStationName: String,
    isInterChangeRouteOpen: Boolean,
    onToggleRoute: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    ComponentCard(
        modifier = modifier
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                if (time > 0) {
                    MetroRouteSubInfoItem(
                        title = stringResource(Res.string.runtime),
                        imageVector = vectorResource(Res.drawable.time),
                        value = "$time Mins"
                    )

                }

                if (fare > 0) {
                    Spacer(modifier = Modifier.width(8.dp))
                    MetroRouteSubInfoItem(
                        title = stringResource(Res.string.fare),
                        imageVector = vectorResource(Res.drawable.fare),
                        value = "₹ $fare"
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))
                MetroRouteSubInfoItem(
                    title = stringResource(Res.string.stations),
                    imageVector = vectorResource(Res.drawable.metro),
                    value = stations.toString()
                )

                Spacer(modifier = Modifier.width(8.dp))
                MetroRouteSubInfoItem(
                    title = stringResource(Res.string.interchanges),
                    imageVector = vectorResource(Res.drawable.swap),
                    value = interchanges.toString()
                )

            }

            Spacer(modifier = Modifier.height(12.dp))

            SourceAndDestinationTitle(
                sourceStationName = startStationName,
                destinationStationName = destinationStationName,
                modifier = Modifier
                    .fillMaxWidth(),
                value = isInterChangeRouteOpen,
                onToggle = onToggleRoute
            )
        }
    }
}

@Composable
private fun SourceAndDestinationTitle(
    sourceStationName: String,
    destinationStationName: String,
    value: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = sourceStationName,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = stringResource(Res.string.to),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MetroUiColor.onSurface,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                )
            )

            Text(
                text = destinationStationName,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }

        Switch(
            checked = value,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedTrackColor = Color.Transparent,
                uncheckedTrackColor = Color.Transparent,
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.primary,
                checkedBorderColor = MaterialTheme.colorScheme.primary,
                uncheckedBorderColor = MaterialTheme.colorScheme.primary
            ),
        )
    }

}

@Composable
private fun MetroRouteSubInfoItem(
    imageVector: ImageVector,
    title: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.border(
            1.dp,
            MetroUiColor.subHeading,
            RoundedCornerShape(12.dp)
        )
            .padding(12.dp)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(20.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MetroUiColor.onSurface,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MetroUiColor.subHeading,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            ),
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}
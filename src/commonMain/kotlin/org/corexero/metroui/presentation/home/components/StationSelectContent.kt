package org.corexero.metroui.presentation.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import jaipurmetro.metroui.generated.resources.Res
import jaipurmetro.metroui.generated.resources.from_station
import jaipurmetro.metroui.generated.resources.swap
import jaipurmetro.metroui.generated.resources.to_station
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun StationSelectContent(
    source: String,
    destination: String,
    allStations: List<String>,
    onSourceChanged: (String) -> Unit,
    onDestinationChanged: (String) -> Unit,
    onSwap: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            StationTextFieldInput(
                value = source,
                suggestions = allStations,
                onValueChange = {
                    onSourceChanged(it)
                },
                isSource = true,
                hintText = stringResource(Res.string.from_station),
                modifier = Modifier
                    .fillMaxWidth()
            )

            StationTextFieldInput(
                value = destination,
                suggestions = allStations,
                onValueChange = {
                    onDestinationChanged(it)
                },
                isSource = false,
                hintText = stringResource(Res.string.to_station),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Card(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 32.dp, top = 38.dp)
                .padding(vertical = 6.dp),
            shape = CircleShape,
            border = BorderStroke(1.5.dp, Color(0xFFD0D7DE)),
            backgroundColor = Color(0xFFF0F4F8),
            elevation = 2.dp
        ) {
            Box(
                modifier = Modifier
                    .clickable { onSwap() }
                    .size(36.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.swap),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp),
                    tint = Color.Black
                )
            }
        }
    }
}
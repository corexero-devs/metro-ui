package org.corexero.metroui.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jaipurmetro.metroui.generated.resources.Res
import jaipurmetro.metroui.generated.resources.find_route
import jaipurmetro.metroui.generated.resources.plan_your_journey
import org.corexero.metroui.ui.theme.interFont
import org.jetbrains.compose.resources.stringResource

@Composable
fun StationSelectionCard(
    source: String,
    destination: String,
    allStations: List<String>,
    onSourceChanged: (String) -> Unit,
    onDestinationChanged: (String) -> Unit,
    onGetRouteClick: () -> Unit,
    onSwap: () -> Unit,
    modifier: Modifier = Modifier
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 16.dp,
                    horizontal = 16.dp
                )
        ) {

            Text(
                text = stringResource(Res.string.plan_your_journey),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            StationSelectContent(
                source = source,
                destination = destination,
                allStations = allStations,
                onSourceChanged = onSourceChanged,
                onDestinationChanged = onDestinationChanged,
                onSwap = onSwap,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    keyboardController?.hide()
                    onGetRouteClick()
                },
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(
                    vertical = 12.dp
                )
            ) {
                Text(
                    text = stringResource(Res.string.find_route),
                    style = TextStyle(
                        fontFamily = interFont,
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

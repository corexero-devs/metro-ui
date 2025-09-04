package com.codeancy.metroui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeancy.metroui.common.components.ComponentCard
import com.codeancy.metroui.domain.models.StationUi
import indianmetro.metroui.generated.resources.Res
import indianmetro.metroui.generated.resources.find_route
import indianmetro.metroui.generated.resources.plan_your_journey
import org.jetbrains.compose.resources.stringResource

@Composable
fun StationSelectionCard(
    source: TextFieldValue,
    destination: TextFieldValue,
    allStations: List<StationUi>,
    onSourceChanged: (TextFieldValue) -> Unit,
    onDestinationChanged: (TextFieldValue) -> Unit,
    onSourceStationSelected: (StationUi?) -> Unit,
    onDestinationStationSelected: (StationUi?) -> Unit,
    onGetRouteClick: () -> Unit,
    onSwap: () -> Unit,
    modifier: Modifier = Modifier
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    ComponentCard(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
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
                onSourceStationSelected = onSourceStationSelected,
                onDestinationStationSelected = onDestinationStationSelected,
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
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text(
                    text = stringResource(Res.string.find_route),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

package org.corexero.metroui.presentation.route.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jaipurmetro.metroui.generated.resources.Res
import jaipurmetro.metroui.generated.resources.ic_run
import org.corexero.metroui.ui.theme.interFont
import org.corexero.metroui.ui.theme.subHeadingTitle
import org.corexero.metroui.utils.hexToColor
import org.corexero.metroui.domain.model.StationUi
import org.corexero.metroui.presentation.common.components.MetroEndIcon
import org.corexero.metroui.presentation.common.components.MetroStartIcon
import org.corexero.metroui.presentation.common.components.MetroStopInBetween
import org.jetbrains.compose.resources.vectorResource

@Composable
fun MetroRouteStation(
    stationUi: StationUi,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),

    ) {

        when (stationUi.icon) {
            StationUi.StationIcon.In -> {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MetroStartIcon()
                    Spacer(modifier = Modifier.height(2.dp))
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_run),
                        contentDescription = null,
                        tint = stationUi.colorHex.hexToColor(),
                        modifier = Modifier
                            .size(20.dp)
                            .graphicsLayer {
                                scaleX = 1f
                            }
                    )
                }
            }

            StationUi.StationIcon.Out -> {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_run),
                        contentDescription = null,
                        tint = stationUi.colorHex.hexToColor(),
                        modifier = Modifier
                            .size(20.dp)
                            .graphicsLayer {
                                scaleX = -1f
                            }
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    if (stationUi.isEndStation) {
                        MetroEndIcon()
                    }
                }
            }

            StationUi.StationIcon.Train -> {
                Icon(
                    imageVector =
                        vectorResource(Res.drawable.ic_run),
                    contentDescription = null,
                    tint = stationUi.colorHex.hexToColor(),
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterVertically)
                        .graphicsLayer {
                            scaleX = 1f
                        }
                )
            }
        }


        Box(
            modifier = Modifier
                .padding(start = 32.dp, end = 24.dp)
                .width(1.dp)
                .background(subHeadingTitle)
                .fillMaxHeight()
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(
                4.dp,
                Alignment.CenterVertically
            )
        ) {
            Text(
                text = stationUi.name,
                style = TextStyle(
                    color = Color(0xFF262626),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = interFont
                )
            )
            val descriptionText = stationUi.description?.asString()
            if (descriptionText?.isNotEmpty() == true) {
                Text(
                    text = descriptionText,
                    style = TextStyle(
                        color = if (stationUi.isInterchange) Color.Red else MaterialTheme.colors.secondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = interFont
                    )
                )
            }
            val platformText = stationUi.platform?.asString()
            if (platformText?.isNotEmpty() == true) {
                Text(
                    text = platformText,
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = interFont
                    )
                )
            }
        }

        Text(
            text = "${stationUi.time} Mins",
            style = TextStyle(
                color = Color(0xFF3772d2),
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = interFont
            )
        )
    }
    if (!stationUi.isEndStation) {
        MetroStopInBetween(
            color = stationUi.colorHex.hexToColor()
        )
    }
}
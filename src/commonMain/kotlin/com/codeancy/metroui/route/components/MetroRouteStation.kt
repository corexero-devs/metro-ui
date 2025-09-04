package com.codeancy.metroui.route.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeancy.metroui.common.utils.MetroUiColor
import com.codeancy.metroui.common.utils.hexToColor
import com.codeancy.metroui.domain.models.StationUi
import indianmetro.metroui.generated.resources.Res
import indianmetro.metroui.generated.resources.ic_run
import indianmetro.metroui.generated.resources.metro
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
                        vectorResource(Res.drawable.metro),
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
                .background(MetroUiColor.subHeading)
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
                text = stationUi.name.asString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF262626),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            )
            val descriptionText = stationUi.description?.asString()
            if (descriptionText?.isNotEmpty() == true) {
                Text(
                    text = descriptionText,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (stationUi.isInterchange) Color.Red else MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                )
            }
            val platformText = stationUi.platform?.asString()
            if (platformText?.isNotEmpty() == true) {
                Text(
                    text = platformText,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                )
            }
        }

        Text(
            text = "${stationUi.time} Mins",
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color(0xFF3772d2),
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
    }
    if (!stationUi.isEndStation) {
        MetroStopInBetween(
            color = stationUi.colorHex.hexToColor()
        )
    }
}

@Composable
fun MetroStartIcon() {
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .border(
                2.dp,
                Color.Green,
                CircleShape
            )
    )
}

@Composable
fun MetroEndIcon() {
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .border(
                2.dp,
                Color.Red,
                CircleShape
            )
    )
}

@Composable
fun MetroStopInBetween(
    color: Color = Color.Gray
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Row(
            modifier = Modifier
                .width(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .padding(vertical = 4.dp)
                    .background(color, RoundedCornerShape(20.dp))
                    .width(2.dp)
            )
        }
        Box(
            modifier = Modifier
                .padding(start = 32.dp, end = 24.dp)
                .width(1.dp)
                .background(MetroUiColor.subHeading)
                .fillMaxHeight()
        )
    }
}
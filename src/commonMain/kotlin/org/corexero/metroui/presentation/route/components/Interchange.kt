package org.corexero.metroui.presentation.route.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jaipurmetro.metroui.generated.resources.Res
import jaipurmetro.metroui.generated.resources.hide_all_station
import jaipurmetro.metroui.generated.resources.platform_no
import jaipurmetro.metroui.generated.resources.show_all_station
import jaipurmetro.metroui.generated.resources.towards
import org.corexero.metroui.ui.theme.interFont
import org.corexero.metroui.ui.theme.subHeadingTitle
import org.corexero.metroui.utils.hexToColor
import org.corexero.metroui.domain.model.RouteResultUi
import org.corexero.metroui.presentation.common.components.ComponentCard
import org.jetbrains.compose.resources.stringResource

@Composable
fun InterchangeRoute(
    routeResultUi: RouteResultUi,
    modifier: Modifier = Modifier
) {

    var selectedInterchangeIndex: Int? by remember {
        mutableStateOf(null)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        routeResultUi.interchange.forEachIndexed { index, interchange ->
            if (index != 0) {
                ChangeLine(
                    lineName = interchange.lineName,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Interchange(
                startStationName = interchange.sourceStation.name,
                platformNo = interchange.sourceStation.platformNo,
                stations = interchange.inBetweenStations.map { it.name },
                lineColor = interchange.lineColor.hexToColor(),
                isExpanded = selectedInterchangeIndex == index,
                towardsStationName = interchange.sourceStation.towards,
                destinationStationName = interchange.destinationStation.name,
                modifier = Modifier
                    .fillMaxWidth(),
                onToggle = {
                    selectedInterchangeIndex = if (selectedInterchangeIndex == index) {
                        null
                    } else {
                        index
                    }
                }
            )
        }
    }
}

@Composable
private fun Interchange(
    startStationName: String,
    towardsStationName: String?,
    lineColor: Color,
    platformNo: String?,
    destinationStationName: String,
    isExpanded: Boolean,
    stations: List<String>,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {

    val interChangeModifier = remember {
        Modifier
            .fillMaxHeight()
            .padding(end = 16.dp)
    }

    ComponentCard(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) {

                StartInterChangeLine(
                    lineColor = lineColor,
                    modifier = interChangeModifier,
                )

                Row(
                    modifier = Modifier
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(
                        text = startStationName,
                        style = TextStyle(
                            fontFamily = interFont,
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium,
                        )
                    )

                    AnimatedVisibility(towardsStationName != null) {
                        Text(
                            text = stringResource(Res.string.towards),
                            style = TextStyle(
                                fontFamily = interFont,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = subHeadingTitle.copy(alpha = 0.7f)
                            )
                        )
                    }
                }
            }

            AnimatedVisibility(platformNo != null && towardsStationName != null) {
                requireNotNull(towardsStationName)
                requireNotNull(platformNo)
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Max)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                ) {

                    InterChangeLine(
                        withDot = false,
                        lineColor = lineColor,
                        modifier = interChangeModifier
                    )

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top,
                    ) {
                        Text(
                            text = stringResource(Res.string.platform_no, platformNo),
                            style = TextStyle(
                                fontFamily = interFont,
                                fontSize = 10.sp,
                                color = lineColor,
                                fontWeight = FontWeight.Medium,
                            )
                        )
                        Text(
                            text = towardsStationName,
                            style = TextStyle(
                                fontFamily = interFont,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = subHeadingTitle
                            )
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
            ) {

                InterChangeLine(
                    withDot = false,
                    lineColor = lineColor,
                    modifier = interChangeModifier
                )

                if (stations.isNotEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { onToggle() }
                    ) {

                        Icon(
                            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                        )

                        Text(
                            text = if (isExpanded) stringResource(Res.string.hide_all_station) else stringResource(
                                Res.string.show_all_station
                            ),
                            style = TextStyle(
                                fontFamily = interFont,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = subHeadingTitle
                            )
                        )
                    }
                }

            }

            AnimatedVisibility(isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onToggle() }
                ) {
                    stations.forEach { station ->
                        Row(
                            modifier = Modifier
                                .height(IntrinsicSize.Max)
                        ) {

                            InterChangeLine(
                                withDot = true,
                                lineColor = lineColor,
                                modifier = interChangeModifier,
                            )

                            Text(
                                text = station,
                                style = TextStyle(
                                    fontFamily = interFont,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = subHeadingTitle
                                ),
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                            )

                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
            ) {
                InterChangeLine(
                    withDot = false,
                    lineColor = lineColor,
                    modifier = interChangeModifier
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
            ) {

                EndChangeLine(
                    lineColor = lineColor,
                    modifier = interChangeModifier,
                )

                Text(
                    text = destinationStationName,
                    style = TextStyle(
                        fontFamily = interFont,
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                    )
                )

            }
        }

    }
}

@Composable
private fun InterChangeLine(
    lineColor: Color,
    modifier: Modifier = Modifier,
    withDot: Boolean = false,
    alignment: Alignment = Alignment.Center,
) {
    Box(
        modifier = modifier
            .width(12.dp)
            .background(lineColor)
    ) {
        if (withDot) {
            Box(
                modifier = Modifier
                    .align(alignment)
                    .clip(CircleShape)
                    .size(12.dp)
                    .border(
                        1.dp,
                        subHeadingTitle,
                        CircleShape
                    )
                    .background(Color.White)
            )
        }
    }
}

@Composable
private fun StartInterChangeLine(
    lineColor: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(12.dp)
            .clip(
                RoundedCornerShape(
                    topEnd = 1000.dp,
                    topStart = 1000.dp
                )
            )
            .background(lineColor)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .clip(CircleShape)
                .size(12.dp)
                .border(
                    1.dp,
                    subHeadingTitle,
                    CircleShape
                )
                .background(Color.White)
        )
    }
}

@Composable
private fun EndChangeLine(
    lineColor: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(12.dp)
            .clip(
                RoundedCornerShape(
                    bottomEnd = 1000.dp,
                    bottomStart = 1000.dp
                )
            )
            .background(lineColor)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clip(CircleShape)
                .size(12.dp)
                .border(
                    1.dp,
                    subHeadingTitle,
                    CircleShape
                )
                .background(Color.White)
        )
    }
}
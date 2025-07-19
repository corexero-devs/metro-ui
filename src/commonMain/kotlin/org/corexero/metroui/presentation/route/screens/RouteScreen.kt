package org.corexero.metroui.presentation.route.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.corexero.metroui.presentation.route.components.InterchangeRoute
import org.corexero.metroui.presentation.route.components.MetroRouteHeader
import org.corexero.metroui.presentation.route.components.MetroRouteStations
import org.corexero.metroui.presentation.route.components.MetroRouteSubInfo
import org.corexero.metroui.ui.components.ProgressDialog
import org.corexero.metroui.ui.theme.backgroundColor
import org.corexero.metroui.presentation.route.viewmodels.RoutScreenState
import org.corexero.metroui.presentation.route.viewmodels.RouteScreenUiAction

@Composable
fun RouteScreen(
    state: RoutScreenState,
    onBack: () -> Unit,
    onAction: (RouteScreenUiAction) -> Unit,
    onShare: (ImageBitmap) -> Unit,
    showInAppReview: () -> Unit,
) {

    val graphicsLayer = rememberGraphicsLayer()
    val scope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .safeDrawingPadding()
            .background(backgroundColor),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        MetroRouteHeader(
            onBack = onBack,
            onShare = {
                scope.launch {
                    val imageBitmap = graphicsLayer.toImageBitmap()
                    onShare(imageBitmap)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        ProgressDialog(
            state.showProgress,
            onDismissRequest = { onAction(RouteScreenUiAction.GoBack) }
        )

        val routeResultUi = state.routeResultUi ?: return@Column

        var isInterchangeFormatOpen by remember {
            mutableStateOf(true)
        }

        MetroRouteSubInfo(
            time = routeResultUi.interchange.last().destinationStation.time,
            fare = routeResultUi.fare,
            stations = routeResultUi.stations,
            interchanges = routeResultUi.interchanges,
            startStationName = routeResultUi.sourceStation.name,
            destinationStationName = routeResultUi.destinationStation.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            isInterChangeRouteOpen = isInterchangeFormatOpen,
            onToggleRoute = {
                isInterchangeFormatOpen = it
            }
        )

        AnimatedContent(isInterchangeFormatOpen) {
            if (it) {
                InterchangeRoute(
                    routeResultUi = routeResultUi,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .verticalScroll(rememberScrollState())
                        .drawWithContent {
                            graphicsLayer.record {
                                this@drawWithContent.drawContent()
                            }
                            drawLayer(graphicsLayer)
                        }
                )
            } else {
                MetroRouteStations(
                    routeResultUi = routeResultUi,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .verticalScroll(rememberScrollState())
                        .drawWithContent {
                            graphicsLayer.record {
                                this@drawWithContent.drawContent()
                            }
                            drawLayer(graphicsLayer)
                        }
                )
            }
        }

        if (state.showInAppReview) {
            showInAppReview()
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}
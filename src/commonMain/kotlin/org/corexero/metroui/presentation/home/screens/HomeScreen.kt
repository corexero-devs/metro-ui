package org.corexero.metroui.presentation.home.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import jaipurmetro.metroui.generated.resources.Res
import jaipurmetro.metroui.generated.resources.unable_to_open_whatsapp
import org.corexero.metroui.presentation.common.components.StatusBarColor
import org.corexero.metroui.presentation.home.components.Disclaimer
import org.corexero.metroui.presentation.home.components.HomeScreenHeader
import org.corexero.metroui.presentation.home.components.LastMetroTiming
import org.corexero.metroui.presentation.home.components.NearestMetro
import org.corexero.metroui.presentation.home.components.QuickAccess
import org.corexero.metroui.presentation.home.components.RecentRouteResults
import org.corexero.metroui.presentation.home.components.StationSelectionCard
import org.corexero.metroui.presentation.home.utils.HomeScreenState
import org.corexero.jaipurmetro.presentation.home.utils.HomeScreenUiAction
import org.corexero.metroui.ui.components.SnackBar
import org.corexero.metroui.ui.theme.backgroundColor
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    state: HomeScreenState,
    onAction: (HomeScreenUiAction) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .safeDrawingPadding()
            .background(backgroundColor)
            .verticalScroll(rememberScrollState()),
    ) {

        StatusBarColor()
        HomeScreenHeader(
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        StationSelectionCard(
            source = state.sourceString,
            destination = state.destinationString,
            allStations = state.allStation,
            onSourceChanged = {
                onAction(HomeScreenUiAction.ChangeSource(it))
            },
            onDestinationChanged = {
                onAction(HomeScreenUiAction.ChangeDestination(it))
            },
            onGetRouteClick = {
                onAction(
                    HomeScreenUiAction.GetRoute(
                        state.source?.id,
                        state.destination?.id
                    )
                )
                keyboardController?.hide()
            },
            onSwap = {
                onAction(HomeScreenUiAction.SwapSourceAndDestination)
            },
            modifier = Modifier
                .padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val uriHandler = LocalUriHandler.current

        val errorMessage = stringResource(
            Res.string.unable_to_open_whatsapp
        )

        QuickAccess(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) { quickAccess ->
            when (quickAccess) {
                QuickAccess.Map -> {
                    onAction(HomeScreenUiAction.OnMetroMapClick)
                }

                QuickAccess.BOOK_TICKET -> {
                    try {
                        uriHandler.openUri("https://wa.me/+919650855800?text=Hi")
                        onAction(HomeScreenUiAction.ShareOnWhatsApp)
                    } catch (_: Exception) {
                        onAction(
                            HomeScreenUiAction.ShowError(errorMessage)
                        )
                    }
                }

                QuickAccess.NearestMetro -> {
                    onAction(HomeScreenUiAction.OnNearestMetroClick)
                }

                QuickAccess.Timings -> {
                    onAction(HomeScreenUiAction.OnLastMetroClick)
                }
            }
        }

        NearestMetro(
            state = state.nearestMetroState,
            onAction = onAction,
            modifier = Modifier
                .fillMaxWidth()
        )

        LastMetroTiming(
            lastMetroTimingState = state.lastMetroTimingState,
            onAction = onAction,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state.recentRouteResults.isNotEmpty()) {
            RecentRouteResults(
                routeResultsUi = state.recentRouteResults,
                onClick = {
                    onAction(
                        HomeScreenUiAction.GetRecentRoute(
                            it.sourceStation.id,
                            it.destinationStation.id
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Disclaimer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        )

    }

    SnackBar(
        state.showError,
        message = state.errorMessage,
        onDismiss = {
            onAction(HomeScreenUiAction.DismissError)
        },
        backgroundColor = MaterialTheme.colors.error
    )

}
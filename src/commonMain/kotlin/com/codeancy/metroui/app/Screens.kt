package com.codeancy.metroui.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codeancy.metroui.common.utils.IntentUtils
import com.codeancy.metroui.domain.models.LiveLocationUi
import com.codeancy.metroui.home.HomeScreen
import com.codeancy.metroui.home.HomeViewModel
import com.codeancy.metroui.home.utils.HomeScreenUiAction
import com.codeancy.metroui.route.RouteScreen
import com.codeancy.metroui.route.RouteScreenUiAction
import com.codeancy.metroui.route.RouteViewModel
import com.codeancy.metroui.route.utils.RouteScreenRouteUi
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data class HomeScreenRoute(
    val feedback: Boolean = false,
) {

    companion object {
        @Composable
        fun Invoke(
            homeScreenRoute: HomeScreenRoute,
            onNavigateToRouteScreen: (Int, Int, Boolean) -> Unit,
            onNavigateToMapScreen: () -> Unit,
        ) {
            val homeViewModel: HomeViewModel = koinViewModel(
                parameters = {
                    parametersOf(
                        homeScreenRoute
                    )
                }
            )

            HomeScreen(
                state = homeViewModel.homeScreenState.collectAsStateWithLifecycle().value,
                onAction = {
                    homeViewModel.onAction(it)
                    when (it) {
                        is HomeScreenUiAction.GetRoute -> onNavigateToRouteScreen(
                            it.sourceId,
                            it.destId,
                            it.isRecent
                        )

                        is HomeScreenUiAction.OnMetroMapClick -> onNavigateToMapScreen()
                        else -> Unit
                    }
                }
            )
        }
    }

}

@Serializable
data class RouteScreenRoute(
    val sourceId: Int,
    val destId: Int,
    val isRecent: Boolean = false
) {
    companion object {

        @Composable
        fun Invoke(
            routeScreenRoute: RouteScreenRoute,
            showInAppReview: () -> Unit,
            onNavigateUp: () -> Unit,
        ) {

            val intentUtils = koinInject<IntentUtils>()

            val routeViewModel: RouteViewModel = koinViewModel<RouteViewModel>(
                parameters = {
                    parametersOf(routeScreenRoute.toRouteScreenRoute())
                }
            )

            val state by routeViewModel.state.collectAsState()
            val providerState by routeViewModel.providerState.collectAsStateWithLifecycle()
            val liveLocationUi by routeViewModel.liveLocationState.collectAsStateWithLifecycle()


            LaunchedEffect(liveLocationUi) {
                when (liveLocationUi) {
                    LiveLocationUi.NotInMetro -> {
                        routeViewModel.onAction(RouteScreenUiAction.ShowNotInsideMetroError)
                    }

                    else -> Unit
                }
            }

            RouteScreen(
                state = state,
                liveLocationUi = liveLocationUi,
                locationAccess = providerState,
                onAction = {
                    routeViewModel.onAction(it)
                    if (it is RouteScreenUiAction.GoBack) {
                        onNavigateUp()
                    }
                },
                onBack = onNavigateUp,
                showInAppReview = {
                    showInAppReview()
                },
                onShare = {
                    intentUtils.onShareMetroRoute(it)
                },
            )
        }

    }


    fun toRouteScreenRoute(): RouteScreenRouteUi {
        return RouteScreenRouteUi(
            sourceId = sourceId,
            destId = destId,
            isRecent = isRecent
        )
    }

}

@Serializable
data object MapScreenRoute

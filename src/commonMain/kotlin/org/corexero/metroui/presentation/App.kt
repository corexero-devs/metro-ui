package org.corexero.metroui.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.corexero.jaipurmetro.presentation.home.utils.HomeScreenUiAction
import org.corexero.metroui.presentation.home.viewmodels.HomeViewModel
import org.corexero.metroui.ui.MapScreen
import org.corexero.metroui.ui.components.MetroTopAppBar
import org.corexero.metroui.ui.theme.MetroTheme
import org.corexero.metroui.domain.tracker.AnalyticsTracker
import org.corexero.metroui.analytics.FirebaseAnalyticsEvents
import org.corexero.metroui.analytics.FirebaseAnalyticsParams
import org.corexero.metroui.analytics.ScreenName
import org.corexero.metroui.presentation.home.screens.HomeScreen
import org.corexero.metroui.presentation.route.screens.RouteScreen
import org.corexero.metroui.presentation.route.viewmodels.RouteScreenUiAction
import org.corexero.metroui.presentation.route.viewmodels.RouteViewModel
import org.corexero.metroui.ui.MetroDataProvider
import org.corexero.metroui.utils.appVersion
import org.corexero.metroui.utils.platformName
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val metroData = MetroDataProvider.current

    MetroTheme {
        val navController = rememberNavController()
        NavHost(navController, startDestination = "home") {
            composable("home") {
                val homeViewModel: HomeViewModel =
                    viewModel {
                        HomeViewModel(
                            recentSearchRepository = metroData.recentSearchRepository,
                            locationRepository = metroData.locationRepository,
                            stationRepository = metroData.stationRepository,
                            analyticsTracker = metroData.analyticsTracker
                        )
                    }
                HomeScreen(
                    state = homeViewModel.homeScreenState.collectAsStateWithLifecycle().value,
                    onAction = {
                        homeViewModel.onAction(it)
                        when (it) {
                            is HomeScreenUiAction.GetRoute -> {
                                if (it.sourceId != null && it.destId != null) {
                                    navController.navigate("route/${it.sourceId}/${it.destId}/false")
                                }
                            }

                            is HomeScreenUiAction.GetRecentRoute -> {
                                navController.navigate("route/${it.sourceId}/${it.destId}/true")
                            }

                            is HomeScreenUiAction.OnMetroMapClick -> {
                                navController.navigate("map")
                            }

                            else -> {}
                        }
                    }
                )
            }
            composable("route/{sourceId}/{destId}/{fromRecentSearch}") {
                val routeViewModel: RouteViewModel = viewModel {
                    RouteViewModel(
                        savedStateHandle = it.savedStateHandle,
                        recentSearchRepository = metroData.recentSearchRepository,
                        analyticsTracker = metroData.analyticsTracker,
                        preferenceRepository = metroData.preferenceRepository,
                        featureFlagRepository = metroData.featureFlagRepository,
                        routeRepository = metroData.routeRepository,
                    )
                }
                RouteScreen(
                    state = routeViewModel.routeScreenState,
                    onAction = {
                        routeViewModel.onAction(it)
                        if (it is RouteScreenUiAction.GoBack) {
                            navController.popBackStack()
                        }
                    },
                    onBack = {
                        navController.navigateUp()
                    },
                    onShare = {
                        metroData.intentUtils.onShareMetroRoute(it)
                    },
                    showInAppReview = {
                        metroData.intentUtils.showInAppReview()
                    },
                )
            }
            composable("map") {
                Scaffold(
                    topBar = { MetroTopAppBar(onBack = { navController.popBackStack() }) })
                {
                    MapScreen()
                }
            }
        }
    }
    LaunchedEffect(metroData.analyticsTracker) {
        initFirebaseAnalytics(metroData.analyticsTracker)
    }
}

private fun initFirebaseAnalytics(
    analyticsTracker: AnalyticsTracker
) {
    analyticsTracker
        .setGlobalProperties(
            mapOf(
                FirebaseAnalyticsParams.PLATFORM_TYPE to platformName,
                FirebaseAnalyticsParams.APP_VERSION to appVersion
            )
        )
    analyticsTracker.logEvent(
        FirebaseAnalyticsEvents.APP_LAUNCH,
        screenName = ScreenName.HOME_SCREEN
    )
}
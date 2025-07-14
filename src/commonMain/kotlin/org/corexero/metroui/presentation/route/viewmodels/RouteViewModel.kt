package org.corexero.metroui.presentation.route.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.corexero.metroui.domain.model.RouteResultUi
import org.corexero.metroui.domain.repository.FeatureFlagRepository
import org.corexero.metroui.domain.repository.PreferenceRepository
import org.corexero.metroui.domain.repository.RecentSearchRepository
import org.corexero.metroui.domain.repository.RouteRepository
import org.corexero.metroui.domain.tracker.AnalyticsTracker
import org.corexero.metroui.analytics.FirebaseAnalyticsEvents
import org.corexero.metroui.analytics.FirebaseAnalyticsParams
import org.corexero.metroui.analytics.ScreenName
import org.corexero.metroui.utils.currentTimeMillis


data class RoutScreenState(
    val showProgress: Boolean = true,
    val routeResultUi: RouteResultUi? = null,
    val showInAppReview: Boolean = false
)

sealed interface RouteScreenUiAction {
    data object GoBack : RouteScreenUiAction
    data object ShareScreenShot : RouteScreenUiAction
}

class RouteViewModel(
    savedStateHandle: SavedStateHandle,
    private val recentSearchRepository: RecentSearchRepository,
    private val analyticsTracker: AnalyticsTracker,
    private val preferenceRepository: PreferenceRepository,
    private val featureFlagRepository: FeatureFlagRepository,
    private val routeRepository: RouteRepository
) : ViewModel() {

    private val sourceId = savedStateHandle.get<String>("sourceId") ?: ""
    private val destId = savedStateHandle.get<String>("destId") ?: ""
    private val isFromRecentSearch = savedStateHandle.get<Boolean>("fromRecentSearch") == true

    var routeScreenState by mutableStateOf(RoutScreenState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val startTime = currentTimeMillis()
            if (isFromRecentSearch) {
                val recentRouteResult = recentSearchRepository.getRecentSearch(
                    sourceId.toInt(),
                    destId.toInt()
                )
                if (recentRouteResult != null) {
                    routeScreenState = routeScreenState.copy(
                        routeResultUi = recentRouteResult,
                        showProgress = false
                    )
                    if (!recentRouteResult.isPlatFormDataUpdated) {
                        updatePlatFormDetails(recentRouteResult)
                    }
                    return@launch
                }
            }
            val metroRoute = routeRepository.getRoute(sourceId, destId)
                .also { launch { recentSearchRepository.addOrReorder(it) } }
            routeScreenState = routeScreenState.copy(
                routeResultUi = metroRoute,
                showProgress = false
            )
            analyticsTracker.logEvent(
                name = FirebaseAnalyticsEvents.ROUTE_LOAD_TIME,
                screenName = ScreenName.ROUTE_SCREEN,
                params = mapOf(
                    FirebaseAnalyticsParams.SOURCE_ID to sourceId,
                    FirebaseAnalyticsParams.DEST_ID to destId,
                    FirebaseAnalyticsParams.TIME to (currentTimeMillis() - startTime) / 1000
                )
            )
            updatePlatFormDetails(metroRoute)
            //If app review is not show, show the app review
            if (featureFlagRepository.shouldShowInAppReview() &&
                preferenceRepository.shouldShowInAppReview()
            ) {
                delay(5000)
                routeScreenState = routeScreenState.copy(
                    showInAppReview = true
                )
                analyticsTracker.logEvent(
                    name = FirebaseAnalyticsEvents.REVIEW_POP_UP_SHOW,
                    screenName = ScreenName.ROUTE_SCREEN
                )
                preferenceRepository.updateInAppReviewShown()
            }
        }
    }

    private fun updatePlatFormDetails(
        routeResult: RouteResultUi
    ) {
        if (routeResult.isPlatFormDataUpdated) return
        viewModelScope.launch(Dispatchers.IO) {
            val metroRoute = routeRepository.updatePlatForms(routeResult)
                .also { launch { recentSearchRepository.addOrReorder(it) } }
            routeScreenState = routeScreenState.copy(
                routeResultUi = metroRoute,
                showProgress = false
            )
        }
    }

    fun onAction(action: RouteScreenUiAction) = viewModelScope.launch(Dispatchers.IO) {
        when (action) {
            RouteScreenUiAction.ShareScreenShot -> {

            }

            RouteScreenUiAction.GoBack -> {
                routeScreenState = routeScreenState.copy(showProgress = false)
            }

            else -> {}
        }
    }
}
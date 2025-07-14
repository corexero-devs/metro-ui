package org.corexero.jaipurmetro.presentation.home.utils

import org.corexero.metroui.presentation.home.utils.HomeScreenState
import org.corexero.metroui.domain.model.NearestMetroStationUi

sealed interface HomeScreenUiAction {
    data class ChangeSource(val value: String) : HomeScreenUiAction
    data class ChangeDestination(val value: String) : HomeScreenUiAction
    data class GetRoute(val sourceId: Int?, val destId: Int?) : HomeScreenUiAction
    data class GetRecentRoute(val sourceId: Int, val destId: Int) : HomeScreenUiAction
    data object SwapSourceAndDestination : HomeScreenUiAction
    data object DismissProgress : HomeScreenUiAction
    data object DismissError : HomeScreenUiAction
    data object ShareOnWhatsApp : HomeScreenUiAction
    data object OnMetroMapClick : HomeScreenUiAction
    data object OnNearestMetroClick : HomeScreenUiAction
    data object OnDismissNeatestMetro : HomeScreenUiAction

    data object OnLocationPermissionGranted : HomeScreenUiAction

    data object OnLocationPermissionDenied : HomeScreenUiAction

    data class OnSelectNearestMetroStationUi(
        val metroStationUi: NearestMetroStationUi
    ) : HomeScreenUiAction

    data object OnLastMetroClick : HomeScreenUiAction

    data class OnLastMetroChangeSource(
        val stationSelect: HomeScreenState.LastMetroTimingState.StationSelect,
        val value: String
    ) : HomeScreenUiAction

    data class OnLastMetroChangeDestination(
        val stationSelect: HomeScreenState.LastMetroTimingState.StationSelect,
        val value: String
    ) : HomeScreenUiAction

    data object OnLastMetroStationSwap : HomeScreenUiAction

    data object OnGetLastMetroTiming : HomeScreenUiAction

    data object OnDismissLastMetroTiming : HomeScreenUiAction

}

package org.corexero.metroui.presentation.home.utils

import androidx.compose.runtime.Stable
import org.corexero.metroui.domain.model.NearestMetroStationUi
import org.corexero.metroui.domain.model.RouteResultUi
import org.corexero.metroui.domain.model.StationUi

@Stable
data class HomeScreenState(
    val sourceString: String = "",
    val destinationString: String = "",
    val source: StationUi? = null,
    val destination: StationUi? = null,
    val allStation: List<String> = emptyList(),
    val showProgress: Boolean = false,
    val showError: Boolean = false,
    val errorMessage: String = "",
    val recentRouteResults: List<RouteResultUi> = emptyList(),
    val nearestMetroState: NearestMetroState? = null,
    val lastMetroTimingState: LastMetroTimingState? = null
) {

    sealed interface NearestMetroState {
        data object Loading : NearestMetroState
        data object ShowRationale : NearestMetroState
        data class NearestMetroStations(
            val stations: List<NearestMetroStationUi>
        ) : NearestMetroState
    }

    sealed interface LastMetroTimingState {

        data class StationSelect(
            val destination: StationUi? = null,
            val source: StationUi? = null,
            val destinationString: String = "",
            val sourceString: String = "",
            val stations: List<StationUi>
        ) : LastMetroTimingState

        data class Loading(
            val source: StationUi,
            val destination: StationUi,
        ) : LastMetroTimingState

        data class LastMetroTime(
            val source: StationUi,
            val destination: StationUi,
            val firstMetroTime: String,
            val lastMetroTime: String
        ) : LastMetroTimingState

    }

}

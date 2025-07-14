package org.corexero.metroui.presentation.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.corexero.jaipurmetro.presentation.home.utils.HomeScreenUiAction
import org.corexero.metroui.analytics.FirebaseAnalyticsEvents
import org.corexero.metroui.analytics.FirebaseAnalyticsParams
import org.corexero.metroui.analytics.ScreenName
import org.corexero.metroui.domain.model.StationUi
import org.corexero.metroui.domain.repository.LocationRepository
import org.corexero.metroui.domain.repository.RecentSearchRepository
import org.corexero.metroui.domain.repository.StationRepository
import org.corexero.metroui.domain.tracker.AnalyticsTracker
import org.corexero.metroui.domain.utils.runCatchingOrNull
import org.corexero.metroui.presentation.home.utils.HomeScreenState
import org.corexero.metroui.presentation.home.utils.HomeScreenState.LastMetroTimingState.LastMetroTime
import org.corexero.metroui.presentation.home.utils.HomeScreenState.LastMetroTimingState.Loading
import org.corexero.metroui.presentation.home.utils.HomeScreenState.LastMetroTimingState.StationSelect

class HomeViewModel(
    private val recentSearchRepository: RecentSearchRepository,
    private val locationRepository: LocationRepository,
    private val analyticsTracker: AnalyticsTracker,
    private val stationRepository: StationRepository
) : ViewModel() {
    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState

    private lateinit var allStationList: List<StationUi>

    private var nearestMetroJob: Job? = null

    private var lastMetroJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            allStationList = stationRepository.getAllStations()
            _homeScreenState.update {
                it.copy(
                    allStation = allStationList.map { station -> station.name })
            }
        }
        viewModelScope.launch {
            recentSearchRepository.getRecentSearches()
                .flowOn(Dispatchers.IO)
                .collect { routeResults ->
                    _homeScreenState.update {
                        it.copy(recentRouteResults = routeResults)
                    }
                }
        }
    }

    fun onAction(action: HomeScreenUiAction) = viewModelScope.launch(Dispatchers.IO) {
        when (action) {
            is HomeScreenUiAction.ChangeSource -> {
                val station = allStationList.find { station ->
                    station.name == action.value
                }
                _homeScreenState.update {
                    it.copy(
                        sourceString = action.value, source = station
                    )
                }
                station?.let {
                    analyticsTracker.logEvent(
                        name = FirebaseAnalyticsEvents.SOURCE_SELECT,
                        screenName = ScreenName.HOME_SCREEN,
                        params = mapOf(
                            FirebaseAnalyticsParams.SOURCE_ID to station.id,
                            FirebaseAnalyticsParams.SOURCE_NAME to action.value
                        )
                    )
                }
            }

            is HomeScreenUiAction.ChangeDestination -> {
                val station = allStationList.find { station ->
                    station.name == action.value
                }
                _homeScreenState.update {
                    it.copy(
                        destinationString = action.value, destination = station
                    )
                }
                station?.let {
                    analyticsTracker.logEvent(
                        name = FirebaseAnalyticsEvents.DEST_SELECT,
                        screenName = ScreenName.HOME_SCREEN,
                        params = mapOf(
                            FirebaseAnalyticsParams.DEST_ID to station.id,
                            FirebaseAnalyticsParams.DEST_NAME to action.value
                        )
                    )
                }
            }

            is HomeScreenUiAction.GetRoute -> {
                val validateRoute = validateRoute(action.sourceId, action.destId)
                analyticsTracker.logEvent(
                    name = FirebaseAnalyticsEvents.GET_ROUTE,
                    screenName = ScreenName.HOME_SCREEN,
                    params = mapOf(
                        FirebaseAnalyticsParams.SOURCE_ID to action.sourceId,
                        FirebaseAnalyticsParams.DEST_ID to action.destId,
                        FirebaseAnalyticsParams.ERROR to !validateRoute
                    )
                )
            }

            is HomeScreenUiAction.GetRecentRoute -> {
                val validateRoute = validateRoute(action.sourceId, action.destId)
                analyticsTracker.logEvent(
                    name = FirebaseAnalyticsEvents.GET_RECENT_ROUTE,
                    screenName = ScreenName.HOME_SCREEN,
                    params = mapOf(
                        FirebaseAnalyticsParams.SOURCE_ID to action.sourceId,
                        FirebaseAnalyticsParams.DEST_ID to action.destId,
                        FirebaseAnalyticsParams.ERROR to !validateRoute
                    )
                )
            }

            is HomeScreenUiAction.SwapSourceAndDestination -> {
                _homeScreenState.update {
                    val sourceString = it.sourceString
                    val destinationString = it.destinationString
                    val source = it.source
                    val destination = it.destination
                    it.copy(
                        sourceString = destinationString,
                        destinationString = sourceString,
                        source = destination,
                        destination = source
                    )
                }
            }

            is HomeScreenUiAction.DismissProgress -> {
                _homeScreenState.update {
                    it.copy(showProgress = false)
                }
            }

            is HomeScreenUiAction.DismissError -> {
                _homeScreenState.update {
                    it.copy(showError = false)
                }
            }

            is HomeScreenUiAction.ShareOnWhatsApp -> {
                analyticsTracker.logEvent(
                    name = FirebaseAnalyticsEvents.BOOK_TICKET,
                    screenName = ScreenName.HOME_SCREEN,
                    params = mapOf()
                )
            }

            is HomeScreenUiAction.OnMetroMapClick -> {
                analyticsTracker.logEvent(
                    name = FirebaseAnalyticsEvents.METRO_MAP, screenName = ScreenName.HOME_SCREEN
                )
            }

            HomeScreenUiAction.OnNearestMetroClick -> {
                handleNearestMetro()
            }

            HomeScreenUiAction.OnDismissNeatestMetro, HomeScreenUiAction.OnLocationPermissionDenied -> {
                cancelNearestMetro()
            }

            HomeScreenUiAction.OnLocationPermissionGranted -> {
                loadNearestMetroStations()
            }

            is HomeScreenUiAction.OnSelectNearestMetroStationUi -> {
                if (::allStationList.isInitialized) {
                    val station = allStationList.find {
                        it.id == action.metroStationUi.stationId
                    }
                    if (station != null) {
                        _homeScreenState.update {
                            it.copy(
                                source = station,
                                sourceString = station.name,
                                nearestMetroState = null
                            )
                        }
                    }
                }
            }

            HomeScreenUiAction.OnLastMetroClick -> {
                _homeScreenState.update {
                    it.copy(
                        lastMetroTimingState = StationSelect(
                            stations = if (::allStationList.isInitialized) allStationList else emptyList()
                        )
                    )
                }
            }

            HomeScreenUiAction.OnDismissLastMetroTiming -> {
                lastMetroJob?.cancel()
                _homeScreenState.update {
                    it.copy(
                        lastMetroTimingState = null
                    )
                }
            }

            HomeScreenUiAction.OnGetLastMetroTiming -> {
                loadLastMetroTiming()
            }

            is HomeScreenUiAction.OnLastMetroChangeDestination -> {
                val station = allStationList.find { station ->
                    station.name == action.value
                }
                _homeScreenState.update {
                    it.copy(
                        lastMetroTimingState = action.stationSelect.copy(
                            destinationString = action.value,
                            destination = station
                        )
                    )
                }
                station?.let {
                    analyticsTracker.logEvent(
                        name = FirebaseAnalyticsEvents.LAST_METRO_TIME_DEST_SELECT,
                        screenName = ScreenName.HOME_SCREEN,
                        params = mapOf(
                            FirebaseAnalyticsParams.SOURCE_ID to station.id,
                            FirebaseAnalyticsParams.SOURCE_NAME to action.value
                        )
                    )
                }
            }

            is HomeScreenUiAction.OnLastMetroChangeSource -> {
                val station = allStationList.find { station ->
                    station.name == action.value
                }
                _homeScreenState.update {
                    it.copy(
                        lastMetroTimingState = action.stationSelect.copy(
                            sourceString = action.value,
                            source = station
                        )
                    )
                }
                station?.let {
                    analyticsTracker.logEvent(
                        name = FirebaseAnalyticsEvents.LAST_METRO_TIME_SOURCE_SELECT,
                        screenName = ScreenName.HOME_SCREEN,
                        params = mapOf(
                            FirebaseAnalyticsParams.SOURCE_ID to station.name,
                            FirebaseAnalyticsParams.SOURCE_NAME to action.value
                        )
                    )
                }
            }

            HomeScreenUiAction.OnLastMetroStationSwap -> {
                _homeScreenState.update {
                    val metroState = it.lastMetroTimingState
                    if (metroState is StationSelect) {
                        it.copy(
                            lastMetroTimingState = metroState.copy(
                                source = metroState.destination,
                                sourceString = metroState.destinationString,
                                destination = metroState.source,
                                destinationString = metroState.sourceString
                            )
                        )
                    } else {
                        it
                    }
                }
            }
        }
    }

    private fun loadLastMetroTiming() {
        val metroState = _homeScreenState.value.lastMetroTimingState
        if (metroState !is StationSelect
            || metroState.source == null
            || metroState.destination == null
        ) {
            showErrorMessage("Please select source and destination station")
        } else {
            _homeScreenState.update {
                it.copy(
                    lastMetroTimingState = Loading(
                        source = metroState.source,
                        destination = metroState.destination
                    )
                )
            }
            lastMetroJob = viewModelScope.launch(Dispatchers.IO) {
                val metroTime = stationRepository.getFirstAndLastMetroTime(
                    source = metroState.source,
                    destination = metroState.destination
                )
                if (metroTime != null) {
                    _homeScreenState.update {
                        it.copy(
                            lastMetroTimingState = LastMetroTime(
                                source = metroState.source,
                                destination = metroState.destination,
                                firstMetroTime = metroTime.first,
                                lastMetroTime = metroTime.second
                            )
                        )
                    }
                    analyticsTracker.logEvent(
                        name = FirebaseAnalyticsEvents.FIRST_LAST_METRO,
                        screenName = ScreenName.HOME_SCREEN,
                        params = mapOf(
                            FirebaseAnalyticsParams.ERROR to false
                        )
                    )
                } else {
                    analyticsTracker.logEvent(
                        name = FirebaseAnalyticsEvents.FIRST_LAST_METRO,
                        screenName = ScreenName.HOME_SCREEN,
                        params = mapOf(
                            FirebaseAnalyticsParams.ERROR to true
                        )
                    )
                    _homeScreenState.update { it.copy(lastMetroTimingState = null) }
                    showErrorMessage("Sorry, Something went wrong.")
                }
            }
        }
    }

    private fun handleNearestMetro() {
        if (locationRepository.hasLocationPermission()) {
            loadNearestMetroStations()
        } else {
            _homeScreenState.update {
                it.copy(
                    nearestMetroState = HomeScreenState.NearestMetroState.ShowRationale
                )
            }
        }
    }

    private fun loadNearestMetroStations() {
        viewModelScope.launch {
            _homeScreenState.update {
                it.copy(
                    nearestMetroState = HomeScreenState.NearestMetroState.Loading
                )
            }
            nearestMetroJob = launch(Dispatchers.IO) {
                val location = runCatchingOrNull {
                    locationRepository.getLocation()
                }
                if (location != null) {
                    val nearestMetroStations = stationRepository.getNearestMetroStations(location)
                    _homeScreenState.update {
                        it.copy(
                            nearestMetroState = HomeScreenState.NearestMetroState.NearestMetroStations(
                                stations = nearestMetroStations
                            )
                        )
                    }
                } else {
                    _homeScreenState.update {
                        it.copy(
                            nearestMetroState = null
                        )
                    }
                    showErrorMessage("Please turn on your location")
                    locationRepository.openLocationSettings()
                }
            }
        }
    }

    private fun cancelNearestMetro() {
        nearestMetroJob?.cancel()
        _homeScreenState.update {
            it.copy(nearestMetroState = null)
        }
    }

    private fun validateRoute(sourceId: Int?, destinationId: Int?): Boolean {
        if (sourceId == null || destinationId == null) {
            showErrorMessage(
                if (sourceId == null) "Please select a proper Source station"
                else "Please select a proper Destination station"
            )
            return false
        }
        return true
    }

    private fun showErrorMessage(errorMessage: String) {
        _homeScreenState.update {
            it.copy(
                showError = true,
                errorMessage = errorMessage
            )
        }
    }
}
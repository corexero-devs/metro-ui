package org.corexero.metroui.ui

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import org.corexero.metroui.domain.model.MetroBrandConfig
import org.corexero.metroui.domain.utils.IntentUtils
import org.corexero.metroui.domain.repository.FeatureFlagRepository
import org.corexero.metroui.domain.repository.LocationRepository
import org.corexero.metroui.domain.repository.PreferenceRepository
import org.corexero.metroui.domain.repository.RecentSearchRepository
import org.corexero.metroui.domain.repository.RouteRepository
import org.corexero.metroui.domain.repository.StationRepository
import org.corexero.metroui.domain.tracker.AnalyticsTracker


@Stable
data class MetroData(
    val intentUtils: IntentUtils,
    val recentSearchRepository: RecentSearchRepository,
    val locationRepository: LocationRepository,
    val preferenceRepository: PreferenceRepository,
    val featureFlagRepository: FeatureFlagRepository,
    val routeRepository: RouteRepository,
    val analyticsTracker: AnalyticsTracker,
    val stationRepository: StationRepository,
    val metroBrandConfig: MetroBrandConfig
)

val MetroDataProvider = compositionLocalOf<MetroData> { error("No MetroData Provided") }
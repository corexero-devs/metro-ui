package org.corexero.metroui

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import org.corexero.metroui.data.datastore.DataStoreManager
import org.corexero.metroui.data.datastore.MetroDataStoreFactory
import org.corexero.metroui.data.repositoryImpl.FirebaseAnalyticsTracker
import org.corexero.metroui.data.repositoryImpl.FirebaseFeatureFlag
import org.corexero.metroui.data.repositoryImpl.LocationRepositoryImpl
import org.corexero.metroui.data.repositoryImpl.PreferenceRepositoryImpl
import org.corexero.metroui.data.repositoryImpl.RecentSearchRepositoryImpl
import org.corexero.metroui.domain.model.MetroBrandConfig
import org.corexero.metroui.domain.repository.RouteRepository
import org.corexero.metroui.domain.repository.StationRepository
import org.corexero.metroui.domain.utils.IntentUtils
import org.corexero.metroui.presentation.App
import org.corexero.metroui.ui.MetroData
import org.corexero.metroui.ui.MetroDataProvider

open class BaseMetroActivity : ComponentActivity() {

    private val intentUtils by lazy {
        IntentUtils(this)
    }

    private val dataStoreManager by lazy {
        DataStoreManager(MetroDataStoreFactory(applicationContext))
    }

    private val recentSearchRepository by lazy {
        RecentSearchRepositoryImpl(dataStoreManager)
    }

    private val locationRepository by lazy {
        LocationRepositoryImpl(this)
    }

    private val preferenceRepository by lazy {
        PreferenceRepositoryImpl(dataStoreManager)
    }

    private val firebaseAnalyticsTracker by lazy {
        FirebaseAnalyticsTracker()
    }

    @Composable
    fun setMetroContent(
        stationRepository: StationRepository,
        routeRepository: RouteRepository,
        metroBrandConfig: MetroBrandConfig
    ) {
        val metroData = remember {
            getMetroData(stationRepository, routeRepository, metroBrandConfig)
        }
        CompositionLocalProvider(MetroDataProvider provides metroData) {
            App()
        }
    }

    private fun getMetroData(
        stationsRepository: StationRepository,
        routeRepository: RouteRepository,
        metroBrandConfig: MetroBrandConfig
    ): MetroData {
        return MetroData(
            intentUtils = intentUtils,
            recentSearchRepository = recentSearchRepository,
            locationRepository = locationRepository,
            featureFlagRepository = FirebaseFeatureFlag,
            analyticsTracker = firebaseAnalyticsTracker,
            preferenceRepository = preferenceRepository,
            stationRepository = stationsRepository,
            routeRepository = routeRepository,
            metroBrandConfig = metroBrandConfig
        )
    }

}
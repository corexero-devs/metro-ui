package org.corexero.metroui

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.window.ComposeUIViewController
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
import platform.UIKit.UIViewController

fun MetroUiViewController(
    stationRepository: StationRepository,
    routeRepository: RouteRepository,
    metroBrandConfig: MetroBrandConfig
): UIViewController =
    ComposeUIViewController {
        val localUIViewController = LocalUIViewController.current
        val intentUtils = remember { IntentUtils(localUIViewController) }
        val dataStoreManager = remember { DataStoreManager(MetroDataStoreFactory()) }
        val recentSearchRepository = remember { RecentSearchRepositoryImpl(dataStoreManager) }
        val locationRepository = remember { LocationRepositoryImpl() }
        val preferenceRepository = remember { PreferenceRepositoryImpl(dataStoreManager) }
        val metroData = remember {
            MetroData(
                intentUtils = intentUtils,
                recentSearchRepository = recentSearchRepository,
                locationRepository = locationRepository,
                featureFlagRepository = FirebaseFeatureFlag,
                analyticsTracker = FirebaseAnalyticsTracker,
                preferenceRepository = preferenceRepository,
                stationRepository = stationRepository,
                routeRepository = routeRepository,
                metroBrandConfig = metroBrandConfig
            )
        }
        CompositionLocalProvider(
            MetroDataProvider provides metroData
        ) {
            App()
        }
    }
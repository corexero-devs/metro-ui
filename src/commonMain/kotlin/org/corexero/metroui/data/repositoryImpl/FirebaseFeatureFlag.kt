package org.corexero.metroui.data.repositoryImpl

import org.corexero.metroui.data.repositoryImpl.firebase.ConfigKey
import org.corexero.metroui.data.repositoryImpl.firebase.FirebaseRemoteConfigs
import org.corexero.metroui.domain.repository.FeatureFlagRepository

object FirebaseFeatureFlag : FeatureFlagRepository {
    override fun shouldShowInAppReview(): Boolean {
        return FirebaseRemoteConfigs.getBoolean(ConfigKey.EnableInAppReview)
    }
}
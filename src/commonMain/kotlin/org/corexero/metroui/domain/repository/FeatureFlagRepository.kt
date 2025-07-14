package org.corexero.metroui.domain.repository

interface FeatureFlagRepository {

    fun shouldShowInAppReview(): Boolean

}
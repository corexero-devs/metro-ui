package org.corexero.metroui.domain.repository

interface PreferenceRepository {

    suspend fun shouldShowInAppReview(): Boolean

    suspend fun updateInAppReviewShown()

}
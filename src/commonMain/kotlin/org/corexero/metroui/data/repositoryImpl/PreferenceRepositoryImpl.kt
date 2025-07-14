package org.corexero.metroui.data.repositoryImpl

import org.corexero.metroui.data.datastore.DataStoreKey
import org.corexero.metroui.data.datastore.DataStoreManager
import org.corexero.metroui.domain.repository.PreferenceRepository

class PreferenceRepositoryImpl(
    private val dataStoreManager: DataStoreManager
) : PreferenceRepository {

    override suspend fun shouldShowInAppReview(): Boolean {
        return FirebaseFeatureFlag.shouldShowInAppReview() &&
                dataStoreManager.getFirst(DataStoreKey.ShowInAppReview)
    }

    override suspend fun updateInAppReviewShown() {
        dataStoreManager.put(DataStoreKey.ShowInAppReview, false)
    }

}
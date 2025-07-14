package org.corexero.metroui.data.repositoryImpl

import org.corexero.metroui.domain.model.Location
import org.corexero.metroui.domain.repository.LocationRepository

expect class LocationRepositoryImpl : LocationRepository {
    override fun hasLocationPermission(): Boolean
    override suspend fun getLocation(): Location?
    override fun openLocationSettings()
}
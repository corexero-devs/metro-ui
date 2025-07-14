package org.corexero.metroui.domain.repository

import org.corexero.metroui.domain.model.Location

interface LocationRepository {

    fun hasLocationPermission(): Boolean

    suspend fun getLocation(): Location?

    fun openLocationSettings()

}
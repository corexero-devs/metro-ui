package org.corexero.metroui.domain.repository

import org.corexero.metroui.domain.model.Location
import org.corexero.metroui.domain.model.StationUi
import org.corexero.metroui.domain.model.NearestMetroStationUi

interface StationRepository {

    fun getAllStations(): List<StationUi>

    fun getNearestMetroStations(
        location: Location
    ): List<NearestMetroStationUi>

    fun getFirstAndLastMetroTime(
        source: StationUi,
        destination: StationUi
    ): Pair<String, String>?

}
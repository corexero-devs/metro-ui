package com.codeancy.metroui.domain.repository

import com.codeancy.metroui.domain.models.LocationUi
import com.codeancy.metroui.domain.models.NearestMetroStationUi
import com.codeancy.metroui.domain.models.StationUi

interface StationRepository {

    fun getAllStations(): List<StationUi>

    fun getNearestMetroStations(
        locationUi: LocationUi
    ): List<NearestMetroStationUi>

    fun getFirstAndLastMetroTime(
        source: StationUi,
        destination: StationUi
    ): Pair<String, String>?

}
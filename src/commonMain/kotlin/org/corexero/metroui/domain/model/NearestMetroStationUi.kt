package org.corexero.metroui.domain.model

import androidx.compose.runtime.Stable

@Stable
data class NearestMetroStationUi(
    val distanceKm: Double,
    val lineColors: List<String>,
    val stationName: String,
    val stationId: Int,
) {

    fun formatDistance(): String {
        val rounded = (distanceKm * 10).toInt() / 10.0
        return "$rounded km"
    }

    fun toTravelTime(): String {
        val speed = if (distanceKm <= 1.0) 5.0 else 30.0  // km/h
        val timeInMinutes = (distanceKm / speed) * 60
        val rounded = (timeInMinutes * 10).toInt() / 10.0
        return "$rounded mins"
    }
}
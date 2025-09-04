package com.codeancy.metroui.domain.models

import androidx.compose.runtime.Stable


@Stable
data class RouteResultUi(
    val sourceStation: StationUi,
    val destinationStation: StationUi,
    val fare: Int,
    val interchanges: Int,
    val stations: Int,
    val interchange: List<Interchange>,
) {

    @Stable
    data class Interchange(
        val sourceStation: StationUi,
        val destinationStation: StationUi,
        val inBetweenStations: List<StationUi>,
        val lineColor: String,
        val lineName: String
    )

    val formattedFare: String
        get() {
            return if (fare > 0) {
                "₹$fare"
            } else {
                ""
            }
        }

}
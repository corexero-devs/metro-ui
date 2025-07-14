package org.corexero.metroui.domain.model

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable


@Stable
@Serializable
data class RouteResultUi(
    val sourceStation: StationUi,
    val destinationStation: StationUi,
    val fare: Int,
    val interchanges: Int,
    val stations: Int,
    val interchange: List<Interchange>,
    val isPlatFormDataUpdated: Boolean = false
) {

    @Stable
    @Serializable
    data class Interchange(
        val sourceStation: StationUi,
        val destinationStation: StationUi,
        val inBetweenStations: List<StationUi>,
        val lineColor: String,
        val lineName: String,
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
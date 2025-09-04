package com.codeancy.metroui.domain.models

import androidx.compose.runtime.Stable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Stable
sealed interface LiveLocationUi {

    data object Initializing : LiveLocationUi

    @Stable
    data class Location(
        val startStation: LocationInfoedUi,
        val endStation: LocationInfoedUi,
        val fraction: Float,
        val time: Long
    ) : LiveLocationUi

    @Stable
    data object NotInMetro : LiveLocationUi

    @OptIn(ExperimentalTime::class)
    val isLiveUpdated: Boolean
        get() {
            when (this) {
                is Location -> {
                    val currentTime = Clock.System.now().toEpochMilliseconds()
                    val timeDifference = currentTime - time
                    return timeDifference < 10 * 1000
                }

                else -> {
                    return false
                }

            }

        }

}

fun LiveLocationUi.Location.ifExistInThisInterChange(
    interchange: RouteResultUi.Interchange
): LiveLocationUi.Location? {
    return if (
        listOf(interchange.sourceStation)
            .plus(interchange.inBetweenStations)
            .plus(listOf(interchange.destinationStation))
            .map { it.id }
            .zipWithNext()
            .contains(startStation.entity to endStation.entity)
    ) {
        this
    } else {
        null
    }
}
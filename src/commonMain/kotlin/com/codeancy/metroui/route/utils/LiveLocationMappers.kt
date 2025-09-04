package com.codeancy.metroui.route.utils

import com.codeancy.metroui.domain.models.LiveLocationUi
import com.codeancy.metroui.domain.models.LocationInfoedUi
import com.codeancy.metroui.domain.models.LocationUi
import com.codeancy.metroui.domain.models.RouteResultUi
import com.codeancy.metroui.domain.models.StationUi
import org.corexero.sutradhar.location.models.LiveLocation
import org.corexero.sutradhar.location.models.Location
import org.corexero.sutradhar.location.models.LocationInfoed

fun RouteResultUi.toLiveLocationWithId(): List<LocationInfoed> {
    val liveLocations = interchange
        .flatMap {
            listOf(
                it.sourceStation,
            ) + it.inBetweenStations + listOf(it.destinationStation)
        }.map { it.toLiveLocationWithId() }
    return liveLocations
        .mapIndexed { index, liveLocationWithId ->
            if (index == liveLocations.lastIndex) {
                liveLocationWithId
            } else if (liveLocationWithId.entity == liveLocations[index + 1].entity) {
                null
            } else {
                liveLocationWithId
            }
        }.filterNotNull()
}

fun StationUi.toLiveLocationWithId(): LocationInfoed {
    return LocationInfoed(
        location = locationUi.toLocation(),
        entity = id
    )
}

fun Location.toLocationUi() = LocationUi(
    lat = lat,
    long = long
)

fun LocationUi.toLocation() = Location(
    lat = lat,
    long = long
)

fun LiveLocation.toLiveLocationUi(): LiveLocationUi {
    return when (this) {
        LiveLocation.Initializing -> LiveLocationUi.Initializing
        is LiveLocation.Location -> LiveLocationUi.Location(
            startStation = LocationInfoedUi(
                location = this.start.location.toLocationUi(),
                entity = this.start.entity
            ),
            endStation = LocationInfoedUi(
                location = this.end.location.toLocationUi(),
                entity = this.end.entity
            ),
            fraction = this.fraction,
            time = this.time
        )

        LiveLocation.NotInAnySegment -> LiveLocationUi.NotInMetro
    }
}
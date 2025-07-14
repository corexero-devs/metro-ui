package org.corexero.metroui.data.repositoryImpl

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.suspendCancellableCoroutine
import org.corexero.metroui.domain.model.Location
import org.corexero.metroui.domain.repository.LocationRepository
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.coroutines.resume

private class LocationProvider : NSObject(), CLLocationManagerDelegateProtocol {

    private val locationManager = CLLocationManager().apply {
        desiredAccuracy = kCLLocationAccuracyBest
        delegate = this@LocationProvider
    }

    private var onLocationResult: ((Location?) -> Unit)? = null

    fun hasPermission(): Boolean {
        val status = CLLocationManager.authorizationStatus()
        return status == kCLAuthorizationStatusAuthorizedWhenInUse ||
                status == kCLAuthorizationStatusAuthorizedAlways
    }

    fun requestLocation(callback: (Location?) -> Unit) {
        onLocationResult = callback
        locationManager.requestLocation()
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        val clLocation = didUpdateLocations.firstOrNull() as? CLLocation
        val coord = clLocation?.coordinate

        val location = coord?.useContents {
            Location(lat = latitude, long = longitude)
        }

        onLocationResult?.invoke(location)
        onLocationResult = null
    }

    override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
        onLocationResult?.invoke(null)
        onLocationResult = null
    }
}

actual class LocationRepositoryImpl : LocationRepository {

    private val delegate = LocationProvider()

    actual override fun hasLocationPermission(): Boolean {
        return delegate.hasPermission()
    }

    actual override suspend fun getLocation(): Location? {
        return suspendCancellableCoroutine { continuation ->
            if (!hasLocationPermission()) {
                continuation.resume(null)
                return@suspendCancellableCoroutine
            }

            delegate.requestLocation { location ->
                continuation.resume(location)
            }
        }
    }

    actual override fun openLocationSettings() {
        // no - op
    }

}
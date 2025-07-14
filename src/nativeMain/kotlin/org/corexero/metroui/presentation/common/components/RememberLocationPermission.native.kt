package org.corexero.metroui.presentation.common.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.CoreLocation.kCLAuthorizationStatusRestricted
import platform.darwin.NSObject

@Composable
actual fun rememberLocationPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
): Permission {
    val retainedManager = remember {
        val locationManager = CLLocationManager()
        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManager(
                manager: CLLocationManager,
                didChangeAuthorizationStatus: CLAuthorizationStatus
            ) {
                when (manager.authorizationStatus) {
                    kCLAuthorizationStatusAuthorizedWhenInUse,
                    kCLAuthorizationStatusAuthorizedAlways -> onPermissionGranted()

                    kCLAuthorizationStatusDenied,
                    kCLAuthorizationStatusRestricted -> onPermissionDenied()

                    else -> Unit
                }
            }
        }
        locationManager.delegate = delegate

        // Wrap both in a retained holder
        object {
            val manager = locationManager
        }
    }

    return remember {
        object : Permission {
            override fun request() {
                val status = CLLocationManager.authorizationStatus()
                when (status) {
                    kCLAuthorizationStatusAuthorizedWhenInUse,
                    kCLAuthorizationStatusAuthorizedAlways -> onPermissionGranted()

                    kCLAuthorizationStatusNotDetermined -> retainedManager.manager.requestWhenInUseAuthorization()
                    else -> onPermissionDenied()
                }
            }
        }
    }
}
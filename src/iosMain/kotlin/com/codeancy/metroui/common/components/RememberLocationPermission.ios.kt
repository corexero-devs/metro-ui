package com.codeancy.metroui.common.components

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
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString
import platform.darwin.NSObject
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

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

            override fun hasPermission(): Boolean {
                val status = CLLocationManager.authorizationStatus()
                return status == kCLAuthorizationStatusAuthorizedWhenInUse ||
                        status == kCLAuthorizationStatusAuthorizedAlways
            }

            override fun openSettings() {
                // iOS can only open THIS APP’s settings page (not the global Location Services screen)
                val url = NSURL(string = UIApplicationOpenSettingsURLString)
                dispatch_async(dispatch_get_main_queue()) {
                    UIApplication.sharedApplication.openURL(url)
                }
            }

        }
    }
}

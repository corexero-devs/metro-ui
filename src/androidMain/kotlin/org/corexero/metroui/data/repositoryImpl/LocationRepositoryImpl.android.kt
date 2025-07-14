package org.corexero.metroui.data.repositoryImpl

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import org.corexero.metroui.domain.model.Location
import org.corexero.metroui.domain.repository.LocationRepository

actual class LocationRepositoryImpl(
    private val context: Context
) : LocationRepository {

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }


    actual override fun hasLocationPermission(): Boolean {
        return (context.checkSelfPermission(
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) || (context.checkSelfPermission(
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    @Suppress("MissingPermission")
    actual override suspend fun getLocation(): Location? {
        return suspendCancellableCoroutine { cont ->

            val currentLocationRequest = CurrentLocationRequest
                .Builder()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                .build()

            val cancellationTokenSource = CancellationTokenSource()

            fusedLocationClient.getCurrentLocation(
                currentLocationRequest,
                cancellationTokenSource.token
            ).addOnSuccessListener { location ->
                if (location != null) {
                    cont.resumeWith(
                        Result.success(
                            Location(
                                lat = location.latitude,
                                long = location.longitude
                            )
                        )
                    )
                } else {
                    cont.resumeWith(Result.success(null))
                }
            }.addOnFailureListener {
                cont.resumeWith(Result.success(null))
            }

            cont.invokeOnCancellation { cancellationTokenSource.cancel() }
        }
    }

    actual override fun openLocationSettings() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGpsEnabled && !isNetworkEnabled) {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

}
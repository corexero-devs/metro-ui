package com.codeancy.metroui.route.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeancy.metroui.common.components.ComponentCard
import com.codeancy.metroui.common.components.PermissionRationale
import com.codeancy.metroui.common.components.rememberLocationPermission
import com.codeancy.metroui.common.utils.MetroUiColor
import com.codeancy.metroui.firebase.AnalyticsEvents
import com.codeancy.metroui.firebase.ScreenName
import com.codeancy.metroui.firebase.logEvent
import indianmetro.metroui.generated.resources.Res
import indianmetro.metroui.generated.resources.allow_access_to_your_location_metro
import indianmetro.metroui.generated.resources.allow_access_to_your_location_metro_access
import indianmetro.metroui.generated.resources.allow_location_access_in_android
import indianmetro.metroui.generated.resources.enable_location
import indianmetro.metroui.generated.resources.live_location
import indianmetro.metroui.generated.resources.live_location_hint
import indianmetro.metroui.generated.resources.live_metro
import indianmetro.metroui.generated.resources.live_metro_location
import indianmetro.metroui.generated.resources.nearest_metro_location
import indianmetro.metroui.generated.resources.status_off
import indianmetro.metroui.generated.resources.status_on
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.corexero.sutradhar.analytics.FirebaseAnalyticsTracker
import org.corexero.sutradhar.location.models.LocationProviderStatus
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun LiveLocationSettingRow(
    checked: Boolean,
    locationAccess: LocationProviderStatus?,
    hasLocation: Boolean,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope { Dispatchers.Default }

    val title = stringResource(Res.string.live_location)
    val status =
        if (checked) stringResource(Res.string.status_on) else stringResource(Res.string.status_off)
    val hint = stringResource(Res.string.live_location_hint)
    var shouldShowRationale by remember {
        mutableStateOf(false)
    }

    val locationPermission = rememberLocationPermission(
        onPermissionGranted = {
            shouldShowRationale = false
            onCheckedChange(true)
        },
        onPermissionDenied = {
            scope.launch {
                FirebaseAnalyticsTracker.logEvent(
                    eventName = AnalyticsEvents.LIVE_LOCATION_PERMISSION_DENIED,
                    screenName = ScreenName.ROUTE_SCREEN
                )
            }
            shouldShowRationale = false
        }
    )

    if (shouldShowRationale) {
        PermissionRationale(
            title = stringResource(Res.string.live_metro),
            icon = vectorResource(Res.drawable.nearest_metro_location),
            subTitle = stringResource(Res.string.live_metro_location),
            description = stringResource(Res.string.allow_access_to_your_location_metro),
            ctaText = stringResource(Res.string.allow_location_access_in_android),
            onRequestPermission = {
                locationPermission.request()
            },
            onDismiss = {
                shouldShowRationale = false
            }
        )
    }

    when (locationAccess) {
        LocationProviderStatus.NoProviderEnabled -> {
            if (checked) {
                PermissionRationale(
                    title = stringResource(Res.string.live_metro),
                    icon = vectorResource(Res.drawable.nearest_metro_location),
                    subTitle = stringResource(Res.string.live_metro_location),
                    description = stringResource(Res.string.allow_access_to_your_location_metro_access),
                    ctaText = stringResource(Res.string.enable_location),
                    onRequestPermission = {
                        locationPermission.openSettings()
                    },
                    onDismiss = {
                        onCheckedChange(false)
                    }
                )
            }
        }

        else -> Unit
    }

    ComponentCard(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LiveLocation(
                        isLiveUpdate = checked,
                        hasLocation = hasLocation,
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            color = MetroUiColor.onSurface,
                            fontWeight = FontWeight.Medium,
                        )
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        status,
                        color = MetroUiColor.subHeading
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = hint,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        color = MetroUiColor.subHeading,
                        fontWeight = FontWeight.Medium,
                    )
                )
            }
            Switch(
                checked = checked,
                enabled = enabled,
                onCheckedChange = {
                    if (it) {
                        if (!locationPermission.hasPermission()) {
                            shouldShowRationale = true
                        } else {
                            onCheckedChange(true)
                        }
                    } else {
                        onCheckedChange(false)
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = Color.Transparent,
                    uncheckedTrackColor = Color.Transparent,
                    checkedBorderColor = MaterialTheme.colorScheme.primary,
                    uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.primary
                ),
            )
        }
    }
}

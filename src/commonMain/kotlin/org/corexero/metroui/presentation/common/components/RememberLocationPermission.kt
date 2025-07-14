package org.corexero.metroui.presentation.common.components

import androidx.compose.runtime.Composable

interface Permission {
    fun request()
}

@Composable
expect fun rememberLocationPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
): Permission
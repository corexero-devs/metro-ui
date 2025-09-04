package com.codeancy.metroui.common.components

import androidx.compose.runtime.Composable

interface Permission {
    fun request()

    fun openSettings()

    fun hasPermission(): Boolean

}

@Composable
expect fun rememberLocationPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
): Permission
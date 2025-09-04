package com.codeancy.metroui.domain.models

import androidx.compose.runtime.Stable

@Stable
data class AppUpdateUi(
    val isHardUpdate: Boolean,
    val appUrl: String,
    val newVersion: Int
)
package com.codeancy.metroui.data.dtos

import kotlinx.serialization.Serializable

@Serializable
data class AppUpdateInfoDto(
    val isHardUpdate: Boolean,
    val newVersionCode: Int,
    val androidAppUrl: String,
    val iosAppUrl: String
)
package com.codeancy.metroui.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class LocationUi(
    val lat: Double,
    val long: Double
)
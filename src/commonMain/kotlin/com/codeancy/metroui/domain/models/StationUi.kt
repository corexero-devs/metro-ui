package com.codeancy.metroui.domain.models

import androidx.compose.runtime.Stable
import com.codeancy.metroui.common.utils.UiText

@Stable
data class StationUi(
    val id: Long,
    val name: UiText.DynamicString,
    val description: UiText?,
    val platform: UiText?,
    val time: Long,
    val colorHex: String,
    val lineName: String,
    val platformNo: String? = null,
    val towards: String? = null,
    val code: String? = null,
    val locationUi: LocationUi
) {

    enum class StationIcon {
        In,
        Out,
        Train
    }

    enum class StationType {
        Regular,
        Interchange,
        Start,
        End
    }

}
package com.codeancy.metroui.domain.models

import androidx.compose.runtime.Stable
import com.codeancy.metroui.common.utils.UiText

@Stable
data class StationUi(
    val id: Long,
    val name: UiText.DynamicString,
    val icon: StationIcon,
    val description: UiText?,
    val platform: UiText?,
    val time: Int,
    val colorHex: String,
    val isInterchange: Boolean,
    val isFirstStation: Boolean,
    val isEndStation: Boolean,
    val lineName: String,
    val platformNo: Int? = null,
    val towards: String? = null,
    val code: String? = null,
    val locationUi: LocationUi
) {

    enum class StationIcon {
        In,
        Out,
        Train
    }

}
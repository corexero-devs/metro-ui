package org.corexero.metroui.domain.model

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable
import org.corexero.metroui.domain.utils.UiText

@Stable
@Serializable
data class StationUi(
    val id: Int,
    val name: String,
    val code:String,
    val icon: StationIcon,
    val description: UiText?,
    val platform: UiText?,
    val time: Int,
    val colorHex: String,
    val isInterchange: Boolean,
    val isFirstStation: Boolean,
    val isEndStation: Boolean,
    val lineName: String,
    val platformNo: String? = null,
    val towards: String? = null
) {

    enum class StationIcon {
        In,
        Out,
        Train
    }

}
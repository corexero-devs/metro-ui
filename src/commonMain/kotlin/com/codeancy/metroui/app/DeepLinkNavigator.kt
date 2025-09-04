package com.codeancy.metroui.app

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.corexero.sutradhar.utils.Logger

class DeepLinkNavigator {

    private val _deepsLinks = MutableStateFlow<Screen?>(null)

    val deepsLinks = _deepsLinks

    fun navigate(screen: Screen) {
        Logger.debug(TAG, "Navigating to $screen")
        _deepsLinks.update {
            screen
        }
    }

    companion object {
        private const val TAG = "DeepLinkNavigator"
    }

}

sealed interface Screen {
    data object Home : Screen
    data class Route(
        val sourceId: Long,
        val destId: Long,
    ) : Screen

    data object Map : Screen

    data object AskForFeedback : Screen

    data object RateUs : Screen
}

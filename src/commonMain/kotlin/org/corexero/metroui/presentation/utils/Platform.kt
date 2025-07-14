package org.corexero.metroui.presentation.utils

enum class Platform {
    Android,
    Ios
}

expect fun getPlatform(): Platform
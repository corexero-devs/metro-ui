package org.corexero.metroui.data.repositoryImpl.firebase

sealed class ConfigKey<T>(val key: String, val defaultValue: T) {
    data object EnableInAppReview : ConfigKey<Boolean>(
        key = "ENABLE_IN_APP_REVIEW",
        defaultValue = true
    )

    data object EnableNewUi : ConfigKey<Boolean>(
        key = "ENABLE_NEW_UI",
        defaultValue = true
    )
}
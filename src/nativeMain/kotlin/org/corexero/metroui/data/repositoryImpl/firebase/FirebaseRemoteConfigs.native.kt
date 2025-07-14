package org.corexero.metroui.data.repositoryImpl.firebase

actual object FirebaseRemoteConfigs {

    private var firebaseRemoteConfig: FirebaseRemoteConfigs? = null

    fun setUpFirebaseRemoteConfig(firebaseRemoteConfigs: FirebaseRemoteConfigs) {
        firebaseRemoteConfig = firebaseRemoteConfigs
    }

    private val defaults = mutableMapOf<String, Any>(
        ConfigKey.EnableInAppReview.key to ConfigKey.EnableInAppReview.defaultValue,
        ConfigKey.EnableNewUi.key to ConfigKey.EnableNewUi.defaultValue
    )

    actual fun fetchConfig() {
        firebaseRemoteConfig?.fetchConfig()
    }

    actual fun getString(configKey: ConfigKey<String>): String {
        return firebaseRemoteConfig?.getString(configKey) ?: ""
    }

    actual fun getLong(configKey: ConfigKey<Long>): Long {
        return firebaseRemoteConfig?.getLong(configKey) ?: 0L
    }

    actual fun getBoolean(configKey: ConfigKey<Boolean>): Boolean {
        return firebaseRemoteConfig?.getBoolean(configKey) ?: false
    }

}
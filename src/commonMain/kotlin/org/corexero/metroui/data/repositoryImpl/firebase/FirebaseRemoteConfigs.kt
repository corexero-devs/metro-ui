package org.corexero.metroui.data.repositoryImpl.firebase

expect object FirebaseRemoteConfigs {
    fun fetchConfig()

    fun getString(configKey: ConfigKey<String>): String

    fun getBoolean(configKey: ConfigKey<Boolean>): Boolean

    fun getLong(configKey: ConfigKey<Long>): Long
}
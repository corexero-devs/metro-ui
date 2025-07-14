package org.corexero.metroui.data.repositoryImpl.firebase

import android.util.Log
import com.google.firebase.ktx.BuildConfig
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import java.util.concurrent.TimeUnit

actual object FirebaseRemoteConfigs {
    private val defaults = mutableMapOf<String, Any>(
        ConfigKey.EnableInAppReview.key to ConfigKey.EnableInAppReview.defaultValue,
        ConfigKey.EnableNewUi.key to ConfigKey.EnableNewUi.defaultValue
    )

    actual fun fetchConfig() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) {
                //fetch every ten seconds in debug mode
                10
            } else {
                //fetch every 3 hours in production mode
                TimeUnit.HOURS.toSeconds(3)
            }
        }
        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(defaults)
            fetchAndActivate().addOnCompleteListener {
                Log.d("FirebaseRemoteConfigs", "RemoteConfig Fetch complete ")
            }
        }
    }

    actual fun getString(configKey: ConfigKey<String>): String {
        return Firebase.remoteConfig.getString(configKey.key)
    }

    actual fun getLong(configKey: ConfigKey<Long>): Long {
        return Firebase.remoteConfig.getLong(configKey.key)
    }

    actual fun getBoolean(configKey: ConfigKey<Boolean>): Boolean {
        return Firebase.remoteConfig.getBoolean(configKey.key)
    }
}
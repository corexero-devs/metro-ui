package org.corexero.metroui.data.repositoryImpl

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import org.corexero.metroui.analytics.ScreenName
import org.corexero.metroui.domain.tracker.AnalyticsTracker

actual class FirebaseAnalyticsTracker : AnalyticsTracker {

    private val firebaseAnalytics = Firebase.analytics

    actual override fun logEvent(
        name: String,
        screenName: ScreenName,
        params: Map<String, Any?>?
    ) {
        val allParams = params?.toMutableMap() ?: mutableMapOf()
        allParams["screen_name"] = screenName.name.lowercase()
        Log.d(TAG, "event name: $name params: $allParams")
        firebaseAnalytics.logEvent(name, allParams.toBundle())
    }

    actual override fun setGlobalProperties(params: Map<String, Any>?) {
        firebaseAnalytics.setDefaultEventParameters(params?.toBundle())
    }

    actual override fun setUserId(id: String) {
        firebaseAnalytics.setUserId(id)
    }

    private fun Map<String, Any?>.toBundle() = Bundle().apply {
        forEach { (key, value) ->
            if (value == null) return@forEach
            when (value::class) {
                String::class -> putString(key, value as String)
                Int::class -> putInt(key, value as Int)
                Long::class -> putLong(key, value as Long)
                Double::class -> putDouble(key, value as Double)
                Boolean::class -> putBoolean(key, value as Boolean)
            }
        }
    }

    companion object {
        private const val TAG = "FirebaseAnalytics"
    }
}